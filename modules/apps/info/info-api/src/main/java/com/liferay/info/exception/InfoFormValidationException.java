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

package com.liferay.info.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class InfoFormValidationException extends InfoFormException {

	public InfoFormValidationException() {
		_infoFieldUniqueId = StringPool.BLANK;
	}

	public InfoFormValidationException(String infoFieldUniqueId) {
		_infoFieldUniqueId = infoFieldUniqueId;
	}

	public String getInfoFieldUniqueId() {
		return _infoFieldUniqueId;
	}

	public static class FileSize extends InfoFormValidationException {

		public FileSize(String infoFieldUniqueId, String maximumSizeAllowed) {
			super(infoFieldUniqueId);

			_maximumSizeAllowed = maximumSizeAllowed;
		}

		@Override
		public String getLocalizedMessage(Locale locale) {
			return LanguageUtil.format(
				locale, "the-attachment-has-to-be-x-or-less",
				_maximumSizeAllowed);
		}

		public String getMaximumSizeAllowed() {
			return _maximumSizeAllowed;
		}

		private final String _maximumSizeAllowed;

	}

	public static class InvalidFileExtension
		extends InfoFormValidationException {

		public InvalidFileExtension(
			String infoFieldUniqueId, String validFileExtensions) {

			super(infoFieldUniqueId);

			_validFileExtensions = validFileExtensions;
		}

		@Override
		public String getLocalizedMessage(Locale locale) {
			return LanguageUtil.format(
				locale, "the-accepted-extensions-for-the-file-are-x",
				_validFileExtensions);
		}

		public String getValidFileExtensions() {
			return _validFileExtensions;
		}

		private final String _validFileExtensions;

	}

	public static class InvalidInfoFieldValue
		extends InfoFormValidationException {

		public InvalidInfoFieldValue(String infoFieldUniqueId) {
			super(infoFieldUniqueId);
		}

		@Override
		public String getLocalizedMessage(Locale locale) {
			return LanguageUtil.get(locale, "this-field-is-invalid");
		}

	}

	public static class RequiredInfoField extends InfoFormValidationException {

		public RequiredInfoField(String infoFieldUniqueId) {
			super(infoFieldUniqueId);
		}

		@Override
		public String getLocalizedMessage(Locale locale) {
			return LanguageUtil.get(locale, "this-field-is-required");
		}

	}

	private final String _infoFieldUniqueId;

}