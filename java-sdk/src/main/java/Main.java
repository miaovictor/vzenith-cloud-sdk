import com.vzenith.cloud.client.FaceClient;
import com.vzenith.cloud.client.MatchRequest;
import com.vzenith.cloud.util.BinaryUtil;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
  private static final String appId = "1";
  private static final String accessKeyId = "3a3840104638228c51eed9d9685a61be";
  private static final String accessSecret = "d4aeac76a3c0240594df06c4fb59883f";

  private static void testDetect(FaceClient client) {
    InputStream inputStream = null;
    byte[] data = null;
    try {
      inputStream = new FileInputStream("/home/victor/Pictures/范冰冰.jpeg");
      data = new byte[inputStream.available()];
      inputStream.read(data);
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String image = BinaryUtil.toBase64String(data);
    String imageType = "BASE64";
    JSONObject response = client.detect(image, imageType, new HashMap<String, String>());

    System.out.println(response.toString());
  }

  private static void testMatch(FaceClient client) {
    List<String> images = new ArrayList<String>();
    images.add("/home/victor/Pictures/范冰冰.jpeg");
    images.add("/home/victor/Pictures/赵丽颖.jpeg");

    List<MatchRequest> matchs = new ArrayList<MatchRequest>();

    for (String path : images) {
      InputStream inputStream = null;
      byte[] data = null;
      try {
        inputStream = new FileInputStream(path);
        data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      String image = BinaryUtil.toBase64String(data);
      String imageType = "BASE64";

      MatchRequest match = new MatchRequest(image, imageType);
      matchs.add(match);
    }

    JSONObject response = client.match(matchs);

    System.out.println(response.toString());
  }

  private static void testSearch(FaceClient client) {
    InputStream inputStream = null;
    byte[] data = null;
    try {
      inputStream = new FileInputStream("/home/victor/Pictures/范冰冰.jpeg");
      data = new byte[inputStream.available()];
      inputStream.read(data);
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String image = BinaryUtil.toBase64String(data);
    String imageType = "BASE64";
    JSONObject response = client.search(image, imageType, "test_group_01", new HashMap<String, String>());

    System.out.println(response.toString());
  }

  private static void testGetUser(FaceClient client) {
    JSONObject response = client.getUser("guan_xiao_tong", "test_group_01", new HashMap<String, String>());
    System.out.println(response.toString());
  }

  public static void main(String[] args) {
    FaceClient client = new FaceClient(appId, accessKeyId, accessSecret);
    client.setBaseURL("http://192.168.10.10");
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);

    testDetect(client);
    testMatch(client);
    testSearch(client);
    testGetUser(client);
  }
}
