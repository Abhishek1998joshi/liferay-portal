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

package com.liferay.search.experiences.internal.blueprint.enhancer;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.internal.aggregation.AggregationsImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.Aggregation;
import com.liferay.search.experiences.rest.dto.v1_0.Avg;
import com.liferay.search.experiences.rest.dto.v1_0.Cardinality;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.General;
import com.liferay.search.experiences.rest.dto.v1_0.Query;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SXPBlueprintSearchRequestEnhancerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEnhance() {
		SXPBlueprintSearchRequestEnhancer sxpBlueprintSearchRequestEnhancer =
			new SXPBlueprintSearchRequestEnhancer(
				new AggregationsImpl(),
				new ComplexQueryPartBuilderFactoryImpl(), new QueriesImpl(),
				_searchRequestBuilder,
				new SXPBlueprint() {
					{
						configuration = _createConfiguration();
					}
				});

		sxpBlueprintSearchRequestEnhancer.enhance();

		SearchRequest searchRequest = _searchRequestBuilder.build();

		Map<String, com.liferay.portal.search.aggregation.Aggregation>
			aggregationsMap = searchRequest.getAggregationsMap();

		Assert.assertEquals(
			aggregationsMap.toString(), 2, aggregationsMap.size());

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		Assert.assertEquals("must_not", complexQueryPart.getOccur());

		WrapperQuery wrapperQuery = (WrapperQuery)complexQueryPart.getQuery();

		Assert.assertEquals(
			JSONUtil.put(
				"term", JSONUtil.put("status", 0)
			).toString(),
			new String(wrapperQuery.getSource()));
	}

	private Configuration _createConfiguration() {
		return new Configuration() {
			{
				aggregations = HashMapBuilder.put(
					RandomTestUtil.randomString(),
					() -> {
						Aggregation aggregation = new Aggregation();

						Avg avg = new Avg();

						avg.setField(RandomTestUtil.randomString());

						aggregation.setAvg(avg);

						return aggregation;
					}
				).put(
					RandomTestUtil.randomString(),
					() -> {
						Aggregation aggregation = new Aggregation();

						Cardinality cardinality = new Cardinality();

						cardinality.setField(RandomTestUtil.randomString());
						cardinality.setPrecision_threshold(
							RandomTestUtil.randomInt());

						aggregation.setCardinality(cardinality);

						return aggregation;
					}
				).build();
				general = new General();
				queries = new Query[] {
					new Query() {
						{
							clauses = new Clause[] {
								new Clause() {
									{
										occur = "must_not";
										queryJSON = JSONUtil.put(
											"term", JSONUtil.put("status", 0)
										).toString();
									}
								}
							};
							enabled = true;
						}
					}
				};
			}
		};
	}

	private final SearchRequestBuilder _searchRequestBuilder =
		new SearchRequestBuilderImpl(new SearchRequestBuilderFactoryImpl());

}