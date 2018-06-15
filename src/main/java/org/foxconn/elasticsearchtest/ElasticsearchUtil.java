package org.foxconn.elasticsearchtest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ElasticsearchUtil {
	public static void main(String[] args) throws IOException {
		ElasticsearchUtil util = new ElasticsearchUtil();
		util.test1();
	}
	
	private void test1() throws IOException{
		try(
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));){
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices("social-*");
			searchRequest.source(searchSourceBuilder);
			SearchResponse searchResponse = client.search(searchRequest);
			System.out.println(searchResponse.toString());
		}
		
	}
}
