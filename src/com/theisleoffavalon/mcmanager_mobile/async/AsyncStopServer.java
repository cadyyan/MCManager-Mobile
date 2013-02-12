package com.theisleoffavalon.mcmanager_mobile.async;

import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;

import com.theisleoffavalon.mcmanager_mobile.ServerActivity;

public class AsyncStopServer extends AsyncTask<Activity, Void, Void> {

	@Override
	protected Void doInBackground(Activity... activity) {
		try {
			((ServerActivity) activity[0]).getRc().stopServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
