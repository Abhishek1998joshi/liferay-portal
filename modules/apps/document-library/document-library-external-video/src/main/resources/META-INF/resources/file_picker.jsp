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
DLExternalVideo dlExternalVideo = (DLExternalVideo)request.getAttribute(DLExternalVideo.class.getName());
String getDLExternalVideoFieldsURL = (String)request.getAttribute(DLExternalVideoWebKeys.GET_EXTERNAL_VIDEO_FIELDS_URL);
String namespace = (String)request.getAttribute(DLExternalVideoWebKeys.NAMESPACE);
String onFilePickCallback = (String)request.getAttribute(DLExternalVideoWebKeys.ON_FILE_PICK_CALLBACK);
%>

<liferay-util:html-top
	outputKey="document_library_external_video_file_picker_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/file_picker.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="form-group">
	<aui:input label="video-url" name="externalVideoURL" value="<%= (dlExternalVideo != null) ? dlExternalVideo.getURL() : null %>" wrapperCssClass="mb-0" />

	<p class="form-text"><liferay-ui:message key="video-url-help" /></p>

	<div class="file-picker-preview-video">
		<div class="file-picker-preview-video-container">
			<c:choose>
				<c:when test="<%= dlExternalVideo != null %>">
					<%= dlExternalVideo.getEmbeddableHTML() %>
				</c:when>
				<c:otherwise>
					<div class="file-picker-preview-video-placeholder">
						<clay:icon
							symbol="video"
						/>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<react:component
		module="js/ExternalVideoPreview"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"externalVideoHTML", (dlExternalVideo != null) ? dlExternalVideo.getEmbeddableHTML() : ""
			).put(
				"externalVideoURL", (dlExternalVideo != null) ? dlExternalVideo.getURL() : ""
			).put(
				"getDLExternalVideoFieldsURL", getDLExternalVideoFieldsURL
			).put(
				"namespace", namespace
			).put(
				"onFilePickCallback", onFilePickCallback
			).build()
		%>'
	/>
</div>