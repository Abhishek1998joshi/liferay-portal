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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseEmailResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetGenericParentEmailsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetEmail() throws Exception {
			Assert.assertTrue(true);
	}

	protected void assertResponseCode(int expectedResponseCode, Http.Response actualResponse) {
		Assert.assertEquals(expectedResponseCode, actualResponse.getResponseCode());
	}

	protected Page<Email> invokeGetGenericParentEmailsPage(
				Object genericParentId,Pagination pagination)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/emails", genericParentId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetGenericParentEmailsPageResponse(
				Object genericParentId,Pagination pagination)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/emails", genericParentId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected Email invokeGetEmail(
				Long emailId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/emails/{email-id}", emailId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), EmailImpl.class);
	}

	protected Http.Response invokeGetEmailResponse(
				Long emailId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/emails/{email-id}", emailId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}

	protected Email randomEmail() {
		return new EmailImpl() {
			{

						email = RandomTestUtil.randomString();
						id = RandomTestUtil.randomLong();
						type = RandomTestUtil.randomString();
	}
		};
	}

	protected Group testGroup;

	protected static class EmailImpl implements Email {

	public String getEmail() {
				return email;
	}

	public void setEmail(String email) {
				this.email = email;
	}

	@JsonIgnore
	public void setEmail(
				UnsafeSupplier<String, Throwable> emailUnsafeSupplier) {

				try {
					email = emailUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String email;
	public Long getId() {
				return id;
	}

	public void setId(Long id) {
				this.id = id;
	}

	@JsonIgnore
	public void setId(
				UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {

				try {
					id = idUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long id;
	public String getType() {
				return type;
	}

	public void setType(String type) {
				this.type = type;
	}

	@JsonIgnore
	public void setType(
				UnsafeSupplier<String, Throwable> typeUnsafeSupplier) {

				try {
					type = typeUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String type;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(userNameAndPassword.getBytes());

		options.addHeader("Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object... values) {
		return template.replaceAll("\\{.*\\}", String.valueOf(values[0]));
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}