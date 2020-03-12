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

package com.liferay.redirect.web.internal.util.comparator;

import com.liferay.redirect.model.RedirectEntry;

/**
 * @author Alejandro Tardín
 */
public class RedirectEntryDestinationURLComparator
	extends BaseRedirectEntryComparator<String> {

	public RedirectEntryDestinationURLComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	protected String getFieldName() {
		return "destinationURL";
	}

	@Override
	protected String getFieldValue(RedirectEntry redirectEntry) {
		return redirectEntry.getDestinationURL();
	}

}