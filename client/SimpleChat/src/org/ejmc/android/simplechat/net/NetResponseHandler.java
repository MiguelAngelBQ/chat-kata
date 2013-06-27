package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.RequestError;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Empty response handler.
 * 
 * Base class for Net Response handlers.
 * 
 * @author startic
 * 
 * @param <Response>
 */
public class NetResponseHandler<Response>{
	private ChatList lista;
	/**
	 * Handles a successful request
	 * */
	public void onSuccess(Response response) {
		lista=(ChatList) response;
	}

	public ChatList getLista(){
		return lista;
	}
	/**
	 * Handles a network error.
	 */
	public void onNetError(Context ct, String t, int d) {
		Toast.makeText(ct, t, d);
	}

	/**
	 * Handles a request error.
	 */
	public void onRequestError(RequestError error) {
		//Toast.makeText(ct, t, d);
	}
}
