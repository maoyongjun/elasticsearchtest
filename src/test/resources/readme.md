1、在elasticsearch的安装中。可能会遇到x-pack报错的问题，可以将这个插件拿掉，然后启动。
2、elasticsearch不像solr那样自带网站界面可以供用户调试。如果需要进行开发调试，可以安装kibana.
3、在官网下载的kibana 是放了一个64位的node.exe。如果在32位系统中安装kibana，需要自己下载一个32位
的node.js来安装。另外，如果elasticsearch中去掉了x-pack的插件，同样需要在kibana中也去掉，
这样kibana才能正常使用管理界面，否则会卡住登录界面，无法操作。
4、在使用elasticsearch的RestHighLevelClient的时候，如果报错class找不到的时候，可以将maven库中
相应的jar包删除后，重新下载。


总结的ES的相关知识如下：
一、ES搜索引擎简介：ES在搜索的过程中，需要先创建索引，然后对非结构化或者半结构化的数据进行索引。 存储完成后，可对索引的数据，进行查询。进行ES相关开发，常常配合kibana进行使用。ES是基于REST API的。获取数据使用的get请求，更新或者写入数据使用是PUT请求，删除数据使用的DELETE请求。

二、ES的API：通过ES的API可以 管理索引，对文档进行索引，查询等。
1、ES中索引的管理：具体来讲，可以进行索引的创建，修改和删除。其中在索引的创建过程中，可以进行相关的设定。例如设置分片的数量。然后，需要对索引创建Mapping映射。创建索引的过程类似于在数据库中创建表。Mapping映射，指定索引中的field,已经fieldType。例如，存储 title，类型为text（text类型是可分词的）。对文档进行索引，ES支持自动识别field类型，可以不建立mapping,直接对document进行索引。这时候，ES会根据document中的字段，自动的创建field，并指定fieldType。
2、ES中查询文档。和Lucence类型，分为布尔查询、词组查询、模糊查询等。
3、ES中索引的聚合。例如，对商品按照类型进行统计。语法是:
HTTP GET {“agg”:{“aggname”:{“aggfunction”:””}}}


JAVA中使用ES的API：可以直接使用ES high rest level client或者httpClient。或者和spring data整合（SPA）。
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






ES中查询建议的使用：

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

在这里 chnia 因为索引里面有china所以会输出为建议词 china


text里面存放需要查询的文档，这个文档会通过分词后，给出查询建议。
field是要查询的字段。

suggest查询和query查询 的索引机制不一样，所以和模糊查询还是有差异。








