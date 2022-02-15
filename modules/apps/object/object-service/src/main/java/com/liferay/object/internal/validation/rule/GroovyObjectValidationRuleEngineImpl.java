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

package com.liferay.object.internal.validation.rule;

import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.Scripting;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ObjectValidationRuleEngine.class)
public class GroovyObjectValidationRuleEngineImpl
	implements ObjectValidationRuleEngine {

	@Override
	public boolean evaluate(
		String expression, Map<String, Object> inputObjects) {

		try {
			_execute(inputObjects, new HashSet<>(), expression);

			return true;
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	@Override
	public String getName() {
		return "groovy";
	}

	private Map<String, Object> _execute(
			Map<String, Object> inputObjects, Set<String> outputObjects,
			String script)
		throws PortalException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Map<String, Object> results = null;

		try {
			currentThread.setContextClassLoader(classLoader);

			results = _scripting.eval(
				null, inputObjects, outputObjects, "groovy", script);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return results;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroovyObjectValidationRuleEngineImpl.class);

	@Reference
	private Scripting _scripting;

}