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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static DataDefinition toDataDefinition(String json)
		throws Exception {

		return new DataDefinition() {
			{
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

				dataDefinitionFields = JSONUtil.toArray(
					jsonObject.getJSONArray("fields"),
					fieldJSONObject -> _toDataDefinitionField(fieldJSONObject),
					DataDefinitionField.class);
			}
		};
	}

	public static String toJSON(DataDefinition dataDefinition)
		throws Exception {

		return JSONUtil.put(
			"fields",
			JSONUtil.toJSONArray(
				dataDefinition.getDataDefinitionFields(),
				dataDefinitionField -> _toJSONObject(dataDefinitionField))
		).toString();
	}

	private static void _put(
		JSONObject jsonObject, String key, LocalizedValue[] localizedValues) {

		if (ArrayUtil.isEmpty(localizedValues)) {
			return;
		}

		JSONObject localizedValueJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (LocalizedValue localizedValue : localizedValues) {
			localizedValueJSONObject.put(
				localizedValue.getKey(), localizedValue.getValue());
		}

		jsonObject.put(key, localizedValueJSONObject);
	}

	private static DataDefinitionField _toDataDefinitionField(
			JSONObject jsonObject)
		throws Exception {

		return new DataDefinitionField() {
			{
				defaultValue = jsonObject.getString("defaultValue");

				if (!jsonObject.has("type")) {
					throw new Exception("Type is required");
				}

				fieldType = jsonObject.getString("type");

				indexable = jsonObject.getBoolean("indexable", true);

				if (!jsonObject.has("label")) {
					throw new Exception("Label is required");
				}

				label = LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("label"));

				localizable = jsonObject.getBoolean("localizable", false);

				if (!jsonObject.has("name")) {
					throw new Exception("Name is required");
				}

				name = jsonObject.getString("name");

				repeatable = jsonObject.getBoolean("repeatable", false);

				if (!jsonObject.has("tip")) {
					throw new Exception("Tip is required");
				}

				tip = LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("tip"));
			}
		};
	}

	private static JSONObject _toJSONObject(
			DataDefinitionField dataDefinitionField)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Object defaultValue = dataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", dataDefinitionField.getIndexable());

		_put(jsonObject, "label", dataDefinitionField.getLabel());

		jsonObject.put("localizable", dataDefinitionField.getLocalizable());

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new Exception("Name is required");
		}

		jsonObject.put("name", name);

		jsonObject.put("repeatable", dataDefinitionField.getRepeatable());

		_put(jsonObject, "tip", dataDefinitionField.getTip());

		String type = dataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new Exception("Type is required");
		}

		jsonObject.put("type", type);

		return jsonObject;
	}

}