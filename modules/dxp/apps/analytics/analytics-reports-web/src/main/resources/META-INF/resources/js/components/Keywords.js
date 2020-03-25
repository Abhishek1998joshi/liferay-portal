/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';

const KEYWORD_VALUE_TYPE = [
	{label: Liferay.Language.get('traffic'), name: 'traffic'},
	{label: Liferay.Language.get('volume'), name: 'volume'},
	{label: Liferay.Language.get('position'), name: 'position'},
];

export default function Keywords({currentPage, languageTag}) {
	const [isDropdownOpen, setIsDropdownOpen] = React.useState(false);

	const [keywordValueType, setKeywordValueType] = React.useState(
		KEYWORD_VALUE_TYPE.find(keywordValueType => {
			return keywordValueType.name === 'traffic';
		})
	);

	return (
		<table className="table-keywords">
			<thead>
				<tr>
					<th>
						{Liferay.Language.get('best-keyword')}
						<span className="text-secondary">
							<Hint
								message={Liferay.Language.get(
									'best-keyword-help'
								)}
								title={Liferay.Language.get('best-keyword')}
							/>
						</span>
					</th>
					<th>
						<ClayDropDown
							active={isDropdownOpen}
							onActiveChange={isActive =>
								setIsDropdownOpen(isActive)
							}
							trigger={
								<ClayButton
									className="px-0 text-body"
									displayType="link"
									small
								>
									<span className="font-weight-bold">
										<span className="pr-2">
											{keywordValueType.label}
										</span>
										<ClayIcon symbol="angle-down" />
									</span>
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList>
								{KEYWORD_VALUE_TYPE.map((valueType, index) => (
									<ClayDropDown.Item
										active={
											valueType.name ===
											keywordValueType.name
										}
										key={index}
										onClick={() => {
											setKeywordValueType(
												KEYWORD_VALUE_TYPE.find(
													keywordValueType => {
														return (
															keywordValueType.name ===
															valueType.name
														);
													}
												)
											);
											setIsDropdownOpen(false);
										}}
									>
										{valueType.label}
									</ClayDropDown.Item>
								))}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</th>
				</tr>
			</thead>
			<tbody>
				{currentPage.data.keywords.map(keyword => {
					return (
						<tr key={keyword.title}>
							<td>{keyword.title}</td>
							<td align="right" className="text-secondary">
								{numberFormat(
									languageTag,
									keywordValueType.name === 'traffic'
										? keyword.value
										: keywordValueType.name === 'volume'
										? keyword.volume
										: keyword.position
								)}
							</td>
						</tr>
					);
				})}
			</tbody>
		</table>
	);
}

Keywords.proptypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
};
