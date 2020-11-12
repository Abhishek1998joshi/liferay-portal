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

package com.liferay.document.library.external.video.internal.display.context;

import com.liferay.document.library.display.context.BaseDLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLFilePicker;
import com.liferay.document.library.external.video.internal.ExternalVideo;
import com.liferay.document.library.external.video.internal.constants.ExternalVideoConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 * @author Alejandro Tardín
 */
public class ExternalVideoDLEditFileEntryDisplayContext
	extends BaseDLEditFileEntryDisplayContext {

	public ExternalVideoDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DLFileEntryType dlFileEntryType) {

		super(
			_UUID, parentDLEditFileEntryDisplayContext, httpServletRequest,
			httpServletResponse, dlFileEntryType);
	}

	public ExternalVideoDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileEntry fileEntry,
		ExternalVideo externalVideo) {

		super(
			_UUID, parentDLEditFileEntryDisplayContext, httpServletRequest,
			httpServletResponse, fileEntry);

		_externalVideo = externalVideo;
	}

	@Override
	public DLFilePicker getDLFilePicker(String onFilePickCallback) {
		return new ExternalVideoDLFilePicker(
			request, _externalVideo, onFilePickCallback);
	}

	@Override
	public long getMaximumUploadSize() {
		return 0;
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible() {
		return false;
	}

	@Override
	public boolean isCheckinButtonVisible() {
		return false;
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() {
		return false;
	}

	@Override
	public boolean isDDMStructureVisible(DDMStructure ddmStructure)
		throws PortalException {

		String ddmStructureKey = ddmStructure.getStructureKey();

		if (ddmStructureKey.equals(
				ExternalVideoConstants.DDM_STRUCTURE_KEY_EXTERNAL_VIDEO)) {

			return false;
		}

		return super.isDDMStructureVisible(ddmStructure);
	}

	@Override
	public boolean isVersionInfoVisible() {
		return false;
	}

	private static final UUID _UUID = UUID.fromString(
		"f3dad960-a5ea-4499-badd-0d1a06ee1c93");

	private ExternalVideo _externalVideo;

}