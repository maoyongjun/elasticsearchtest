**ES的安装**

1. 在elasticsearch的安装中。可能会遇到x-pack报错的问题，可以将这个插件拿掉，然后启动。
2. elasticsearch不像solr那样自带网站界面可以供用户调试。如果需要进行开发调试，可以安装kibana.
3. 在官网下载的kibana 是放了一个64位的node.exe。如果在32位系统中安装kibana，需要自己下载一个32位
的node.js来安装。另外，如果elasticsearch中去掉了x-pack的插件，同样需要在kibana中也去掉，
这样kibana才能正常使用管理界面，否则会卡住登录界面，无法操作。
4. 在使用elasticsearch的RestHighLevelClient的时候，如果报错class找不到的时候，可以将maven库中
相应的jar包删除后，重新下载。

********************************************

**ES的基础概念：**

1. 集群：多个ES服务器可以构成一个集群。集群的名称默认为elasticsearch。
2. 节点：每一个节点都有一个UUID，唯一的ID，在集群中可以确认节点会加入到那个集群中。
3. 索引：表示对一类文件进行存储。例如 用户信息。
4. 文档:每一个加入索引的数据，都称为一个文档。例如 将用户信息序列化为json加入索引。这个json数据就是一个文档。
5. 分片和备份数比例:一个索引在磁盘中用一个文件存储时，由于空间和效率的原因，存储不合适时，需要将索引分为多个分片来存储。考虑高可用性。会同时声明备份数,例如分片是5，备份数是1，会生成10个分片。其中5个是备份。


******************************************


**ES中索引的管理：**

######*具体来讲，可以进行索引的创建，修改和删除。其中在索引的创建过程中，可以进行相关的设定。例如设置分片的数量。然后，需要对索引创建Mapping映射。创建索引的过程类似于在数据库中创建表。Mapping映射，指定索引中的field,已经fieldType。例如，存储 title，类型为text（text类型是可分词的）。对文档进行索引，ES支持自动识别field类型，可以不建立mapping,直接对document进行索引。这时候，ES会根据document中的字段，自动的创建field，并指定fieldType。*
1. ES中查询文档。和Lucence类型，分为布尔查询、词组查询、模糊查询等。
2. ES中索引的聚合。例如，对商品按照类型进行统计。语法是:HTTP GET {“agg”:{“aggname”:{“aggfunction”:””}}}


********************************************
**kibana中调用ES的API：**


#####ES中查询建议的使用：


######*在这里 chnia 因为索引里面有china所以会输出为建议词 chinatext里面存放需要查询的文档，这个文档会通过分词后，给出查询建议。field是要查询的字段。suggest查询和query查询 的索引机制不一样，所以和模糊查询还是有差异。*

	POST /test/_search
	{
	  "query": {
	    "match_all": {}
	  },
	  "suggest": {
	    "text": "chnia",
	    "mysuggest1": {
	      "term": {
	        "field": "content"
	      }
	    }
	  }
	}



#####指标聚合：

	POST /test/_search
	{
	  "size":0,
	  "aggs": {
	    "NAME": {
	      "max": {
	        "field": "count"
	      }
	    }
	  }
	  
	}
*******************************************

**JAVA中使用ES的API：**
######*可以直接使用ES high rest level client或者httpClient。或者和spring data整合（SPA）。*

	ES high Rest level client中的API。 
	RestHighLevelClient  restHighLevelClient =
	new RestHighLevelClient(RestClient.builder(new HttpPost(“ip”,port))).//创建客户端
	SearchRequest  request = new SearchRequest();//创建请求
	SearchSourceBuilder builder = new SearchSourceBuilder();//创建查询器
	Builder.query(QueryBuilders.marchAllQuery());//设置查询器
	Builder.aggregation(AggregationBuilders.terms(“top_10_states”)).field(“state”);
	//聚合索引
	request.source(builder);//关联请求和查询
	restHighLevelClient.search(request);//进行查询







