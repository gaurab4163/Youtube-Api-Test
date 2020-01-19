package youtubeDownloader;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Sample Java code for youtube.playlists.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiExample {
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String DEVELOPER_KEY = "";

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.Playlists.List request = youtubeService.playlists()
            .list("snippet,contentDetails");
        PlaylistListResponse response = request.setKey(DEVELOPER_KEY)
            .setId("PLb2daHyDTFlR5DlOlbWM7pL2DdPhk9hYA")
            .setMaxResults(50L)
            .execute();
        System.out.println(response);
        
        System.out.println("hello");
        System.out
        .println(jsonGetRequest("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,contentDetails&maxResults=50&playlistId=PLb2daHyDTFlR5DlOlbWM7pL2DdPhk9hYA&key=AIzaSyC7JSIzGDAFQzp7LB7od4iuO08ZFU1mdTU"));
        
    }
    
    
    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
      }

      public static String jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
          URL url = new URL(urlQueryString);
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
          connection.setDoOutput(true);
          connection.setInstanceFollowRedirects(false);
          connection.setRequestMethod("GET");
          connection.setRequestProperty("Content-Type", "application/json");
          connection.setRequestProperty("charset", "utf-8");
          connection.connect();
          InputStream inStream = connection.getInputStream();
          json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        return json;
      }
}
