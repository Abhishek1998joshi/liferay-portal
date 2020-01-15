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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.application.DepotApplication;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collection;

/**
 * @author Cristina González
 */
public class DepotAdminApplicationsDisplayContext {

	public DepotAdminApplicationsDisplayContext(
		DepotApplicationController depotApplicationController) {

		_depotApplicationController = depotApplicationController;
	}

	public Collection<DepotApplication> getDepotApplications() {
		return _depotApplicationController.getCustomizableDepotApplications();
	}

	public boolean isEnabled(String portletId, long groupId)
		throws PortalException {

		return _depotApplicationController.isEnabled(portletId, groupId);
	}

	private final DepotApplicationController _depotApplicationController;

}