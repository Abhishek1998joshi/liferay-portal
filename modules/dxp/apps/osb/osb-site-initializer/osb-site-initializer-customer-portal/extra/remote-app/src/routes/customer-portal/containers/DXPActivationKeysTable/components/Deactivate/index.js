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

import {Button as ClayButton} from '@clayui/core';
import {useModal} from '@clayui/modal';
import {useState} from 'react';
import {useApplicationProvider} from '../../../../../../common/context/AppPropertiesProvider';
import {putDeactivateKeys} from '../../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {actionTypes} from '../../../../context/reducer';
import {ALERT_DOWNLOAD_TYPE, STATUS_CODE} from '../../../../utils/constants';
import {useActivationKeys} from '../../context';
import DeactivateKeysModal from './Modal';

const DeactivateButton = ({
	deactivateKeysStatus,
	selectedKeys,
	sessionId,
	setDeactivateKeysStatus,
}) => {
	const [{activationKeys}, dispatch] = useActivationKeys();

	const {licenseKeyDownloadURL} = useApplicationProvider();
	const [isDeactivating, setIsDeactivating] = useState(false);
	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => {
			setIsVisibleModal(false);
			setDeactivateKeysStatus('');
		},
	});

	const deactivateKeysConfirm = async () => {
		setIsDeactivating(true);
		const licenseKeyIds = selectedKeys
			.map((selectedKey) => `licenseKeyIds=${selectedKey}`)
			.join('&');

		const response = await putDeactivateKeys(
			licenseKeyDownloadURL,
			licenseKeyIds,
			sessionId
		);

		if (response.status === STATUS_CODE.successNoContent) {
			setIsDeactivating(false);
			setIsVisibleModal(false);

			const activationKeysMinusDeactivated = activationKeys.filter(
				(activationKey) => !selectedKeys.includes(activationKey.id)
			);
			dispatch({
				payload: activationKeysMinusDeactivated,
				type: actionTypes.UPDATE_ACTIVATION_KEYS,
			});

			return setDeactivateKeysStatus(ALERT_DOWNLOAD_TYPE.success);
		}

		setIsDeactivating(false);
		setDeactivateKeysStatus(ALERT_DOWNLOAD_TYPE.danger);
	};

	return (
		<>
			{isVisibleModal && (
				<DeactivateKeysModal
					deactivateKeysConfirm={deactivateKeysConfirm}
					deactivateKeysStatus={deactivateKeysStatus}
					isDeactivating={isDeactivating}
					observer={observer}
					onClose={onClose}
				/>
			)}

			<ClayButton
				className="btn-outline-danger cp-deactivate-button mx-2 px-3 py-2"
				onClick={() => setIsVisibleModal(true)}
			>
				Deactivate
			</ClayButton>
		</>
	);
};

export default DeactivateButton;
