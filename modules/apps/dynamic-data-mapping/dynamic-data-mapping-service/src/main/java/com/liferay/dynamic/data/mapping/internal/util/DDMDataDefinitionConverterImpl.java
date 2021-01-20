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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMDataDefinitionConverter;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = DDMDataDefinitionConverter.class)
public class DDMDataDefinitionConverterImpl
	implements DDMDataDefinitionConverter {

	@Override
	public String convert(DDMForm ddmForm, Locale defaultLocale) {
		if (Objects.equals(ddmForm.getDefinitionSchemaVersion(), "2.0")) {
			return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
		}

		ddmForm.setDefinitionSchemaVersion("2.0");

		_upgradeFields(ddmForm.getDDMFormFields(), defaultLocale);

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	@Override
	public String convert(String dataDefinition, Locale defaultLocale)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, dataDefinition);

		return convert(ddmForm, defaultLocale);
	}

	private DDMFormFieldOptions _getDDMFormFieldOptions(
		DDMFormField ddmFormField) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		LocalizedValue localizedValue = ddmFormField.getLabel();

		for (Locale locale : localizedValue.getAvailableLocales()) {
			ddmFormFieldOptions.addOptionLabel(
				ddmFormField.getName(), locale,
				localizedValue.getString(locale));
		}

		return ddmFormFieldOptions;
	}

	private LocalizedValue _getEmptyLocalizedValue(Locale defaultLocale) {
		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		localizedValue.addString(defaultLocale, StringPool.BLANK);

		return localizedValue;
	}

	private LocalizedValue _getLocalizedPredefinedValue(
		DDMFormField ddmFormField) {

		LocalizedValue newPredefinedValue = new LocalizedValue();

		LocalizedValue oldPredefinedValue = ddmFormField.getPredefinedValue();

		for (Locale locale : oldPredefinedValue.getAvailableLocales()) {
			if (GetterUtil.getBoolean(oldPredefinedValue.getString(locale))) {
				newPredefinedValue.addString(locale, ddmFormField.getName());
			}
			else {
				newPredefinedValue.addString(locale, StringPool.BLANK);
			}
		}

		newPredefinedValue.setDefaultLocale(
			oldPredefinedValue.getDefaultLocale());

		return newPredefinedValue;
	}

	private void _upgradeBooleanField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("string");
		ddmFormField.setDDMFormFieldOptions(
			_getDDMFormFieldOptions(ddmFormField));
		ddmFormField.setPredefinedValue(
			_getLocalizedPredefinedValue(ddmFormField));
		ddmFormField.setType("checkbox_multiple");
	}

	private void _upgradeColorField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("string");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("color");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeDateField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("date");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("date");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeDecimalField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("double");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("numeric");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeDocumentLibraryField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("document-library");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("document_library");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeField(
		DDMFormField ddmFormField, Locale defaultLocale) {

		if (Objects.equals(ddmFormField.getType(), "checkbox")) {
			_upgradeBooleanField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-color")) {
			_upgradeColorField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-date")) {
			_upgradeDateField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-decimal")) {
			_upgradeDecimalField(ddmFormField);
		}
		else if (Objects.equals(
					ddmFormField.getType(), "ddm-documentlibrary")) {

			_upgradeDocumentLibraryField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-geolocation")) {
			_upgradeGeolocation(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-image")) {
			_upgradeImageField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-integer")) {
			_upgradeIntegerField(ddmFormField);
		}
		else if (Objects.equals(
					ddmFormField.getType(), "ddm-journal-article")) {

			_upgradeJournalArticleField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-link-to-page")) {
			_upgradeLinkToPageField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-number")) {
			_upgradeNumberField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-separator")) {
			_upgradeSeparatorField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-text-html")) {
			_upgradeHTMLField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "select")) {
			_upgradeSelectField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "text")) {
			_upgradeTextField(ddmFormField, defaultLocale);
		}
		else if (Objects.equals(ddmFormField.getType(), "textarea")) {
			_upgradeTextArea(ddmFormField, defaultLocale);
		}

		if (!Objects.equals(ddmFormField.getType(), "separator") &&
			Validator.isNull(ddmFormField.getIndexType())) {

			ddmFormField.setIndexType("none");
		}

		_upgradeFields(ddmFormField.getNestedDDMFormFields(), defaultLocale);
	}

	private void _upgradeFields(
		List<DDMFormField> ddmFormFields, Locale defaultLocale) {

		if (ddmFormFields.isEmpty()) {
			return;
		}

		for (DDMFormField ddmFormField : ddmFormFields) {
			_upgradeField(ddmFormField, defaultLocale);
		}
	}

	private void _upgradeGeolocation(DDMFormField ddmFormField) {
		ddmFormField.setDataType("geolocation");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("geolocation");
	}

	private void _upgradeHTMLField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("string");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("rich_text");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeImageField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("image");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("image");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeIntegerField(DDMFormField ddmFormField) {
		ddmFormField.setType("numeric");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeJournalArticleField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("journal-article");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("journal_article");
	}

	private void _upgradeLinkToPageField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("link-to-page");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("link_to_layout");
	}

	private void _upgradeNumberField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("double");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("numeric");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeSelectField(DDMFormField ddmFormField) {
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setProperty("dataSourceType", "[manual]");
		ddmFormField.setProperty("ddmDataProviderInstanceId", "[]");
		ddmFormField.setProperty("ddmDataProviderInstanceOutput", "[]");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeSeparatorField(DDMFormField ddmFormField) {
		ddmFormField.setDataType(StringPool.BLANK);
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("separator");
	}

	private void _upgradeTextArea(
		DDMFormField ddmFormField, Locale defaultLocale) {

		ddmFormField.setFieldNamespace(StringPool.BLANK);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("Option", defaultLocale, "Option");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setProperty("autocomplete", false);
		ddmFormField.setProperty("dataSourceType", "manual");
		ddmFormField.setProperty("ddmDataProviderInstanceId", "[]");
		ddmFormField.setProperty("ddmDataProviderInstanceOutput", "[]");
		ddmFormField.setProperty("displayStyle", "multiline");
		ddmFormField.setProperty(
			"placeholder", _getEmptyLocalizedValue(defaultLocale));
		ddmFormField.setProperty(
			"tooltip", _getEmptyLocalizedValue(defaultLocale));
		ddmFormField.setType("text");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeTextField(
		DDMFormField ddmFormField, Locale defaultLocale) {

		ddmFormField.setFieldNamespace(StringPool.BLANK);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("Option", defaultLocale, "Option");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setProperty("autocomplete", false);
		ddmFormField.setProperty("dataSourceType", "manual");
		ddmFormField.setProperty("ddmDataProviderInstanceId", "[]");
		ddmFormField.setProperty("ddmDataProviderInstanceOutput", "[]");
		ddmFormField.setProperty("displayStyle", "singleline");
		ddmFormField.setProperty(
			"placeholder", _getEmptyLocalizedValue(defaultLocale));
		ddmFormField.setProperty(
			"tooltip", _getEmptyLocalizedValue(defaultLocale));

		ddmFormField.setType("text");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	@Reference
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMFormSerializer _ddmFormSerializer;

}