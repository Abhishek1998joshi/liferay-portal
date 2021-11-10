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

package com.liferay.friendly.url.web.internal.servlet;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.web.internal.util.comparator.FriendlyURLEntryLocalizationComparator;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.friendly.url.web.internal.servlet.FriendlyURLServlet",
		"osgi.http.whiteboard.servlet.pattern=/friendly-url/*",
		"servlet.init.httpMethods=DELETE,GET,HEAD,POST"
	},
	service = Servlet.class
)
public class FriendlyURLServlet extends HttpServlet {

	@Override
	protected void doDelete(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			String className = _getClassName(httpServletRequest);

			InfoItemPermissionProvider infoItemPermissionProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemPermissionProvider.class, className);

			if (!infoItemPermissionProvider.hasPermission(
					PermissionCheckerFactoryUtil.create(
						_portal.getUser(httpServletRequest)),
					new InfoItemReference(
						className, _getClassPK(httpServletRequest)),
					ActionKeys.UPDATE)) {

				_writeJSON(httpServletResponse, JSONUtil.put("success", false));
			}
			else {
				_friendlyURLEntryLocalService.
					deleteFriendlyURLLocalizationEntry(
						_getEntryId(httpServletRequest),
						_getLanguageId(httpServletRequest));

				_writeJSON(httpServletResponse, JSONUtil.put("success", true));
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_writeJSON(httpServletResponse, JSONUtil.put("success", false));
		}
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			User user = _portal.getUser(httpServletRequest);

			if (user.isDefaultUser() ||
				!Objects.equals(
					_getClassName(httpServletRequest),
					Layout.class.getName())) {

				_writeJSON(httpServletResponse, JSONUtil.put("success", false));
			}
			else {
				_writeJSON(
					httpServletResponse,
					_getFriendlyURLEntryLocalizationsJSONObject(
						httpServletRequest));
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_writeJSON(httpServletResponse, JSONUtil.put("success", false));
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			String className = _getClassName(httpServletRequest);
			long classPK = _getClassPK(httpServletRequest);

			InfoItemPermissionProvider infoItemPermissionProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemPermissionProvider.class, className);

			if (!infoItemPermissionProvider.hasPermission(
					PermissionCheckerFactoryUtil.create(
						_portal.getUser(httpServletRequest)),
					new InfoItemReference(className, classPK),
					ActionKeys.UPDATE) ||
				!className.equals(Layout.class.getName())) {

				_writeJSON(httpServletResponse, JSONUtil.put("success", false));
			}
			else {
				String languageId = _getLanguageId(httpServletRequest);

				FriendlyURLEntryLocalization friendlyURLEntryLocalization =
					_friendlyURLEntryLocalService.
						getFriendlyURLEntryLocalization(
							_getEntryId(httpServletRequest), languageId);

				_layoutLocalService.updateFriendlyURL(
					_portal.getUserId(httpServletRequest), classPK,
					friendlyURLEntryLocalization.getUrlTitle(), languageId);

				_writeJSON(httpServletResponse, JSONUtil.put("success", true));
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_writeJSON(httpServletResponse, JSONUtil.put("success", false));
		}
	}

	private String _getClassName(HttpServletRequest httpServletRequest)
		throws PortalException {

		List<String> parts = StringUtil.split(
			httpServletRequest.getPathInfo(), CharPool.SLASH);

		return parts.get(0);
	}

	private long _getClassPK(HttpServletRequest httpServletRequest) {
		List<String> parts = StringUtil.split(
			httpServletRequest.getPathInfo(), CharPool.SLASH);

		return GetterUtil.getLong(parts.get(1));
	}

	private long _getEntryId(HttpServletRequest httpServletRequest) {
		List<String> parts = StringUtil.split(
			httpServletRequest.getPathInfo(), CharPool.SLASH);

		return GetterUtil.getLong(parts.get(2));
	}

	private JSONObject _getFriendlyURLEntryLocalizationsJSONObject(
			HttpServletRequest httpServletRequest)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			_getClassPK(httpServletRequest));

		JSONObject friendlyURLEntryLocalizationsJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (String languageId : layout.getAvailableLanguageIds()) {
			List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
				_friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
					layout.getGroupId(),
					_layoutFriendlyURLEntryHelper.getClassNameId(
						layout.isPrivateLayout()),
					layout.getPlid(), languageId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, _friendlyURLEntryLocalizationComparator);

			String mainUrlTitle = layout.getFriendlyURL(
				LocaleUtil.fromLanguageId(languageId));

			friendlyURLEntryLocalizationsJSONObject.put(
				languageId,
				JSONUtil.put(
					"current",
					JSONUtil.put("urlTitle", _http.decodeURL(mainUrlTitle))
				).put(
					"history",
					_getJSONArray(
						ListUtil.filter(
							friendlyURLEntryLocalizations,
							friendlyURLEntryLocalization -> !Objects.equals(
								friendlyURLEntryLocalization.getUrlTitle(),
								mainUrlTitle)),
						this::_serializeFriendlyURLEntryLocalization)
				).put(
					"success", true
				));
		}

		return friendlyURLEntryLocalizationsJSONObject;
	}

	private <T> JSONArray _getJSONArray(
		List<T> list, Function<T, JSONSerializable> serialize) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		list.forEach(t -> jsonArray.put(serialize.apply(t)));

		return jsonArray;
	}

	private String _getLanguageId(HttpServletRequest httpServletRequest) {
		List<String> parts = StringUtil.split(
			httpServletRequest.getPathInfo(), CharPool.SLASH);

		return parts.get(3);
	}

	private JSONObject _serializeFriendlyURLEntryLocalization(
		FriendlyURLEntryLocalization friendlyEntryLocalization) {

		if (friendlyEntryLocalization == null) {
			return null;
		}

		return JSONUtil.put(
			"friendlyURLEntryId",
			friendlyEntryLocalization.getFriendlyURLEntryId()
		).put(
			"friendlyURLEntryLocalizationId",
			Long.valueOf(
				friendlyEntryLocalization.getFriendlyURLEntryLocalizationId())
		).put(
			"urlTitle", _http.decodeURL(friendlyEntryLocalization.getUrlTitle())
		);
	}

	private void _writeJSON(
			HttpServletResponse httpServletResponse, JSONObject jsonObject)
		throws IOException {

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		if (jsonObject.has("success") && !jsonObject.getBoolean("success")) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		ServletOutputStream servletOutputStream =
			httpServletResponse.getOutputStream();

		servletOutputStream.print(jsonObject.toJSONString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLServlet.class);

	private final FriendlyURLEntryLocalizationComparator
		_friendlyURLEntryLocalizationComparator =
			new FriendlyURLEntryLocalizationComparator();

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Http _http;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}