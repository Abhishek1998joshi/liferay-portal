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

package com.liferay.headless.admin.list.type.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.client.pagination.Page;
import com.liferay.headless.admin.list.type.client.pagination.Pagination;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionResourceTest
	extends BaseListTypeDefinitionResourceTestCase {

	@Override
	@Test
	public void testGetListTypeDefinitionsPage() throws Exception {
		Page<ListTypeDefinition> page =
			listTypeDefinitionResource.getListTypeDefinitionsPage(
				null, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		ListTypeDefinition listTypeDefinition1 =
			testGetListTypeDefinitionsPage_addListTypeDefinition(
				randomListTypeDefinition());

		ListTypeDefinition listTypeDefinition2 =
			testGetListTypeDefinitionsPage_addListTypeDefinition(
				randomListTypeDefinition());

		page = listTypeDefinitionResource.getListTypeDefinitionsPage(
			null, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			listTypeDefinition1, (List<ListTypeDefinition>)page.getItems());
		assertContains(
			listTypeDefinition2, (List<ListTypeDefinition>)page.getItems());
		assertValid(page);

		listTypeDefinitionResource.deleteListTypeDefinition(
			listTypeDefinition1.getId());

		listTypeDefinitionResource.deleteListTypeDefinition(
			listTypeDefinition2.getId());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeDefinition() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeDefinitionNotFound() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeDefinitionsPage() throws Exception {
	}

	@Override
	protected ListTypeDefinition randomListTypeDefinition() throws Exception {
		ListTypeDefinition listTypeDefinition =
			super.randomListTypeDefinition();

		listTypeDefinition.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));

		ListTypeEntry listTypeEntry = new ListTypeEntry();

		listTypeEntry.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));
		listTypeEntry.setKey(RandomTestUtil.randomString());

		listTypeDefinition.setListTypeEntries(
			new ListTypeEntry[] {listTypeEntry});

		return listTypeDefinition;
	}

	@Override
	protected ListTypeDefinition
			testDeleteListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	@Override
	protected ListTypeDefinition
			testGetListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	@Override
	protected ListTypeDefinition
			testGetListTypeDefinitionsPage_addListTypeDefinition(
				ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _addListTypeDefinition(listTypeDefinition);
	}

	@Override
	protected ListTypeDefinition
			testGraphQLListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	@Override
	protected ListTypeDefinition
			testPostListTypeDefinition_addListTypeDefinition(
				ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _addListTypeDefinition(listTypeDefinition);
	}

	@Override
	protected ListTypeDefinition
			testPutListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	private ListTypeDefinition _addListTypeDefinition(
			ListTypeDefinition listTypeDefinition)
		throws Exception {

		return listTypeDefinitionResource.postListTypeDefinition(
			listTypeDefinition);
	}

}