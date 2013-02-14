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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.theisleoffavalon.mcmanager.mobile.MinecraftCommand;
import com.theisleoffavalon.mcmanager.mobile.R;
import com.theisleoffavalon.mcmanager.mobile.ServerActivity;
import com.theisleoffavalon.mcmanager.mobile.adapters.ConsoleAdapter;

/**
 * Class that represents the fragment that contains the output and input for the
 * console.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class ConsoleFragment extends Fragment {

	/**
	 * refresh rate for the screen info. Currently set to 3 secs.
	 */
	private final int					REFRESH_RATE	= 3000;

	/**
	 * ArrayAdapter for the spinner.
	 */
	private ArrayAdapter<String>		adapter;

	/**
	 * List that contains all the commands the server can do.
	 */
	private List<String>				command;

	/**
	 * int that tells the server the index of the last message received.
	 */
	private int							index			= -1;

	/**
	 * List that stores all the console messages.
	 */
	private List<String>				messages;

	/**
	 * Custom Adapter for the console output.
	 */
	private ConsoleAdapter				console;

	/**
	 * Handle to the auto refreshed async task.
	 */
	private AsyncGetConsoleOutputTask	async;

	/**
	 * Handler that calls the refresh command based on a time.
	 */
	private Handler						timer;

	/**
	 * Runnable command to auto refresh the screen based on the REFRESH_RATE;
	 */
	public Runnable						refresh			= new Runnable() {

															@Override
															public void run() {
																ConsoleFragment.this.async = new AsyncGetConsoleOutputTask();
																ConsoleFragment.this.async
																		.execute();
																ConsoleFragment.this.timer
																		.postDelayed(
																				ConsoleFragment.this.refresh,
																				ConsoleFragment.this.REFRESH_RATE);
															}

														};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.messages = new ArrayList<String>();
		this.command = new ArrayList<String>();
		this.timer = new Handler();
		this.refresh.run();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_console, null, false);

		this.console = new ConsoleAdapter(getActivity(), this.messages);
		ListView consoleView = (ListView) view
				.findViewById(R.id.fragment_console_console);
		consoleView.setAdapter(this.console);

		final Spinner spinner = (Spinner) view
				.findViewById(R.id.fragment_console_commands);

		this.adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, this.command);

		this.adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(ConsoleFragment.this.adapter);

		final EditText params = (EditText) view
				.findViewById(R.id.fragment_console_parameters);

		((Button) view.findViewById(R.id.fragment_console_send))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						new AsyncExecuteCommandTask().execute(new String[] {
								spinner.getSelectedItem().toString(),
								params.getText().toString() });

					}
				});

		// new AsyncGetConsoleOutputTask().execute();
		new AsyncGetConsoleCommandsTask().execute();
		return view;
	}

	/**
	 * Calls when the fragment is destroyed.
	 */
	@Override
	public void onDestroy() {
		this.async.cancel(true);
		this.timer.removeCallbacks(this.refresh);
		super.onDestroy();

	}

	/**
	 * Is called when the screen needs to be refreshed.
	 */
	public void refresh() {
		((EditText) getView().findViewById(R.id.fragment_console_parameters))
				.setText("");
		this.async = new AsyncGetConsoleOutputTask();
		this.async.execute();
	}

	/**
	 * Class that calls an async call on the server to get the commands the
	 * server can do.
	 * 
	 * @author eberta
	 * @modified 2/14/13
	 */
	public class AsyncGetConsoleCommandsTask extends
			AsyncTask<Void, List<String>, Void> {

		@SuppressWarnings("unchecked")
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
				// Toast.makeText(getActivity(),
				// "Connection error: " + e.getLocalizedMessage(),
				// Toast.LENGTH_LONG).show();
				Log.e("AsyncGetConsoleCommandsTask", "IOException", e);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(List<String>... commands) {
			ConsoleFragment.this.adapter.addAll(commands[0]);
			// ConsoleFragment.this.command.clear();
			// ConsoleFragment.this.command.addAll(commands[0]);
			// ConsoleFragment.this.adapter.notifyDataSetInvalidated();
			// ConsoleFragment.this.spinnerTest.setSelection(0);
			// getView().invalidate();
		}
	}

	/**
	 * Class that executes commands on the server
	 * 
	 * @author eberta
	 * @modified 2/14/15
	 */
	public class AsyncExecuteCommandTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			Map<String, MinecraftCommand> commands = null;
			try {
				commands = ((ServerActivity) getActivity()).getRc()
						.getAllMinecraftCommands();
				MinecraftCommand c = commands.get(params[0]);
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("args", params[1]);
				((ServerActivity) getActivity()).getRc()
						.executeCommand(c, args);

				publishProgress();
			} catch (IOException e) {
				// Toast.makeText(getActivity(),
				// "Connection error: " + e.getLocalizedMessage(),
				// Toast.LENGTH_LONG).show();
				Log.e("AsyncExecuteCommandTask", "IOException", e);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... voids) {

			refresh();
		}
	}

	/**
	 * Class that gets the console output from the server.
	 * 
	 * @author eberta
	 * @modified 2/14/13
	 */
	public class AsyncGetConsoleOutputTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				ConsoleFragment.this.index = (int) ((ServerActivity) getActivity())
						.getRc().getConsoleMessages(ConsoleFragment.this.index,
								ConsoleFragment.this.messages);

				publishProgress();

			} catch (IOException e) {
				// Toast.makeText(getActivity(),
				// "Connection error: " + e.getLocalizedMessage(),
				// Toast.LENGTH_LONG).show();
				Log.e("AsyncGetConsoleOutputTask", "IOException", e);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... voids) {
			ConsoleFragment.this.console.notifyDataSetChanged();
		}

	}

}
