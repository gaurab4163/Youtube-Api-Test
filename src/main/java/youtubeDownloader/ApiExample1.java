package youtubeDownloader;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

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
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiExample1 {
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
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
            .list("snippet,contentDetails");
        PlaylistItemListResponse response = request.setKey(DEVELOPER_KEY)
            .setMaxResults(50L)
            .setPageToken("CDIQAA")
            .setPlaylistId("PLb2daHyDTFlR5DlOlbWM7pL2DdPhk9hYA")
            .execute();
       // System.out.println(response);
        
        System.out.println("hello");
        
//       System.out.println(response.toString()); 
//        ArrayList json3 = (ArrayList) response.get("items");
  //      JSONArray json2 = (JSONArray) response.get("items");
//        System.out.println(json2);
//        
//        for(int i=0;i<json2.length();i++) {
//        	JSONObject snippet=(JSONObject) json2.getJSONObject(i).get("snippet");
//        	JSONObject resourceId=(JSONObject) snippet.get("resourceId");
//        	String videoId=resourceId.getString("videoId");
//        	System.out.println(videoId);
//        	
//        }
        
        
     
        JSONObject json = readJsonFromUrl("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,contentDetails&maxResults=50&playlistId=PLb2daHyDTFlR5DlOlbWM7pL2DdPhk9hYA&key=AIzaSyC7JSIzGDAFQzp7LB7od4iuO08ZFU1mdTU");

        JSONObject pageInfo=(JSONObject) json.get("pageInfo");
        int totalResults=pageInfo.getInt("totalResults");
        int resultsPerPage=pageInfo.getInt("resultsPerPage");
        
        for(int iteration=0;iteration<=(totalResults/resultsPerPage);iteration++) {
        	if(iteration!=0) {
        		json= readJsonFromUrl("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,contentDetails&maxResults=50&playlistId=PLb2daHyDTFlR5DlOlbWM7pL2DdPhk9hYA&key=AIzaSyC7JSIzGDAFQzp7LB7od4iuO08ZFU1mdTU"+"&pageToken="+json.get("nextPageToken"));
        	}
        	
        	JSONArray items = (JSONArray) json.get("items");
           // System.out.println(items);
            
            for(int i=0;i<items.length();i++) {
            	JSONObject snippet=(JSONObject) items.getJSONObject(i).get("snippet");
            	JSONObject resourceId=(JSONObject) snippet.get("resourceId");
            	String videoId=resourceId.getString("videoId");
            	System.out.println(videoId);
            	
            }
        }
        
        
        
        
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }

      public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          return json;
        } finally {
          is.close();
        }
      }
    
    
}
