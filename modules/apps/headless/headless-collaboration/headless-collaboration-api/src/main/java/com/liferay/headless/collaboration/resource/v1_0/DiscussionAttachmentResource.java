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

package com.liferay.headless.collaboration.resource.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.DiscussionAttachment;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-collaboration/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface DiscussionAttachmentResource {

	public void deleteDiscussionAttachment(Long discussionAttachmentId)
		throws Exception;

	public DiscussionAttachment getDiscussionAttachment(
			Long discussionAttachmentId)
		throws Exception;

	public Page<DiscussionAttachment>
			getDiscussionForumPostingDiscussionAttachmentsPage(
				Long discussionForumPostingId)
		throws Exception;

	public DiscussionAttachment postDiscussionForumPostingDiscussionAttachment(
			Long discussionForumPostingId, MultipartBody multipartBody)
		throws Exception;

	public Page<DiscussionAttachment>
			getDiscussionThreadDiscussionAttachmentsPage(
				Long discussionThreadId)
		throws Exception;

	public DiscussionAttachment postDiscussionThreadDiscussionAttachment(
			Long discussionThreadId, MultipartBody multipartBody)
		throws Exception;

	public void setContextCompany(Company contextCompany);

}