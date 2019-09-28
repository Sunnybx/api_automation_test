package com.bx.testng;

import com.bx.login.Login;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginTest {


    @DataProvider(name = "user")
    public Object[][] data() throws IOException{
        return Login.LoginData.GenerateData("/Users/baoxi/接口自动化测试/test/src/test/java/com/bx/testng/user_success.xlsx",0);
    }


    @Test(dataProvider = "user")
    public void test(String username,String password) throws IOException{
        String url = "http://mtg.jflyfox.com/front/message.html";

        HashMap<String,String> params = new HashMap<String, String>();


        params.put(username,password);

        CloseableHttpResponse closeableHttpResponse = Login.GET(url,params);

        String resp = EntityUtils.toString(closeableHttpResponse.getEntity());
        System.out.println(resp);
        int status = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(status,302,"statusCode is not 302");


        Header []headers = closeableHttpResponse.getAllHeaders();
        for (int i = 0;i < headers.length;i++){
            if (headers[i].equals("Location: http://mtg.jflyfox.com/home")){
                Assert.assertEquals(headers[i].equals("Location: http://mtg.jflyfox.com/home"),true,"登录失败！");
            }
            else{
                continue;
            }
        }


    }

    @Test(dataProvider = "user")
    public void home(String username,String password) throws IOException, URISyntaxException {

        String url = "http://mtg.jflyfox.com/front/person.html";

        HashMap<String,String> params = new HashMap<String, String>();
        params.put(username,password);

        CloseableHttpResponse closeableHttpResponse = Login.GETDerict(url,params);
        int status = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(status ,200,"status is not 200");

        String resp = EntityUtils.toString(closeableHttpResponse.getEntity());
        System.out.println(resp);
        Assert.assertEquals(resp.contains("个人信息"),true,"登录成功跳转首页失败！");

    }
}
