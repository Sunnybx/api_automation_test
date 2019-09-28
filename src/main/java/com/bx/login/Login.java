package com.bx.login;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpHost;
import java.net.URI;
import java.net.URL;
import org.apache.http.client.utils.URIUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login{
    public static CloseableHttpResponse GET(String url, HashMap<String,String> params) throws IOException {
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie clientCookie = null ;

        for(Map.Entry<String,String> param:params.entrySet()){
            clientCookie = new BasicClientCookie(param.getKey(),param.getValue());
        }

        cookieStore.addCookie(clientCookie);

        CloseableHttpClient closeableHttpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpPost httpPost = new HttpPost(url);


        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        return closeableHttpResponse;
    }

    public static CloseableHttpResponse GETDerict(String url,HashMap<String,String> params) throws IOException,URISyntaxException {

        CookieStore store = new BasicCookieStore();
        BasicClientCookie clientCookie = null ;

        for(Map.Entry<String,String> param:params.entrySet()){
            clientCookie = new BasicClientCookie(param.getKey(),param.getValue());
        }

        store.addCookie(clientCookie);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();

        HttpGet httpGet= new HttpGet(url);
        HttpClientContext context = new HttpClientContext();

        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet,context);
        HttpHost target = context.getTargetHost();
        List<URI> redirectLocations = context.getRedirectLocations();
        URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);

        HttpGet httpGet1 = new HttpGet(location);
        closeableHttpResponse = httpClient.execute(httpGet1);
        return closeableHttpResponse;
    }

    public static class LoginData {

        public static Object[][] GenerateData(String filePath,int sheetId) throws IOException {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sh = wb.getSheetAt(sheetId);
            int numberRows = sh.getPhysicalNumberOfRows();
            int numberCell = sh.getRow(0).getLastCellNum();
            Object [][] data = new Object[numberRows][numberCell];
            for (int i = 0;i < numberRows;i++){
                if (null == sh.getRow(i) || "".equals(sh.getRow(i))){
                    continue;
                }
                for (int j = 0;j < numberCell;j++){
                    if (null == sh.getRow(i).getCell(j) || "".equals(sh.getRow(i).getCell(j))){
                        continue;
                    }
                    XSSFCell cell = sh.getRow(i).getCell(j);
                    cell.setCellType(CellType.STRING);
                    data[i][j] = cell.getStringCellValue();
                }
            }
            return data;
        }
    }
}
