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

package com.liferay.document.library.external.video.internal.provider;

import com.liferay.document.library.external.video.internal.DLExternalVideo;
import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;

import java.net.HttpURLConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "type=" + EditorEmbedProviderTypeConstants.VIDEO,
	service = {DLExternalVideoProvider.class, EditorEmbedProvider.class}
)
public class YouTubeDLExternalVideoProvider
	implements DLExternalVideoProvider, EditorEmbedProvider {

	@Override
	public DLExternalVideo getDLExternalVideo(String url) {
		Matcher matcher = _urlPattern.matcher(url);

		if (!matcher.matches()) {
			return null;
		}

		try {
			Http.Options options = new Http.Options();

			options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
			options.setLocation(
				"https://www.youtube.com/oembed?format=json&url=" + url);

			String responseJSON = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				responseJSON);

			return new DLExternalVideo() {

				@Override
				public String getDescription() {
					return null;
				}

				@Override
				public String getEmbeddableHTML() {
					return jsonObject.getString("html");
				}

				@Override
				public String getIconURL() {
					return _servletContext.getContextPath() +
						"/icons/youtube.png";
				}

				@Override
				public String getTitle() {
					return jsonObject.getString("title");
				}

				@Override
				public String getURL() {
					return url;
				}

			};
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return null;
		}
	}

	@Override
	public String getId() {
		return "youtube";
	}

	@Override
	public String getTpl() {
		return StringBundler.concat(
			"<iframe allow=\"autoplay; encrypted-media\" allowfullscreen ",
			"height=\"315\" frameborder=\"0\" ",
			"src=\"https://www.youtube.com/embed/{embedId}?rel=0\" ",
			"width=\"560\"></iframe>");
	}

	@Override
	public String[] getURLSchemes() {
		return new String[] {_urlPattern.pattern()};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		YouTubeDLExternalVideoProvider.class);

	private static final Pattern _urlPattern = Pattern.compile(
		"https?:\\/\\/(?:www\\.)?youtube\\.com\\/watch\\?v=(\\S*)$");

	@Reference
	private Http _http;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.external.video)"
	)
	private ServletContext _servletContext;

}