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
long accountEntryId = ParamUtil.getLong(request, "accountEntryId");

String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNull(backURL)) {
	PortletURL viewAccountRolesURL = renderResponse.createRenderURL();

	viewAccountRolesURL.setParameter("mvcRenderCommandName", "/account_admin/edit_account_entry");
	viewAccountRolesURL.setParameter("screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_ROLES);
	viewAccountRolesURL.setParameter("accountEntryId", String.valueOf(accountEntryId));

	backURL = viewAccountRolesURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "add-new-role"));
%>

<portlet:actionURL name="/account_admin/edit_account_role" var="editAccountRoleURL" />

<liferay-frontend:edit-form
	action="<%= editAccountRoleURL %>"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= String.valueOf(accountEntryId) %>" />

		<aui:model-context model="<%= Role.class %>" />

		<aui:input helpMessage="title-field-help" name="title" />
		<aui:input name="description" />

		<%
		String nameLabel = LanguageUtil.get(request, "role-key");
		%>

		<liferay-ui:error exception="<%= RoleNameException.class %>">
			<p>
				<liferay-ui:message arguments="<%= new String[] {nameLabel, RoleConstants.getNameGeneralRestrictions(locale, PropsValues.ROLES_NAME_ALLOW_NUMERIC), RoleConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
			</p>

			<p>
				<liferay-ui:message arguments="<%= new String[] {nameLabel, RoleConstants.NAME_INVALID_CHARACTERS} %>" key="the-x-cannot-contain-the-following-invalid-characters-x" />
			</p>
		</liferay-ui:error>

		<aui:input helpMessage="key-field-help" label="key" name="name" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script require="frontend-js-web/liferay/debounce/debounce.es as debounceModule">
	var form = document.getElementById('<portlet:namespace />fm');

	if (form) {
		var nameInput = form.querySelector('#<portlet:namespace />name');
		var titleInput = form.querySelector('#<portlet:namespace />title');

		if (nameInput && titleInput) {
			var debounce = debounceModule.default;

			var handleOnTitleInput = function(event) {
				var value = event.target.value;

				if (nameInput.hasAttribute('maxLength')) {
					value = value.substring(0, nameInput.getAttribute('maxLength'));
				}

				nameInput.value = value;
			};

			titleInput.addEventListener('input', debounce(handleOnTitleInput, 200));
		}
	}
</aui:script>