package org.ejmc.android.simplechat;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {

	ListView txtListaMensajes;
	EditText txtMensaje;
	TextView lblIP;
	String Nick;
	int seq = 0;
	final NetRequests nr = new NetRequests();
	ArrayAdapter<String> adaptador;
	ListView lv;
	ChatList cl;
	List<String> listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		txtListaMensajes = (ListView) findViewById(R.id.lstMensajes);
		txtMensaje = (EditText) findViewById(R.id.txtMensaje);
		lblIP = (TextView) findViewById(R.id.lblIP);
		Bundle bundle = getIntent().getExtras();
		lblIP.setText(bundle.getString("ip"));
		Nick = bundle.getString("nick");
		cl = new ChatList();
		listItems = cl.getMessages();
		adaptador = new ArrayAdapter<String>(ChatActivity.this,
				android.R.layout.simple_list_item_1, listItems);
		lv = (ListView) findViewById(R.id.lstMensajes);
		lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		lv.setStackFromBottom(true);
		lv.setAdapter(adaptador);
		UpdatesThread t1=new UpdatesThread(); 
		t1.start();
		// Show the Up button in the action bar.
		setupActionBar();

	}

	public void enviar(View view) {
		
		final NetRequests nr = new NetRequests();
		new Thread(new Runnable() {
			public void run() {
				nr.chatPOST(txtMensaje.getText().toString(), Nick);
			}
		}).start();
		txtMensaje.setText("");
	}

	protected void onStart() {
		super.onStart();
		// refreshHandler.post(refreshThread);
	}

	private Handler puente = new Handler() {
		@Override
		public void handleMessage(android.os.Message msgAnd) {
			// ChatList cl2 = (ChatList) msgAnd.obj;
			// for(int i=0; i<cl2.getMessages().size();i++){
			// cl.getMessages().add(cl2.getMessages().get(i));
			// }
			adaptador.notifyDataSetChanged();

		}
	};

	public class UpdatesThread extends Thread {
		final NetRequests nr = new NetRequests();

		Timer timer;

		@Override
		public void run() {
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 500);
		}

		TimerTask timerTask = new TimerTask() {
			public void run() {
				NetResponseHandler<ChatList> handler = new NetResponseHandler<ChatList>();
				nr.chatGET(seq, handler);
				Message messageAnd = new Message();
				messageAnd.obj = handler.getLista();

				ChatList cl2 = (ChatList) messageAnd.obj;
				for (int i = 0; i < cl2.getMessages().size(); i++) {
					listItems.add(cl2.getMessages().get(i));
				}
				seq += cl2.getMessages().size();
				puente.sendMessage(messageAnd);
			}

		};
	}

	Handler refreshHandler = new Handler();

	// private final Runnable refreshThread = new Runnable() {
	// @Override
	// public void run() {
	// NetResponseHandler<ChatList> handler = new
	// NetResponseHandler<ChatList>();
	// nr.chatGET(seq, handler);
	//
	// Message messageAnd = new Message();
	// messageAnd.obj = handler.getLista();
	// puente.sendMessage(messageAnd);
	// refreshHandler.postDelayed(this, 1000);
	// }
	// };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().hide();
	}

	public void volver(View view) {
		finish();
	}

}
