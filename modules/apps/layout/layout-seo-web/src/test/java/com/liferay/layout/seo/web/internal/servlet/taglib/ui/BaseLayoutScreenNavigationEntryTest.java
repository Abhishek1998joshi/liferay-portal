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

package com.liferay.layout.seo.web.internal.servlet.taglib.ui;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class BaseLayoutScreenNavigationEntryTest {

	@Test
	public void testIsVisibleLayoutPrototype() {
		TestLayoutScreenNavigationEntry testLayoutScreenNavigationEntry =
			new TestLayoutScreenNavigationEntry();

		Assert.assertFalse(
			testLayoutScreenNavigationEntry.isVisible(
				Mockito.mock(User.class), _getLayout(LayoutPrototype.class)));
	}

	@Test
	public void testIsVisibleLayoutSetPrototype() {
		TestLayoutScreenNavigationEntry testLayoutScreenNavigationEntry =
			new TestLayoutScreenNavigationEntry();

		Assert.assertFalse(
			testLayoutScreenNavigationEntry.isVisible(
				Mockito.mock(User.class),
				_getLayout(LayoutSetPrototype.class)));
	}

	private Layout _getLayout(Class<?> clazz) {
		Group group = Mockito.mock(Group.class);

		if (LayoutSetPrototype.class.equals(clazz)) {
			Mockito.when(
				group.isLayoutSetPrototype()
			).thenReturn(
				true
			);
		}
		else if (LayoutPrototype.class.equals(clazz)) {
			Mockito.when(
				group.isLayoutPrototype()
			).thenReturn(
				true
			);
		}
		else {
			throw new IllegalArgumentException();
		}

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getGroup()
		).thenReturn(
			group
		);

		return layout;
	}

	private static class TestLayoutScreenNavigationEntry
		extends BaseLayoutScreenNavigationEntry {

		@Override
		public String getEntryKey() {
			return StringUtil.randomString();
		}

		@Override
		protected String getJspPath() {
			return StringUtil.randomString();
		}

	}

}