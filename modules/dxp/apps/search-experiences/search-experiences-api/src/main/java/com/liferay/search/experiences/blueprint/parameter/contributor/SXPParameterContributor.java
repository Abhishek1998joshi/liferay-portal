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

package com.liferay.search.experiences.blueprint.parameter.contributor;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public interface SXPParameterContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		List<SXPParameter> sxpParameters);

}