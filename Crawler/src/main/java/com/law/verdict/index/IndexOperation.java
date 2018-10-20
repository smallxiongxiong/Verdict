package com.law.verdict.index;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.law.verdict.constant.CrawlerConstant;

@Service
public class IndexOperation<T> {
	private static Logger log = LoggerFactory.getLogger(IndexOperation.class);
	private static String index_name = System.getProperty(CrawlerConstant.KEY_INDEX_NAME);
	private static String index = "/" + index_name + "/verdict/";
	static {
		index_name = index_name == null ? "law" : index_name;
		index = "/" + index_name + "/verdict/";
		log.info("index logpath: {}", index);
	}

	@Autowired
	RestClient restClient;

	public boolean addIndexDocument(String value, String id) {
		boolean result = false;
		try {
			Map<String, String> params = Collections.emptyMap();
			HttpEntity entity = new NStringEntity(value, ContentType.APPLICATION_JSON);
			Response response = restClient.performRequest("PUT",  index + id, params, entity);
			StatusLine statusLine = response.getStatusLine();
			int code = statusLine.getStatusCode();
			if (200 <= code && code < 300) {
				result = true;
			} else {
				log.error("code:{}, reason:{}", statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean addIndexDocument(T t) {
		Gson gson = new Gson();
		String json = gson.toJson(t);
		return this.addIndexDocument(json, ((IndexDocument) t).getCaseID().replaceAll("-", ""));
	}
}
