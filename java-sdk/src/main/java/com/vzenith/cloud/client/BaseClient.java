package com.vzenith.cloud.client;

import com.vzenith.cloud.http.Headers;
import com.vzenith.cloud.http.HttpClient;
import com.vzenith.cloud.http.HttpRequest;
import com.vzenith.cloud.http.HttpResponse;
import com.vzenith.cloud.util.BinaryUtil;
import com.vzenith.cloud.util.ClientConfiguration;
import com.vzenith.cloud.util.PresignedUrl;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.Date;
import java.util.HashMap;

public abstract class BaseClient {
  protected String appId;
  protected String accessKeyId;
  protected String accessKeySecret;
  protected ClientConfiguration config;

  protected BaseClient(String appId, String accessKeyId, String accessKeySecret) {
    this.appId = appId;
    this.accessKeyId = accessKeyId;
    this.accessKeySecret = accessKeySecret;
  }

  /**
   * @param timeout 服务器建立连接的超时时间（单位：毫秒）
   */
  public void setConnectionTimeoutInMillis(int timeout) {
    if (config == null) {
      config = new ClientConfiguration();
    }
    this.config.setConnectionTimeoutMillis(timeout);
  }

  /**
   * @param timeout 通过打开的连接传输数据的超时时间（单位：毫秒）
   */
  public void setSocketTimeoutInMillis(int timeout) {
    if (config == null) {
      config = new ClientConfiguration();
    }
    this.config.setSocketTimeoutMillis(timeout);
  }

  /**
   * 设置访问网络需要的http代理
   *
   * @param host 代理服务器地址
   * @param port 代理服务器端口
   */
  public void setHttpProxy(String host, int port) {
    if (config == null) {
      config = new ClientConfiguration();
    }
    this.config.setProxy(host, port, Proxy.Type.HTTP);
  }

  /**
   * 设置访问网络需要的socket代理
   *
   * @param host 代理服务器地址
   * @param port 代理服务器端口
   */
  public void setSocketProxy(String host, int port) {
    if (config == null) {
      config = new ClientConfiguration();
    }
    this.config.setProxy(host, port, Proxy.Type.SOCKS);
  }

  public void setBaseURL(String baseURL) {
    if (config == null) {
      config = new ClientConfiguration();
    }
    this.config.setBaseURL(baseURL);
  }

  protected JSONObject requestServer(HttpRequest request) {
    HttpResponse response = HttpClient.execute(request, this.accessKeyId, this.accessKeySecret);
    String resData = response.getBodyStr();
    Integer status = response.getStatus();
    if (status == 200) {
      JSONObject data = new JSONObject();
      data.put("error_code", 0);
      data.put("error_message", "Success!");
      data.put("result", new JSONObject(resData));
      return data;
    } else {
      return new JSONObject(resData);
    }
  }
}
