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

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.theisleoffavalon.mcmanager_mobile.R;
import com.theisleoffavalon.mcmanager_mobile.adapters.Mod_Adapter;
import com.theisleoffavalon.mcmanager_mobile.datatypes.Mod;

public class ModsFragment extends Fragment {
	public static List<Mod>		modList;

	public static Mod_Adapter	ma;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mods, null, false);

		if (modList == null) {
			modList = new ArrayList<Mod>();
			modList.add(new Mod("Test Mod", "infinity", "1.4.7", true));
			modList.add(new Mod("Test 1", "2", "1.3", false));
			modList.add(new Mod("Test 2", "2.0.1", "1.3", false));
			modList.add(new Mod("Test 3", "5.1.4", "1.4", true));
		}

		ma = new Mod_Adapter(getActivity(), modList);

		ListView modListView = (ListView) view.findViewById(R.id.frag_mod_list);
		modListView.addHeaderView(inflater.inflate(
				R.layout.fragment_mods_row_header, null));
		modListView.setAdapter(ma);

		return view;
	}

}
