1、在elasticsearch的安装中。可能会遇到x-pack报错的问题，可以将这个插件拿掉，然后启动。
2、elasticsearch不像solr那样自带网站界面可以供用户调试。如果需要进行开发调试，可以安装kibana.
3、在官网下载的kibana 是放了一个64位的node.exe。如果在32位系统中安装kibana，需要自己下载一个32位
的node.js来安装。另外，如果elasticsearch中去掉了x-pack的插件，同样需要在kibana中也去掉，
这样kibana才能正常使用管理界面，否则会卡住登录界面，无法操作。
4、在使用elasticsearch的RestHighLevelClient的时候，如果报错class找不到的时候，可以将maven库中
相应的jar包删除后，重新下载。