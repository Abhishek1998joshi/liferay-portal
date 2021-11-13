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

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;

/**
 * @author André de Oliveira
 */
public class ElementInstanceUtil {

	public static ElementInstance toElementInstance(String json) {
		return unpack(ElementInstance.unsafeToDTO(json));
	}

	public static ElementInstance[] toElementInstances(String json) {
		if (json == null) {
			return null;
		}

		try {
			return JSONUtil.toArray(
				JSONFactoryUtil.createJSONArray(json),
				jsonObject -> toElementInstance(jsonObject.toString()),
				ElementInstance.class);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static ElementInstance unpack(ElementInstance elementInstance) {
		if (elementInstance == null) {
			return null;
		}

		SXPElement sxpElement = elementInstance.getSxpElement();

		if (sxpElement != null) {
			elementInstance.setSxpElement(SXPElementUtil.unpack(sxpElement));
		}

		return elementInstance;
	}

	public static ElementInstance[] unpack(ElementInstance[] elementInstances) {
		for (int i = 0; i < elementInstances.length; i++) {
			elementInstances[i] = unpack(elementInstances[i]);
		}

		return elementInstances;
	}

}