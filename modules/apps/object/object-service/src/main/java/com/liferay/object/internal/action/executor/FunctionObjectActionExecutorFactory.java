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

package com.liferay.object.internal.action.executor;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.configuration.FunctionObjectActionExecutorFactoryConfiguration;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Http.Method;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.object.configuration.FunctionObjectActionExecutorFactoryConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = ObjectActionExecutor.class
)
public class FunctionObjectActionExecutorFactory
	implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setBody(
			payloadJSONObject.toString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(_location);
		options.setMethod(Method.POST);
		options.setTimeout(_timeout);

		// Adding OAuth is pending a change by App Sec.

		// _authorize(companyId, options, userId);

		_http.URLtoByteArray(options);
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			ObjectActionExecutorConstants.KEY_FUNCTION, StringPool.POUND, _key);
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_functionObjectActionExecutorFactoryConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionObjectActionExecutorFactoryConfiguration.class,
				properties);

		_key = _getExternalReferenceCode(properties);

		Company company = _getCompany(properties);

		_oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					company.getCompanyId(),
					_functionObjectActionExecutorFactoryConfiguration.
						oauth2Application());

		_location = _getLocation();

		_timeout = _functionObjectActionExecutorFactoryConfiguration.timeout();
	}

	private Company _getCompany(Map<String, Object> properties)
		throws Exception {

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId > 0) {
			return _companyLocalService.getCompanyById(companyId);
		}

		String webId = (String)properties.get(
			"dxp.lxc.liferay.com.virtualInstanceId");

		if (Validator.isNotNull(webId)) {
			if (Objects.equals(webId, "default")) {
				webId = PropsValues.COMPANY_DEFAULT_WEB_ID;
			}

			return _companyLocalService.getCompanyByWebId(webId);
		}

		throw new IllegalStateException(
			"The property \"companyId\" or " +
				"\"dxp.lxc.liferay.com.virtualInstanceId\" must be set");
	}

	private String _getExternalReferenceCode(Map<String, Object> properties) {
		String externalReferenceCode = GetterUtil.getString(
			properties.get(Constants.SERVICE_PID));

		int index = externalReferenceCode.indexOf('~');

		if (index > 0) {
			externalReferenceCode = externalReferenceCode.substring(index + 1);
		}

		return externalReferenceCode;
	}

	private String _getLocation() {
		String resourcePath =
			_functionObjectActionExecutorFactoryConfiguration.resourcePath();

		if (resourcePath.contains(Http.PROTOCOL_DELIMITER)) {
			return resourcePath;
		}

		if (resourcePath.startsWith(StringPool.SLASH)) {
			resourcePath = resourcePath.substring(1);
		}

		String homePageURL = _oAuth2Application.getHomePageURL();

		if (homePageURL.endsWith(StringPool.SLASH)) {
			homePageURL = homePageURL.substring(0, homePageURL.length() - 1);
		}

		return StringBundler.concat(
			homePageURL, StringPool.SLASH, resourcePath);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private FunctionObjectActionExecutorFactoryConfiguration
		_functionObjectActionExecutorFactoryConfiguration;

	@Reference
	private Http _http;

	private String _key;
	private String _location;
	private OAuth2Application _oAuth2Application;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	private int _timeout;

}