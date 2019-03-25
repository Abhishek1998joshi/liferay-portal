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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.collaboration.dto.v1_0.DiscussionForumPosting;
import com.liferay.headless.collaboration.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.TaxonomyCategoryUtil;
import com.liferay.headless.collaboration.internal.odata.entity.v1_0.DiscussionForumPostingEntityModel;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionForumPostingResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/discussion-forum-posting.properties",
	scope = ServiceScope.PROTOTYPE,
	service = DiscussionForumPostingResource.class
)
public class DiscussionForumPostingResourceImpl
	extends BaseDiscussionForumPostingResourceImpl
	implements EntityModelResource {

	@Override
	public void deleteDiscussionForumPosting(Long discussionForumPostingId)
		throws Exception {

		_mbMessageService.deleteMessage(discussionForumPostingId);
	}

	@Override
	public DiscussionForumPosting getDiscussionForumPosting(
			Long discussionForumPostingId)
		throws Exception {

		return _toDiscussionForumPosting(
			_mbMessageService.getMessage(discussionForumPostingId));
	}

	@Override
	public Page<DiscussionForumPosting>
			getDiscussionForumPostingDiscussionForumPostingsPage(
				Long discussionForumPostingId, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getDiscussionForumPostingsPage(
			discussionForumPostingId, filter, pagination, sorts);
	}

	@Override
	public Page<DiscussionForumPosting>
			getDiscussionThreadDiscussionForumPostingsPage(
				Long discussionThreadId, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			discussionThreadId);

		return _getDiscussionForumPostingsPage(
			mbThread.getRootMessageId(), filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DiscussionForumPosting
			postDiscussionForumPostingDiscussionForumPosting(
				Long discussionForumPostingId,
				DiscussionForumPosting discussionForumPosting)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			discussionForumPostingId);

		return _addDiscussionThread(
			mbMessage.getGroupId(), discussionForumPostingId,
			discussionForumPosting);
	}

	@Override
	public DiscussionForumPosting postDiscussionThreadDiscussionForumPosting(
			Long discussionThreadId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			discussionThreadId);

		return _addDiscussionThread(
			mbThread.getGroupId(), mbThread.getRootMessageId(),
			discussionForumPosting);
	}

	@Override
	public DiscussionForumPosting putDiscussionForumPosting(
			Long discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			discussionForumPostingId);

		return _toDiscussionForumPosting(
			_mbMessageService.updateDiscussionMessage(
				mbMessage.getClassName(), mbMessage.getClassPK(),
				discussionForumPostingId, discussionForumPosting.getHeadline(),
				discussionForumPosting.getArticleBody(),
				ServiceContextUtil.createServiceContext(
					discussionForumPosting.getKeywords(),
					discussionForumPosting.getTaxonomyCategoryIds(),
					mbMessage.getGroupId(),
					discussionForumPosting.getViewableByAsString())));
	}

	private DiscussionForumPosting _addDiscussionThread(
			Long contentSpaceId, Long discussionForumPostingId,
			DiscussionForumPosting discussionForumPosting)
		throws PortalException {

		return _toDiscussionForumPosting(
			_mbMessageService.addMessage(
				discussionForumPostingId, discussionForumPosting.getHeadline(),
				discussionForumPosting.getArticleBody(),
				MBMessageConstants.DEFAULT_FORMAT, Collections.emptyList(),
				false, 0.0, false,
				ServiceContextUtil.createServiceContext(
					discussionForumPosting.getKeywords(),
					discussionForumPosting.getTaxonomyCategoryIds(),
					contentSpaceId,
					discussionForumPosting.getViewableByAsString())));
	}

	private Page<DiscussionForumPosting> _getDiscussionForumPostingsPage(
			Long discussionForumPostingId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.ENTRY_CLASS_PK,
						String.valueOf(discussionForumPostingId)),
					BooleanClauseOccur.MUST_NOT);
				booleanFilter.add(
					new TermFilter(
						"parentMessageId",
						String.valueOf(discussionForumPostingId)),
					BooleanClauseOccur.MUST);
			},
			filter, MBMessage.class, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			document -> _toDiscussionForumPosting(
				_mbMessageService.getMessage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private DiscussionForumPosting _toDiscussionForumPosting(
			MBMessage mbMessage)
		throws PortalException {

		return new DiscussionForumPosting() {
			{
				articleBody = mbMessage.getBody();
				contentSpaceId = mbMessage.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userService.getUserById(mbMessage.getUserId()));
				dateCreated = mbMessage.getCreateDate();
				dateModified = mbMessage.getModifiedDate();
				headline = mbMessage.getSubject();
				id = mbMessage.getMessageId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						KBArticle.class.getName(), mbMessage.getClassPK()),
					AssetTag.NAME_ACCESSOR);
				numberOfDiscussionAttachments =
					mbMessage.getAttachmentsFileEntriesCount();
				numberOfDiscussionForumPostings =
					_mbMessageLocalService.getChildMessagesCount(
						mbMessage.getMessageId(),
						WorkflowConstants.STATUS_APPROVED);
				showAsAnswer = mbMessage.isAnswer();
				taxonomyCategories = transformToArray(
					_assetCategoryLocalService.getCategories(
						MBMessage.class.getName(), mbMessage.getClassPK()),
					TaxonomyCategoryUtil::toTaxonomyCategory,
					TaxonomyCategory.class);
			}
		};
	}

	private static final EntityModel _entityModel =
		new DiscussionForumPostingEntityModel();

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserService _userService;

}