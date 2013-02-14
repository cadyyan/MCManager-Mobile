package com.theisleoffavalon.mcmanager.mobile.adapters;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.theisleoffavalon.mcmanager.mobile.R;

public class ConsoleCommandAdapter extends ArrayAdapter<String> implements
		SpinnerAdapter {

	private Context			context;

	private List<String>	commandList;

	public ConsoleCommandAdapter(Context context, List<String> commandList) {
		super(context, R.layout.fragment_console_list, commandList);
		this.commandList = commandList;
		this.context = context;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_console_list, parent,
				false);

		TextView command = (TextView) view
				.findViewById(R.id.fragment_console_console_row_text);

		command.setText(this.commandList.get(position).toString());

		return view;
	}

}
