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

import com.theisleoffavalon.mcmanager.mobile.R;

/**
 * Custom Adapter for the console so it alternates background colors.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class ConsoleAdapter extends ArrayAdapter<String> {

	/**
	 * List of console output.
	 */
	private List<String>	consoleList;

	public ConsoleAdapter(Context context, List<String> consoleList) {
		super(context, R.layout.fragment_console_list, consoleList);
		this.consoleList = consoleList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_console_list, parent,
				false);

		TextView command = (TextView) view
				.findViewById(R.id.fragment_console_console_row_text);

		command.setText(this.consoleList.get(position));

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

		return view;
	}
}
