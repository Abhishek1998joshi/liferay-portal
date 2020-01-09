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

package com.liferay.dispatch.scheduler;

import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
public interface DispatchTriggerSchedulerEntryTracker {

	public void addScheduledTask(
			long dispatchTriggerId, String cronExpression, Date startDate,
			Date endDate)
		throws SchedulerException;

	public void deleteScheduledTask(long dispatchTriggerId)
		throws SchedulerException;

	public Date getNextFireDate(long dispatchTriggerId);

	public Date getPreviousFireDate(long dispatchTriggerId);

	public SchedulerResponse getSchedulerResponse(long dispatchTriggerId)
		throws SchedulerException;

}