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

package com.liferay.headless.collaboration.dto.v1_0;

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
@GraphQLName("BlogPosting")
@XmlRootElement(name = "BlogPosting")
public class BlogPosting {

	public AggregateRating getAggregateRating() {
		return aggregateRating;
	}

	public String getAlternativeHeadline() {
		return alternativeHeadline;
	}

	public String getArticleBody() {
		return articleBody;
	}

	public String getCaption() {
		return caption;
	}

	public Categories[] getCategories() {
		return categories;
	}

	public Long[] getCategoryIds() {
		return categoryIds;
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

	public String getDescription() {
		return description;
	}

	public String getEncodingFormat() {
		return encodingFormat;
	}

	public String getFriendlyUrlPath() {
		return friendlyUrlPath;
	}

	public Boolean getHasComments() {
		return hasComments;
	}

	public String getHeadline() {
		return headline;
	}

	public Long getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public Long getImageId() {
		return imageId;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		this.aggregateRating = aggregateRating;
	}

	@JsonIgnore
	public void setAggregateRating(
		UnsafeSupplier<AggregateRating, Throwable>
			aggregateRatingUnsafeSupplier) {

		try {
			aggregateRating = aggregateRatingUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setAlternativeHeadline(String alternativeHeadline) {
		this.alternativeHeadline = alternativeHeadline;
	}

	@JsonIgnore
	public void setAlternativeHeadline(
		UnsafeSupplier<String, Throwable> alternativeHeadlineUnsafeSupplier) {

		try {
			alternativeHeadline = alternativeHeadlineUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setArticleBody(String articleBody) {
		this.articleBody = articleBody;
	}

	@JsonIgnore
	public void setArticleBody(
		UnsafeSupplier<String, Throwable> articleBodyUnsafeSupplier) {

		try {
			articleBody = articleBodyUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@JsonIgnore
	public void setCaption(
		UnsafeSupplier<String, Throwable> captionUnsafeSupplier) {

		try {
			caption = captionUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCategories(Categories[] categories) {
		this.categories = categories;
	}

	@JsonIgnore
	public void setCategories(
		UnsafeSupplier<Categories[], Throwable> categoriesUnsafeSupplier) {

		try {
			categories = categoriesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCategoryIds(Long[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	@JsonIgnore
	public void setCategoryIds(
		UnsafeSupplier<Long[], Throwable> categoryIdsUnsafeSupplier) {

		try {
			categoryIds = categoryIdsUnsafeSupplier.get();
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

	public void setEncodingFormat(String encodingFormat) {
		this.encodingFormat = encodingFormat;
	}

	@JsonIgnore
	public void setEncodingFormat(
		UnsafeSupplier<String, Throwable> encodingFormatUnsafeSupplier) {

		try {
			encodingFormat = encodingFormatUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setFriendlyUrlPath(String friendlyUrlPath) {
		this.friendlyUrlPath = friendlyUrlPath;
	}

	@JsonIgnore
	public void setFriendlyUrlPath(
		UnsafeSupplier<String, Throwable> friendlyUrlPathUnsafeSupplier) {

		try {
			friendlyUrlPath = friendlyUrlPathUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setHasComments(Boolean hasComments) {
		this.hasComments = hasComments;
	}

	@JsonIgnore
	public void setHasComments(
		UnsafeSupplier<Boolean, Throwable> hasCommentsUnsafeSupplier) {

		try {
			hasComments = hasCommentsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	@JsonIgnore
	public void setHeadline(
		UnsafeSupplier<String, Throwable> headlineUnsafeSupplier) {

		try {
			headline = headlineUnsafeSupplier.get();
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

	public void setImage(Image image) {
		this.image = image;
	}

	@JsonIgnore
	public void setImage(UnsafeSupplier<Image, Throwable> imageUnsafeSupplier) {
		try {
			image = imageUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	@JsonIgnore
	public void setImageId(
		UnsafeSupplier<Long, Throwable> imageIdUnsafeSupplier) {

		try {
			imageId = imageIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	@JsonIgnore
	public void setKeywords(
		UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier) {

		try {
			keywords = keywordsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(42);

		sb.append("{");

		sb.append("aggregateRating=");

		sb.append(aggregateRating);
		sb.append(", alternativeHeadline=");

		sb.append(alternativeHeadline);
		sb.append(", articleBody=");

		sb.append(articleBody);
		sb.append(", caption=");

		sb.append(caption);
		sb.append(", categories=");

		sb.append(categories);
		sb.append(", categoryIds=");

		sb.append(categoryIds);
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
		sb.append(", description=");

		sb.append(description);
		sb.append(", encodingFormat=");

		sb.append(encodingFormat);
		sb.append(", friendlyUrlPath=");

		sb.append(friendlyUrlPath);
		sb.append(", hasComments=");

		sb.append(hasComments);
		sb.append(", headline=");

		sb.append(headline);
		sb.append(", id=");

		sb.append(id);
		sb.append(", image=");

		sb.append(image);
		sb.append(", imageId=");

		sb.append(imageId);
		sb.append(", keywords=");

		sb.append(keywords);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected AggregateRating aggregateRating;

	@GraphQLField
	@JsonProperty
	protected String alternativeHeadline;

	@GraphQLField
	@JsonProperty
	protected String articleBody;

	@GraphQLField
	@JsonProperty
	protected String caption;

	@GraphQLField
	@JsonProperty
	protected Categories[] categories;

	@GraphQLField
	@JsonProperty
	protected Long[] categoryIds;

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
	protected String description;

	@GraphQLField
	@JsonProperty
	protected String encodingFormat;

	@GraphQLField
	@JsonProperty
	protected String friendlyUrlPath;

	@GraphQLField
	@JsonProperty
	protected Boolean hasComments;

	@GraphQLField
	@JsonProperty
	protected String headline;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected Image image;

	@GraphQLField
	@JsonProperty
	protected Long imageId;

	@GraphQLField
	@JsonProperty
	protected String[] keywords;

}