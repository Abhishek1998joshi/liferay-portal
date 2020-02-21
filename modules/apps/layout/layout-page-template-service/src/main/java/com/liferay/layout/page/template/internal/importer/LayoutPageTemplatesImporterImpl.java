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

package com.liferay.layout.page.template.internal.importer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageTemplate;
import com.liferay.headless.delivery.dto.v1_0.PageTemplateCollection;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = LayoutPageTemplatesImporter.class)
public class LayoutPageTemplatesImporterImpl
	implements LayoutPageTemplatesImporter {

	@Override
	public void importFile(
			long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			Map<String, PageTemplateCollectionEntry>
				pageTemplateCollectionEntryMap =
					_getPageTemplateCollectionEntryMap(zipFile);

			if (MapUtil.isEmpty(pageTemplateCollectionEntryMap)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No valid layout page template entries found in " +
							zipFile.getName());
				}
			}
		}
	}

	private PageDefinition _getPageDefinition(String fileName, ZipFile zipFile)
		throws IOException {

		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.FORWARD_SLASH) + 1);

		ZipEntry zipEntry = zipFile.getEntry(
			path +
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_DEFINITION);

		if (zipEntry == null) {
			return null;
		}

		String content = StringUtil.read(zipFile.getInputStream(zipEntry));

		return _objectMapper.readValue(content, PageDefinition.class);
	}

	private Map<String, PageTemplateCollectionEntry>
			_getPageTemplateCollectionEntryMap(ZipFile zipFile)
		throws IOException {

		Map<String, PageTemplateCollectionEntry> pageTemplateCollectionMap =
			new HashMap<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isPageTemplateCollectionFile(zipEntry.getName())) {

				continue;
			}

			String[] path = StringUtil.split(
				zipEntry.getName(), CharPool.SLASH);

			String pageTemplateCollectionKey = path[1];

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			PageTemplateCollection pageTemplateCollection =
				_objectMapper.readValue(content, PageTemplateCollection.class);

			pageTemplateCollectionMap.put(
				pageTemplateCollectionKey,
				new PageTemplateCollectionEntry(
					pageTemplateCollectionKey, pageTemplateCollection));
		}

		enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isPageTemplateFile(zipEntry.getName())) {

				continue;
			}

			String[] path = StringUtil.split(
				zipEntry.getName(), CharPool.SLASH);

			PageTemplateCollectionEntry pageTemplateCollectionEntry =
				pageTemplateCollectionMap.get(path[1]);

			if (pageTemplateCollectionEntry == null) {
				continue;
			}

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			PageTemplate pageTemplate = _objectMapper.readValue(
				content, PageTemplate.class);

			pageTemplateCollectionEntry.addPageTemplateEntry(
				path[2],
				new PageTemplateEntry(
					pageTemplate,
					_getPageDefinition(zipEntry.getName(), zipFile)));
		}

		return pageTemplateCollectionMap;
	}

	private boolean _isPageTemplateCollectionFile(String fileName) {
		String[] path = StringUtil.split(fileName, CharPool.SLASH);

		if ((path.length == 3) && Objects.equals(path[0], _ROOT_FOLDER) &&
			Objects.equals(
				path[2],
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_TEMPLATE_COLLECTION)) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateFile(String fileName) {
		String[] path = StringUtil.split(fileName, CharPool.SLASH);

		if ((path.length == 4) && Objects.equals(path[0], _ROOT_FOLDER) &&
			Objects.equals(
				path[3],
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_TEMPLATE)) {

			return true;
		}

		return false;
	}

	private static final String _ROOT_FOLDER = "page-templates";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplatesImporterImpl.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	private class PageTemplateCollectionEntry {

		public PageTemplateCollectionEntry(
			String key, PageTemplateCollection pageTemplateCollection) {

			_key = key;
			_pageTemplateCollection = pageTemplateCollection;

			_pageTemplateEntries = new HashMap<>();
		}

		public void addPageTemplateEntry(
			String key, PageTemplateEntry pageTemplateEntry) {

			_pageTemplateEntries.put(key, pageTemplateEntry);
		}

		private final String _key;
		private final PageTemplateCollection _pageTemplateCollection;
		private final Map<String, PageTemplateEntry> _pageTemplateEntries;

	}

	private class PageTemplateEntry {

		public PageTemplateEntry(
			PageTemplate pageTemplate, PageDefinition pageDefinition) {

			_pageTemplate = pageTemplate;
			_pageDefinition = pageDefinition;
		}

		private final PageDefinition _pageDefinition;
		private final PageTemplate _pageTemplate;

	}

}