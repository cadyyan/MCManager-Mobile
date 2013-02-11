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
import java.util.Scanner;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.theisleoffavalon.mcmanager_mobile.R;
import com.theisleoffavalon.mcmanager_mobile.ServerActivity;
import com.theisleoffavalon.mcmanager_mobile.adapters.Player_Adapter;
import com.theisleoffavalon.mcmanager_mobile.datatypes.Player;

public class InfoFragment extends Fragment {

	private List<Player>	playerList;

	private Player_Adapter	pa;

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

		new AsyncGetInfoTask().execute(getActivity());

		if (this.playerList == null) {
			this.playerList = new ArrayList<Player>();
			this.playerList.add(new Player("Terminator", "155.92.13.12", null));
			this.playerList.add(new Player("The Ugly", "155.92.123.11", null));
			this.playerList.add(new Player("Postal", "10.12.14.12", null));
			this.playerList.add(new Player("Temporal", "1.1.1.1", null));
			this.playerList.add(new Player("Ooops", "0.0.0.0", null));
		}

		this.pa = new Player_Adapter(getActivity(), this.playerList);
		playerListView.setAdapter(this.pa);

		return view;
	}

	private List<Player> parseList(String stringList) {
		List<Player> temp = new ArrayList<Player>();
		Scanner scan = new Scanner(stringList);
		scan.useDelimiter(",");

		while (scan.hasNext()) {
			temp.add(new Player(scan.next(), "", null));
		}

		return temp;
	}

	public class AsyncGetInfoTask extends
			AsyncTask<Activity, Void, Map<String, String>> {

		@Override
		protected Map<String, String> doInBackground(Activity... activity) {
			Activity act = activity[0];
			Map<String, String> serverInfo = null;
			try {
				serverInfo = ((ServerActivity) getActivity()).getRc()
						.getServerInfo();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return serverInfo;
		}

		@Override
		protected void onPostExecute(Map<String, String> serverInfo) {
			((TextView) getView().findViewById(R.id.frag_info_uptime))
					.setText(serverInfo.get("uptime"));
			InfoFragment.this.playerList = parseList(serverInfo.get("players"));
			InfoFragment.this.pa.notifyDataSetChanged();
			// Toast.makeText(getBaseContext(), result,
			// Toast.LENGTH_LONG).show();
		}
	}
}
