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

import java.net.MalformedURLException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.theisleoffavalon.mcmanager_mobile.Listener.AppTabListener;

public class ServerActivity extends Activity {

	protected RestClient	rc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_panel);

		try {
			this.rc = new RestClient("http", getIntent().getExtras().getString(
					"address"), 1716, "");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_control_panel, menu);
		return true;
	}

	public RestClient getRc() {
		return this.rc;
	}

}
