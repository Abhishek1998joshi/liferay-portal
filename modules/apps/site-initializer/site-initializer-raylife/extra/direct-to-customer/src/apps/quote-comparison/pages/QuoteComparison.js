import React, {useEffect, useState} from 'react';

import {LiferayService} from '~/shared/services/liferay';
import ProductComparison from '../components/product-comparison';

const QuoteComparison = () => {
	const [quotes, setQuotes] = useState([]);

	useEffect(() => {
		LiferayService.LiferayAPI.get('/o/c/quotecomparisons')
			.then((response) => setQuotes(response.data.items))
			.catch((error) => console.error(error.message));
	}, []);

	return (
		<div className="quote-comparison">
			{quotes.map((quote, index) => (
				<ProductComparison key={index} {...quote} />
			))}
		</div>
	);
};

export default QuoteComparison;
