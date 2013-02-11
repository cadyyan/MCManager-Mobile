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

package com.theisleoffavalon.mcmanager_mobile.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.theisleoffavalon.mcmanager_mobile.R;
import com.theisleoffavalon.mcmanager_mobile.ServerActivity;
import com.theisleoffavalon.mcmanager_mobile.adapters.PlayerAdapter;
import com.theisleoffavalon.mcmanager_mobile.datatypes.Player;
import com.theisleoffavalon.mcmanager_mobile.helpers.Convert;

public class InfoFragment extends Fragment {

	private List<Player>	playerList;

	private PlayerAdapter	pa;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_info, null, false);

		TextView servername = (TextView) view
				.findViewById(R.id.frag_info_server_name);
		TextView uptime = (TextView) view.findViewById(R.id.frag_info_uptime);

		ListView playerListView = (ListView) view
				.findViewById(R.id.player_list);
		servername.setText("Test Server");

		if (this.playerList == null) {
			this.playerList = new ArrayList<Player>();
			this.playerList.add(new Player("Terminator", "155.92.13.12", null));
			this.playerList.add(new Player("The Ugly", "155.92.123.11", null));
			this.playerList.add(new Player("Postal", "10.12.14.12", null));
			this.playerList.add(new Player("Temporal", "1.1.1.1", null));
			this.playerList.add(new Player("Ooops", "0.0.0.0", null));
		}

		this.pa = new PlayerAdapter(getActivity(), this.playerList);
		playerListView.setAdapter(this.pa);

		new AsyncGetInfoTask().execute();

		return view;
	}

	private List<Player> parseList(List<String> stringList) {
		List<Player> temp = new ArrayList<Player>();
		for (String t : stringList) {
			temp.add(new Player(t, null, null));
		}

		return temp;
	}

	public class AsyncGetInfoTask extends
			AsyncTask<Void, Map<String, Object>, Void> {

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... Void) {
			Log.d("AsyncGetInfoTask", "doInBackground");
			Map<String, Object> serverInfo = null;
			try {
				serverInfo = ((ServerActivity) getActivity()).getRc()
						.getServerInfo();

				publishProgress(serverInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onProgressUpdate(Map<String, Object>... serverInfo) {
			((TextView) getView().findViewById(R.id.frag_info_uptime))
					.setText(Convert.formatTime(((Long) serverInfo[0]
							.get("uptime"))));
			InfoFragment.this.playerList = parseList((List<String>) serverInfo[0]
					.get("players"));
			Log.d("PostExecute", serverInfo.toString());
			InfoFragment.this.pa.notifyDataSetInvalidated();
			// Toast.makeText(getBaseContext(), result,
			// Toast.LENGTH_LONG).show();

		}
	}
}
