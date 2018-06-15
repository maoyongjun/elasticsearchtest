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
		// org.elasticsearch.client.node.NodeClient.NodeClient(Settings
		// settings, ThreadPool threadPool)
		// Settings settings = new Setting<>(key, defaultValue, parser,
		// properties)
		// ElasticsearchClient client = new NodeClient(settings, threadPool)
		// IndexRequest indexRequest;
		// IndexRequestBuilder builder = new IndexRequestBuilder(client, action)
		// InetAddress address = new Inet4Address();

		// HttpHost hosts = new HttpHost("10.67.124.158",9200);
		// RestClientBuilder builder = new RestClientBuilder(hosts,hosts);
		// RestHighLevelClient restHighLevelClient = new
		// RestHighLevelClient(builder);
		// IndicesClient client = new IndicesClient(restHighLevelClient);

		//CreateIndexRequest createIndexRequest = new CreateIndexRequest();
		// IndicesClient client = null;
		// client.create(createIndexRequest);

		// HttpClientConfigCallback restClientBuilder = null;
		// HttpHost host = null;
		// host = new HttpHost("localhost");
		// HttpHost[] hosts = new HttpHost[] { host };
		// HttpAsyncClientBuilder httpClientBuilder =
		// HttpAsyncClientBuilder.create().setMaxConnPerRoute(1)
		// .setMaxConnTotal(1);
		// CloseableHttpAsyncClient closeableHttpAsyncClient =
		// httpClientBuilder.build();
		// boolean isRunning = closeableHttpAsyncClient.isRunning();
		// System.out.println(isRunning);
		// RestHighLevelClient restClient = new
		// RestHighLevelClient(httpClientBuilder);
		// org.apache.http.conn.ConnectionKeepAliveStrategy
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
//		AuthCache auth = new AuthCache();
		
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
