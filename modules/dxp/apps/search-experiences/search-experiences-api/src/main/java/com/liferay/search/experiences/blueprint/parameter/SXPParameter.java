/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.blueprint.parameter;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public interface SXPParameter {

	public boolean evaluateContains(JSONObject jsonObject);

	public boolean evaluateEquals(JSONObject jsonObject);

	public boolean evaluateGreaterThan(
		boolean closedRange, JSONObject jsonObject);

	public boolean evaluateIn(JSONObject jsonObject);

	public boolean evaluateInRange(JSONObject jsonObject);

	public String evaluateTemplateVariable(Map<String, String> options);

	public String getName();

	public String getTemplateVariable();

	public Object getValue();

	public boolean isTemplateVariable();

}