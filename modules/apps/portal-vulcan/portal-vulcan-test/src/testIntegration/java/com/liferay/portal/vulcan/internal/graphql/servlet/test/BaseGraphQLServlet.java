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

package com.liferay.portal.vulcan.internal.graphql.servlet.test;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public class BaseGraphQLServlet {

	public static class TestQuery {

		public static void setField(String field) {
			TestQuery._field = field;
		}

		public static void setId(long id) {
			TestQuery._id = id;
		}

		@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
		public BaseGraphQLServlet.TestQuery.TestDTO testDTO() throws Exception {
			return new TestDTO() {
				{
					id = _id;
				}
			};
		}

		public static class TestDTO {

			public long getId() {
				return id;
			}

			public void setId(long id) {
				this.id = id;
			}

			@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
			protected long id;

		}

		@GraphQLTypeExtension(TestDTO.class)
		public class TestGraphQLTypeExtension {

			public TestGraphQLTypeExtension(TestDTO testDTO) {
				_testDTO = testDTO;
			}

			@com.liferay.portal.vulcan.graphql.annotation.GraphQLField
			public String field() {
				return _field;
			}

			private final TestDTO _testDTO;

		}

		private static String _field;
		private static long _id;

	}

	public static class TestServletData implements ServletData {

		public TestServletData(String field, long id) {
			TestQuery.setField(field);
			TestQuery.setId(id);
		}

		@Override
		public Object getMutation() {
			return null;
		}

		@Override
		public String getPath() {
			return "/test-path-graphql/v1.0";
		}

		@Override
		public Object getQuery() {
			return new TestQuery();
		}

	}

	protected JSONObject invoke(GraphQLField graphQLField) throws Exception {
		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		options.setBody(
			new Http.Body(
				JSONUtil.put(
					"query", queryGraphQLField.toString()
				).toString(),
				ContentTypes.APPLICATION_JSON, "UTF-8"));

		options.setLocation("http://localhost:8080/o/graphql");
		options.setMethod(Http.Method.POST);

		return JSONFactoryUtil.createJSONObject(HttpUtil.URLtoString(options));
	}

	protected static class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

}