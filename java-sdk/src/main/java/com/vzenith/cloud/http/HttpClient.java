package com.vzenith.cloud.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
  public static HttpResponse execute(HttpRequest request, String accessKeyId, String accessKeySecret) {
    String charset = request.getContentEncoding();
    String content = request.getBody();
    HashMap<String, String> header = request.getHeaders();

    HttpResponse response = new HttpResponse();

    DataOutputStream out = null;
    InputStream is = null;

    try {
      long expiresLong = System.currentTimeMillis();
      expiresLong += (60 * 1000);
      Date expires = new Date(expiresLong);
      URL url = request.generatePresignedUrl(expires, accessKeyId, accessKeySecret);

      System.out.println(url.toString());

      Proxy proxy = request.getConfig() == null ? Proxy.NO_PROXY : request.getConfig().getProxy();
      HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);

      // set timeout
      if (request.getConfig() != null) {
        conn.setConnectTimeout(request.getConfig().getConnectionTimeoutMillis());
        conn.setReadTimeout(request.getConfig().getSocketTimeoutMillis());
      }

      conn.setDoOutput(true);
      conn.setRequestMethod(request.getHttpMethod().toString());

      // 添加header
      for (Map.Entry<String, String> entry : header.entrySet()) {
        conn.setRequestProperty(entry.getKey(), entry.getValue());
      }

      conn.connect();
      if (content.length() > 0) {
        out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(charset));
        out.flush();
      }

      int statusCode = conn.getResponseCode();
      response.setHeader(conn.getHeaderFields());
      response.setStatus(statusCode);
      response.setCharset(charset);

      if (statusCode == 200) {
        is = conn.getInputStream();
      } else {
        is = conn.getErrorStream();
      }

      if (is != null) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
          outStream.write(buffer, 0, len);
        }
        response.setBody(outStream.toByteArray());
      }
      return response;

    } catch (ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }
}
