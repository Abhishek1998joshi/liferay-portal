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

package com.liferay.knowledge.base.internal.search.spi.model.index.contributor;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tibor Lipusz
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = ModelDocumentContributor.class
)
public class KBArticleModelDocumentContributor
	implements ModelDocumentContributor<KBArticle> {

	@Override
	public void contribute(Document document, KBArticle baseModel) {
	}

}