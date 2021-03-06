package org.ejmc.android.simplechat;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity{
	
	EditText txtNick;
	EditText txtIP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		txtNick = (EditText) findViewById(R.id.txtNick);
		txtIP = (EditText) findViewById(R.id.txtIP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void ejecutar(View view) {
		
        Intent i = new Intent(this, ChatActivity.class );
        i.putExtra("nick", txtNick.getText().toString());
        i.putExtra("ip", txtIP.getText().toString());
        txtNick.setText("");
        txtIP.setText("");
        startActivity(i);
   }
}
