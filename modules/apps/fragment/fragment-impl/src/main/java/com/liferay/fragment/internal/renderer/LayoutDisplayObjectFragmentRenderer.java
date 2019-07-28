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

package com.liferay.fragment.internal.renderer;

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.renderer.InfoItemRenderer;
import com.liferay.info.renderer.InfoItemRendererTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class LayoutDisplayObjectFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "display-page";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "select-content"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						))))
		).toString();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "content");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Object displayObject = _getDisplayObject(httpServletRequest);

		if (displayObject == null) {
			_printPortletMessageInfo(
				httpServletRequest, httpServletResponse,
				"the-rendered-content-will-be-shown-here");

			return;
		}

		Class<?> displayObjectClass = displayObject.getClass();

		List<InfoItemRenderer> infoItemRenderers = _getInfoItemRenderer(
			displayObjectClass);

		if (infoItemRenderers == null) {
			_printPortletMessageInfo(
				httpServletRequest, httpServletResponse,
				"there-are-no-available-renderers-for-the-selected-content");

			return;
		}

		InfoItemRenderer infoItemRenderer = infoItemRenderers.get(0);

		infoItemRenderer.render(
			displayObject, httpServletRequest, httpServletResponse);
	}

	private Object _getDisplayObject(HttpServletRequest httpServletRequest) {
		InfoDisplayObjectProvider infoDisplayObjectProvider =
			(InfoDisplayObjectProvider)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		if (infoDisplayObjectProvider == null) {
			return null;
		}

		return infoDisplayObjectProvider.getDisplayObject();
	}

	private List<InfoItemRenderer> _getInfoItemRenderer(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();

		if (interfaces.length != 0) {
			for (Class<?> anInterface : interfaces) {
				List<InfoItemRenderer> infoItemRenderers =
					_infoItemRendererTracker.getInfoItemRenderers(
						anInterface.getName());

				if (!infoItemRenderers.isEmpty()) {
					return infoItemRenderers;
				}
			}
		}

		Class<?> superclass = clazz.getSuperclass();

		if (superclass != null) {
			return _getInfoItemRenderer(superclass);
		}

		return null;
	}

	private void _printPortletMessageInfo(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String message) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			StringBundler sb = new StringBundler(3);

			sb.append("<div class=\"portlet-msg-info\">");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass());

			sb.append(LanguageUtil.get(resourceBundle, message));

			sb.append("</div>");

			printWriter.write(sb.toString());
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioe, ioe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutDisplayObjectFragmentRenderer.class);

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

}