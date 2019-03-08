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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CommentUtil;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.headless.common.spi.resource.SPICommentResource;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/comment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {CommentResource.class}
)
public class CommentResourceImpl
	extends BaseCommentResourceImpl implements EntityModelResource {

	@Activate
	public void afterPropertiesSet() {
		_spiCommentResource = new SPICommentResource<>(
			BlogsEntry.class.getName(), _commentManager, contextCompany,
			comment -> CommentUtil.toComment(
				comment, _commentManager, _portal));
	}

	@Override
	public boolean deleteComment(Long commentId) throws Exception {
		return _spiCommentResource.deleteComment(commentId);
	}

	@Override
	public Page<Comment> getBlogPostingCommentsPage(
			Long blogPostingId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _spiCommentResource.getEntityCommentsPage(
			blogsEntry.getGroupId(), blogPostingId, filter, pagination, sorts);
	}

	@Override
	public Comment getComment(Long commentId) throws Exception {
		return _spiCommentResource.getComment(commentId);
	}

	@Override
	public Page<Comment> getCommentCommentsPage(
			Long commentId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _spiCommentResource.getCommentCommentsPage(
			commentId, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _spiCommentResource.getEntityModel(multivaluedMap);
	}

	@Override
	public Comment postBlogPostingComment(Long blogPostingId, Comment comment)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _spiCommentResource.postEntityComment(
			blogsEntry.getGroupId(), blogPostingId, comment.getText());
	}

	@Override
	public Comment postCommentComment(Long parentCommentId, Comment comment)
		throws Exception {

		return _spiCommentResource.postCommentComment(
			parentCommentId, comment.getText());
	}

	@Override
	public Comment putComment(Long commentId, Comment comment)
		throws Exception {

		return _spiCommentResource.putComment(commentId, comment.getText());
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private Portal _portal;

	private SPICommentResource<Comment> _spiCommentResource;

}