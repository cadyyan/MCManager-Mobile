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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.theisleoffavalon.mcmanager_mobile.MainConsole;
import com.theisleoffavalon.mcmanager_mobile.MinecraftCommand;
import com.theisleoffavalon.mcmanager_mobile.R;
import com.theisleoffavalon.mcmanager_mobile.ServerActivity;
import com.theisleoffavalon.mcmanager_mobile.adapters.PlayerAdapter;
import com.theisleoffavalon.mcmanager_mobile.async.AsyncStopServer;
import com.theisleoffavalon.mcmanager_mobile.datatypes.Player;
import com.theisleoffavalon.mcmanager_mobile.helpers.Convert;

public class InfoFragment extends Fragment {

	private List<Player>	playerList;

	private PlayerAdapter	pa;

	private Handler			timer;

	public Runnable			refresh	= new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											new AsyncGetInfoTask().execute();
											InfoFragment.this.timer
													.postDelayed(
															InfoFragment.this.refresh,
															3000);
										}

									};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.timer = new Handler();
		this.refresh.run();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_info, null, false);

		TextView servername = (TextView) view
				.findViewById(R.id.frag_info_server_name);

		Button button = (Button) view.findViewById(R.id.frag_info_stop);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("Stop Server?")
						.setMessage("Do you really want to stop the server?")
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										new AsyncStopServer()
												.execute(getActivity());
										Intent intent = new Intent(
												getActivity().getBaseContext(),
												MainConsole.class);
										startActivity(intent);
									}

								}).setNegativeButton(R.string.no, null).show();
			}
		});

		ListView playerListView = (ListView) view
				.findViewById(R.id.player_list);
		servername.setText("Test Server");

		// if (this.playerList == null) {
		// this.playerList = new ArrayList<Player>();
		// this.playerList.add(new Player("Terminator", "155.92.13.12", null));
		// this.playerList.add(new Player("The Ugly", "155.92.123.11", null));
		// this.playerList.add(new Player("Postal", "10.12.14.12", null));
		// this.playerList.add(new Player("Temporal", "1.1.1.1", null));
		// this.playerList.add(new Player("Ooops", "0.0.0.0", null));
		// }
		this.playerList = new ArrayList<Player>();
		this.pa = new PlayerAdapter(getActivity(), this.playerList);
		playerListView.setAdapter(this.pa);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.timer.removeCallbacks(this.refresh);
	}

	private List<Player> parseList(List<String> stringList) {
		List<Player> temp = new ArrayList<Player>();
		for (String t : stringList) {
			temp.add(new Player(t, null, null));
		}

		return temp;
	}

	public void refresh() {
		new AsyncGetInfoTask().execute();
	}

	public void kickPlayer(String name) {
		new AsyncKickPlayer().execute(name);
	}

	public void banPlayer(String name) {
		new AsyncBanPlayer().execute(name);
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

			((TextView) getView().findViewById(R.id.frag_info_memoryusage))
					.setText(Convert.formatMemory((Long) serverInfo[0]
							.get("usedMemory")));
			((TextView) getView().findViewById(R.id.frag_info_maxmemory))
					.setText(Convert.formatMemory((Long) serverInfo[0]
							.get("maxMemory")));
			InfoFragment.this.playerList
					.removeAll(InfoFragment.this.playerList);
			InfoFragment.this.playerList
					.addAll(parseList((List<String>) serverInfo[0]
							.get("players")));
			Log.d("Progress Update", serverInfo.toString());
			InfoFragment.this.pa.notifyDataSetInvalidated();
			// Toast.makeText(getBaseContext(), result,
			// Toast.LENGTH_LONG).show();

		}
	}

	public class AsyncKickPlayer extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... player) {
			try {
				Map<String, MinecraftCommand> list = ((ServerActivity) getActivity())
						.getRc().getAllMinecraftCommands();
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("args", player[0]);
				((ServerActivity) getActivity()).getRc().executeCommand(
						list.get("Kick"), args);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	public class AsyncBanPlayer extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... player) {
			try {
				Map<String, MinecraftCommand> list = ((ServerActivity) getActivity())
						.getRc().getAllMinecraftCommands();
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("args", player[0]);
				((ServerActivity) getActivity()).getRc().executeCommand(
						list.get("Ban"), args);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}
}
