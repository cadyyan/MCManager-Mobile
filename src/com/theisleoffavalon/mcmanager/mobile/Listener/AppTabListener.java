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

package com.theisleoffavalon.mcmanager.mobile.Listener;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.theisleoffavalon.mcmanager.mobile.R;
import com.theisleoffavalon.mcmanager.mobile.fragments.ConsoleFragment;
import com.theisleoffavalon.mcmanager.mobile.fragments.InfoFragment;
import com.theisleoffavalon.mcmanager.mobile.fragments.ModsFragment;

/**
 * Class to listen to tab changes.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class AppTabListener implements TabListener {

	/**
	 * Activity that calls the AppTabListener.
	 */
	private Activity		activity;

	/**
	 * Activity's fragment manager.
	 */
	private FragmentManager	fm;

	public AppTabListener(Activity activity) {
		this.activity = activity;
		this.fm = activity.getFragmentManager();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (tab.getText().equals("Info")) {
			ft.replace(R.id.activity_control_panel_fragment,
					new InfoFragment(), "Info");
		} else if (tab.getText().equals("Console")) {
			ft.replace(R.id.activity_control_panel_fragment,
					new ConsoleFragment(), "Console");
		} else if (tab.getText().equals("Mods")) {
			ft.replace(R.id.activity_control_panel_fragment,
					new ModsFragment(), "Mods");
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// // TODO Auto-generated method stub
		// if(tab.getText().equals("Info")){
		// ft.hide(fm.findFragmentByTag("Info"));
		// }else if(tab.getText().equals("Console")){
		// ft.hide(fm.findFragmentByTag("Console"));
		// }else if(tab.getText().equals("Mods")){
		// ft.hide(fm.findFragmentByTag("Mods"));
		// }
	}

}
