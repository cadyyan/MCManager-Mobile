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

package com.theisleoffavalon.mcmanager.mobile;

import java.net.MalformedURLException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.theisleoffavalon.mcmanager.mobile.Listener.AppTabListener;
import com.theisleoffavalon.mcmanager.mobile.fragments.ConsoleFragment;
import com.theisleoffavalon.mcmanager.mobile.fragments.InfoFragment;
import com.theisleoffavalon.mcmanager.mobile.fragments.ModsFragment;

/**
 * Activity that displays all the tabs and the server info.
 * 
 * @author eberta
 * @modified 2/13/13
 */
public class ServerActivity extends Activity {

	/**
	 * Instance of the RestClient that is used to communicate with the server.
	 */
	protected RestClient	rc;

	/**
	 * Sets up the tabs and the listener for the tabs.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_panel);
		try {
			this.rc = new RestClient("http", getIntent().getStringExtra(
					"address"), 2013, "/rpc");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ActionBar abar = getActionBar();
		abar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab info = abar.newTab().setText("Info");
		Tab console = abar.newTab().setText("Console");
		Tab mods = abar.newTab().setText("Mods");

		info.setTabListener(new AppTabListener(this));
		console.setTabListener(new AppTabListener(this));
		mods.setTabListener(new AppTabListener(this));

		abar.addTab(info);
		abar.addTab(console);
		abar.addTab(mods);

		abar.selectTab(info);
	}

	/**
	 * Creates the options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_control_panel, menu);
		return true;
	}

	/**
	 * Selects the correct refresh method and also switches back to the main
	 * login activity.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.menu_refresh: // Calls the correct refresh command based
									// on the tab loaded.
				String variable = getActionBar().getSelectedTab().getText()
						.toString();
				if (variable.equals("Info")) {
					InfoFragment info = (InfoFragment) getFragmentManager()
							.findFragmentByTag("Info");
					info.refresh();
				} else if (variable.equals("Console")) {
					ConsoleFragment console = (ConsoleFragment) getFragmentManager()
							.findFragmentByTag("Console");
					console.refresh();
				} else if (variable.equals("Mods")) {
					ModsFragment mods = (ModsFragment) getFragmentManager()
							.findFragmentByTag("Mods");
					mods.refresh();
				}
				return true;
			case R.id.menu_switch:
				Intent intent = new Intent(getBaseContext(), MainConsole.class);
				startActivity(intent);
				return true;
			default:
				return false;
		}
	}

	/**
	 * Returns the active RestClient
	 * 
	 * @return
	 */
	public RestClient getRc() {
		return this.rc;
	}

}
