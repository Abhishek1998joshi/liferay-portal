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

package com.liferay.headless.foundation.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Category")
@XmlRootElement(name = "Category")
public class Category {

	public String[] getAvailableLanguages() {
		return availableLanguages;
	}

	public Creator getCreator() {
		return creator;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getHasCategories() {
		return hasCategories;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ParentCategory getParentCategory() {
		return parentCategory;
	}

	public ParentVocabulary getParentVocabulary() {
		return parentVocabulary;
	}

	public Long getParentVocabularyId() {
		return parentVocabularyId;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
		UnsafeSupplier<String[], Throwable> availableLanguagesUnsafeSupplier) {

		try {
			availableLanguages = availableLanguagesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	@JsonIgnore
	public void setCreatorId(
		UnsafeSupplier<Long, Throwable> creatorIdUnsafeSupplier) {

		try {
			creatorId = creatorIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setHasCategories(Boolean hasCategories) {
		this.hasCategories = hasCategories;
	}

	@JsonIgnore
	public void setHasCategories(
		UnsafeSupplier<Boolean, Throwable> hasCategoriesUnsafeSupplier) {

		try {
			hasCategories = hasCategoriesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setParentCategory(ParentCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	@JsonIgnore
	public void setParentCategory(
		UnsafeSupplier<ParentCategory, Throwable>
			parentCategoryUnsafeSupplier) {

		try {
			parentCategory = parentCategoryUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setParentVocabulary(ParentVocabulary parentVocabulary) {
		this.parentVocabulary = parentVocabulary;
	}

	@JsonIgnore
	public void setParentVocabulary(
		UnsafeSupplier<ParentVocabulary, Throwable>
			parentVocabularyUnsafeSupplier) {

		try {
			parentVocabulary = parentVocabularyUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setParentVocabularyId(Long parentVocabularyId) {
		this.parentVocabularyId = parentVocabularyId;
	}

	@JsonIgnore
	public void setParentVocabularyId(
		UnsafeSupplier<Long, Throwable> parentVocabularyIdUnsafeSupplier) {

		try {
			parentVocabularyId = parentVocabularyIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(26);

		sb.append("{");

		sb.append("availableLanguages=");

		sb.append(availableLanguages);
		sb.append(", parentCategory=");

		sb.append(parentCategory);
		sb.append(", creator=");

		sb.append(creator);
		sb.append(", creatorId=");

		sb.append(creatorId);
		sb.append(", dateCreated=");

		sb.append(dateCreated);
		sb.append(", dateModified=");

		sb.append(dateModified);
		sb.append(", description=");

		sb.append(description);
		sb.append(", hasCategories=");

		sb.append(hasCategories);
		sb.append(", id=");

		sb.append(id);
		sb.append(", name=");

		sb.append(name);
		sb.append(", parentVocabulary=");

		sb.append(parentVocabulary);
		sb.append(", parentVocabularyId=");

		sb.append(parentVocabularyId);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String[] availableLanguages;

	@GraphQLField
	@JsonProperty
	protected Creator creator;

	@GraphQLField
	@JsonProperty
	protected Long creatorId;

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;

	@GraphQLField
	@JsonProperty
	protected Date dateModified;

	@GraphQLField
	@JsonProperty
	protected String description;

	@GraphQLField
	@JsonProperty
	protected Boolean hasCategories;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected ParentCategory parentCategory;

	@GraphQLField
	@JsonProperty
	protected ParentVocabulary parentVocabulary;

	@GraphQLField
	@JsonProperty
	protected Long parentVocabularyId;

}