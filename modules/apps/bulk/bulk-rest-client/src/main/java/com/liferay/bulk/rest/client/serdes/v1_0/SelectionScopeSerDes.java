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

import com.liferay.bulk.rest.client.dto.v1_0.SelectionScope;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class SelectionScopeSerDes {

	public static SelectionScope toDTO(String json) {
		SelectionScopeJSONParser selectionScopeJSONParser =
			new SelectionScopeJSONParser();

		return selectionScopeJSONParser.parseToDTO(json);
	}

	public static SelectionScope[] toDTOs(String json) {
		SelectionScopeJSONParser selectionScopeJSONParser =
			new SelectionScopeJSONParser();

		return selectionScopeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SelectionScope selectionScope) {
		if (selectionScope == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"folderId\": ");

		if (selectionScope.getFolderId() == null) {
			sb.append("null");
		}
		else {
			sb.append(selectionScope.getFolderId());
		}

		sb.append(", ");

		sb.append("\"repositoryId\": ");

		if (selectionScope.getRepositoryId() == null) {
			sb.append("null");
		}
		else {
			sb.append(selectionScope.getRepositoryId());
		}

		sb.append(", ");

		sb.append("\"selectAll\": ");

		if (selectionScope.getSelectAll() == null) {
			sb.append("null");
		}
		else {
			sb.append(selectionScope.getSelectAll());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class SelectionScopeJSONParser
		extends BaseJSONParser<SelectionScope> {

		@Override
		protected SelectionScope createDTO() {
			return new SelectionScope();
		}

		@Override
		protected SelectionScope[] createDTOArray(int size) {
			return new SelectionScope[size];
		}

		@Override
		protected void setField(
			SelectionScope selectionScope, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "folderId")) {
				if (jsonParserFieldValue != null) {
					selectionScope.setFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "repositoryId")) {
				if (jsonParserFieldValue != null) {
					selectionScope.setRepositoryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectAll")) {
				if (jsonParserFieldValue != null) {
					selectionScope.setSelectAll((Boolean)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}