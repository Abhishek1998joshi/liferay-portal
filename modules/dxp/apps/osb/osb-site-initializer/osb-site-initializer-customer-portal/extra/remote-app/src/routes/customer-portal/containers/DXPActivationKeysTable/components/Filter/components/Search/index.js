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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {useState} from 'react';
import {useActivationKeys} from '../../../../context';
import {actionTypes} from '../../../../context/reducer';

const Search = () => {
	const [
		{activationKeysFilteredByConditions, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();

	const [typeTerm, setTypeTerm] = useState('');
	const [searchOrTimes, setSearchOrTimes] = useState(true);

	function searchActivationKeys(searchTerm) {
		toSearchAndFilterKeys.toSearchTerm = searchTerm.toLowerCase();
		dispatch({
			payload: toSearchAndFilterKeys,
			type: actionTypes.UPDATE_TO_SERACH_AND_FILTER_KEYS,
		});

		dispatch({
			payload: searchTerm ? true : false,
			type: actionTypes.UPDATE_WAS_SEARCHED,
		});
	}

	return (
		<div>
			<div>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							aria-label="Search"
							className="form-control input-group-inset input-group-inset-after"
							onChange={(event) => {
								setTypeTerm(event.target.value);
								setSearchOrTimes(true);
							}}
							placeholder="Search"
							type="text"
							value={typeTerm}
						/>

						<ClayInput.GroupInsetItem after tag="span">
							{searchOrTimes ? (
								<ClayButtonWithIcon
									displayType="unstyled"
									onClick={() => {
										if (typeTerm) {
											searchActivationKeys(typeTerm);
											setSearchOrTimes(false);
										}
									}}
									symbol="search"
									type="submit"
								/>
							) : (
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-none"
									displayType="unstyled"
									onClick={() => {
										setTypeTerm('');
										searchActivationKeys('');
										setSearchOrTimes(true);
									}}
									symbol="times"
								/>
							)}
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</div>

			{!searchOrTimes && activationKeysFilteredByConditions.length > 0 && (
				<p>
					<b>{`${activationKeysFilteredByConditions.length}`}</b>

					<b>
						{activationKeysFilteredByConditions.length === 1
							? ' result'
							: ' results'}
					</b>

					<b>{` for "${typeTerm}"`}</b>
				</p>
			)}
		</div>
	);
};
export default Search;
