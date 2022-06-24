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

package com.liferay.info.internal.request.struts;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormPrincipalException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.internal.request.helper.InfoRequestFieldValuesProviderHelper;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.layout.page.template.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true, property = "path=/portal/add_info_item",
	service = StrutsAction.class
)
public class AddInfoItemStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String className = _portal.getClassName(
			ParamUtil.getLong(originalHttpServletRequest, "classNameId"));
		String formItemId = ParamUtil.getString(
			httpServletRequest, "formItemId");

		String redirect = null;

		try {
			CaptchaUtil.check(httpServletRequest);

			InfoItemCreator<Object> infoItemCreator =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemCreator.class, className);

			if (infoItemCreator == null) {
				throw new InfoFormException();
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			infoItemCreator.createFromInfoItemFieldValues(
				themeDisplay.getScopeGroupId(),
				InfoItemFieldValues.builder(
				).infoFieldValues(
					_infoRequestFieldValuesProviderHelper.getInfoFieldValues(
						httpServletRequest)
				).infoItemReference(
					new InfoItemReference(className, 0)
				).build());

			LayoutStructure layoutStructure = _getLayoutStructure(
				httpServletRequest);

			redirect = _getRedirect(
				_getFormStyledLayoutStructureItem(formItemId, layoutStructure),
				themeDisplay);

			if (redirect == null) {
				SessionMessages.add(originalHttpServletRequest, formItemId);
			}
		}
		catch (CaptchaException captchaException) {
			if (_log.isDebugEnabled()) {
				_log.debug(captchaException);
			}

			SessionErrors.add(
				originalHttpServletRequest, formItemId,
				new InfoFormValidationException.InvalidCaptcha());
		}
		catch (InfoFormValidationException infoFormValidationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormValidationException);
			}

			SessionErrors.add(
				originalHttpServletRequest, formItemId,
				infoFormValidationException);

			if (Validator.isNotNull(
					infoFormValidationException.getInfoFieldUniqueId())) {

				SessionErrors.add(
					originalHttpServletRequest,
					infoFormValidationException.getInfoFieldUniqueId(),
					infoFormValidationException);
			}
		}
		catch (InfoFormException infoFormException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormException);
			}

			SessionErrors.add(
				originalHttpServletRequest, formItemId, infoFormException);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			InfoFormException infoFormException = new InfoFormException();

			if (exception instanceof PrincipalException) {
				infoFormException = new InfoFormPrincipalException();
			}

			SessionErrors.add(
				originalHttpServletRequest, formItemId, infoFormException);
		}

		if (redirect == null) {
			redirect = httpServletRequest.getHeader(HttpHeaders.REFERER);
		}

		httpServletResponse.sendRedirect(redirect);

		return null;
	}

	@Activate
	@Modified
	protected void activate() {
		_infoRequestFieldValuesProviderHelper =
			new InfoRequestFieldValuesProviderHelper(_infoItemServiceTracker);
	}

	private FormStyledLayoutStructureItem _getFormStyledLayoutStructureItem(
			String formItemId, LayoutStructure layoutStructure)
		throws Exception {

		LayoutStructureItem formLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(formItemId);

		if ((formLayoutStructureItem == null) ||
			!(formLayoutStructureItem instanceof
				FormStyledLayoutStructureItem)) {

			throw new InfoFormException();
		}

		return (FormStyledLayoutStructureItem)formLayoutStructureItem;
	}

	private String _getLayoutRedirect(
			JSONObject successMessageJSONObject, ThemeDisplay themeDisplay)
		throws Exception {

		long groupId = successMessageJSONObject.getLong("groupId");
		boolean privateLayout = successMessageJSONObject.getBoolean(
			"privateLayout");
		long layoutId = successMessageJSONObject.getLong("layoutId");

		Layout layout = _layoutService.fetchLayout(
			groupId, privateLayout, layoutId);

		if (layout != null) {
			return _portal.getLayoutURL(layout, themeDisplay);
		}

		return null;
	}

	private LayoutStructure _getLayoutStructure(
			HttpServletRequest httpServletRequest)
		throws InfoFormException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				ParamUtil.getLong(
					httpServletRequest, "plid", themeDisplay.getPlid()),
				ParamUtil.getLong(
					httpServletRequest, "segmentsExperienceId",
					themeDisplay.getPlid()));

		if (layoutStructure == null) {
			throw new InfoFormException();
		}

		return layoutStructure;
	}

	private String _getRedirect(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject successMessageJSONObject =
			formStyledLayoutStructureItem.getSuccessMessageJSONObject();

		if (successMessageJSONObject == null) {
			return null;
		}

		String redirect = null;

		if (successMessageJSONObject.has("url")) {
			redirect = _getURLRedirect(themeDisplay, successMessageJSONObject);
		}
		else if (successMessageJSONObject.has("layoutUuid")) {
			redirect = _getLayoutRedirect(
				successMessageJSONObject, themeDisplay);
		}

		return redirect;
	}

	private String _getURLRedirect(
			ThemeDisplay themeDisplay, JSONObject successMessageJSONObject)
		throws Exception {

		JSONObject urlJSONObject = successMessageJSONObject.getJSONObject(
			"url");

		String redirect = urlJSONObject.getString(themeDisplay.getLanguageId());

		if (Validator.isNull(redirect)) {
			String siteDefaultLanguageId = LanguageUtil.getLanguageId(
				_portal.getSiteDefaultLocale(themeDisplay.getScopeGroupId()));

			redirect = urlJSONObject.getString(siteDefaultLanguageId);
		}

		return redirect;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInfoItemStrutsAction.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	private volatile InfoRequestFieldValuesProviderHelper
		_infoRequestFieldValuesProviderHelper;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}