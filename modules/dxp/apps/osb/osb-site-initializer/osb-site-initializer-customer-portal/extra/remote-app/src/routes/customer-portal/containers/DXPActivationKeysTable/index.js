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
import ClayAlert from '@clayui/alert';
import {ButtonWithIcon} from '@clayui/core';
import {useModal} from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useEffect, useState} from 'react';
import RoundedGroupButtons from '../../../../common/components/RoundedGroupButtons';
import Table from '../../../../common/components/Table';
import {useApplicationProvider} from '../../../../common/context/AppPropertiesProvider';
import {getActivationLicenseKey} from '../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {useCustomerPortal} from '../../context';
import {ALERT_DOWNLOAD_TYPE} from '../../utils/constants/alertDownloadType';
import {AUTO_CLOSE_ALERT_TIME} from '../../utils/constants/autoCloseAlertTime';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../DXPActivationKeysTable/utils/constants/alertAggregateKeysDownloadText';
import {getActivationKeyDownload} from '../DXPActivationKeysTable/utils/getActivationKeyDownload';
import DXPActivationKeysTableHeader from './components/Header';
import ModalKeyDetails from './components/ModalKeyDetails';
import {useActivationKeys} from './context';
import {actionTypes} from './context/reducer';
import {
	ACTIVATION_KEYS_LICENSE_FILTER_TYPES,
	ACTIVATION_STATUS,
	COLUMNS,
} from './utils/constants';
import {
	EnvironmentTypeColumn,
	ExpirationDateColumn,
	KeyTypeColumn,
	StatusColumn,
} from './utils/constants/columns-definitions';
import {downloadActivationLicenseKey} from './utils/downloadActivationLicenseKey';
import {getGroupButtons} from './utils/getGroupButtons';
import {getTooltipContentRenderer} from './utils/getTooltipContentRenderer';

const MAX_ITEMS = 9999;
const PAGE = 1;

