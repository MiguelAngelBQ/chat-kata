package org.ejmc.android.simplechat.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests {

	/**
	 * Gets chat messages from sequence number.
	 * 
	 * @param seq
	 * @param handler
	 */
	//NetResponseHandler<ChatList> handler
	public void chatGET(int seq, NetResponseHandler<ChatList> handler) {
		HttpClient httpClient = new DefaultHttpClient();
		 
		HttpGet del = new HttpGet("http://172.20.0.9/chat-kata/api/chat?seq="+seq);
		 
		del.setHeader("Content-type", "text/json");
		ChatList Msg = new ChatList();
		try
		{
		        HttpResponse resp = httpClient.execute(del);
		        InputStream instream = resp.getEntity().getContent();
		        
		        String respStr = convertStreamToString(instream);
		        instream.close();
		        
		        JSONObject jsonObject = new JSONObject(respStr);
		        
		        JSONArray respJSON = new JSONArray();
		        respJSON = jsonObject.getJSONArray("messages");
		 
		        for(int i=0; i<respJSON.length(); i++)
		        {
		            JSONObject obj = respJSON.getJSONObject(i);
		 
		            Msg.setMessage(obj.getString("nick"),obj.getString("message"));
		        }		        		        
		        handler.onSuccess(Msg);
		}
		catch(Exception ex)
		{
		        Log.e("ServicioRest","Error!", ex);
		}
	}
	
	private static String convertStreamToString(InputStream is) {
		   /*
		    * To convert the InputStream to String we use the BufferedReader.readLine()
		    * method. We iterate until the BufferedReader return null which means
		    * there's no more data to read. Each line will appended to a StringBuilder
		    * and returned as String.
		    */
		   BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		   StringBuilder sb = new StringBuilder();

		   String line = null;
		   try {
		       while ((line = reader.readLine()) != null) {
		           sb.append(line + "\n");
		       }
		   } catch (IOException e) {
		       e.printStackTrace();
		   } finally {
		       try {
		           is.close();
		       } catch (IOException e) {
		           e.printStackTrace();
		       }
		   }
		   return sb.toString();
		}

	/**
	 * POST message to chat.
	 * 
	 * @param message
	 * @param handler
	 */
	// , NetResponseHandler<Message> handler
	public void chatPOST(String message, String nick) {
		HttpClient httpClient = new DefaultHttpClient();

		HttpPost post = new HttpPost(
				"http://172.20.0.9/chat-kata/api/chat");

		post.setHeader("Content-type", "text/json");
		
		// Construimos el objeto cliente en formato JSON
		try {
			JSONObject dato = new JSONObject();
			
			dato.put("message", message);
			dato.put("nick", nick);

			StringEntity entity = new StringEntity(dato.toString());
			post.setEntity(entity);

			HttpResponse resp = httpClient.execute(post);
			String respStr = EntityUtils.toString(resp.getEntity());

		} catch (Exception e) {
			Log.e("ServicioRest", "Error!", e);
		}
	}

}
