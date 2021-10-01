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

package com.liferay.object.internal.action.trigger.util;

import com.liferay.object.action.trigger.ObjectActionTrigger;
import com.liferay.object.constants.ObjectActionTriggerConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ObjectActionTriggerUtil {

	public static List<ObjectActionTrigger> getDefaultObjectActionTriggers() {
		return _defaultObjectActionTriggers;
	}

	private static final List<ObjectActionTrigger>
		_defaultObjectActionTriggers = Collections.unmodifiableList(
			Arrays.asList(
				new ObjectActionTrigger(
					ObjectActionTriggerConstants.KEY_ON_AFTER_CREATE,
					ObjectActionTriggerConstants.TYPE_TRANSACTION),
				new ObjectActionTrigger(
					ObjectActionTriggerConstants.KEY_ON_AFTER_REMOVE,
					ObjectActionTriggerConstants.TYPE_TRANSACTION),
				new ObjectActionTrigger(
					ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
					ObjectActionTriggerConstants.TYPE_TRANSACTION)));

}