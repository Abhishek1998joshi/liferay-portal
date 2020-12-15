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

package com.liferay.document.library.video.internal.video.renderer;

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.document.library.video.internal.constants.DLVideoPortletKeys;
import com.liferay.document.library.video.renderer.DLVideoRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLVideoRenderer.class)
public class DLVideoRendererImpl implements DLVideoRenderer {

	@Override
	public String renderHTML(
		FileVersion fileVersion, HttpServletRequest httpServletRequest) {

		DLVideoExternalShortcut dlVideoExternalShortcut =
			_dlVideoExternalShortcutResolver.resolve(fileVersion);

		if (dlVideoExternalShortcut != null) {
			return dlVideoExternalShortcut.renderHTML(httpServletRequest);
		}

		return StringBundler.concat(
			"<iframe class=\"video-preview-dl\" height=\"315\" ",
			"frameborder=\"0\" src=\"",
			_getEmbedVideoURL(fileVersion, httpServletRequest), "&",
			"\" width=\"560\"></iframe>");
	}

	private String _getEmbedVideoURL(
		FileVersion fileVersion, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		PortletURL getEmbedVideoURL =
			requestBackedPortletURLFactory.createRenderURL(
				DLVideoPortletKeys.DL_VIDEO);

		try {
			getEmbedVideoURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
		}

		getEmbedVideoURL.setParameter(
			"mvcRenderCommandName", "/document_library_video/embed_video");
		getEmbedVideoURL.setParameter(
			"fileVersionId", String.valueOf(fileVersion.getFileVersionId()));

		return getEmbedVideoURL.toString();
	}

	@Reference
	private DLVideoExternalShortcutResolver _dlVideoExternalShortcutResolver;

}