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

package com.liferay.talend.service;

import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class LiferayServiceTest {

	@Test
	public void testGetPageableEndpoints() {
		LiferayService liferayService = new LiferayService();

		List<String> padeableEndpoints = liferayService.getPageableEndpoints(
			_getOpenApiJsonObject());

		Assert.assertFalse(
			"pageable endpoints are empty", padeableEndpoints.isEmpty());

		Assert.assertEquals(
			"pageable endpoints contain path", "/test/path",
			padeableEndpoints.get(0));
	}

	@Test
	public void testIsValidEndpointURL() {
		LiferayService liferayService = new LiferayService();

		Assert.assertTrue(
			liferayService.isValidEndpointURL(
				"http://localhost:8080/o/test-endpoint/v1.0/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidEndpointURL(
				"https://localhost:8080/o/test-endpoint/v1.0/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidEndpointURL(
				"https://localhost.com/o/test-endpoint/v1.0/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidEndpointURL(
				"https://test.com/o/test-endpoint/v1/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidEndpointURL(
				"https://test.com/o/test-endpoint/v1.0.23/openapi.yaml"));

		Assert.assertTrue(
			liferayService.isValidEndpointURL(
				"http://test.com:8080/o/test-endpoint/v1.0/openapi.json"));
	}

	private JsonObject _getOpenApiJsonObject() {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

		objectBuilder.add("key1", "value1");
		objectBuilder.add("key2", "value2");

		objectBuilder.add(
			"components",
			Json.createObjectBuilder(
			).add(
				"schemas",
				Json.createObjectBuilder(
				).add(
					"TestEntityUno",
					Json.createObjectBuilder(
					).add(
						"key", "value"
					)
				).add(
					"TestEntityDue",
					Json.createObjectBuilder(
					).add(
						"key", "value"
					)
				).add(
					"TestEntity",
					Json.createObjectBuilder(
					).add(
						"properties",
						Json.createObjectBuilder(
						).add(
							"items",
							Json.createObjectBuilder(
							).add(
								"type", "array"
							)
						).add(
							"page",
							Json.createObjectBuilder(
							).add(
								"type", "integer"
							)
						)
					)
				)
			));

		objectBuilder.add(
			"paths",
			Json.createObjectBuilder(
			).add(
				"/test/invalid/path/1",
				Json.createObjectBuilder(
				).add(
					"key", "value"
				)
			).add(
				"/test/invalid/path/3",
				Json.createObjectBuilder(
				).add(
					"key", "value"
				)
			).add(
				"/test/path",
				Json.createObjectBuilder(
				).add(
					"get",
					Json.createObjectBuilder(
					).add(
						"responses",
						Json.createObjectBuilder(
						).add(
							"default",
							Json.createObjectBuilder(
							).add(
								"content",
								Json.createObjectBuilder(
								).add(
									"application/json",
									Json.createObjectBuilder(
									).add(
										"schema",
										Json.createObjectBuilder(
										).add(
											"$ref",
											"#/components/schemas/TestEntity"
										)
									)
								)
							)
						)
					)
				)
			));

		return objectBuilder.build();
	}

}