package com.example.mcmanager_mobile.fragments;


import com.example.mcmanager_mobile.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_info, null, false);
		
		return view;
	}

}
