package com.vzenith.cloud.client;

import org.json.JSONObject;

public class MatchRequest {
  private String image;
  private String imageType;

  public MatchRequest(String image, String imageType) {
    this.image = image;
    this.imageType = imageType;
  }

  public JSONObject toJsonObject() {
    JSONObject obj = new JSONObject();
    obj.put("image", this.image);
    obj.put("image_type", this.imageType);
    return obj;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getImageType() {
    return imageType;
  }

  public void setImageType(String imageType) {
    this.imageType = imageType;
  }
}
