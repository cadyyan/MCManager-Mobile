package com.example.mcmanager_mobile.Listener;

import com.example.mcmanager_mobile.R;
import com.example.mcmanager_mobile.fragments.ConsoleFragment;
import com.example.mcmanager_mobile.fragments.InfoFragment;
import com.example.mcmanager_mobile.fragments.ModsFragment;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class AppTabListener implements TabListener {
	
	
	
	private Activity activity;
	
	private FragmentManager fm;
	
	public AppTabListener(Activity activity){
		this.activity = activity;
		this.fm = activity.getFragmentManager();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(tab.getText().equals("Info")){
				ft.replace(R.id.activity_control_panel_fragment, new InfoFragment(), "Info");
		}else if(tab.getText().equals("Console")){
				ft.replace(R.id.activity_control_panel_fragment, new ConsoleFragment(), "Console");
		}else if(tab.getText().equals("Mods")){
				ft.replace(R.id.activity_control_panel_fragment, new ModsFragment(), "Mods");
		}
		
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//		// TODO Auto-generated method stub
//		if(tab.getText().equals("Info")){
//			ft.hide(fm.findFragmentByTag("Info"));
//		}else if(tab.getText().equals("Console")){
//			ft.hide(fm.findFragmentByTag("Console"));
//		}else if(tab.getText().equals("Mods")){
//			ft.hide(fm.findFragmentByTag("Mods"));
//		}
	}

}
