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

package com.liferay.headless.collaboration.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseBlogPostingResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-collaboration/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteBlogPosting() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetBlogPosting() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetContentSpaceBlogPostingsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPatchBlogPosting() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceBlogPosting() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutBlogPosting() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertEquals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		Assert.assertTrue(
			blogPosting1 + " does not equal " + blogPosting2,
			equals(blogPosting1, blogPosting2));
	}

	protected void assertEquals(
		List<BlogPosting> blogPostings1, List<BlogPosting> blogPostings2) {

		Assert.assertEquals(blogPostings1.size(), blogPostings2.size());

		for (int i = 0; i < blogPostings1.size(); i++) {
			BlogPosting blogPosting1 = blogPostings1.get(i);
			BlogPosting blogPosting2 = blogPostings2.get(i);

			assertEquals(blogPosting1, blogPosting2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BlogPosting> blogPostings1, List<BlogPosting> blogPostings2) {

		Assert.assertEquals(blogPostings1.size(), blogPostings2.size());

		for (BlogPosting blogPosting1 : blogPostings1) {
			boolean contains = false;

			for (BlogPosting blogPosting2 : blogPostings2) {
				if (equals(blogPosting1, blogPosting2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				blogPostings2 + " does not contain " + blogPosting1, contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Page<BlogPosting> page) {
		boolean valid = false;

		Collection<BlogPosting> blogPostings = page.getItems();

		int size = blogPostings.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		if (blogPosting1 == blogPosting2) {
			return true;
		}

		return false;
	}

	protected boolean invokeDeleteBlogPosting(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteBlogPostingResponse(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPosting invokeGetBlogPosting(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPosting.class);
	}

	protected Http.Response invokeGetBlogPostingResponse(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<BlogPosting> invokeGetContentSpaceBlogPostingsPage(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceBlogPostingsLocation(
				contentSpaceId, filterString, pagination, sortString));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<BlogPosting>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceBlogPostingsPageResponse(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceBlogPostingsLocation(
				contentSpaceId, filterString, pagination, sortString));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPosting invokePatchBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPosting.class);
	}

	protected Http.Response invokePatchBlogPostingResponse(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPosting invokePostContentSpaceBlogPosting(
			Long contentSpaceId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/blog-postings",
					contentSpaceId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPosting.class);
	}

	protected Http.Response invokePostContentSpaceBlogPostingResponse(
			Long contentSpaceId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/blog-postings",
					contentSpaceId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPosting invokePutBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPosting.class);
	}

	protected Http.Response invokePutBlogPostingResponse(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/blog-postings/{blog-posting-id}", blogPostingId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPosting randomBlogPosting() {
		return new BlogPosting() {
			{
				alternativeHeadline = RandomTestUtil.randomString();
				articleBody = RandomTestUtil.randomString();
				caption = RandomTestUtil.randomString();
				contentSpace = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				hasComments = RandomTestUtil.randomBoolean();
				headline = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				imageId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public int getItemsPerPage() {
			return itemsPerPage;
		}

		public int getLastPageNumber() {
			return lastPageNumber;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public int getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected int itemsPerPage;

		@JsonProperty
		protected int lastPageNumber;

		@JsonProperty("page")
		protected int pageNumber;

		@JsonProperty
		protected int totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _getContentSpaceBlogPostingsLocation(
		Long contentSpaceId, String filterString, Pagination pagination,
		String sortString) {

		String url =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/blog-postings",
					contentSpaceId);

		url += "?filter=" + URLCodec.encodeURL(filterString);
		url += "&page=" + pagination.getPageNumber();
		url += "&pageSize=" + pagination.getItemsPerPage();
		url += "&sort=" + URLCodec.encodeURL(sortString);

		return url;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}