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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.theisleoffavalon.mcmanager_mobile.MinecraftCommand;
import com.theisleoffavalon.mcmanager_mobile.R;
import com.theisleoffavalon.mcmanager_mobile.ServerActivity;

;

public class ConsoleFragment extends Fragment {

	private ArrayAdapter<String>	adapter;
	private List<String>			commands;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_console, null, false);

		Spinner spinner = (Spinner) view
				.findViewById(R.id.fragment_console_commands);
		this.adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item,
				this.commands = new ArrayList<String>());
		ConsoleFragment.this.adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(ConsoleFragment.this.adapter);

		new AsyncGetConsoleCommandsTask().execute();
		return view;
	}

	public class AsyncGetConsoleCommandsTask extends
			AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... Void) {
			Map<String, MinecraftCommand> temp = null;
			try {
				List<String> commands = new ArrayList<String>();
				temp = ((ServerActivity) getActivity()).getRc()
						.getAllMinecraftCommands();
				for (MinecraftCommand c : temp.values()) {
					commands.add(c.getName());
				}

				publishProgress(commands);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private void publishProgress(List<String> commands) {
			commands.clear();
			commands.addAll(commands);
			ConsoleFragment.this.adapter.notifyDataSetInvalidated();
		}
	}

	public class AsyncGetConsoleOutputTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
