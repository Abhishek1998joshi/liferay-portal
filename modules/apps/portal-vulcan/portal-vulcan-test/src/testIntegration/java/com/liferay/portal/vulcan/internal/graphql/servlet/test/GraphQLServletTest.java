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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Luis Miguel Barcos
 */
@RunWith(Arquillian.class)
public class GraphQLServletTest extends BaseGraphQLServlet {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetDataFromGraphQLTypeExtension() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(GraphQLServletTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String field = RandomTestUtil.randomString();
		long id = RandomTestUtil.randomLong();

		ServiceRegistration<ServletData> serviceRegistration =
			bundleContext.registerService(
				ServletData.class, new TestServletData(field, id),
				null);

		GraphQLField graphQLField = new GraphQLField(
			"testDTO", new GraphQLField("id"), new GraphQLField("field"));

		JSONObject jsonObject = JSONUtil.getValueAsJSONObject(
			invoke(graphQLField), "JSONObject/data", "JSONObject/testDTO");

		Assert.assertEquals(jsonObject.get("field"), field);
		Assert.assertEquals(jsonObject.get("id"), id);

		serviceRegistration.unregister();
	}

}