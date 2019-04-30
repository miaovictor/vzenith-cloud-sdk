package com.vzenith.cloud.http;

import com.vzenith.cloud.util.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private HashMap<String, String> headers;
  private HashMap<String, String> params;
  private String body = "";
  private URI uri;
  private HttpMethod httpMethod;
  private String contentEncoding;
  private ClientConfiguration config;

  public HttpRequest(ClientConfiguration config) {
    headers = new HashMap<String, String>();
    params = new HashMap<String, String>();
    httpMethod = HttpMethod.POST;
    contentEncoding = HttpCharacterEncoding.DEFAULT_ENCODING;
    this.config = config;
  }

  public HttpRequest(HashMap<String, String> header, HashMap<String, String> bodyParams) {
    headers = header;
    params = bodyParams;
  }

  public String getContentEncoding() {
    return contentEncoding;
  }

  public void setContentEncoding(String contentEncoding) {
    this.contentEncoding = contentEncoding;
  }

  public void addHeader(String key, String value) {
    headers.put(key, value);
    if (key.equals(Headers.CONTENT_ENCODING)) {
      this.contentEncoding = value;
    }
  }

  public void addParam(String key, String value) {
    params.put(key, value);
  }

  public HashMap<String, String> getParams() {
    return params;
  }

  public String getParamStr() {
    StringBuffer buffer = new StringBuffer();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      buffer.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
    }
    if (buffer.length() > 0) {
      buffer.deleteCharAt(buffer.length() - 1);
    }
    return buffer.toString();
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setParams(HashMap<String, String> params) {
    this.params = params;
  }

  public HashMap<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(HashMap<String, String> headers) {
    this.headers = headers;
  }

  public URI getUri() {
    return uri;
  }

  public void setUri(URI uri) {
    this.uri = uri;
  }

  public void setUri(String url) {
    try {
      this.uri = new URI(this.config.getBaseURL() + url);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }

  public ClientConfiguration getConfig() {
    return config;
  }

  public void setConfig(ClientConfiguration config) {
    this.config = config;
  }

  public URL generatePresignedUrl(Date expires, String accessKeyId, String accessKeySecret) {
    PresignedUrl presignedUrl = new PresignedUrl(this, accessKeyId, accessKeySecret)
      .setExpires(expires);
    return presignedUrl.generatePresignedUrl();
  }
}
