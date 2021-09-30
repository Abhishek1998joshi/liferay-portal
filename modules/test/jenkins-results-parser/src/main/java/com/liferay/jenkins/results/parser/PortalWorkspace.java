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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalWorkspace extends BaseWorkspace {

	@Override
	public void setUp() {
		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		primaryWorkspaceGitRepository.setUp();

		primaryWorkspaceGitRepository.writePropertiesFiles();

		_setUpPortalProfile();

		_updateBladeSamplesWorkspaceGitRepository();
		_updatePluginsWorkspaceGitRepository();
		_updatePortalPrivateWorkspaceGitRepository();
		_updatePortalsPlutoWorkspaceGitRepository();
		_updateReleaseWorkspaceGitRepository();

		List<Callable<Object>> callables = new ArrayList<>();

		for (final WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			if (workspaceGitRepository.equals(primaryWorkspaceGitRepository)) {
				continue;
			}

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() {
					workspaceGitRepository.setUp();

					workspaceGitRepository.writePropertiesFiles();

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
			callables, threadPoolExecutor);

		parallelExecutor.execute();
	}

	protected PortalWorkspace(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PortalWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		super(primaryRepositoryName, upstreamBranchName);
	}

	private void _setUpPortalProfile() {
		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		if (!(primaryWorkspaceGitRepository instanceof
				PortalWorkspaceGitRepository)) {

			return;
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			(PortalWorkspaceGitRepository)primaryWorkspaceGitRepository;

		portalWorkspaceGitRepository.setUpPortalProfile();
	}

	private void _updateBladeSamplesWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit-blade-samples", "liferay-blade-samples");
	}

	private void _updatePluginsWorkspaceGitRepository() {
		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		if (!(primaryWorkspaceGitRepository instanceof
				PortalWorkspaceGitRepository)) {

			return;
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			(PortalWorkspaceGitRepository)primaryWorkspaceGitRepository;

		_updateWorkspaceGitRepository(
			"git-commit-plugins",
			portalWorkspaceGitRepository.getPluginsRepositoryDirName());
	}

	private void _updatePortalPrivateWorkspaceGitRepository() {
		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		if (!(primaryWorkspaceGitRepository instanceof
				PortalWorkspaceGitRepository)) {

			return;
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			(PortalWorkspaceGitRepository)primaryWorkspaceGitRepository;

		_updateWorkspaceGitRepository(
			"git-commit-portal-private",
			portalWorkspaceGitRepository.getPortalPrivateRepositoryDirName());
	}

	private void _updatePortalsPlutoWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit-portals-pluto", "portals-pluto");
	}

	private void _updateReleaseWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit/liferay-release-tool-ee", "liferay-release-tool-ee");
	}

	private void _updateWorkspaceGitRepository(
		String gitCommitFilePath, String gitRepositoryName) {

		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		String gitCommit = primaryWorkspaceGitRepository.getFileContent(
			gitCommitFilePath);

		if (!JenkinsResultsParserUtil.isSHA(gitCommit)) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository(gitRepositoryName);

		if (workspaceGitRepository == null) {
			return;
		}

		workspaceGitRepository.setBranchSHA(gitCommit);
	}

}