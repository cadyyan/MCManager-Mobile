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

package com.theisleoffavalon.mcmanager.mobile.async;

import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.theisleoffavalon.mcmanager.mobile.ServerActivity;

/**
 * Async Task that is called when the player wants to stop the server.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class AsyncStopServer extends AsyncTask<Activity, Void, Void> {

	@Override
	protected Void doInBackground(Activity... activity) {
		try {
			((ServerActivity) activity[0]).getRc().stopServer();
		} catch (IOException e) {
			Log.e("AsyncStopServer", "IOException", e);
		}
		return null;
	}

}
