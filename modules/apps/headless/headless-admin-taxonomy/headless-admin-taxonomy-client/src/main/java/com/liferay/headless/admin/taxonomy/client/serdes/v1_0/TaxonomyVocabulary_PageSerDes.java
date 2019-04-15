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

package com.liferay.headless.admin.taxonomy.client.serdes.v1_0;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary_Page;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class TaxonomyVocabulary_PageSerDes {

	public static TaxonomyVocabulary_Page toDTO(String json) {
		TaxonomyVocabulary_PageJSONParser taxonomyVocabulary_PageJSONParser =
			new TaxonomyVocabulary_PageJSONParser();

		return taxonomyVocabulary_PageJSONParser.parseToDTO(json);
	}

	public static TaxonomyVocabulary_Page[] toDTOs(String json) {
		TaxonomyVocabulary_PageJSONParser taxonomyVocabulary_PageJSONParser =
			new TaxonomyVocabulary_PageJSONParser();

		return taxonomyVocabulary_PageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		TaxonomyVocabulary_Page taxonomyVocabulary_Page) {

		if (taxonomyVocabulary_Page == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (taxonomyVocabulary_Page.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < taxonomyVocabulary_Page.getItems().length;
				 i++) {

				sb.append(
					TaxonomyVocabularySerDes.toJSON(
						taxonomyVocabulary_Page.getItems()[i]));

				if ((i + 1) < taxonomyVocabulary_Page.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (taxonomyVocabulary_Page.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabulary_Page.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (taxonomyVocabulary_Page.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabulary_Page.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (taxonomyVocabulary_Page.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabulary_Page.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (taxonomyVocabulary_Page.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabulary_Page.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class TaxonomyVocabulary_PageJSONParser
		extends BaseJSONParser<TaxonomyVocabulary_Page> {

		protected TaxonomyVocabulary_Page createDTO() {
			return new TaxonomyVocabulary_Page();
		}

		protected TaxonomyVocabulary_Page[] createDTOArray(int size) {
			return new TaxonomyVocabulary_Page[size];
		}

		protected void setField(
			TaxonomyVocabulary_Page taxonomyVocabulary_Page,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary_Page.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyVocabularySerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyVocabulary[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary_Page.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary_Page.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary_Page.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary_Page.setTotalCount(
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