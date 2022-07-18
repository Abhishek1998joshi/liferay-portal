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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class LayoutActionsDisplayContext {

	public LayoutActionsDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getDropdownItems() {
		Layout layout = _getLayout();

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _isShowConfigureAction(layout),
						dropdownItem -> {
							dropdownItem.setHref(
								_getConfigureLayoutURL(layout));

							dropdownItem.setIcon("cog");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "configure"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private String _getConfigureLayoutURL(Layout layout) {
		String currentURL = PortalUtil.getCurrentURL(_httpServletRequest);

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).setRedirect(
			currentURL
		).setBackURL(
			currentURL
		).setParameter(
			"groupId", layout.getGroupId()
		).setParameter(
			"privateLayout", layout.isPrivateLayout()
		).setParameter(
			"selPlid", layout.getPlid()
		).buildPortletURL(
		).toString();
	}

	private Layout _getLayout() {
		Layout layout = _themeDisplay.getLayout();

		if (layout.isDraftLayout()) {
			layout = LayoutLocalServiceUtil.fetchLayout(layout.getClassPK());
		}

		return layout;
	}

	private boolean _isShowConfigureAction(Layout layout)
		throws PortalException {

		return LayoutPermissionUtil.containsLayoutUpdatePermission(
			_themeDisplay.getPermissionChecker(), layout);
	}

	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}