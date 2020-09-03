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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.internal.servlet.JSResolveModulesServlet;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdatesListener;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = NPMRegistryUpdatesListener.class)
public class NPMRegistryUpdatesListenerImpl
	implements NPMRegistryUpdatesListener {

	@Override
	public void onAfterUpdate() {
		ServiceReference<JSResolveModulesServlet> serviceReference =
			_bundleContext.getServiceReference(JSResolveModulesServlet.class);

		JSResolveModulesServlet jsResolveModulesServlet =
			_bundleContext.getService(serviceReference);

		try {
			jsResolveModulesServlet.updateETag();
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

}