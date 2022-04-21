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

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayWindowState;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eduardo García
 */
public class CookiesBannerDisplayContext
	extends BaseCookiesBannerDisplayContext {

	public CookiesBannerDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderRequest, renderResponse);
	}

	public Object getConfigurationURL() {
		return PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/cookies_banner_configuration/view.jsp"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getContent(Locale locale) {

		// TODO

		return "";
	}

}