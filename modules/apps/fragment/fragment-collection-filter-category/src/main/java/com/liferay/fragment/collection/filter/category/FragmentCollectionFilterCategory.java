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

package com.liferay.fragment.collection.filter.category;

import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.category.display.context.FragmentCollectionFilterCategoryDisplayContext;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	immediate = true, property = "fragment.collection.filter.key=category",
	service = FragmentCollectionFilter.class
)
public class FragmentCollectionFilterCategory
	implements FragmentCollectionFilter {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "category");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			httpServletRequest.setAttribute(
				FragmentCollectionFilterCategoryDisplayContext.class.getName(),
				new FragmentCollectionFilterCategoryDisplayContext(
					_fragmentEntryConfigurationParser, fragmentRendererContext,
					httpServletRequest));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/page.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to render collection filter fragment", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionFilterCategory.class);

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.collection.filter.category)"
	)
	private ServletContext _servletContext;

}