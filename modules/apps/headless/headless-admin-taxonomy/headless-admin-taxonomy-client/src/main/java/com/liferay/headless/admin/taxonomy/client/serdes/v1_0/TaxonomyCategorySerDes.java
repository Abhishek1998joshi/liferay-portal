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

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class TaxonomyCategorySerDes {

	public static TaxonomyCategory toDTO(String json) {
		TaxonomyCategoryJSONParser taxonomyCategoryJSONParser =
			new TaxonomyCategoryJSONParser();

		return taxonomyCategoryJSONParser.parseToDTO(json);
	}

	public static TaxonomyCategory[] toDTOs(String json) {
		TaxonomyCategoryJSONParser taxonomyCategoryJSONParser =
			new TaxonomyCategoryJSONParser();

		return taxonomyCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaxonomyCategory taxonomyCategory) {
		if (taxonomyCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"availableLanguages\": ");

		if (taxonomyCategory.getAvailableLanguages() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < taxonomyCategory.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");

				sb.append(taxonomyCategory.getAvailableLanguages()[i]);

				sb.append("\"");

				if ((i + 1) < taxonomyCategory.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(taxonomyCategory.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (taxonomyCategory.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					taxonomyCategory.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (taxonomyCategory.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					taxonomyCategory.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (taxonomyCategory.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(taxonomyCategory.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (taxonomyCategory.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategory.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (taxonomyCategory.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(taxonomyCategory.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"numberOfTaxonomyCategories\": ");

		if (taxonomyCategory.getNumberOfTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategory.getNumberOfTaxonomyCategories());
		}

		sb.append(", ");

		sb.append("\"parentTaxonomyCategory\": ");

		sb.append(
			ParentTaxonomyCategorySerDes.toJSON(
				taxonomyCategory.getParentTaxonomyCategory()));
		sb.append(", ");

		sb.append("\"parentTaxonomyVocabulary\": ");

		sb.append(
			ParentTaxonomyVocabularySerDes.toJSON(
				taxonomyCategory.getParentTaxonomyVocabulary()));
		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (taxonomyCategory.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(taxonomyCategory.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class TaxonomyCategoryJSONParser
		extends BaseJSONParser<TaxonomyCategory> {

		@Override
		protected TaxonomyCategory createDTO() {
			return new TaxonomyCategory();
		}

		@Override
		protected TaxonomyCategory[] createDTOArray(int size) {
			return new TaxonomyCategory[size];
		}

		@Override
		protected void setField(
			TaxonomyCategory taxonomyCategory, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "availableLanguages")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfTaxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setNumberOfTaxonomyCategories(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentTaxonomyCategory")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setParentTaxonomyCategory(
						ParentTaxonomyCategorySerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentTaxonomyVocabulary")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setParentTaxonomyVocabulary(
						ParentTaxonomyVocabularySerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setViewableBy(
						TaxonomyCategory.ViewableBy.create(
							(String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}