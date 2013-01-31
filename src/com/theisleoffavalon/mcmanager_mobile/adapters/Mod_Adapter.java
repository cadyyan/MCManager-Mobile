package com.theisleoffavalon.mcmanager_mobile.adapters;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.theisleoffavalon.mcmanager_mobile.R;
import com.theisleoffavalon.mcmanager_mobile.datatypes.Mod;

public class Mod_Adapter extends ArrayAdapter<Mod> {

	private List<Mod>	modList;

	public Mod_Adapter(Context context, List<Mod> modList) {
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
		TextView modMinecraftVersion = (TextView) view
				.findViewById(R.id.mods_minecraft_version);
		TextView modUpdate = (TextView) view.findViewById(R.id.mods_update);

		modName.setText(this.modList.get(position).getName().toString());
		modVersion.setText(this.modList.get(position).getVersionNumber()
				.toString());
		modMinecraftVersion.setText(this.modList.get(position).getGameVersion()
				.toString());

		if (this.modList.get(position).isUpdateAvailable()) {
			modUpdate.setText("Yes");
		} else {
			modUpdate.setText("No");
		}

		if ((position % 3) == 0) {
			view.setBackgroundColor(Color.parseColor("#593D28"));
		} else if ((position % 3) == 1) {
			view.setBackgroundColor(Color.parseColor("#976C4A"));
		} else {
			view.setBackgroundColor(Color.parseColor("#6C6C6C"));
		}

		return view;
	}

}
