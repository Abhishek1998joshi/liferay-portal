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

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.FieldArray;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.Format;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryModelDocumentContributor
	implements ModelDocumentContributor<ObjectEntry> {

	public ObjectEntryModelDocumentContributor(
		String className,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService) {

		_className = className;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@Override
	public void contribute(Document document, ObjectEntry objectEntry) {
		try {
			_contribute(document, objectEntry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index object entry " +
						objectEntry.getObjectEntryId(),
					exception);
			}
		}
	}

	private void _addField(
		FieldArray fieldArray, String fieldName, String valueFieldName,
		String value) {

		Field field = new Field("");

		field.addField(new Field("fieldName", fieldName));
		field.addField(new Field("valueFieldName", valueFieldName));
		field.addField(new Field(valueFieldName, value));

		fieldArray.addField(field);
	}

	private void _appendToContent(
		StringBundler sb, String objectFieldName, String stringValue) {

		sb.append(objectFieldName);
		sb.append(": ");
		sb.append(stringValue);
		sb.append(StringPool.COMMA_AND_SPACE);
	}

	private void _contribute(Document document, ObjectEntry objectEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document);
			_log.debug("Object entry " + objectEntry);
		}

		document.addKeyword(
			"objectDefinitionId", objectEntry.getObjectDefinitionId());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		document.addKeyword(
			"objectDefinitionName", objectDefinition.getShortName());

		String titleFieldPrefix = "title";

		// TODO

		//String titleFieldPrefix = objectDefinition.getTitleFieldPrefix();

		if (titleFieldPrefix == null) {

			// TODO If no title field prefix is defined by the user, build a
			// title from a set of rules

			String title = "This is a title";

			// TODO Can we camel case this?

			document.add(new Field("object_entry_title", title));
		}

		FieldArray fieldArray = (FieldArray)document.getField(
			"nestedFieldArray");

		if (fieldArray == null) {
			fieldArray = new FieldArray("nestedFieldArray");

			document.add(fieldArray);
		}

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectEntry.getObjectDefinitionId());

		StringBundler sb = new StringBundler(objectFields.size() * 4);

		for (ObjectField objectField : objectFields) {
			_contribute(
				document, fieldArray, objectEntry, objectField, sb,
				titleFieldPrefix, values);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		// TODO Can we camel case this?

		document.add(new Field("object_entry_content", sb.toString()));
	}

	private void _contribute(
		Document document, FieldArray fieldArray, ObjectEntry objectEntry,
		ObjectField objectField, StringBundler sb, String titleFieldPrefix,
		Map<String, Serializable> values) {

		if (!objectField.isIndexed()) {
			return;
		}

		Object value = values.get(objectField.getName());

		if (value == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Object entry ", objectEntry.getObjectEntryId(),
						" has object field \"", objectField.getName(),
						"\" with a null value"));
			}

			return;
		}

		String objectFieldName = objectField.getName();
		String stringValue = String.valueOf(value);

		if ((titleFieldPrefix != null) &&
			objectFieldName.startsWith(titleFieldPrefix)) {

			if (!Validator.isBlank(objectField.getIndexedLanguageId())) {
				document.add(
					new Field(
						"object_entry_title_" +
							objectField.getIndexedLanguageId(),
						stringValue));
			}
			else {
				document.add(new Field("object_entry_title", stringValue));
			}

			document.add(new Field("title_field_prefix", titleFieldPrefix));
		}

		if (objectField.isIndexedAsKeyword()) {
			_addField(
				fieldArray, objectFieldName, "value_keyword",
				StringUtil.lowerCase(stringValue));

			_appendToContent(sb, objectFieldName, stringValue);
		}
		else if (value instanceof BigDecimal) {
			_addField(fieldArray, objectFieldName, "value_double", stringValue);

			_appendToContent(sb, objectFieldName, stringValue);
		}
		else if (value instanceof Boolean) {
			_addField(
				fieldArray, objectFieldName, "value_boolean", stringValue);
			_addField(
				fieldArray, objectFieldName, "value_keyword",
				_translate((Boolean)value));

			_appendToContent(sb, objectFieldName, stringValue);
		}
		else if (value instanceof Date) {
			_addField(
				fieldArray, objectFieldName, "value_date",
				_getDateString(value));

			_appendToContent(sb, objectFieldName, _getDateString(value));
		}
		else if (value instanceof Double) {
			_addField(fieldArray, objectFieldName, "value_double", stringValue);

			_appendToContent(sb, objectFieldName, stringValue);
		}
		else if (value instanceof Integer) {
			_addField(
				fieldArray, objectFieldName, "value_integer", stringValue);

			_appendToContent(sb, objectFieldName, stringValue);
		}
		else if (value instanceof Long) {
			_addField(fieldArray, objectFieldName, "value_long", stringValue);

			_appendToContent(sb, objectFieldName, stringValue);
		}
		else if (value instanceof String) {
			if (Validator.isBlank(objectField.getIndexedLanguageId())) {
				_addField(
					fieldArray, objectFieldName, "value_text", stringValue);
			}
			else {
				_addField(
					fieldArray, objectFieldName,
					"value_" + objectField.getIndexedLanguageId(), stringValue);
			}

			_addField(
				fieldArray, objectFieldName, "value_keyword_lowercase",
				_getSortableValue(stringValue));

			_appendToContent(sb, objectFieldName, stringValue);
		}
		else if (value instanceof byte[]) {
			_addField(
				fieldArray, objectFieldName, "value_binary",
				Base64.encode((byte[])value));
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Object entry ", objectEntry.getObjectEntryId(),
						" has object field \"", objectFieldName,
						"\" with unsupported value ", value));
			}
		}
	}

	private String _getDateString(Object value) {
		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		return format.format(value);
	}

	private String _getSortableValue(String value) {
		if (value.length() > 256) {
			return value.substring(0, 256);
		}

		return value;
	}

	private String _translate(Boolean value) {
		if (value.booleanValue()) {
			return "yes";
		}

		return "no";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelDocumentContributor.class);

	private final String _className;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;

}