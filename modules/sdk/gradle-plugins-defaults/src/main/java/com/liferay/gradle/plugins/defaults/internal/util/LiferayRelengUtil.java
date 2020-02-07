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

package com.liferay.gradle.plugins.defaults.internal.util;

import com.liferay.gradle.plugins.cache.WriteDigestTask;
import com.liferay.gradle.plugins.defaults.LiferayThemeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.tasks.WriteArtifactPublishCommandsTask;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerBasePlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.lang.reflect.Method;

import java.nio.file.Files;

import java.util.Objects;
import java.util.Properties;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.maven.MavenDeployer;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.MavenRepositoryHandlerConvention;
import org.gradle.api.tasks.Upload;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 * @author Peter Shin
 */
public class LiferayRelengUtil {

	public static String getArtifactRemoteURL(
			Project project, PublishArtifact publishArtifact, boolean cdn)
		throws Exception {

		StringBuilder sb = _getArtifactRemoteBaseURL(project, cdn);

		String name = GradleUtil.getArchivesBaseName(project);

		sb.append(name);

		sb.append('/');
		sb.append(project.getVersion());
		sb.append('/');
		sb.append(name);
		sb.append('-');
		sb.append(project.getVersion());

		String classifier = publishArtifact.getClassifier();

		if (Validator.isNotNull(classifier)) {
			sb.append('-');
			sb.append(classifier);
		}

		sb.append('.');
		sb.append(publishArtifact.getExtension());

		return sb.toString();
	}

	public static File getRelengDir(File projectDir) {
		File rootDir = GradleUtil.getRootDir(projectDir, _RELENG_DIR_NAME);

		if (rootDir == null) {
			return null;
		}

		File relengDir = new File(rootDir, _RELENG_DIR_NAME);

		return new File(relengDir, FileUtil.relativize(projectDir, rootDir));
	}

	public static File getRelengDir(Project project) {
		return getRelengDir(project.getProjectDir());
	}

	public static boolean hasStaleParentTheme(Project project) {
		WriteDigestTask writeDigestTask = (WriteDigestTask)GradleUtil.getTask(
			project,
			LiferayThemeDefaultsPlugin.WRITE_PARENT_THEMES_DIGEST_TASK_NAME);

		if (!Objects.equals(
				writeDigestTask.getDigest(), writeDigestTask.getOldDigest())) {

			Logger logger = project.getLogger();

			if (logger.isInfoEnabled()) {
				logger.info("The digest for {} has changed.", writeDigestTask);
			}

			return true;
		}

		return false;
	}

	public static boolean hasUnpublishedDependencies(Project project) {
		for (Configuration configuration : project.getConfigurations()) {
			String name = configuration.getName();

			if (name.equals(
					JSTranspilerBasePlugin.JS_COMPILE_CONFIGURATION_NAME) ||
				name.equals(
					JSTranspilerPlugin.SOY_COMPILE_CONFIGURATION_NAME) ||
				name.startsWith("test")) {

				continue;
			}

			for (Dependency dependency : configuration.getDependencies()) {
				if (_isStaleProjectDependency(
						project, configuration, dependency)) {

					return true;
				}
			}
		}

		return false;
	}

	public static boolean hasUnpublishedCommits(
		Project project, File artifactProjectDir, File artifactPropertiesFile) {

		Logger logger = project.getLogger();

		Properties artifactProperties = new Properties();

		if (artifactPropertiesFile.exists()) {
			artifactProperties = GUtil.loadProperties(artifactPropertiesFile);
		}

		String artifactGitId = artifactProperties.getProperty(
			"artifact.git.id");

		if (Validator.isNull(artifactGitId)) {
			if (logger.isInfoEnabled()) {
				logger.info("{} has never been published", artifactProjectDir);
			}

			return true;
		}

		Project rootProject = project.getRootProject();

		String gitId = GitUtil.getGitResult(
			project, rootProject.getProjectDir(), "rev-parse", "--short",
			"HEAD");

		File gitResultsDir = new File(
			rootProject.getBuildDir(), "releng/git-results/" + gitId);

		StringBuilder sb = new StringBuilder();

		sb.append(artifactProjectDir.getName());
		sb.append('-');
		sb.append(artifactGitId);
		sb.append('-');

		File file = new File(gitResultsDir, sb.toString() + "true");

		if (file.exists()) {
			return true;
		}

		file = new File(gitResultsDir, sb.toString() + "false");

		if (file.exists()) {
			return false;
		}

		String result = GitUtil.getGitResult(
			project, artifactProjectDir, "log", "--format=%s",
			artifactGitId + "..HEAD", ".");

		String[] lines = result.split("\\r?\\n");

		for (String line : lines) {
			if (logger.isInfoEnabled()) {
				logger.info(line);
			}

			if (Validator.isNull(line)) {
				continue;
			}

			if (line.contains(_IGNORED_MESSAGE_PATTERN)) {
				continue;
			}

			_createNewFile(new File(gitResultsDir, sb.toString() + "true"));

			return true;
		}

		_createNewFile(new File(gitResultsDir, sb.toString() + "false"));

		return false;
	}

