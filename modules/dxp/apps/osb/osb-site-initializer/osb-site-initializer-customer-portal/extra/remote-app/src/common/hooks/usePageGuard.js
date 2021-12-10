import {useQuery} from '@apollo/client';
import {useEffect, useState} from 'react';
import {LiferayTheme} from '../services/liferay';
import {getAccountRolesAndAccountFlags} from '../services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../services/liferay/search-params';
import { API_BASE_URL } from '../utils';

const liferaySiteName = LiferayTheme.getLiferaySiteName();

const validateExternalReferenceCode = (
	accountBriefs,
	externalReferenceCode
) => {
	const accountBrief = accountBriefs.find(
		(accountBrief) =>
			accountBrief.externalReferenceCode === externalReferenceCode
	);

	return accountBrief;
};

const onboardingPageGuard = (
	accountBriefs,
	externalReferenceCode,
	accountFlags,
	accountAccountRoles
) => {
	return {
		location: `${API_BASE_URL}${liferaySiteName}/onboarding?${PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`,
		validate:
			!accountFlags.length &&
			accountAccountRoles.find(
				({name}) => name === 'Account Administrator'
			) &&
			validateExternalReferenceCode(accountBriefs, externalReferenceCode),
	};
};

const overviewPageGuard = (accountBriefs, externalReferenceCode) => {
	const isValidExternalReferenceCode = validateExternalReferenceCode(
		accountBriefs,
		externalReferenceCode
	);
	const validation =
		isValidExternalReferenceCode || accountBriefs.length === 1;

	const getExternalReferenceCode = () => {
		if (isValidExternalReferenceCode) {
			return externalReferenceCode;
		} else if (accountBriefs.length === 1) {
			return accountBriefs[0].externalReferenceCode;
		}
	};

	return {
		location: `${API_BASE_URL}${liferaySiteName}/overview?${
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		}=${getExternalReferenceCode()}`,
		validate: validation,
	};
};

const usePageGuard = (
	userAccount,
	guard,
	alternativeGuard,
	externalReferenceCode
) => {
	const [isLoading, setLoading] = useState(true);

	const {data} = useQuery(getAccountRolesAndAccountFlags, {
		variables: {
			accountFlagsFilter: `accountKey eq '${externalReferenceCode}' and name eq 'onboarding' and userUuid eq '${userAccount.externalReferenceCode}' and value eq 1`,
			accountId: userAccount.id,
		},
	});

	useEffect(() => {
		if (data) {
			if (
				!validateExternalReferenceCode(
					userAccount.accountBriefs,
					externalReferenceCode
				) ||
				!guard(
					userAccount.accountBriefs,
					externalReferenceCode,
					data.c?.accountFlags?.items,
					data.accountAccountRoles?.items
				).validate
			) {
				const {
					location,
					validate: alternativeValidate,
				} = alternativeGuard(
					userAccount.accountBriefs,
					externalReferenceCode,
					data.c?.accountFlags?.items,
					data.accountAccountRoles?.items
				);

				if (alternativeValidate) {
					window.location.href = location;
				} else {
					window.location.href = `${API_BASE_URL}${liferaySiteName}`;
				}
			} else {
				setLoading(false);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	return {
		isLoading,
	};
};

export {usePageGuard, onboardingPageGuard, overviewPageGuard};
