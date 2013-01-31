package com.theisleoffavalon.mcmanager_mobile.adapters;

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

import com.theisleoffavalon.mcmanager_mobile.R;
import com.theisleoffavalon.mcmanager_mobile.datatypes.Player;

public class Player_Adapter extends ArrayAdapter<Player> {

	private List<Player>	playerList;

	public Player_Adapter(Context context, List<Player> playerList) {
		super(context, R.layout.fragment_info_player_row, playerList);
		this.playerList = playerList;
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

		if ((position % 3) == 0) {
			view.setBackgroundColor(Color.parseColor("#593D28"));
		} else if ((position % 3) == 1) {
			view.setBackgroundColor(Color.parseColor("#976C4A"));
		} else {
			view.setBackgroundColor(Color.parseColor("#6C6C6C"));
		}

		ban.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getContext())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.player_row_ban)
						.setMessage(
								"Do you really want to ban "
										+ Player_Adapter.this.playerList.get(
												position).getName() + "?")
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}

								}).setNegativeButton(R.string.no, null).show();

			}
		});

		kick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getContext())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.player_row_kick)
						.setMessage(
								"Do you really want to kick "
										+ Player_Adapter.this.playerList.get(
												position).getName() + "?")
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}

								}).setNegativeButton(R.string.no, null).show();

			}
		});
		return view;
	}
}
