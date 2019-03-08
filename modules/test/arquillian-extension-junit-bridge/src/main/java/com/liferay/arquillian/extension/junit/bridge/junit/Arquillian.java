/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.arquillian.extension.junit.bridge.junit;

import com.liferay.arquillian.extension.junit.bridge.client.BndBundleUtil;
import com.liferay.arquillian.extension.junit.bridge.client.MBeans;
import com.liferay.arquillian.extension.junit.bridge.jmx.JMXTestRunnerMBean;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;

import java.io.Closeable;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.lang.annotation.Annotation;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.TestClass;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Shuyang Zhou
 */
public class Arquillian extends Runner implements Filterable {

	public Arquillian(Class<?> clazz) {
		_clazz = clazz;

		_testClass = new FilteredSortedTestClass(_clazz, null);
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		_testClass = new FilteredSortedTestClass(_clazz, filter);

		List<FrameworkMethod> frameworkMethods = _testClass.getAnnotatedMethods(
			Test.class);

		if (frameworkMethods.isEmpty()) {
			throw new NoTestsRemainException();
		}
	}

	@Override
	public Description getDescription() {
		return Description.createSuiteDescription(
			_clazz.getName(), _clazz.getAnnotations());
	}

	@Override
	public void run(RunNotifier runNotifier) {
		List<FrameworkMethod> frameworkMethods = new ArrayList<>(
			_testClass.getAnnotatedMethods(Test.class));

		frameworkMethods.removeIf(
			frameworkMethod -> {
				if (frameworkMethod.getAnnotation(Ignore.class) != null) {
					runNotifier.fireTestIgnored(
						Description.createTestDescription(
							_clazz, frameworkMethod.getName(),
							frameworkMethod.getAnnotations()));

					return true;
				}

				return false;
			});

		if (frameworkMethods.isEmpty()) {
			return;
		}

		try (Closeable closeable = _installBundle()) {

			// Enfore client side test class initialization, in case it has
			// static blocks to do preparation setup before server tests start.

			Class.forName(_clazz.getName(), true, _clazz.getClassLoader());

			JMXTestRunnerMBean jmxTestRunnerMBean =
				MBeans.getJmxTestRunnerMBean();

			for (FrameworkMethod frameworkMethod : frameworkMethods) {
				Description description = Description.createTestDescription(
					_clazz, frameworkMethod.getName(),
					frameworkMethod.getAnnotations());

				runNotifier.fireTestStarted(description);

				byte[] data = jmxTestRunnerMBean.runTestMethod(
					_clazz.getName(), frameworkMethod.getName());

				try (InputStream inputStream = new UnsyncByteArrayInputStream(
						data);
					ObjectInputStream oos = new ObjectInputStream(
						inputStream)) {

					Throwable throwable = (Throwable)oos.readObject();

					if (throwable != null) {
						if (throwable instanceof AssumptionViolatedException) {
							runNotifier.fireTestAssumptionFailed(
								new Failure(description, throwable));
						}
						else if (throwable instanceof
									MultipleFailureException) {

							MultipleFailureException mfe =
								(MultipleFailureException)throwable;

							for (Throwable t : mfe.getFailures()) {
								runNotifier.fireTestFailure(
									new Failure(description, t));
							}
						}
						else {
							runNotifier.fireTestFailure(
								new Failure(description, throwable));
						}
					}
				}

				runNotifier.fireTestFinished(description);
			}
		}
		catch (Throwable t) {
			runNotifier.fireTestFailure(new Failure(getDescription(), t));
		}
	}

	private Closeable _installBundle() throws Exception {
		Path path = BndBundleUtil.createBundle();

		URI uri = path.toUri();

		URL url = uri.toURL();

		FrameworkMBean frameworkMBean = MBeans.getFrameworkMBean();

		long bundleId;

		try {
			bundleId = frameworkMBean.installBundleFromURL(
				url.getPath(), url.toExternalForm());
		}
		finally {
			Files.delete(path);
		}

		frameworkMBean.startBundle(bundleId);

		return () -> frameworkMBean.uninstallBundle(bundleId);
	}

	private final Class<?> _clazz;
	private TestClass _testClass;

	private class FilteredSortedTestClass extends TestClass {

		@Override
		protected void scanAnnotatedMembers(
			Map<Class<? extends Annotation>, List<FrameworkMethod>>
				frameworkMethodsMap,
			Map<Class<? extends Annotation>, List<FrameworkField>>
				frameworkFieldsMap) {

			super.scanAnnotatedMembers(frameworkMethodsMap, frameworkFieldsMap);

			_testFrameworkMethods = frameworkMethodsMap.get(Test.class);

			_testFrameworkMethods.sort(
				Comparator.comparing(FrameworkMethod::getName));
		}

		private FilteredSortedTestClass(Class<?> clazz, Filter filter) {
			super(clazz);

			if (filter != null) {
				_testFrameworkMethods.removeIf(
					frameworkMethod -> !filter.shouldRun(
						Description.createTestDescription(
							_clazz, frameworkMethod.getName())));
			}
		}

		private List<FrameworkMethod> _testFrameworkMethods;

	}

}