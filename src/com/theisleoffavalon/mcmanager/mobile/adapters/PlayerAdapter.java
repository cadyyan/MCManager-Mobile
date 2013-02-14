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

package com.theisleoffavalon.mcmanager.mobile.adapters;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theisleoffavalon.mcmanager.mobile.R;
import com.theisleoffavalon.mcmanager.mobile.ServerActivity;
import com.theisleoffavalon.mcmanager.mobile.datatypes.Player;
import com.theisleoffavalon.mcmanager.mobile.fragments.InfoFragment;

/**
 * Custom Adapter to help display the Player objects that represent players.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class PlayerAdapter extends ArrayAdapter<Player> {

	/**
	 * Context of the app for the adapter to call on.
	 */
	private Context			context;

	/**
	 * List of players on the server.
	 */
	private List<Player>	playerList;

	public PlayerAdapter(Context context, List<Player> playerList) {
		super(context, R.layout.fragment_info_player_row, playerList);
		this.playerList = playerList;
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_info_player_row, null,
				false);

		TextView name = (TextView) view.findViewById(R.id.player_row_name);
		ImageView picture = (ImageView) view.findViewById(R.id.player_row_icon);
		Button ban = (Button) view.findViewById(R.id.player_row_ban);
		Button kick = (Button) view.findViewById(R.id.player_row_kick);

		name.setText(this.playerList.get(position).getName());

		// if ((position % 3) == 0) {
		// view.setBackgroundColor(Color.parseColor("#593D28"));
		// } else if ((position % 3) == 1) {
		// view.setBackgroundColor(Color.parseColor("#976C4A"));
		// } else {
		// view.setBackgroundColor(Color.parseColor("#6C6C6C"));
		// }

		if ((position % 2) == 0) {
			view.setBackgroundColor(Color.parseColor("#593D28"));
		} else {
			view.setBackgroundColor(Color.parseColor("#976C4A"));
		}

		kick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getContext())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.player_row_kick)
						.setMessage(
								"Do you really want to kick "
										+ PlayerAdapter.this.playerList.get(
												position).getName() + "?")
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										ServerActivity sa = ((ServerActivity) PlayerAdapter.this.context);
										((InfoFragment) sa.getFragmentManager()
												.findFragmentByTag("Info"))
												.kickPlayer(PlayerAdapter.this.playerList
														.get(position)
														.getName());
									}

								}).setNegativeButton(R.string.no, null).show();

			}
		});

		ban.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getContext())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.player_row_ban)
						.setMessage(
								"Do you really want to ban "
										+ PlayerAdapter.this.playerList.get(
												position).getName() + "?")
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										ServerActivity sa = ((ServerActivity) PlayerAdapter.this.context);
										((InfoFragment) sa.getFragmentManager()
												.findFragmentByTag("Info"))
												.banPlayer(PlayerAdapter.this.playerList
														.get(position)
														.getName());
									}

								}).setNegativeButton(R.string.no, null).show();

			}
		});
		return view;
	}
}
