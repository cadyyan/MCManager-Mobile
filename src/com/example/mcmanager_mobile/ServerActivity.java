package com.example.mcmanager_mobile;

import com.example.mcmanager_mobile.Listener.AppTabListener;

import android.os.Bundle;
import android.view.Menu;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;

public class ServerActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_panel);
		
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

}
