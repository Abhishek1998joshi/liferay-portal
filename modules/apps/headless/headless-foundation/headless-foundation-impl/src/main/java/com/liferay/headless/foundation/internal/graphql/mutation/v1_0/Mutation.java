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

package com.liferay.headless.foundation.internal.graphql.mutation.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLInvokeDetached
	public boolean deleteCategory(@GraphQLName("category-id") Long categoryId)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		return categoryResource.deleteCategory(categoryId);
	}

	@GraphQLInvokeDetached
	public Category putCategory(
			@GraphQLName("category-id") Long categoryId,
			@GraphQLName("Category") Category category)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		return categoryResource.putCategory(categoryId, category);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Category postCategoryCategory(
			@GraphQLName("category-id") Long categoryId,
			@GraphQLName("Category") Category category)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		return categoryResource.postCategoryCategory(categoryId, category);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Category postVocabularyCategory(
			@GraphQLName("vocabulary-id") Long vocabularyId,
			@GraphQLName("Category") Category category)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		return categoryResource.postVocabularyCategory(vocabularyId, category);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Keyword postContentSpaceKeyword(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("Keyword") Keyword keyword)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		return keywordResource.postContentSpaceKeyword(contentSpaceId, keyword);
	}

	@GraphQLInvokeDetached
	public boolean deleteKeyword(@GraphQLName("keyword-id") Long keywordId)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		return keywordResource.deleteKeyword(keywordId);
	}

	@GraphQLInvokeDetached
	public Keyword putKeyword(
			@GraphQLName("keyword-id") Long keywordId,
			@GraphQLName("Keyword") Keyword keyword)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		return keywordResource.putKeyword(keywordId, keyword);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount postUserAccount(
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		return userAccountResource.postUserAccount(multipartBody);
	}

	@GraphQLInvokeDetached
	public boolean deleteUserAccount(
			@GraphQLName("user-account-id") Long userAccountId)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		return userAccountResource.deleteUserAccount(userAccountId);
	}

	@GraphQLInvokeDetached
	public UserAccount putUserAccount(
			@GraphQLName("user-account-id") Long userAccountId,
			@GraphQLName("UserAccount") UserAccount userAccount)
		throws Exception {

		UserAccountResource userAccountResource = _createUserAccountResource();

		return userAccountResource.putUserAccount(userAccountId, userAccount);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Vocabulary postContentSpaceVocabulary(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("Vocabulary") Vocabulary vocabulary)
		throws Exception {

		VocabularyResource vocabularyResource = _createVocabularyResource();

		return vocabularyResource.postContentSpaceVocabulary(
			contentSpaceId, vocabulary);
	}

	@GraphQLInvokeDetached
	public boolean deleteVocabulary(
			@GraphQLName("vocabulary-id") Long vocabularyId)
		throws Exception {

		VocabularyResource vocabularyResource = _createVocabularyResource();

		return vocabularyResource.deleteVocabulary(vocabularyId);
	}

	@GraphQLInvokeDetached
	public Vocabulary putVocabulary(
			@GraphQLName("vocabulary-id") Long vocabularyId,
			@GraphQLName("Vocabulary") Vocabulary vocabulary)
		throws Exception {

		VocabularyResource vocabularyResource = _createVocabularyResource();

		return vocabularyResource.putVocabulary(vocabularyId, vocabulary);
	}

	private static CategoryResource _createCategoryResource() throws Exception {
		CategoryResource categoryResource =
			_categoryResourceServiceTracker.getService();

		categoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return categoryResource;
	}

	private static final ServiceTracker<CategoryResource, CategoryResource>
		_categoryResourceServiceTracker;

	private static KeywordResource _createKeywordResource() throws Exception {
		KeywordResource keywordResource =
			_keywordResourceServiceTracker.getService();

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return keywordResource;
	}

	private static final ServiceTracker<KeywordResource, KeywordResource>
		_keywordResourceServiceTracker;

	private static UserAccountResource _createUserAccountResource()
		throws Exception {

		UserAccountResource userAccountResource =
			_userAccountResourceServiceTracker.getService();

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return userAccountResource;
	}

	private static final ServiceTracker
		<UserAccountResource, UserAccountResource>
			_userAccountResourceServiceTracker;

	private static VocabularyResource _createVocabularyResource()
		throws Exception {

		VocabularyResource vocabularyResource =
			_vocabularyResourceServiceTracker.getService();

		vocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return vocabularyResource;
	}

	private static final ServiceTracker<VocabularyResource, VocabularyResource>
		_vocabularyResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

		ServiceTracker<CategoryResource, CategoryResource>
			categoryResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), CategoryResource.class, null);

		categoryResourceServiceTracker.open();

		_categoryResourceServiceTracker = categoryResourceServiceTracker;
		ServiceTracker<KeywordResource, KeywordResource>
			keywordResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), KeywordResource.class, null);

		keywordResourceServiceTracker.open();

		_keywordResourceServiceTracker = keywordResourceServiceTracker;
		ServiceTracker<UserAccountResource, UserAccountResource>
			userAccountResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), UserAccountResource.class, null);

		userAccountResourceServiceTracker.open();

		_userAccountResourceServiceTracker = userAccountResourceServiceTracker;
		ServiceTracker<VocabularyResource, VocabularyResource>
			vocabularyResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), VocabularyResource.class, null);

		vocabularyResourceServiceTracker.open();

		_vocabularyResourceServiceTracker = vocabularyResourceServiceTracker;
	}

}