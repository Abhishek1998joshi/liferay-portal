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

package com.liferay.headless.form.dto.v1_0;

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
@GraphQLName("Form")
@XmlRootElement(name = "Form")
public class Form {

	public String[] getAvailableLanguages() {
		return availableLanguages;
	}

	public Long getContentSpace() {
		return contentSpace;
	}

	public Creator getCreator() {
		return creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public String getDescription() {
		return description;
	}

	public FormRecord[] getFormRecords() {
		return formRecords;
	}

	public Long[] getFormRecordsIds() {
		return formRecordsIds;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public FormStructure getStructure() {
		return structure;
	}

	public Long getStructureId() {
		return structureId;
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

	public void setContentSpace(Long contentSpace) {
		this.contentSpace = contentSpace;
	}

	@JsonIgnore
	public void setContentSpace(
		UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier) {

		try {
			contentSpace = contentSpaceUnsafeSupplier.get();
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

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
		UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier) {

		try {
			datePublished = datePublishedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	@JsonIgnore
	public void setDefaultLanguage(
		UnsafeSupplier<String, Throwable> defaultLanguageUnsafeSupplier) {

		try {
			defaultLanguage = defaultLanguageUnsafeSupplier.get();
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

	public void setFormRecords(FormRecord[] formRecords) {
		this.formRecords = formRecords;
	}

	@JsonIgnore
	public void setFormRecords(
		UnsafeSupplier<FormRecord[], Throwable> formRecordsUnsafeSupplier) {

		try {
			formRecords = formRecordsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setFormRecordsIds(Long[] formRecordsIds) {
		this.formRecordsIds = formRecordsIds;
	}

	@JsonIgnore
	public void setFormRecordsIds(
		UnsafeSupplier<Long[], Throwable> formRecordsIdsUnsafeSupplier) {

		try {
			formRecordsIds = formRecordsIdsUnsafeSupplier.get();
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

	public void setStructure(FormStructure structure) {
		this.structure = structure;
	}

	@JsonIgnore
	public void setStructure(
		UnsafeSupplier<FormStructure, Throwable> structureUnsafeSupplier) {

		try {
			structure = structureUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	@JsonIgnore
	public void setStructureId(
		UnsafeSupplier<Long, Throwable> structureIdUnsafeSupplier) {

		try {
			structureId = structureIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(30);

		sb.append("{");

		sb.append("availableLanguages=");

		sb.append(availableLanguages);
		sb.append(", contentSpace=");

		sb.append(contentSpace);
		sb.append(", creator=");

		sb.append(creator);
		sb.append(", dateCreated=");

		sb.append(dateCreated);
		sb.append(", dateModified=");

		sb.append(dateModified);
		sb.append(", datePublished=");

		sb.append(datePublished);
		sb.append(", defaultLanguage=");

		sb.append(defaultLanguage);
		sb.append(", description=");

		sb.append(description);
		sb.append(", formRecords=");

		sb.append(formRecords);
		sb.append(", formRecordsIds=");

		sb.append(formRecordsIds);
		sb.append(", id=");

		sb.append(id);
		sb.append(", name=");

		sb.append(name);
		sb.append(", structure=");

		sb.append(structure);
		sb.append(", structureId=");

		sb.append(structureId);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String[] availableLanguages;

	@GraphQLField
	@JsonProperty
	protected Long contentSpace;

	@GraphQLField
	@JsonProperty
	protected Creator creator;

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;

	@GraphQLField
	@JsonProperty
	protected Date dateModified;

	@GraphQLField
	@JsonProperty
	protected Date datePublished;

	@GraphQLField
	@JsonProperty
	protected String defaultLanguage;

	@GraphQLField
	@JsonProperty
	protected String description;

	@GraphQLField
	@JsonProperty
	protected FormRecord[] formRecords;

	@GraphQLField
	@JsonProperty
	protected Long[] formRecordsIds;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected FormStructure structure;

	@GraphQLField
	@JsonProperty
	protected Long structureId;

}