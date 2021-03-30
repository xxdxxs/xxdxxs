package com.xxdxxs.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


/**
 * @author: xxdxxs
 */
public class HttpUtils {

    private final static Logger _log = LoggerFactory.getLogger(HttpUtils.class);

    private final static int HTTP_SUCCESS_CODE = 200;

    /**
     * @param url 地址
     * @param businessParam 参数
     * @return HttpEntity
     */
    public static HttpEntity doPost(String url, String businessParam) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().
                    setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                    .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED + "; charset=" + Charsets.UTF_8);
            StringEntity se = new StringEntity(businessParam);
            httpPost.setEntity(se);
            HttpResponse response = null;
            response = httpClient.execute(httpPost);
            if (HTTP_SUCCESS_CODE == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return entity;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用http模拟webservice请求
     *
     * @param requestUrl 请求地址
     * @param requestParam 请求参数
     * @param user 用户
     * @param password 密码
     * @return String
     */
    public static String doWSByHttp(String requestUrl, String requestParam, String user, String password) {
        HttpURLConnection connection = null;
        String resMsg = null;
        try {
            URL url = new URL(requestUrl.trim());
            String authString = user + ":" + password;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);
            byte[] requestBuf;
            requestBuf = requestParam.getBytes(Charsets.UTF_8);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            connection.setRequestMethod(HttpMethod.POST.name());
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", ContentType.TEXT_XML + ";charset=" + Charsets.UTF_8);
            connection.setConnectTimeout(30000);
            connection.connect();
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.write(requestBuf);
            out.flush();
            out.close();
            if (connection.getResponseCode() == HTTP_SUCCESS_CODE) {
                InputStream in = connection.getInputStream();
                ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
                byte[] readBuf = new byte[100];
                while (true) {
                    int ret = in.read(readBuf);
                    if (ret < 0) {
                        break;
                    }
                    bufOut.write(readBuf, 0, ret);
                }
                byte[] rspBuf = bufOut.toByteArray();
                resMsg = new String(rspBuf, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return resMsg;
    }

}
