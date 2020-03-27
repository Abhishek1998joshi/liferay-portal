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

import React, {useEffect, useState} from 'react';

import {COLLECTION_LIST_FORMATS} from '../../config/constants/collectionListFormats';
import CollectionService from '../../services/CollectionService';
import InfoItemService from '../../services/InfoItemService';
import {useDispatch, useSelector} from '../../store/index';
import {ControlsIdConverterContextProvider} from '../ControlsIdConverterContext';

const COLLECTION_ID_DIVIDER = '$';

function collectionIsMapped(collectionConfig) {
	return collectionConfig.collection;
}

function getCollectionPrefix(collectionId, index) {
	return `collection-${collectionId}-${index}${COLLECTION_ID_DIVIDER}`;
}

function getToControlsId(collectionId, index) {
	return itemId => {
		if (!itemId) {
			return null;
		}

		return `${getCollectionPrefix(collectionId, index)}${itemId}`;
	};
}

function fromControlsId(controlsItemId) {
	if (!controlsItemId) {
		return null;
	}

	const [, itemId] = controlsItemId.split(COLLECTION_ID_DIVIDER);

	return itemId || controlsItemId;
}

const NotMappedMessage = () => (
	<div className="page-editor__collection__not-mapped-message">
		{Liferay.Language.get('not-mapped')}
	</div>
);

const Grid = ({
	child,
	collection,
	collectionFields,
	collectionId,
	collectionLength,
	numberOfColumns,
	numberOfItems,
}) => {
	const numberOfRows = Math.ceil(numberOfItems / numberOfColumns);
	const maxNumberOfItems = Math.min(collectionLength, numberOfItems);

	const createRows = () => {
		const rows = [];

		for (let i = 0; i < numberOfRows; i++) {
			const columns = [];

			for (let j = 0; j < numberOfColumns; j++) {
				const index = [i, j].join('-');
				const itemCount = i * numberOfColumns + j;

				columns.push(
					<div className={`col col-${12 / numberOfColumns}`}>
						{itemCount < maxNumberOfItems && (
							<ControlsIdConverterContextProvider
								key={index}
								value={{
									collectionFields,
									collectionItem:
										collection[i * numberOfColumns + j],
									fromControlsId,
									toControlsId: getToControlsId(
										collectionId,
										index
									),
								}}
							>
								{React.cloneElement(child)}
							</ControlsIdConverterContextProvider>
						)}
					</div>
				);
			}

			rows.push(<div className="row">{columns}</div>);
		}

		return rows;
	};

	return createRows();
};

const Stack = ({
	child,
	collection,
	collectionFields,
	collectionId,
	collectionLength,
	numberOfItems,
}) => {
	const maxNumberOfItems = Math.min(collectionLength, numberOfItems);

	return Array.from({length: maxNumberOfItems}).map((_element, idx) => (
		<ControlsIdConverterContextProvider
			key={idx}
			value={{
				collectionFields,
				collectionItem: collection[idx],
				fromControlsId,
				toControlsId: getToControlsId(collectionId, idx),
			}}
		>
			{React.cloneElement(child)}
		</ControlsIdConverterContextProvider>
	));
};

const Collection = React.forwardRef(({children, item}, ref) => {
	const child = React.Children.toArray(children)[0];
	const collectionConfig = item.config;

	const ContentComponent =
		collectionConfig.listFormat === COLLECTION_LIST_FORMATS.grid
			? Grid
			: Stack;

	const dispatch = useDispatch();

	const segmentsExperienceId = useSelector(
		state => state.segmentsExperienceId
	);

	const [collection, setCollection] = useState({
		items: [],
		length: 0,
	});

	useEffect(() => {
		if (collectionConfig.collection) {
			CollectionService.getCollectionField({
				collection: collectionConfig.collection,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
				size: collectionConfig.numberOfItems,
			}).then(response => {
				setCollection(response);
			});
		}
	}, [
		collectionConfig.collection,
		collectionConfig.numberOfItems,
		dispatch,
		segmentsExperienceId,
	]);

	const [collectionFields, setCollectionFields] = useState([]);

	useEffect(() => {
		if (collectionConfig.collection) {
			InfoItemService.getAvailableStructureMappingFields({
				classNameId: collectionConfig.collection.itemType,
				classTypeId: collectionConfig.collection.itemSubtype,
				onNetworkStatus: dispatch,
			}).then(({infoDisplayFields}) => {
				setCollectionFields(infoDisplayFields);
			});
		}
	}, [dispatch, collectionConfig.collection]);

	return (
		<div className="page-editor__collection" ref={ref}>
			{collectionIsMapped(collectionConfig) ? (
				<ContentComponent
					child={child}
					collection={collection.items}
					collectionFields={collectionFields}
					collectionId={item.itemId}
					collectionLength={collection.items.length}
					numberOfColumns={collectionConfig.numberOfColumns}
					numberOfItems={collectionConfig.numberOfItems}
				/>
			) : (
				<NotMappedMessage />
			)}
		</div>
	);
});

export default Collection;
