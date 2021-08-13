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

package com.liferay.template.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.template.constants.TemplatePortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TemplatePortletKeys.TEMPLATE,
		"mvc.command.name=/template/get_ddm_template"
	},
	service = MVCResourceCommand.class
)
public class GetDDMTemplateMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			long ddmTemplateId = ParamUtil.getLong(
				resourceRequest, "ddmTemplateId");

			DDMTemplate ddmTemplate = _ddmTemplateService.getTemplate(
				ddmTemplateId);

			String script = ddmTemplate.getScript();

			String contentType = null;

			String type = ddmTemplate.getType();

			String language = GetterUtil.getString(
				ddmTemplate.getLanguage(), TemplateConstants.LANG_TYPE_VM);

			if (type.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {
				contentType = ContentTypes.APPLICATION_JSON;
			}
			else if (language.equals(TemplateConstants.LANG_TYPE_XSL)) {
				contentType = ContentTypes.TEXT_XML_UTF8;
			}
			else {
				contentType = ContentTypes.TEXT_PLAIN_UTF8;
			}

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, null, script.getBytes(),
				contentType);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return false;
	}

	@Reference
	private DDMTemplateService _ddmTemplateService;

}