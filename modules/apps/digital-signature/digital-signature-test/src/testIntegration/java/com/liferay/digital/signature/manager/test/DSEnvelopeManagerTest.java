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

package com.liferay.digital.signature.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DSEnvelopeManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddDSEnvelope() throws Exception {
		Class<?> clazz = getClass();

		List<DSDocument> documentsList = new ArrayList<>();

		documentsList.add(
			new DSDocument() {
				{
					data = Base64.encode(
						FileUtil.getBytes(
							clazz.getResourceAsStream(
								"dependencies/Document_1.pdf")));
					dsDocumentId = "1";
					name = "Document 1";
				}
			});

		List<DSRecipient> recipientsList = new ArrayList<>();

		recipientsList.add(
			new DSRecipient() {
				{
					dsRecipientId = "1";
					emailAddress = "joseabelenda@gmail.com";
					name = "José Abelenda";
				}
			});

		DSEnvelope dsEnvelope = _dsEnvelopeManager.addDSEnvelope(
			TestPropsValues.getGroupId(),
			new DSEnvelope() {
				{
					dsDocuments = documentsList;
					dsRecipients = recipientsList;
					emailBlurb = "Please, sign the documents";
					emailSubject = "New " + System.currentTimeMillis();
					status = "sent";
				}
			});

		Assert.assertTrue(Validator.isNotNull(dsEnvelope.getDSEnvelopeId()));
	}

	@Test
	public void testGetDSEnvelope() throws Exception {
		String expectedEmailSubject = "New " + System.currentTimeMillis();

		DSEnvelope dsEnvelope = _dsEnvelopeManager.addDSEnvelope(
			TestPropsValues.getGroupId(),
			new DSEnvelope() {
				{
					emailSubject = expectedEmailSubject;
					status = "created";
				}
			});

		dsEnvelope = _dsEnvelopeManager.getDSEnvelope(
			TestPropsValues.getGroupId(), dsEnvelope.getDSEnvelopeId());

		Assert.assertEquals(expectedEmailSubject, dsEnvelope.getEmailSubject());
	}

	@Inject
	private DSEnvelopeManager _dsEnvelopeManager;

}