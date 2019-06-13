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

package com.liferay.talend.runtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import com.liferay.talend.avro.EndpointSchemaInferrer;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.exception.MalformedURLException;
import com.liferay.talend.openapi.Parameter;
import com.liferay.talend.openapi.constants.OpenAPIConstants;
import com.liferay.talend.runtime.client.RESTClient;
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.i18n.TranslatableImpl;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class LiferaySourceOrSink
	extends TranslatableImpl
	implements LiferaySourceOrSinkRuntime, SourceOrSink {

	public JsonNode doDeleteRequest(RuntimeContainer runtimeContainer) {
		return doDeleteRequest(runtimeContainer, null);
	}

	public JsonNode doDeleteRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeDeleteRequest();

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doDeleteRequest(String resourceURL) {
		return doDeleteRequest(null, resourceURL);
	}

	public JsonNode doGetRequest(RuntimeContainer runtimeContainer) {
		return doGetRequest(runtimeContainer, null);
	}

	public JsonNode doGetRequest(
		RuntimeContainer runtimeContainer, String resourceURL) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executeGetRequest();

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doGetRequest(String resourceURL) {
		return doGetRequest(null, resourceURL);
	}

	public JsonNode doPatchRequest(
			RuntimeContainer runtimeContainer, JsonNode jsonNode)
		throws IOException {

		return doPatchRequest(runtimeContainer, null, jsonNode);
	}

	public JsonNode doPatchRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonNode jsonNode) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePatchRequest(jsonNode);

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doPatchRequest(String resourceURL, JsonNode jsonNode) {
		return doPatchRequest(null, resourceURL, jsonNode);
	}

	public JsonNode doPostRequest(
		RuntimeContainer runtimeContainer, JsonNode jsonNode) {

		return doPostRequest(runtimeContainer, null, jsonNode);
	}

	public JsonNode doPostRequest(
		RuntimeContainer runtimeContainer, String resourceURL,
		JsonNode jsonNode) {

		RESTClient restClient = getRestClient(runtimeContainer, resourceURL);

		Response response = restClient.executePostRequest(jsonNode);

		return response.readEntity(JsonNode.class);
	}

	public JsonNode doPostRequest(String resourceURL, JsonNode jsonNode)
		throws IOException {

		return doPostRequest(null, resourceURL, jsonNode);
	}

	@Override
	public List<NamedThing> getAvailableWebSites() throws IOException {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		URL serverURL = URIUtils.extractServerURL(
			URIUtils.toURL(liferayConnectionProperties.getApiSpecURL()));

		UriBuilder uriBuilder = UriBuilder.fromPath(serverURL.toExternalForm());

		URI myUserAccountURI = uriBuilder.path(
			"o/headless-admin-user/v1.0/my-user-account"
		).build();

		JsonNode myUserAccountJsonNode = doGetRequest(
			myUserAccountURI.toASCIIString());

		JsonNode siteBriefsJsonNode = myUserAccountJsonNode.path("siteBriefs");

		List<NamedThing> webSitesList = new ArrayList<>();

		if (!siteBriefsJsonNode.isArray()) {
			return webSitesList;
		}

		for (JsonNode siteBriefJsonNode : siteBriefsJsonNode) {
			JsonNode idJsonNode = siteBriefJsonNode.path("id");
			JsonNode nameJsonNode = siteBriefJsonNode.path("name");

			webSitesList.add(
				new SimpleNamedThing(
					idJsonNode.asText(), nameJsonNode.asText()));
		}

		Comparator<NamedThing> comparator = Comparator.comparing(
			NamedThing::getDisplayName);

		Collections.sort(webSitesList, comparator);

		return webSitesList;
	}

	public LiferayConnectionProperties getConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			liferayConnectionPropertiesProvider.
				getLiferayConnectionProperties();

		if (liferayConnectionProperties.getReferencedComponentId() != null) {
			liferayConnectionProperties =
				liferayConnectionProperties.getReferencedConnectionProperties();
		}

		return liferayConnectionProperties;
	}

	public LiferayConnectionProperties getEffectiveConnection(
		RuntimeContainer runtimeContainer) {

		LiferayConnectionProperties liferayConnectionProperties =
			liferayConnectionPropertiesProvider.
				getLiferayConnectionProperties();

		String referencedComponentId =
			liferayConnectionProperties.getReferencedComponentId();

		// Using another component's connection

		if (referencedComponentId != null) {

			// In a runtime container

			if (runtimeContainer != null) {
				LiferayConnectionProperties sharedLiferayConnectionProperties =
					(LiferayConnectionProperties)
						runtimeContainer.getComponentData(
							referencedComponentId, KEY_CONNECTION_PROPERTIES);

				if (sharedLiferayConnectionProperties != null) {
					return sharedLiferayConnectionProperties;
				}
			}

			// Design time

			liferayConnectionProperties =
				liferayConnectionProperties.getReferencedConnectionProperties();
		}

		if (runtimeContainer != null) {
			runtimeContainer.setComponentData(
				runtimeContainer.getCurrentComponentId(),
				KEY_CONNECTION_PROPERTIES, liferayConnectionProperties);
		}

		return liferayConnectionProperties;
	}

	@Override
	public Set<String> getEndpointList(String operation) {
		Set<String> endpoints = new TreeSet<>();

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonNode apiSpecJsonNode = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		JsonNode pathsJsonNode = apiSpecJsonNode.path(OpenAPIConstants.PATHS);

		Iterator<Map.Entry<String, JsonNode>> pathFields =
			pathsJsonNode.fields();

		while (pathFields.hasNext()) {
			Map.Entry<String, JsonNode> pathEntry = pathFields.next();

			JsonNode pathJsonNode = pathEntry.getValue();

			Iterator<String> operationNameIterator = pathJsonNode.fieldNames();

			while (operationNameIterator.hasNext()) {
				String operationName = operationNameIterator.next();

				if (!Objects.equals(operation, _toUpperCase(operationName))) {
					continue;
				}

				if (!Objects.equals(operation, HttpMethod.GET)) {
					endpoints.add(pathEntry.getKey());

					continue;
				}

				if (_hasPath(
						OpenAPIConstants.PATH_RESPONSE_SCHEMA_REFERENCE,
						pathJsonNode.get(operationName)) ||
					_hasPath(
						OpenAPIConstants.PATH_RESPONSE_SCHEMA_ITEMS_REFERENCE,
						pathJsonNode.get(operationName))) {

					endpoints.add(pathEntry.getKey());
				}
			}
		}

		return endpoints;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public Schema getEndpointSchema(
			RuntimeContainer runtimeContainer, String endpoint)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Schema getEndpointSchema(String endpoint, String operation)
		throws IOException {

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonNode apiSpecJsonNode = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		return EndpointSchemaInferrer.inferSchema(
			endpoint, operation, apiSpecJsonNode);
	}

	@Override
	public List<Parameter> getParameters(String endpoint, String operation) {
		List<Parameter> parameters = new ArrayList<>();

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		String apiSpecURLHref = liferayConnectionProperties.getApiSpecURL();

		JsonNode apiSpecJsonNode = doGetRequest(apiSpecURLHref);

		ArrayNode parametersArrayNode = (ArrayNode)_getChildNode(
			apiSpecJsonNode, OpenAPIConstants.PATHS, endpoint,
			_toLowerCase(operation), OpenAPIConstants.PARAMETRERS);

		for (int i = 0; i < parametersArrayNode.size(); i++) {
			parameters.add(_toParameter(parametersArrayNode.get(i)));
		}

		return parameters;
	}

	public RESTClient getRestClient(RuntimeContainer runtimeContainer) {
		return getRestClient(runtimeContainer, null);
	}

	public RESTClient getRestClient(
		RuntimeContainer runtimeContainer, String resourceURL) {

		if ((resourceURL == null) || resourceURL.isEmpty()) {
			if (restClient != null) {
				return restClient;
			}

			restClient = new RESTClient(
				getEffectiveConnection(runtimeContainer));

			return restClient;
		}

		if ((restClient != null) && restClient.matches(resourceURL)) {
			return restClient;
		}

		return new RESTClient(
			getEffectiveConnection(runtimeContainer), resourceURL);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer runtimeContainer)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	public Set<String> getSupportedOperations(String endpoint) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(null);

		JsonNode apiSpecJsonNode = doGetRequest(
			liferayConnectionProperties.getApiSpecURL());

		JsonNode endpointJsonNode = apiSpecJsonNode.path(
			OpenAPIConstants.PATHS
		).path(
			endpoint
		);

		Iterator<String> operationsIterator = endpointJsonNode.fieldNames();

		Set<String> supportedOperations = new TreeSet<>();

		operationsIterator.forEachRemaining(supportedOperations::add);

		return supportedOperations;
	}

	@Override
	public ValidationResult initialize(
		RuntimeContainer runtimeContainer,
		ComponentProperties componentProperties) {

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		liferayConnectionPropertiesProvider =
			(LiferayConnectionPropertiesProvider)componentProperties;

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		try {
			getRestClient(runtimeContainer);
		}
		catch (TalendRuntimeException tre) {
			return ExceptionUtils.exceptionToValidationResult(tre);
		}

		return validationResultMutable;
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveConnection(runtimeContainer);

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		try {
			URIUtils.validateOpenAPISpecURL(
				liferayConnectionProperties.getApiSpecURL());
		}
		catch (MalformedURLException murle) {
			validationResultMutable.setMessage(murle.getMessage());
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			return validationResultMutable;
		}

		String target = liferayConnectionProperties.getApiSpecURL();
		String password = liferayConnectionProperties.getPassword();

		String userId = liferayConnectionProperties.getUserId();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Validate API spec URL: {}",
				liferayConnectionProperties.getApiSpecURL());
			_log.debug(
				"Validate user ID: {}",
				liferayConnectionProperties.getUserId());
		}

		if ((target == null) || target.isEmpty()) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.apiSpecURL"));

			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			return validationResultMutable;
		}

		if (!liferayConnectionProperties.isAnonymousLogin()) {
			_validateCredentials(userId, password, validationResultMutable);

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.ERROR) {

				return validationResultMutable;
			}
		}

		return validateConnection(
			liferayConnectionProperties, runtimeContainer);
	}

	@Override
	public ValidationResult validateConnection(
		LiferayConnectionPropertiesProvider liferayConnectionPropertiesProvider,
		RuntimeContainer runtimeContainer) {

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		try {
			doGetRequest(runtimeContainer);

			validationResultMutable.setMessage(
				i18nMessages.getMessage("success.validation.connection"));
		}
		catch (TalendRuntimeException tre) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection",
					tre.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_log.error(tre.getMessage(), tre);
		}
		catch (ProcessingException pe) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.jersey",
					pe.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_log.error(pe.getMessage(), pe);
		}
		catch (Throwable t) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.testconnection.general",
					t.getLocalizedMessage()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_log.error(t.getMessage(), t);
		}

		return validationResultMutable;
	}

	protected static final String KEY_CONNECTION_PROPERTIES = "Connection";

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferaySourceOrSink.class);
	}

	protected volatile LiferayConnectionPropertiesProvider
		liferayConnectionPropertiesProvider;
	protected RESTClient restClient;

	private JsonNode _getChildNode(JsonNode jsonNode, String... paths) {
		for (String path : paths) {
			jsonNode = jsonNode.path(path);
		}

		return jsonNode;
	}

	private boolean _hasPath(String path, JsonNode jsonNode) {
		if (!path.contains(">")) {
			return jsonNode.has(path);
		}

		int subpathEndIdx = path.indexOf(">");

		String subpath = path.substring(0, subpathEndIdx);

		if (jsonNode.has(subpath)) {
			return _hasPath(
				path.substring(subpathEndIdx + 1), jsonNode.path(subpath));
		}

		return false;
	}

	private String _toLowerCase(String value) {
		return value.toLowerCase(Locale.US);
	}

	private Parameter _toParameter(JsonNode jsonNode) {
		Parameter parameter = new Parameter();

		JsonNode inJsonNode = jsonNode.get("in");

		String inString = inJsonNode.asText();

		parameter.setType(Parameter.Type.valueOf(_toUpperCase(inString)));

		JsonNode nameJsonNode = jsonNode.get("name");

		parameter.setName(nameJsonNode.asText());

		JsonNode requiredJsonNode = jsonNode.get("required");

		if (requiredJsonNode != null) {
			parameter.setRequired(requiredJsonNode.asBoolean());
		}

		return parameter;
	}

	private String _toUpperCase(String value) {
		return value.toUpperCase(Locale.getDefault());
	}

	private void _validateCredentials(
		String userId, String password,
		ValidationResultMutable validationResultMutable) {

		if (_log.isDebugEnabled()) {
			_log.debug("Validating credentials...");
		}

		if ((userId == null) || userId.isEmpty()) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage("error.validation.connection.userId"));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			return;
		}

		if ((password == null) || password.isEmpty()) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.connection.password"));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferaySourceOrSink.class);

	private static final long serialVersionUID = 3109815759807236523L;

}