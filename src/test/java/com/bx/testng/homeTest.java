package com.bx.testng;
import com.bx.test.Home;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.http.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class homeTest {

    @Test(groups={"home"})
    public void test() throws URISyntaxException,IOException {

        String url = "http://mtg.jflyfox.com/home";
        CloseableHttpResponse closeableHttpResponse = Home.Get(url,null,null);
        int status  = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(status,200,"status code is not 200");

        Header []headers = closeableHttpResponse.getHeaders("Set-Cookie");
        String string = headers[0].toString().split(":")[1];
        String jossionID = string.split(";")[0];

        System.out.println(jossionID);

        String resp = EntityUtils.toString(closeableHttpResponse.getEntity());
        boolean result = resp.contains("首页 - 门头沟信息网");
        Assert.assertEquals(result,true,"首页显示失败！");
    }
    
}
