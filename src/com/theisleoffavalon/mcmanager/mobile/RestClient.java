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

package com.theisleoffavalon.mcmanager.mobile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

import com.theisleoffavalon.mcmanager.mobile.MinecraftCommand.ArgType;

/**
 * This class handles talking to the server <br />
 * Many methods in this class return a Map of <String,Object>, this map contains
 * various different values that were returned from a request as there can be
 * multiple returns with different return types. The mappings for these are the
 * ones default from json-simple
 * 
 * <pre>
 * 	JSON		Java
 * 	string		java.lang.String
 * 	number		java.lang.Number
 * 	true|false	java.lang.Boolean
 * 	null		null
 * 	array		java.util.List
 * 	object		java.util.Map
 * </pre>
 * 
 * These return types should be obvious from their names, but checked type
 * casting should be done.
 * 
 * @author Jacob Henkel
 */
public class RestClient {

	/**
	 * The root URL of the API
	 */
	private URL					rootUrl;

	/**
	 * JSONRPC version string
	 */
	private static final String	JSON_RPC_VERSION	= "2.0";

	/**
	 * Creates a rest client for the given parameters
	 * 
	 * @param protocol
	 *            The protocol to use, can be http or https
	 * @param host
	 *            The host to connect to
	 * @param port
	 *            The port to connect to
	 * @param apiRoot
	 *            The root of the API on the host
	 * @throws MalformedURLException
	 */
	public RestClient(String protocol, String host, int port, String apiRoot)
			throws MalformedURLException {

		this.rootUrl = new URL(protocol, host, port, apiRoot);
		Log.d("RestClient",
				String.format("Rest Client created for %s",
						this.rootUrl.toExternalForm()));
	}

