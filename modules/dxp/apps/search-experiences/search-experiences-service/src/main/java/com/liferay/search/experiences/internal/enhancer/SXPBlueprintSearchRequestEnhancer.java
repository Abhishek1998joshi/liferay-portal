/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.enhancer;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregation;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.rest.dto.v1_0.Aggregation;
import com.liferay.search.experiences.rest.dto.v1_0.Avg;
import com.liferay.search.experiences.rest.dto.v1_0.Cardinality;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.General;
import com.liferay.search.experiences.rest.dto.v1_0.Query;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.beans.ExceptionListener;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Petteri Karttunen
 */
public class SXPBlueprintSearchRequestEnhancer {

	public SXPBlueprintSearchRequestEnhancer(
		Aggregations aggregations,
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		Queries queries, SearchRequestBuilder searchRequestBuilder,
		SXPBlueprint sxpBlueprint) {

		_aggregations = aggregations;
		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_queries = queries;
		_searchRequestBuilder = searchRequestBuilder;

		_configuration = sxpBlueprint.getConfiguration();
	}

	public void enhance() {
		_processAggregations(_configuration.getAggregations());
		_processGeneral(_configuration.getGeneral());
		_processQueries(_configuration.getQueries());

		if (!ArrayUtil.isEmpty(_runtimeException.getSuppressed())) {
			throw _runtimeException;
		}
	}

	private <V> void _forEach(
		Iterable<V> iterable, Consumer<V> consumer,
		ExceptionListener exceptionListener) {

		for (V value : iterable) {
			try {
				consumer.accept(value);
			}
			catch (Exception exception) {
				exceptionListener.exceptionThrown(exception);
			}
		}
	}

	private <K, V> void _forEach(
		Map<K, V> map, BiConsumer<K, V> biConsumer,
		ExceptionListener exceptionListener) {

		map.forEach(
			(key, value) -> {
				try {
					biConsumer.accept(key, value);
				}
				catch (Exception exception) {
					exceptionListener.exceptionThrown(exception);
				}
			});
	}

	private com.liferay.portal.search.aggregation.Aggregation _getAggregation(
		String name, Aggregation aggregation) {

		if (aggregation.getAvg() != null) {
			Avg avg = aggregation.getAvg();

			return _aggregations.avg(name, avg.getField());
		}

		if (aggregation.getCardinality() != null) {
			Cardinality cardinality = aggregation.getCardinality();

			CardinalityAggregation cardinalityAggregation =
				_aggregations.cardinality(name, cardinality.getField());

			cardinalityAggregation.setPrecisionThreshold(
				cardinality.getPrecision_threshold());

			return cardinalityAggregation;
		}

		throw new IllegalArgumentException();
	}

	private void _processAggregation(String name, Aggregation aggregation) {
		_searchRequestBuilder.addAggregation(
			_translateAggregation(name, aggregation));
	}

	private void _processAggregations(Map<String, Aggregation> map) {
		_forEach(
			map, this::_processAggregation, _runtimeException::addSuppressed);
	}

	private void _processClause(Clause clause) {
		com.liferay.portal.search.query.Query query = _toQuery(
			clause.getQueryJSON());

		if (query != null) {
			_searchRequestBuilder.addComplexQueryPart(
				_complexQueryPartBuilderFactory.builder(
				).query(
					query
				).occur(
					clause.getOccur()
				).build());
		}
	}

	private void _processClauses(Clause[] clauses) {
		if (!ArrayUtil.isEmpty(clauses)) {
			_forEach(
				Arrays.asList(clauses), this::_processClause,
				_runtimeException::addSuppressed);
		}
	}

	private void _processGeneral(General general) {
		if (general.getApplyIndexerClauses() != null) {
			_searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.full.query.suppress.indexer.provided.clauses",
					!general.getApplyIndexerClauses()));
		}
	}

	private void _processQueries(Query[] queries) {
		if (!ArrayUtil.isEmpty(queries)) {
			_forEach(
				Arrays.asList(queries), this::_processQuery,
				_runtimeException::addSuppressed);
		}
	}

	private void _processQuery(Query query) {
		if (GetterUtil.getBoolean(query.getEnabled())) {
			_processClauses(query.getClauses());
		}
	}

	private com.liferay.portal.search.query.Query _toQuery(String queryJSON) {
		return _queries.wrapper(queryJSON);
	}

	private com.liferay.portal.search.aggregation.Aggregation
		_translateAggregation(String name, Aggregation aggregation1) {

		com.liferay.portal.search.aggregation.Aggregation aggregation2 =
			_getAggregation(name, aggregation1);

		if (aggregation1.getAggs() != null) {
			_forEach(
				aggregation1.getAggs(),
				(name1, aggregation) -> aggregation2.addChildAggregation(
					_translateAggregation(name1, aggregation)),
				_runtimeException::addSuppressed);
		}

		return aggregation2;
	}

	private final Aggregations _aggregations;
	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private final Configuration _configuration;
	private final Queries _queries;
	private final RuntimeException _runtimeException = new RuntimeException();
	private final SearchRequestBuilder _searchRequestBuilder;

}