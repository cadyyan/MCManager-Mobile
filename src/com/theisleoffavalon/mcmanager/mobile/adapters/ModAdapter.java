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

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.theisleoffavalon.mcmanager.mobile.MinecraftMod;
import com.theisleoffavalon.mcmanager.mobile.R;

/**
 * Custom Adapter for properly displaying Mods in their list.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class ModAdapter extends ArrayAdapter<MinecraftMod> {

	/**
	 * List of the mods in minecraft represented by MinecraftMod objects.
	 */
	private List<MinecraftMod>	modList;

	public ModAdapter(Context context, List<MinecraftMod> modList) {
		super(context, R.layout.fragment_mods_row, modList);
		this.modList = modList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_mods_row, parent, false);

		TextView modName = (TextView) view.findViewById(R.id.mods_name);
		TextView modVersion = (TextView) view
				.findViewById(R.id.mods_mod_version);
		// TextView modMinecraftVersion = (TextView) view
		// .findViewById(R.id.mods_minecraft_version);
		// TextView modUpdate = (TextView) view.findViewById(R.id.mods_update);

		modName.setText(this.modList.get(position).getName().toString());
		modVersion.setText(this.modList.get(position).getVersion().toString());
		// modMinecraftVersion.setText(this.modList.get(position).getGameVersion()
		// .toString());

		// if (this.modList.get(position).isUpdateAvailable()) {
		// modUpdate.setText("Yes");
		// } else {
		// modUpdate.setText("No");
		// }

		// if ((position % 3) == 0) {
		// view.setBackgroundColor(Color.parseColor("#593D28"));
		// } else if ((position % 3) == 1) {
		// view.setBackgroundColor(Color.parseColor("#976C4A"));
		// } else {
		// view.setBackgroundColor(Color.parseColor("#6C6C6C"));
		// }

		/*
		 * Alternates the background between two colors.
		 */
		if ((position % 2) == 0) {
			view.setBackgroundColor(Color.parseColor("#593D28"));
		} else {
			view.setBackgroundColor(Color.parseColor("#976C4A"));
		}

		return view;
	}

}
