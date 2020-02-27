<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
DepotApplicationDisplayContext depotApplicationDisplayContext = (DepotApplicationDisplayContext)request.getAttribute(DepotAdminWebKeys.DEPOT_APPLICATION_DISPLAY_CONTEXT);

PortletURL viewGroupSelectorURL = PortletURLUtil.clone(depotApplicationDisplayContext.getPortletURL(), liferayPortletResponse);

viewGroupSelectorURL.setParameter("groupType", "site");
viewGroupSelectorURL.setParameter("showGroupSelector", Boolean.TRUE.toString());
%>

<div class="container-fluid container-fluid-max-xl pt-4">
	<div class="alert alert-info">
		<span class="alert-indicator">
			<svg class="lexicon-icon lexicon-icon-info-circle" focusable="false" role="presentation">
				<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#info-circle" />
			</svg>
		</span>

		<%
		String taglibViewGroupSelectorLink = "<a href=\"" + HtmlUtil.escape(viewGroupSelectorURL.toString()) + "\">";
		%>

		<strong class="lead">Info:</strong><liferay-ui:message arguments='<%= new Object[] {taglibViewGroupSelectorLink, "</a>"} %>' key="application-is-not-supported.-please-go-back-to-selection" />
	</div>
</div>