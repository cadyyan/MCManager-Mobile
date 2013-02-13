package com.theisleoffavalon.mcmanager_mobile.async;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.theisleoffavalon.mcmanager_mobile.MinecraftCommand;
import com.theisleoffavalon.mcmanager_mobile.ServerActivity;

public class AsyncKickPlayer extends AsyncTask<Activity, Void, Void> {

	@Override
	protected Void doInBackground(Activity... activity) {
		try {
			List<MinecraftCommand> list = ((ServerActivity) activity[0])
					.getRc().getAllMinecraftCommands();
			MinecraftCommand command;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Kick")) {
					command = list.get(i);
					break;
				}
			}
			((ServerActivity) activity[0]).getRc().executeCommand(cmd, params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
