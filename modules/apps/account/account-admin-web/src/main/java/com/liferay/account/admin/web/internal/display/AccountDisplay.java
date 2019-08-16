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

package com.liferay.account.admin.web.internal.display;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AccountDisplay {

	public static AccountDisplay of(AccountEntry accountEntry) {
		return new AccountDisplay(accountEntry);
	}

	public long getAccountId() {
		return _accountId;
	}

	public String getAccountOwner() {
		return _accountOwner;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getParentAccountName() {
		return _parentAccountName;
	}

	public String getStatusLabel() {
		return _statusLabel;
	}

	public String getWebsite() {
		return _website;
	}

	private String _getParentAccountName(AccountEntry accountEntry) {
		long parentAccountEntryId = accountEntry.getParentAccountEntryId();

		if (parentAccountEntryId == 0) {
			return StringPool.BLANK;
		}

		AccountEntry parentAccountEntry =
			AccountEntryLocalServiceUtil.fetchAccountEntry(
				parentAccountEntryId);

		if (parentAccountEntry != null) {
			return parentAccountEntry.getName();
		}

		return StringPool.BLANK;
	}

	private String _getStatusLabel(AccountEntry accountEntry) {
		int status = accountEntry.getStatus();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "active";
		}

		if (status == WorkflowConstants.STATUS_INACTIVE) {
			return "inactive";
		}

		return StringPool.BLANK;
	}

	private String _getWebsite(AccountEntry accountEntry) {
		List<Website> websites = WebsiteLocalServiceUtil.getWebsites(
			accountEntry.getCompanyId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		if (websites.isEmpty()) {
			return StringPool.BLANK;
		}

		Website website = websites.get(0);

		return website.getUrl();
	}

	private AccountDisplay(AccountEntry accountEntry) {
		_accountId = accountEntry.getAccountEntryId();
		_description = accountEntry.getDescription();
		_name = accountEntry.getName();
		_parentAccountName= _getParentAccountName(accountEntry);
		_statusLabel = _getStatusLabel(accountEntry);
		_website = _getWebsite(accountEntry);
	}

	private long _accountId;
	private String _accountOwner = StringPool.BLANK;
	private String _description = StringPool.BLANK;
	private String _name = StringPool.BLANK;
	private String _parentAccountName = StringPool.BLANK;
	private String _statusLabel = StringPool.BLANK;
	private String _website = StringPool.BLANK;

}