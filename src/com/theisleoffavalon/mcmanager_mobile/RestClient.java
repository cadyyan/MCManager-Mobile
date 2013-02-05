/**
 * 
 */
package com.theisleoffavalon.mcmanager_mobile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

import com.theisleoffavalon.mcmanager_mobile.Command.ArgType;

/**
 * This class handles talking to the server
 * 
 * @author Jacob Henkel
 */
public class RestClient {

	/**
	 * The root URL of the API
	 */
	private URL	rootUrl;

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

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
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

	@SuppressWarnings("unchecked")
	public List<Command> getAllCommands() throws IOException {
		List<Command> cmds = new ArrayList<Command>();
		UUID id = UUID.randomUUID();
		JSONObject request = new JSONObject();
		request.put("jsonrpc", JSONRpcValues.JSON_RPC_VERSION);
		request.put("method", "getAllCommands");
		request.put("id", id.toString());

		JSONObject resp = sendJSONRPC(request);
		if ((resp == null) || (resp.get("error") != null)) {
			Log.e("RestClient",
					String.format(
							"An error was encountered with the code %d with the message %s",
							resp.get("code"), resp.get("message")));
			throw new IOException("Invalid resp from server");
		}
		if (resp.get("id").equals(id.toString())) {
			JSONObject json = (JSONObject) resp.get("result");
			Set<String> keys = json.keySet();
			for (String key : keys) {
				JSONObject jcmd = (JSONObject) json.get(key);
				JSONArray jparams = (JSONArray) jcmd.get("params");
				JSONArray jparamTypes = (JSONArray) jcmd.get("paramTypes");

				Map<String, ArgType> params = new HashMap<String, ArgType>();
				for (int i = 0; i < jparams.size(); i++) {
					params.put((String) jparams.get(i), ArgType
							.getArgTypeFromString((String) jparamTypes.get(i)));
				}
				cmds.add(new Command(key, params));
			}
		} else {
			Log.e("RestClient", "Response ID doesn't match!");
			throw new IOException("Got the wrong id on response");
		}
		return cmds;
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
	public Map<String, String> executeCommand(Command cmd,
			Map<String, Object> params) throws IOException {
		Map<String, String> ret = new HashMap<String, String>();
		UUID id = UUID.randomUUID();
		JSONObject request = cmd.createJSONObject(params);
		request.put("jsonrpc", JSONRpcValues.JSON_RPC_VERSION);
		request.put("id", id.toString());

		JSONObject response = sendJSONRPC(request);
		if ((response == null) || (response.get("error") != null)) {
			Log.e("RestClient",
					String.format(
							"An error was encountered with the code %d with the message %s",
							response.get("code"), response.get("message")));
			throw new IOException("Invalid resp from server");
		}
		if (response.get("id").equals(id.toString())) {
			JSONObject json = (JSONObject) response.get("result");
			ret.putAll(json);
		} else {
			Log.e("RestClient", "Response ID doesn't match!");
			throw new IOException("Got the wrong id on response");
		}
		return ret;
	}
}