const DXPActivationKeysTable = ({project, sessionId}) => {
	const [{assetsPath}] = useCustomerPortal();
	const {licenseKeyDownloadURL} = useApplicationProvider();
	const [
		{
			activationKeys,
			activationKeysFilteredByConditions,
			wasFiltered,
			wasSearched,
		},
		dispatch,
	] = useActivationKeys();

	const [statusBar, setStatusBar] = useState({});

	const [activationKeysFiltered, setActivationKeysFiltered] = useState([]);
	const [totalCount, setTotalCount] = useState(0);
	const [activationKeysChecked, setActivationKeysChecked] = useState([]);

	const [filterStatusBar, setFilterStatusBar] = useState('all');

	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const [isLoadingActivationKeys, setIsLoadingActivationKeys] = useState(
		false
	);

	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [currentActivationKey, setCurrentActivationKey] = useState();
	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleModal(false),
	});
	const [
		activationKeysDownloadStatus,
		setActivationKeysDownloadStatus,
	] = useState('');

	useEffect(() => {
		if (activationKeysFiltered.length) {
			setActivationKeysChecked([]);
		}
	}, [activationKeysFiltered]);

	useEffect(() => {
		if (filterStatusBar) {
			setActivePage(1);
		}
	}, [filterStatusBar]);

	useEffect(() => {
		setIsLoadingActivationKeys(true);
		const fetchActivationKeysData = async () => {
			const {items} = await getActivationLicenseKey(
				'KOR-3809080',
				licenseKeyDownloadURL,
				encodeURI('active eq true'),
				PAGE,
				MAX_ITEMS,
				sessionId
			);

			items[0].startDate = '2023-11-05T00:00:00Z';
			items[0].expirationDate = '2024-11-05T00:00:00Z';
			items[0].licenseEntryType = 'virtual-cluster';
			items[0].complimentary = true;
			items[0].productName = 'DXP Developer';
			items[0].maxClusterNodes = 2;
			items[1].startDate = '2023-11-05T00:00:00Z';
			items[1].licenseEntryType = 'virtual-cluster';
			items[1].maxClusterNodes = 48;
			items[1].complimentary = true;
			items[1].expirationDate = '2024-11-05T00:00:00Z';
			items[1].sizing = 'Sizing 4';
			items[2].licenseEntryType = 'virtual-cluster';
			items[2].maxClusterNodes = 14;
			items[2].complimentary = true;
			items[2].startDate = '2019-11-05T00:00:00Z';
			items[2].expirationDate = '2020-11-05T00:00:00Z';
			items[3].licenseEntryType = 'virtual-cluster';
			items[3].maxClusterNodes = 5;
			items[3].startDate = '2018-11-05T00:00:00Z';
			items[3].complimentary = true;
			items[3].expirationDate = '2021-11-05T00:00:00Z';
			items[4].sizing = 'Sizing 3';
			items[5].sizing = 'Sizing 3';
			items[6].sizing = 'Sizing 3';
			items[7].sizing = 'Sizing 3';
			items[8].sizing = 'Sizing 4';
			items[9].sizing = 'Sizing 4';
			items[10].sizing = 'Sizing 4';

			if (items) {
				dispatch({
					payload: items,
					type: actionTypes.UPDATE_ACTIVATION_KEYS,
				});
				dispatch({
					payload: items,
					type:
						actionTypes.UPDATE_ACTIVATION_KEYS_FILTERED_BY_CONDITIONS,
				});
			}

			setIsLoadingActivationKeys(false);
		};

		fetchActivationKeysData();
	}, [dispatch, licenseKeyDownloadURL, project, sessionId]);

	useEffect(() => {
		if (activationKeys.length) {
			setStatusBar({
				activatedTotalCount: activationKeys.filter((activationKey) =>
					ACTIVATION_KEYS_LICENSE_FILTER_TYPES.activated(
						activationKey
					)
				).length,
				allTotalCount: activationKeys.length,
				expiredTotalCount: activationKeys.filter((activationKey) =>
					ACTIVATION_KEYS_LICENSE_FILTER_TYPES.expired(activationKey)
				).length,
				notActiveTotalCount: activationKeys.filter((activationKey) =>
					ACTIVATION_KEYS_LICENSE_FILTER_TYPES.notActivated(
						activationKey
					)
				).length,
			});
		}
	}, [activationKeys]);

	useEffect(() => {
		const activationKeysFilterData = activationKeysFilteredByConditions.filter(
			(activationKey) =>
				ACTIVATION_KEYS_LICENSE_FILTER_TYPES[filterStatusBar]
					? ACTIVATION_KEYS_LICENSE_FILTER_TYPES[filterStatusBar](
							activationKey
					  )
					: Boolean
		);

		setTotalCount(activationKeysFilterData?.length || 0);

		const activationKeysFilterByPage = activationKeysFilterData?.slice(
			itemsPerPage * activePage - itemsPerPage,
			itemsPerPage * activePage
		);

		setActivationKeysFiltered(
			activationKeysFilterByPage?.length
				? activationKeysFilterByPage
				: activationKeysFilterData
		);
	}, [
		activationKeys,
		activationKeysFilteredByConditions,
		activePage,
		filterStatusBar,
		itemsPerPage,
	]);

	const groupButtons = [
		getGroupButtons(ACTIVATION_STATUS.all, statusBar?.allTotalCount),
		getGroupButtons(
			ACTIVATION_STATUS.activated,
			statusBar?.activatedTotalCount
		),
		getGroupButtons(
			ACTIVATION_STATUS.notActivated,
			statusBar?.notActiveTotalCount
		),
		getGroupButtons(
			ACTIVATION_STATUS.expired,
			statusBar?.expiredTotalCount
		),
	];

	const paginationConfig = {
		activePage,
		itemsPerPage,
		labels: {
			paginationResults: 'Showing {0} to {1} of {2}',
			perPageItems: 'Show {0} Items',
			selectPerPageItems: '{0} Items',
		},
		listItemsPerPage: [{label: 5}, {label: 10}, {label: 20}, {label: 50}],
		setActivePage,
		setItemsPerPage,
		showDeltasDropDown: true,
		totalCount,
	};

	const handleAlertStatus = (hasSuccessfullyDownloadedKeys) => {
		setActivationKeysDownloadStatus(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	};

	return (
		<>
			{isVisibleModal && (
				<ModalKeyDetails
					assetsPath={assetsPath}
					currentActivationKey={currentActivationKey}
					downloadActivationLicenseKey={downloadActivationLicenseKey}
					isVisibleModal={isVisibleModal}
					licenseKeyDownloadURL={licenseKeyDownloadURL}
					observer={observer}
					onClose={onClose}
					project={project}
					sessionId={sessionId}
				/>
			)}
			<ClayTooltipProvider
				contentRenderer={({title}) => getTooltipContentRenderer(title)}
				delay={100}
			>
				<div>
					<div className="align-center cp-dxp-activation-key-container d-flex justify-content-between mb-2">
						<h3 className="m-0">Activation Keys</h3>

						<RoundedGroupButtons
							groupButtons={groupButtons}
							handleOnChange={(value) =>
								setFilterStatusBar(value)
							}
						/>
					</div>

					<div className="mt-4 py-2">
						<DXPActivationKeysTableHeader
							accountKey={project.accountKey}
							activationKeys={activationKeysFiltered}
							allActivationsKeys={activationKeys}
							licenseKeyDownloadURL={licenseKeyDownloadURL}
							project={project}
							selectedKeys={activationKeysChecked}
							sessionId={sessionId}
							setActivationKeysFiltered={
								setActivationKeysFiltered
							}
						/>
					</div>

					{!activationKeysFilteredByConditions.length &
					(wasFiltered || wasSearched) ? (
						<p>
							No activation keys found with this search criteria
						</p>
					) : (
						<Table
							checkboxConfig={{
								checkboxesChecked: activationKeysChecked,
								setCheckboxesChecked: setActivationKeysChecked,
							}}
							className="border-0 cp-dxp-activation-key-table"
							columns={COLUMNS}
							hasCheckbox
							hasPagination
							isLoading={isLoadingActivationKeys}
							paginationConfig={paginationConfig}
							rows={activationKeysFiltered.map(
								(activationKey) => ({
									customClickOnRow: () => {
										setCurrentActivationKey(activationKey);
										setIsVisibleModal(true);
									},
									download: (
										<ButtonWithIcon
											displayType="null"
											onClick={() =>
												getActivationKeyDownload(
													activationKey.id,
													licenseKeyDownloadURL,
													sessionId,
													handleAlertStatus,
													activationKey.productName,
													activationKey.productVersion,
													project.name
												)
											}
											small
											symbol="download"
										/>
									),
									envName: (
										<div
											title={[
												activationKey.name,
												activationKey.description,
											]}
										>
											<p className="font-weight-bold m-0 text-neutral-10 text-truncate">
												{activationKey.name}
											</p>

											<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm text-truncate">
												{activationKey.description}
											</p>
										</div>
									),
									envType: (
										<EnvironmentTypeColumn
											activationKey={activationKey}
										/>
									),
									expirationDate: (
										<ExpirationDateColumn
											activationKey={activationKey}
										/>
									),
									id: activationKey.id,
									keyType: (
										<KeyTypeColumn
											activationKey={activationKey}
											assetsPath={assetsPath}
										/>
									),
									status: (
										<StatusColumn
											activationKey={activationKey}
										/>
									),
								})
							)}
						/>
					)}
				</div>
			</ClayTooltipProvider>
			{activationKeysDownloadStatus && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={
							AUTO_CLOSE_ALERT_TIME[activationKeysDownloadStatus]
						}
						className="cp-activation-key-download-alert"
						displayType={
							ALERT_DOWNLOAD_TYPE[activationKeysDownloadStatus]
						}
						onClose={() => setActivationKeysDownloadStatus('')}
					>
						{
							ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT[
								activationKeysDownloadStatus
							]
						}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</>
	);
};

export default DXPActivationKeysTable;
