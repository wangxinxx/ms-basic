/**
The MIT License (MIT) * Copyright (c) 2017 铭飞科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.mingsoft.elasticsearch.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import net.mingsoft.basic.util.SpringUtil;
import net.mingsoft.elasticsearch.mapping.BaseMapping;
import net.mingsoft.elasticsearch.search.IBaseSearch;

/**
 * 
 * 搜索引擎通用工具类
 * 
 * @author 铭飞团队
 * @version 版本号：<br/>
 *          创建日期：2017年6月2日<br/>
 *          历史修订：<br/>
 */
public class ElasticsearchUtil {

	/**
	 * 新增&更新搜索引擎数据
	 * 
	 * @param elasticsearchTemplate
	 *            搜索引擎模板对象，通常由spring提供
	 * @param id
	 *            id值
	 * @param base
	 *            mapping实体
	 */
	public static void saveOrUpdate(String id, BaseMapping base) {
		ElasticsearchTemplate elasticsearchTemplate = (ElasticsearchTemplate)SpringUtil.getBean("elasticsearchTemplate");
		IndexQuery indexQuery = new IndexQueryBuilder().withId(id).withObject(base).build();
		elasticsearchTemplate.index(indexQuery);
	}

	/**
	 * 新增&更新搜索引擎数据
	 * 
	 * @param elasticsearchTemplate
	 *            搜索引擎模板对象，通常由spring提供
	 * @param bases
	 *            BaseMapping 对象集合 
	 */
	public static void saveOrUpdate( List<BaseMapping> bases) {
		ElasticsearchTemplate elasticsearchTemplate = (ElasticsearchTemplate)SpringUtil.getBean("elasticsearchTemplate");
		List<IndexQuery> indexQueries = new ArrayList<IndexQuery>();
		for (int i = 0; i < bases.size(); i++) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(bases.get(i).getId()).withObject(bases.get(i))
					.build();
			indexQueries.add(indexQuery);
		}
		if(indexQueries.size()>0) {
			elasticsearchTemplate.bulkIndex(indexQueries);
		}
	}

	/**
	 * 搜索
	 * 
	 * @param baseSearch
	 *            搜索search对象
	 * @param keyword
	 *            关键字
	 * @param field
	 *            过滤字段与比重
	 * @param orderBy
	 *            排序字段
	 * @param order
	 *            排序方式
	 * @param pageNumber
	 *            当前页码
	 * @param pageSize
	 *            一页显示数量
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<BaseMapping> search(IBaseSearch baseSearch, String keyword, Map<String, Float> field,
			String orderBy, SortOrder order, Integer pageNumber, Integer pageSize) {
		Page<BaseMapping> page = baseSearch
				.search(ElasticsearchUtil.buildSearchQuery(keyword, field, orderBy, order, pageNumber, pageSize));
		return page.getContent();
	}

	/**
	 * 组织SearchQuery
	 * 
	 * @param keyword
	 *            关键字
	 * @param field
	 *            过滤字段与比重
	 * @param orderBy
	 *            排序字段
	 * @param order
	 *            排序方式
	 * @param pageNumber
	 *            当前页码
	 * @param pageSize
	 *            一页显示数量
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static SearchQuery buildSearchQuery(String keyword, Map<String, Float> field, String orderBy,
			SortOrder order, Integer pageNumber, Integer pageSize) {
		FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery();
		Iterator keys = field.keySet().iterator();
		while (keys.hasNext()) {
			String fieldName = String.valueOf(keys.next());
			functionScoreQueryBuilder.add(QueryBuilders.matchPhraseQuery(fieldName, keyword),
					ScoreFunctionBuilders.weightFactorFunction(1000));
		}
		functionScoreQueryBuilder.scoreMode("sum").setMinScore(10.0F);
		// 分页参数

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		SearchQuery sq = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(functionScoreQueryBuilder)
				.withSort(SortBuilders.fieldSort(orderBy).order(order.DESC)).build();
		return sq;
	}

}
