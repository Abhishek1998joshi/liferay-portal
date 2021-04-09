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

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayAttachment;
import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayCaseResult;
import com.liferay.jenkins.results.parser.testray.TestrayFactory;
import com.liferay.jenkins.results.parser.testray.TestrayProject;
import com.liferay.jenkins.results.parser.testray.TestrayRoutine;
import com.liferay.jenkins.results.parser.testray.TestrayServer;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestHistoryMap
	extends HashMap<Map<String, String>, TestHistoryMap.TestHistory> {

	public TestHistoryMap(String jenkinsJobURL, int maxBuildCount) {
		populate(jenkinsJobURL, maxBuildCount);
	}

	public TestHistoryMap(
		String testrayServerName, String projectName, String routineName,
		int maxBuildCount) {

		populate(testrayServerName, projectName, routineName, maxBuildCount);
	}

	public void populate(String jenkinsJobURL, int maxBuildCount) {
		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		List<String> buildResultJsonURLs =
			JenkinsResultsParserUtil.getBuildResultJsonURLs(
				jenkinsJobURL, maxBuildCount);

		Map<String, JSONObject> buildResultJSONObjects =
			JenkinsResultsParserUtil.getBuildResultJSONObjects(
				buildResultJsonURLs);

		for (String buildResultJsonURL : buildResultJsonURLs) {
			if (buildResultJSONObjects.containsKey(buildResultJsonURL)) {
				_populate(buildResultJSONObjects.get(buildResultJsonURL));
			}
		}

		long duration = JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Test history map populated in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	public void populate(
		String testrayServerName, String projectName, String routineName,
		int maxBuildCount) {

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		TestrayServer testrayServer = TestrayFactory.newTestrayServer(
			testrayServerName);

		TestrayProject testrayProject = testrayServer.getTestrayProjectByName(
			projectName);

		TestrayRoutine testrayRoutine = testrayProject.getTestrayRoutineByName(
			routineName);

		for (TestrayBuild testrayBuild :
				testrayRoutine.getTestrayBuilds(maxBuildCount)) {

			for (TestrayCaseResult testrayCaseResult :
					testrayBuild.getTestrayCaseResults()) {

				String name = testrayCaseResult.getName();

				String jobVariant = null;

				List<TestrayAttachment> testrayAttachments =
					testrayCaseResult.getTestrayAttachments();

				if (!testrayAttachments.isEmpty()) {
					for (TestrayAttachment testrayAttachment :
							testrayAttachments) {

						String testrayAttachmentKey =
							testrayAttachment.getKey();

						Matcher matcher = _testrayLogPattern.matcher(
							testrayAttachmentKey);

						if (matcher.find()) {
							jobVariant = matcher.group("jobVariant");
						}
					}
				}

				TestrayCaseResult.Status status = testrayCaseResult.getStatus();

				URL url = testrayCaseResult.getURL();

				put(
					name, jobVariant, url.toString(),
					testrayCaseResult.getErrors(), status.getName());
			}
		}

		long duration = JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Test history map populated in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	public void put(
		String testName, String batchName, String buildURL, String errorSnippet,
		String status) {

		for (String excludedTestNameRegex : _excludedTestNameRegexes) {
			if (testName.matches(".*" + excludedTestNameRegex + ".*")) {
				return;
			}
		}

		if (containsKey(Collections.singletonMap(testName, batchName))) {
			TestHistory testHistory = get(
				Collections.singletonMap(testName, batchName));

			testHistory.add(buildURL, errorSnippet, status);

			return;
		}

		put(
			Collections.singletonMap(testName, batchName),
			new TestHistory(
				testName, batchName, buildURL, errorSnippet, status));
	}

	public void setMinimumStatusChanges(int minimumStatusChanges) {
		_minimumStatusChanges = minimumStatusChanges;
	}

	public void writeFlakyTestDataJavaScriptFile(String filePath)
		throws IOException {

		JSONArray flakyTestDataJSONArray = new JSONArray();

		flakyTestDataJSONArray.put(
			new String[] {"Name", "Batch Type", "Results", "Status Changes"});

		for (TestHistory testHistory : values()) {
			if (testHistory.isFlaky()) {
				flakyTestDataJSONArray.put(testHistory.toJSONArray());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var flakyTestData = ");
		sb.append(flakyTestDataJSONArray.toString());
		sb.append(";\nvar flakyTestDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	public class TestHistory {

		public TestHistory(
			String name, String batchName, String buildURL, String errorSnippet,
			String status) {

			_name = name;
			_batchName = batchName;

			add(buildURL, errorSnippet, status);
		}

		public void add(String buildURL, String errorSnippet, String status) {
			_testHistoryEntries.add(
				new TestHistoryEntry(buildURL, errorSnippet, status));
		}

		public String getBatchName() {
			return _batchName;
		}

		public String getName() {
			return _name;
		}

		public List<String> getStatuses() {
			List<String> statuses = new ArrayList<>();

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				statuses.add(testHistoryEntry.getStatus());
			}

			return statuses;
		}

		public boolean isFlaky() {
			String lastStatus = null;

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				String status = testHistoryEntry.getStatus();

				if (lastStatus == null) {
					lastStatus = status;

					continue;
				}

				if (!lastStatus.equals(status)) {
					lastStatus = status;

					_statusChanges++;
				}
			}

			if (_statusChanges >= _minimumStatusChanges) {
				return true;
			}

			return false;
		}

		public JSONArray toJSONArray() {
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(getName());
			jsonArray.put(getBatchName());

			JSONArray statusesJSONArray = new JSONArray();

			Collections.reverse(_testHistoryEntries);

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				JSONArray statusJSONArray = new JSONArray();

				statusJSONArray.put(testHistoryEntry.getStatus());
				statusJSONArray.put(testHistoryEntry.getBuildURL());

				statusesJSONArray.put(statusJSONArray);
			}

			jsonArray.put(statusesJSONArray);
			jsonArray.put(_statusChanges);

			return jsonArray;
		}

		private final String _batchName;
		private final String _name;
		private int _statusChanges;
		private final List<TestHistoryEntry> _testHistoryEntries =
			new ArrayList<>();

		private class TestHistoryEntry {

			public TestHistoryEntry(
				String buildURL, String errorSnippet, String status) {

				_buildURL = buildURL;
				_errorSnippet = errorSnippet;
				_status = status;
			}

			public String getBuildURL() {
				return _buildURL;
			}

			public String getErrorSnippet() {
				return _errorSnippet;
			}

			public String getStatus() {
				return _status;
			}

			private final String _buildURL;
			private final String _errorSnippet;
			private final String _status;

		}

	}

	private void _populate(JSONObject buildResultJSONObject) {
		JSONArray batchResultsJSONArray = buildResultJSONObject.getJSONArray(
			"batchResults");

		for (int i = 0; i < batchResultsJSONArray.length(); i++) {
			JSONObject batchResultJSONObject =
				batchResultsJSONArray.getJSONObject(i);

			String jobVariant = batchResultJSONObject.getString("jobVariant");

			jobVariant = jobVariant.replaceAll("(.*)/.*", "$1");

			JSONArray testResultsJSONArray = batchResultJSONObject.getJSONArray(
				"testResults");

			for (int j = 0; j < testResultsJSONArray.length(); j++) {
				JSONObject testResultJSONObject =
					testResultsJSONArray.getJSONObject(j);

				String name = testResultJSONObject.optString("name");

				String status = testResultJSONObject.optString("status");

				status = status.replace("REGRESSION", "FAILED");
				status = status.replace("FIXED", "PASSED");

				if (name.startsWith("PortalLogAssertorTest") ||
					name.startsWith("JenkinsLogAsserterTest") ||
					status.equals("SKIPPED")) {

					continue;
				}

				String buildURL = testResultJSONObject.optString("buildURL");

				String errorDetails = testResultJSONObject.optString(
					"errorDetails");

				put(name, jobVariant, buildURL, errorDetails, status);
			}
		}
	}

	private static final List<String> _excludedTestNameRegexes =
		new ArrayList<String>() {
			{
				Properties buildProperties = null;

				try {
					buildProperties =
						JenkinsResultsParserUtil.getBuildProperties();

					String excludedTestNames = buildProperties.getProperty(
						"flaky.test.report.test.name.excludes");

					Collections.addAll(
						this, excludedTestNames.split("\\s*,\\s*"));
				}
				catch (IOException ioException) {
					throw new RuntimeException(
						"Unable to get build properties", ioException);
				}
			}
		};

	private static final Pattern _testrayLogPattern = Pattern.compile(
		"test[0-9-]+\\/[0-9]+\\/.+?\\/[0-9]+\\/(?<jobVariant>.+?)\\/.*");

	private int _minimumStatusChanges = 3;

}