	private static StringBuilder _getArtifactRemoteBaseURL(
			Project project, boolean cdn)
		throws Exception {

		Upload upload = (Upload)GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		RepositoryHandler repositoryHandler = upload.getRepositories();

		MavenDeployer mavenDeployer = (MavenDeployer)repositoryHandler.getAt(
			MavenRepositoryHandlerConvention.DEFAULT_MAVEN_DEPLOYER_NAME);

		Object repository = mavenDeployer.getRepository();

		// org.apache.maven.artifact.ant.RemoteRepository is not in the
		// classpath

		Class<?> repositoryClass = repository.getClass();

		Method getUrlMethod = repositoryClass.getMethod("getUrl");

		String url = (String)getUrlMethod.invoke(repository);

		if (cdn) {
			url = url.replace("http://", "http://cdn.");
			url = url.replace("https://", "https://cdn.");
		}

		StringBuilder sb = new StringBuilder(url);

		if (sb.charAt(sb.length() - 1) != '/') {
			sb.append('/');
		}

		String group = String.valueOf(project.getGroup());

		sb.append(group.replace('.', '/'));

		sb.append('/');

		return sb;
	}

	private static File _getPortalProjectDir(
		Project project, Dependency dependency) {

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		if (portalRootDir == null) {
			return null;
		}

		String dependencyGroup = dependency.getGroup();

		if (!Objects.equals(dependencyGroup, "com.liferay.portal")) {
			return null;
		}

		String dependencyName = dependency.getName();

		if ((dependencyName == null) ||
			!dependencyName.startsWith("com.liferay.")) {

			return null;
		}

		String s = dependencyName.substring(12);

		File portalProjectDir = new File(portalRootDir, s.replace('.', '-'));

		if (!portalProjectDir.exists()) {
			return null;
		}

		return portalProjectDir;
	}

	private static void _createNewFile(File file) {
		File dir = file.getParentFile();

		try {
			Files.createDirectories(dir.toPath());

			file.createNewFile();
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	private static boolean _isStaleProjectDependency(
		Project project, Configuration configuration, Dependency dependency) {

		if (dependency instanceof ProjectDependency) {
			ProjectDependency projectDependency = (ProjectDependency)dependency;

			Project dependencyProject =
				projectDependency.getDependencyProject();

			File artifactPropertiesFile = new File(
				getRelengDir(dependencyProject), "artifact.properties");

			if (hasUnpublishedCommits(
					project, dependencyProject.getProjectDir(),
					artifactPropertiesFile)) {

				Logger logger = project.getLogger();

				if (logger.isQuietEnabled()) {
					logger.quiet(
						"{} has stale project dependency {}.", project,
						dependencyProject.getName());
				}

				return true;
			}
		}

		String configurationName = configuration.getName();

		if (configurationName.startsWith("compile") &&
			Objects.equals(dependency.getVersion(), "default")) {

			File dir = _getPortalProjectDir(project, dependency);

			if (dir != null) {
				StringBuilder sb = new StringBuilder();

				sb.append("modules/");
				sb.append(_RELENG_DIR_NAME);
				sb.append('/');
				sb.append(dir.getName());
				sb.append(".properties");

				File artifactPropertiesFile = new File(
					dir.getParent(), sb.toString());

				if (hasUnpublishedCommits(
						project, dir, artifactPropertiesFile)) {

					Logger logger = project.getLogger();

					if (logger.isQuietEnabled()) {
						logger.quiet(
							"{} has stale portal project dependency {}.",
							project, dir.getName());
					}

					return true;
				}
			}
		}

		return false;
	}

	private static final String _IGNORED_MESSAGE_PATTERN =
		WriteArtifactPublishCommandsTask.IGNORED_MESSAGE_PATTERN;

	private static final String _RELENG_DIR_NAME = ".releng";

}