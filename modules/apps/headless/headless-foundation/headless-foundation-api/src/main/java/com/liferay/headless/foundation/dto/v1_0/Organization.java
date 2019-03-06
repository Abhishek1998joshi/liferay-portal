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

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Organization")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Organization")
public class Organization {

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<String, Exception> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected String comment;

	@Schema(description = "https://www.schema.org/ContactInformation")
	public ContactInformation getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}

	@JsonIgnore
	public void setContactInformation(
		UnsafeSupplier<ContactInformation, Exception>
			contactInformationUnsafeSupplier) {

		try {
			contactInformation = contactInformationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected ContactInformation contactInformation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;

	@Schema(description = "https://www.schema.org/PostalAddress")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@JsonIgnore
	public void setLocation(
		UnsafeSupplier<Location, Exception> locationUnsafeSupplier) {

		try {
			location = locationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Location location;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@JsonIgnore
	public void setLogo(UnsafeSupplier<String, Exception> logoUnsafeSupplier) {
		try {
			logo = logoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected String logo;

	public UserAccount[] getMembers() {
		return members;
	}

	public void setMembers(UserAccount[] members) {
		this.members = members;
	}

	@JsonIgnore
	public void setMembers(
		UnsafeSupplier<UserAccount[], Exception> membersUnsafeSupplier) {

		try {
			members = membersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected UserAccount[] members;

	public Long[] getMembersIds() {
		return membersIds;
	}

	public void setMembersIds(Long[] membersIds) {
		this.membersIds = membersIds;
	}

	@JsonIgnore
	public void setMembersIds(
		UnsafeSupplier<Long[], Exception> membersIdsUnsafeSupplier) {

		try {
			membersIds = membersIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] membersIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected String name;

	public Organization getParentOrganization() {
		return parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	@JsonIgnore
	public void setParentOrganization(
		UnsafeSupplier<Organization, Exception>
			parentOrganizationUnsafeSupplier) {

		try {
			parentOrganization = parentOrganizationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Organization parentOrganization;

	public Long getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	@JsonIgnore
	public void setParentOrganizationId(
		UnsafeSupplier<Long, Exception> parentOrganizationIdUnsafeSupplier) {

		try {
			parentOrganizationId = parentOrganizationIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Long parentOrganizationId;

	@Schema(description = "https://www.schema.org/Service")
	public Services[] getServices() {
		return services;
	}

	public void setServices(Services[] services) {
		this.services = services;
	}

	@JsonIgnore
	public void setServices(
		UnsafeSupplier<Services[], Exception> servicesUnsafeSupplier) {

		try {
			services = servicesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Services[] services;

	public Organization[] getSubOrganization() {
		return subOrganization;
	}

	public void setSubOrganization(Organization[] subOrganization) {
		this.subOrganization = subOrganization;
	}

	@JsonIgnore
	public void setSubOrganization(
		UnsafeSupplier<Organization[], Exception>
			subOrganizationUnsafeSupplier) {

		try {
			subOrganization = subOrganizationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Organization[] subOrganization;

	public Long[] getSubOrganizationIds() {
		return subOrganizationIds;
	}

	public void setSubOrganizationIds(Long[] subOrganizationIds) {
		this.subOrganizationIds = subOrganizationIds;
	}

	@JsonIgnore
	public void setSubOrganizationIds(
		UnsafeSupplier<Long[], Exception> subOrganizationIdsUnsafeSupplier) {

		try {
			subOrganizationIds = subOrganizationIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] subOrganizationIds;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"comment\": ");

		sb.append("\"");
		sb.append(comment);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contactInformation\": ");

		sb.append(contactInformation);
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"location\": ");

		sb.append(location);
		sb.append(", ");

		sb.append("\"logo\": ");

		sb.append("\"");
		sb.append(logo);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"members\": ");

		if (members == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < members.length; i++) {
				sb.append(members[i]);

				if ((i + 1) < members.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"membersIds\": ");

		if (membersIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < membersIds.length; i++) {
				sb.append(membersIds[i]);

				if ((i + 1) < membersIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"parentOrganization\": ");

		sb.append(parentOrganization);
		sb.append(", ");

		sb.append("\"parentOrganizationId\": ");

		sb.append(parentOrganizationId);
		sb.append(", ");

		sb.append("\"services\": ");

		if (services == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < services.length; i++) {
				sb.append(services[i]);

				if ((i + 1) < services.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"subOrganization\": ");

		if (subOrganization == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < subOrganization.length; i++) {
				sb.append(subOrganization[i]);

				if ((i + 1) < subOrganization.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"subOrganizationIds\": ");

		if (subOrganizationIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < subOrganizationIds.length; i++) {
				sb.append(subOrganizationIds[i]);

				if ((i + 1) < subOrganizationIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}