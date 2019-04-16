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

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.Selection;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class SelectionSerDes {

	public static Selection toDTO(String json) {
		SelectionJSONParser selectionJSONParser = new SelectionJSONParser();

		return selectionJSONParser.parseToDTO(json);
	}

	public static Selection[] toDTOs(String json) {
		SelectionJSONParser selectionJSONParser = new SelectionJSONParser();

		return selectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Selection selection) {
		if (selection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"size\": ");

		if (selection.getSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(selection.getSize());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class SelectionJSONParser extends BaseJSONParser<Selection> {

		@Override
		protected Selection createDTO() {
			return new Selection();
		}

		@Override
		protected Selection[] createDTOArray(int size) {
			return new Selection[size];
		}

		@Override
		protected void setField(
			Selection selection, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "size")) {
				if (jsonParserFieldValue != null) {
					selection.setSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}