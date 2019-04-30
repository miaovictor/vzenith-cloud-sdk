package com.vzenith.cloud.client;

import com.vzenith.cloud.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceClient extends BaseClient {
  public FaceClient(String appId, String accessKeyId, String accessKeySecret) {
    super(appId, accessKeyId, accessKeySecret);
  }

  public JSONObject detect(String image, String imageType, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.POST);
    request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

    JSONObject body = new JSONObject();
    body.put("image", image);
    body.put("image_type", imageType);
    if (options != null) {
      for (Map.Entry<String, String> entry : options.entrySet()) {
        body.put(entry.getKey(), entry.getValue());
      }
    }

    request.setBody(body.toString());
    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/faces/detect");
    return requestServer(request);
  }

  public JSONObject match(List<MatchRequest> input) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.POST);
    request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

    JSONArray body = new JSONArray();
    for (MatchRequest req : input) {
      body.put(req.toJsonObject());
    }
    request.setBody(body.toString());
    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/faces/match");
    return requestServer(request);
  }

  public JSONObject search(String image, String imageType, String groupIdList, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.POST);
    request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

    JSONObject body = new JSONObject();
    body.put("image", image);
    body.put("image_type", imageType);
    body.put("group_id_list", groupIdList);
    if (options != null) {
      for (Map.Entry<String, String> entry : options.entrySet()) {
        body.put(entry.getKey(), entry.getValue());
      }
    }

    request.setBody(body.toString());
    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/faces/search");
    return requestServer(request);
  }

  public JSONObject addUser(String image, String imageType, String groupId, String userId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.POST);
    request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

    JSONObject body = new JSONObject();
    body.put("image", image);
    body.put("image_type", imageType);
    body.put("group_id", groupId);
    body.put("user_id", userId);
    if (options != null) {
      for (Map.Entry<String, String> entry : options.entrySet()) {
        body.put(entry.getKey(), entry.getValue());
      }
    }

    request.setBody(body.toString());
    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/faces");
    return requestServer(request);
  }

  public JSONObject updateUser(String image, String imageType, String groupId, String userId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.PUT);
    request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

    JSONObject body = new JSONObject();
    body.put("image", image);
    body.put("image_type", imageType);
    body.put("group_id", groupId);
    body.put("user_id", userId);
    if (options != null) {
      for (Map.Entry<String, String> entry : options.entrySet()) {
        body.put(entry.getKey(), entry.getValue());
      }
    }

    request.setBody(body.toString());
    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/faces");
    return requestServer(request);
  }

  public JSONObject faceDelete(String userId, String groupId, String faceToken, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.DELETE);
    request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

    JSONObject body = new JSONObject();
    body.put("action_type", "USER");

    request.setBody(body.toString());
    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups/" + groupId + "/users/" + userId + "/faces/" + faceToken);
    return requestServer(request);
  }

  public JSONObject getUser(String userId, String groupId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.GET);

    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups/" + groupId + "/users/" + userId);
    return requestServer(request);
  }

  public JSONObject faceGetlist(String userId, String groupId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.GET);

    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups/" + groupId + "/users/" + userId + "/faces");
    return requestServer(request);
  }

  public JSONObject getGroupUsers(String groupId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.GET);

    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups/" + groupId + "/users");
    return requestServer(request);
  }

  public JSONObject deleteUser(String groupId, String userId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.DELETE);

    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups/" + groupId + "/users/" + userId);
    return requestServer(request);
  }

  public JSONObject groupAdd(String groupId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.POST);

    JSONObject body = new JSONObject();
    body.put("group_id", groupId);
    body.put("group_name", "");
    body.put("remark", "");

    request.setBody(body.toString());
    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups");
    return requestServer(request);
  }

  public JSONObject groupDelete(String groupId, HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.DELETE);

    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups/" + groupId);
    return requestServer(request);
  }

  public JSONObject getGroupList(HashMap<String, String> options) {
    HttpRequest request = new HttpRequest(config);
    request.setHttpMethod(HttpMethod.GET);

    request.setUri("/api/frs/v1/user/apps/" + this.appId + "/groups/");
    return requestServer(request);
  }

}
