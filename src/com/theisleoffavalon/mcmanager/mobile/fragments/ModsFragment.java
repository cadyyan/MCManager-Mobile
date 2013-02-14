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

package com.theisleoffavalon.mcmanager.mobile.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.theisleoffavalon.mcmanager.mobile.MinecraftMod;
import com.theisleoffavalon.mcmanager.mobile.R;
import com.theisleoffavalon.mcmanager.mobile.ServerActivity;
import com.theisleoffavalon.mcmanager.mobile.adapters.ModAdapter;

/**
 * Class representing the Mod Fragment.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class ModsFragment extends Fragment {
	/**
	 * List of minecraft mods loaded on server.
	 */
	public static List<MinecraftMod>	modList;

	/**
	 * Custom Adapter that displays the mod objects on screen.
	 */
	public static ModAdapter			ma;

	/**
	 * Handle for the AsyncGetModTask so the task can be called on.
	 */
	private AsyncGetModTask				aModTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mods, null, false);

		/*
		 * Temp modList for when the server is not communicating.
		 */
		// if (modList == null) {
		// modList = new ArrayList<MinecraftMod>();
		// modList.add(new MinecraftMod("Test Mod", "infinity"));
		// modList.add(new MinecraftMod("Test 1", "2"));
		// modList.add(new MinecraftMod("Test 2", "2.0.1"));
		// modList.add(new MinecraftMod("Test 3", "5.1.4"));
		// }
		modList = new ArrayList<MinecraftMod>();
		ma = new ModAdapter(getActivity(), modList);

		ListView modListView = (ListView) view.findViewById(R.id.frag_mod_list);
		modListView.addHeaderView(inflater.inflate(
				R.layout.fragment_mods_row_header, null));

		// Get Server Mod List
		this.aModTask = new AsyncGetModTask();
		this.aModTask.execute();
		modListView.setAdapter(ma);

		return view;
	}

	@Override
	public void onDestroy() {
		this.aModTask.cancel(true);
		super.onDestroy();
	}

	/**
	 * This method is called by the ServerActivity to refresh the fragment's
	 * info.
	 */
	public void refresh() {
		this.aModTask = new AsyncGetModTask();
		this.aModTask.execute();
	}

	/**
	 * This class is to retrieve the info from the server and to display it on
	 * the fragment.
	 * 
	 * @author eberta
	 */
	public class AsyncGetModTask extends
			AsyncTask<Void, List<MinecraftMod>, Void> {

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... Void) {
			Log.d("AsyncGetModTask", "doInBackground");
			List<MinecraftMod> serverMods = null;
			try {
				serverMods = ((ServerActivity) getActivity()).getRc()
						.getServerMods();

				publishProgress(serverMods);
			} catch (IOException e) {
				// Toast.makeText(getActivity(),
				// "Connection error: " + e.getLocalizedMessage(),
				// Toast.LENGTH_LONG).show();
				Log.e("AsyncGetModTask", "IOException", e);
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onProgressUpdate(List<MinecraftMod>... serverInfo) {

			modList.removeAll(modList);
			modList.addAll(serverInfo[0]);
			Log.d("Progress Update", serverInfo.toString());
			ma.notifyDataSetInvalidated();
			// Toast.makeText(getBaseContext(), result,
			// Toast.LENGTH_LONG).show();

		}
	}

}
