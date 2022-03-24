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

package com.liferay.dynamic.data.mapping.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateNameComparator;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Attila Bakay
 */
@RunWith(Arquillian.class)
public class TemplateNameComparatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), defaultLocale);

		_ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class), defaultLocale);

		_ddmTemplate1.setName("default name A", defaultLocale);
		_ddmTemplate1.setName("spanish name A", _esLocale);

		_ddmTemplate2 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class), defaultLocale);

		_ddmTemplate2.setName("default name b", defaultLocale);
		_ddmTemplate2.setName("spanish name B", _esLocale);

		_ddmTemplate3 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class), defaultLocale);

		_ddmTemplate3.setName("default name c", defaultLocale);
		_ddmTemplate3.setName("spanish name C", _esLocale);

		_ddmTemplate4 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class), defaultLocale);

		_ddmTemplate4.setName("default name D", defaultLocale);
	}

	@Test
	public void testCompareWithDefaultLocale() {
		List<DDMTemplate> ddmStructures = new ArrayList<>();

		ddmStructures.add(_ddmTemplate2);
		ddmStructures.add(_ddmTemplate1);
		ddmStructures.add(_ddmTemplate3);
		ddmStructures.add(_ddmTemplate4);

		_templateNameComparator = new TemplateNameComparator(true);

		Collections.sort(ddmStructures, _templateNameComparator);

		Assert.assertEquals(_ddmTemplate1, ddmStructures.get(0));
		Assert.assertEquals(_ddmTemplate2, ddmStructures.get(1));
		Assert.assertEquals(_ddmTemplate3, ddmStructures.get(2));
		Assert.assertEquals(_ddmTemplate4, ddmStructures.get(3));
	}

	@Test
	public void testCompareWithLocale() {
		List<DDMTemplate> ddmStructures = new ArrayList<>();

		ddmStructures.add(_ddmTemplate2);
		ddmStructures.add(_ddmTemplate1);
		ddmStructures.add(_ddmTemplate3);
		ddmStructures.add(_ddmTemplate4);

		_templateNameComparator = new TemplateNameComparator(true, _esLocale);

		Collections.sort(ddmStructures, _templateNameComparator);

		Assert.assertEquals(_ddmTemplate4, ddmStructures.get(0));
		Assert.assertEquals(_ddmTemplate1, ddmStructures.get(1));
		Assert.assertEquals(_ddmTemplate2, ddmStructures.get(2));
		Assert.assertEquals(_ddmTemplate3, ddmStructures.get(3));
	}

	private DDMTemplate _ddmTemplate1;
	private DDMTemplate _ddmTemplate2;
	private DDMTemplate _ddmTemplate3;
	private DDMTemplate _ddmTemplate4;
	private final Locale _esLocale = LocaleUtil.SPAIN;

	@DeleteAfterTestRun
	private Group _group;

	private TemplateNameComparator _templateNameComparator;

}