	/**
	 * Sends a JSONRPC request
	 * 
	 * @param request
	 *            The request to send
	 * @return The response
	 */
	private JSONObject sendJSONRPC(JSONObject request) {
		JSONObject ret = null;

		try {
			HttpURLConnection conn = (HttpURLConnection) this.rootUrl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setChunkedStreamingMode(0);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStreamWriter osw = new OutputStreamWriter(
					new BufferedOutputStream(conn.getOutputStream()));
			request.writeJSONString(osw);
			osw.close();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStreamReader isr = new InputStreamReader(
						new BufferedInputStream(conn.getInputStream()));
				ret = (JSONObject) new JSONParser().parse(isr);
			} else {
				Log.e("RestClient", String.format(
						"Got %d instead of %d for HTTP Response",
						conn.getResponseCode(), HttpURLConnection.HTTP_OK));
				return null;
			}

		} catch (IOException e) {
			Log.e("RestClient", "Error in sendJSONRPC", e);
		} catch (ParseException e) {
			Log.e("RestClient", "Parse return data error", e);
		}
		return ret;
	}

	/**
	 * Creates a JSONRPC Object with required fields besides parameters set
	 * 
	 * @param method
	 *            The method called
	 * @return The object
	 */
	@SuppressWarnings("unchecked")
	private JSONObject createJSONRPCObject(String method) {
		UUID id = UUID.randomUUID();
		JSONObject request = new JSONObject();
		request.put("jsonrpc", JSON_RPC_VERSION);
		request.put("method", method);
		request.put("id", id.toString());
		return request;
	}

	/**
	 * Checks the response for errors
	 * 
	 * @param response
	 *            The response
	 * @param request
	 *            The request sent for the response
	 * @throws IOException
	 *             If an error is encountered
	 */
	private void checkJSONResponse(JSONObject response, JSONObject request)
			throws IOException {
		if ((response == null) || (response.get("error") != null)) {
			Log.e("RestClient",
					String.format(
							"An error was encountered with the code %d with the message %s",
							response.get("code"), response.get("message")));
			throw new IOException("Invalid response from server");
		}
		if (!response.get("id").equals(request.get("id"))) {
			Log.e("RestClient", "Response ID doesn't match!");
			throw new IOException("Got the wrong id on response");
		}
	}

	/**
	 * Does a SHA-256 hash of the password
	 * 
	 * @param password
	 *            The password to hash
	 * @return The hashed password
	 * @throws AuthenticationException
	 *             If a problem occurs during hashing
	 */
	private String hashPassword(String password) throws AuthenticationException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			Log.e("RestClient", "Failed to encode password", e);
			throw new AuthenticationException("Password Encode failure");
		}
	}

	/**
	 * Authenticates the user with the server and gives an authentication token
	 * 
	 * @param user
	 *            The user name to login with
	 * @param password
	 *            The password to use
	 * @return An authentication token if successful, null otherwise
	 * @throws IOException
	 *             If a connection problem occurs
	 * @throws AuthenticationException
	 *             If a problem authenticating occurs
	 */
	@SuppressWarnings("unchecked")
	public String login(String user, String password) throws IOException,
			AuthenticationException {
		String hashedPassword = hashPassword(password);

		Map<String, String> params = new JSONObject();
		params.put("user", user);
		params.put("password", hashedPassword);

		JSONObject request = createJSONRPCObject("login");
		request.put("params", params);
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);

		String auth = (String) response.get("result");

		return auth;
	}

	/**
	 * Gets all available Minecraft commands on the server
	 * 
	 * @return A list of Minecraft commands
	 * @throws IOException
	 *             If an error is encountered
	 */
	public Map<String, MinecraftCommand> getAllMinecraftCommands()
			throws IOException {
		Map<String, MinecraftCommand> cmds = new HashMap<String, MinecraftCommand>();
		JSONObject request = createJSONRPCObject("getAllCommands");
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);

		// Parse result
		@SuppressWarnings("unchecked")
		Map<String, JSONObject> commands = (Map<String, JSONObject>) response
				.get("result");
		for (String name : commands.keySet()) {
			JSONObject paramObj = commands.get(name);
			JSONArray jparams = (JSONArray) paramObj.get("params");
			JSONArray jparamTypes = (JSONArray) paramObj.get("paramTypes");

			Map<String, ArgType> params = new HashMap<String, ArgType>();
			for (int i = 0; i < jparams.size(); i++) {
				params.put((String) jparams.get(i), ArgType
						.getArgTypeFromString((String) jparamTypes.get(i)));
			}
			cmds.put(name, new MinecraftCommand(name, params));
		}
		return cmds;
	}

	/**
	 * Gets information about the server
	 * 
	 * @return A map containing information about the server
	 * @throws IOException
	 *             If a connection problem occurs
	 */
	public Map<String, Object> getServerInfo() throws IOException {
		Map<String, Object> ret = new HashMap<String, Object>();
		// Create request
		JSONObject request = createJSONRPCObject("systemInfo");
		// Send request
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);

		// Parse response
		@SuppressWarnings("unchecked")
		Map<String, Object> json = (JSONObject) response.get("result");
		ret.putAll(json);

		return ret;
	}

	/**
	 * Executes the given command on the server the RestClient is connected to
	 * 
	 * @param cmd
	 *            The command to execute
	 * @param params
	 *            The parameters to pass into the command
	 * @return A map containing any return values
	 * @throws IOException
	 *             If a connection problem occurs
	 */
	@SuppressWarnings("unchecked")
	public String executeCommand(MinecraftCommand cmd,
			Map<String, Object> params) throws IOException {

		// Create request
		JSONObject request = createJSONRPCObject("command");
		JSONObject command = cmd.createJSONObject(params);
		request.put("params", command);
		// Send request
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);

		// Parse response
		String json = (String) response.get("result");

		return json;
	}

	/**
	 * This method gets a list of all mods and their versions that are currently
	 * on the Minecraft server
	 * 
	 * @return A list of mods
	 * @throws IOException
	 *             If a connection problem occurs
	 */
	public List<MinecraftMod> getServerMods() throws IOException {
		List<MinecraftMod> mods = new ArrayList<MinecraftMod>();

		JSONObject request = createJSONRPCObject("getMods");
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);

		@SuppressWarnings("unchecked")
		List<JSONObject> modlist = (List<JSONObject>) response.get("result");
		for (Map<String, String> m : modlist) {
			mods.add(new MinecraftMod(m.get("name"), m.get("version")));
		}

		return mods;

	}

	/**
	 * Gets console messages since index and appends it to message
	 * 
	 * @param index
	 *            The last index, set to -1 for all currently on server
	 * @param messages
	 *            The list to append to
	 * @return The last index of received messages
	 * @throws IOException
	 *             If a connection error occurs
	 */
	@SuppressWarnings("unchecked")
	public long getConsoleMessages(long index, List<String> messages)
			throws IOException {
		long lastIndex = index;
		if (messages == null) {
			Log.e("RestClient", "Passed in List was null!");
			throw new IllegalArgumentException("List was null");
		}
		JSONObject request = createJSONRPCObject("consoleMessages");
		request.put("params", index);
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);

		List<JSONObject> jmessages = (List<JSONObject>) response.get("result");
		for (JSONObject message : jmessages) {
			lastIndex = (Long) message.get("id");
			messages.add((String) message.get("message"));
		}

		return lastIndex;
	}

	/**
	 * Stops the Minecraft server
	 * 
	 * @throws IOException
	 *             If a connection problem occurs
	 */
	public void stopServer() throws IOException {
		JSONObject request = createJSONRPCObject("stopServer");
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);
	}

	/**
	 * Gets a list of all methods on the server's JSON-RPC service
	 * 
	 * @return A map of methods and their descriptions
	 * @throws IOException
	 *             If a connection problem occurs
	 */
	public Map<String, String> getAllMethods() throws IOException {
		Map<String, String> methods = new HashMap<String, String>();
		JSONObject request = createJSONRPCObject("getAllMethods");
		JSONObject response = sendJSONRPC(request);
		checkJSONResponse(response, request);

		// Parse response
		@SuppressWarnings("unchecked")
		Map<String, String> jmethods = (Map<String, String>) response
				.get("result");
		for (String method : jmethods.keySet()) {
			methods.put(method, jmethods.get(method));
		}

		return methods;
	}
}
