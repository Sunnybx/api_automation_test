package com.bx.test;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home {
    public static CloseableHttpResponse Get(String url, HashMap<String,String> params,HashMap<String,String> headers) throws URISyntaxException,IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        if (params != null) {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String,String> param : params.entrySet()){
                uriBuilder.addParameter(param.getKey(),param.getValue());
            }
        }
        HttpGet httpGet = new HttpGet(url);
        if (headers != null){
            for (Map.Entry<String,String> header : headers.entrySet()){
                httpGet.addHeader(header.getKey(),header.getValue());
            }
        }
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        return closeableHttpResponse;
    }
}
