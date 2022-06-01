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

package com.liferay.client.extension.type.internal.manager;

import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = CETManager.class)
public class CETManagerImpl implements CETManager {

	@Override
	public List<CET> getCETs(
			long companyId, String keywords, Pagination pagination, Sort sort)
		throws PortalException {

		return TransformUtil.transform(
			_clientExtensionEntryLocalService.search(
				companyId, keywords, pagination.getStartPosition(),
				pagination.getEndPosition(), sort),
			clientExtensionEntry -> _cetFactory.cet(clientExtensionEntry));
	}

	@Override
	public int getCETsCount(long companyId, String keywords)
		throws PortalException {

		return _clientExtensionEntryLocalService.searchCount(
			companyId, keywords);
	}

	@Reference
	private CETFactory _cetFactory;

	@Reference
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

}