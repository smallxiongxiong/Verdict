package com.law.verdict.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.law.verdict.tools.StringTool;

@Component
public class ESClient {

	@Value("${law.es.cluster.name}")
	private String name;
	@Value("${law.es.cluster.nodes}")
	private String nodes;
	@Value("${law.es.cluster.connectType}")
	private String connectType;

	
	@Bean(name = "RestClient")
    public RestClient getEsClient() {
		String[] nodeArray = nodes.split(",");
		HttpHost[] httpHosts = new HttpHost[nodeArray.length];
		for(int i = 0; i < nodeArray.length; i++) {
			String[] element = nodeArray[i].split(":");
			if(element.length != 2) {
				throw new RuntimeException("less the necessary element(ip or port)");
			}
			if(StringTool.isIPAddress(element[0])) {
				throw new RuntimeException("ip address is not correct");
			}
			int port;
			try {
				port = Integer.valueOf(element[1]);
			}catch(Exception e) {
				throw new RuntimeException("port is not correct, can not convert to String", e);
			}
			if(port <= 0 || port > 65535) {
				throw new RuntimeException("port is not correct, the scope of port is between 1 and 65535");
			}
			httpHosts[i] = new HttpHost(element[0], port, this.connectType);
		}
		if(httpHosts.length <= 0) {
			throw new RuntimeException("client setting is empty, check the setting");
		}
		RestClientBuilder builder = RestClient.builder(httpHosts);
        return builder.build();
    }
	
	
}
