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

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.rest.dto.v1_0.Site;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsChannel;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.SiteDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.SiteDTOConverterContext;
import com.liferay.analytics.settings.rest.resource.v1_0.SiteResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/site.properties",
	scope = ServiceScope.PROTOTYPE, service = SiteResource.class
)
public class SiteResourceImpl extends BaseSiteResourceImpl {

	@Override
	public Page<Site> getSitesPage(Pagination pagination) throws Exception {
		com.liferay.analytics.settings.rest.internal.client.pagination.Page
			<AnalyticsChannel> analyticsChannelsPage =
				_analyticsCloudClient.getAnalyticsChannelsPage(
					contextCompany.getCompanyId(), null, 0, 100);

		Collection<AnalyticsChannel> analyticsChannels =
			analyticsChannelsPage.getItems();

		Stream<AnalyticsChannel> stream = analyticsChannels.stream();

		Map<Long, String> analyticsChannelsMap = stream.collect(
			Collectors.toMap(
				AnalyticsChannel::getId, AnalyticsChannel::getName));

		return Page.of(
			transform(
				_groupService.search(
					contextCompany.getCompanyId(), _CLASS_NAME_IDS, null,
					_getGroupParams(), pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				group -> _siteDTOConverter.toDTO(
					new SiteDTOConverterContext(
						group.getGroupId(),
						contextAcceptLanguage.getPreferredLocale(),
						analyticsChannelsMap),
					group)),
			pagination,
			_groupService.searchCount(
				contextCompany.getCompanyId(), _CLASS_NAME_IDS, null,
				_getGroupParams()));
	}

	private LinkedHashMap<String, Object> _getGroupParams() {
		return LinkedHashMapBuilder.<String, Object>put(
			"active", Boolean.TRUE
		).put(
			"site", Boolean.TRUE
		).build();
	}

	private static final long[] _CLASS_NAME_IDS;

	static {
		_CLASS_NAME_IDS = new long[] {
			PortalUtil.getClassNameId(Group.class),
			PortalUtil.getClassNameId(Organization.class)
		};
	}

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private GroupService _groupService;

	@Reference
	private SiteDTOConverter _siteDTOConverter;

}