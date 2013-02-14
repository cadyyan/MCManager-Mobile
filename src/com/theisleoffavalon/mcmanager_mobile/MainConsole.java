/*
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE Version 2, December 2004
 * 
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 * 
 * Everyone is permitted to copy and distribute verbatim or modified copies of
 * this license document, and changing it is allowed as long as the name is
 * changed.
 * 
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING,
 * DISTRIBUTION AND MODIFICATION
 * 
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.theisleoffavalon.mcmanager_mobile;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is the main login activity that is launched when the app launches.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class MainConsole extends Activity {

	/**
	 * RestClient used to communicate with the server.
	 */
	private RestClient	rc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText username = ((EditText) findViewById(R.id.profile_selector_username));
		final EditText password = (EditText) findViewById(R.id.profile_selector_password);
		final EditText server = ((EditText) findViewById(R.id.profile_selector_server));
		Button login = (Button) findViewById(R.id.profile_selector_login);

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					MainConsole.this.rc = new RestClient("http", server
							.getText().toString(), 1716, "/rpc");
					new AsyncLoginTask().execute();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	// //
	// ///////////////////////////////////////////////////////////////////////////////////
	// // HI!!!! (I just like messing with you) - James
	// //
	// ///////////////////////////////////////////////////////////////////////////////////
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }

	/**
	 * This class creates an asyn object that checks the login info against the
	 * server.
	 * 
	 * @author eberta
	 * @modified 2/14/13
	 */
	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				String token = MainConsole.this.rc
						.login(((EditText) findViewById(R.id.profile_selector_username))
								.getText().toString(),
								((EditText) findViewById(R.id.profile_selector_password))
										.getText().toString());

				if (token != null) {
					publishProgress();
				}
			} catch (AuthenticationException e) {
				// Toast.makeText(getBaseContext(), e.getLocalizedMessage(),
				// Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				// Toast.makeText(getBaseContext(), e.getLocalizedMessage(),
				// Toast.LENGTH_LONG).show();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... voids) {
			Intent intent = new Intent(getBaseContext(), ServerActivity.class);
			intent.putExtra("address",
					((EditText) findViewById(R.id.profile_selector_server))
							.getText().toString());
			intent.putExtra("serverName",
					((EditText) findViewById(R.id.profile_selector_server))
							.getText().toString());
			startActivity(intent);
		}
	}

}
