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

package com.liferay.asset.categories.admin.web.internal.util;

import com.liferay.asset.categories.admin.web.internal.configuration.FFAssetCategoriesAdminWebConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Rubén Pulido
 */
@Component(
	configurationPid = "com.liferay.asset.categories.admin.web.internal.configuration.FFAssetCategoriesAdminWebConfiguration",
	immediate = true, service = {}
)
public class FFAssetCategoriesAdminWebConfigurationUtil {

	public static boolean setDisplayPageTemplateEnabled() {
		return _ffAssetCategoriesAdminWebConfiguration.
			setDisplayPageTemplateEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffAssetCategoriesAdminWebConfiguration =
			ConfigurableUtil.createConfigurable(
				FFAssetCategoriesAdminWebConfiguration.class, properties);
	}

	private static volatile FFAssetCategoriesAdminWebConfiguration
		_ffAssetCategoriesAdminWebConfiguration;

}