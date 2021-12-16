import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {CardFormActionsWithSave} from '../../../../../../../common/components/fragments/Card/FormActionsWithSave';
import FormCard from '../../../../../../../common/components/fragments/Card/FormCard';
import {LiferayService} from '../../../../../../../common/services/liferay';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../../../../common/services/liferay/storage';
import {clearExitAlert} from '../../../../../../../common/utils/exitAlert';
import {smoothScroll} from '../../../../../../../common/utils/scroll';
import {AppContext} from '../../../../../context/AppContext';
import {setSelectedProduct} from '../../../../../context/actions';
import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../../../utils/constants';
import {BusinessTypeSearch} from './Search';

export function FormBasicBusinessType({form}) {
	const {setSection} = useStepWizard();
	const [newSelectedProduct, setNewSelectedProduct] = useState('');
	const {dispatch, state} = useContext(AppContext);
	const {setValue} = useFormContext();

	const goToNextForm = () => {
		setSection(AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION);

		if (state.selectedProduct !== newSelectedProduct) {
			setValue('business', '');
			dispatch(setSelectedProduct(newSelectedProduct));
		}

		smoothScroll();
	};

	const goToPreviousPage = () => {
		clearExitAlert();

		window.location.href = LiferayService.getLiferaySiteName();

		if (Storage.itemExist(STORAGE_KEYS.BACK_TO_EDIT)) {
			Storage.removeItem(STORAGE_KEYS.BACK_TO_EDIT);
		}
	};

	return (
		<FormCard>
			<div className="d-flex flex-column mb-5">
				<BusinessTypeSearch
					form={form}
					setNewSelectedProduct={setNewSelectedProduct}
				/>
			</div>

			<CardFormActionsWithSave
				isValid={!!form?.basics?.businessCategoryId}
				onNext={goToNextForm}
				onPrevious={goToPreviousPage}
			/>
		</FormCard>
	);
}
