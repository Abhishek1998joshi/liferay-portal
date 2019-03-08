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

package com.liferay.data.engine.rest.internal.io;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionJSONSerializer {

	public static String serialize(DataDefinition dataDefinition) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"fields",
			_getDEDataDefinitionFieldsJSONArray(
				dataDefinition.getDataDefinitionFields()));

		return jsonObject.toJSONString();
	}

	private static JSONObject _getDataDefinitionFieldJSONObject(
			DataDefinitionField dataDefinitionField)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Object defaultValue = dataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", dataDefinitionField.getIndexable());

		LocalizedValue[] label = dataDefinitionField.getLabel();

		if (!(label.length == 0)) {
			_setProperty("label", jsonObject, label);
		}

		jsonObject.put("localizable", dataDefinitionField.getLocalizable());

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new Exception("Name property is required");
		}

		jsonObject.put("name", name);

		jsonObject.put("repeatable", dataDefinitionField.getRepeatable());

		LocalizedValue[] tip = dataDefinitionField.getTip();

		if (!(tip.length == 0)) {
			_setProperty("tip", jsonObject, tip);
		}

		String type = dataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new Exception("Type property is required");
		}

		jsonObject.put("type", type);

		return jsonObject;
	}

	private static JSONArray _getDEDataDefinitionFieldsJSONArray(
			DataDefinitionField[] dataDefinitionFields)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DataDefinitionField dataDefinitionField : dataDefinitionFields) {
			jsonArray.put(
				_getDataDefinitionFieldJSONObject(dataDefinitionField));
		}

		return jsonArray;
	}

	private static void _setProperty(
		String propertyKey, JSONObject jsonObject,
		LocalizedValue[] localizedValues) {

		JSONObject languageJSONObject = JSONFactoryUtil.createJSONObject();

		for (LocalizedValue localizedValue : localizedValues) {
			languageJSONObject.put(
				localizedValue.getKey(), localizedValue.getValue());
		}

		jsonObject.put(propertyKey, languageJSONObject);
	}

}