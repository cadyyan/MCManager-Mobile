/**
 * 
 */
package com.theisleoffavalon.mcmanager_mobile;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * This class represents a command that the server API is exposing
 * 
 * @author Jacob Henkel
 */
@SuppressLint("DefaultLocale")
public class Command {

	private String			name;
	private List<String>	arguments;
	private List<ArgType>	argumentTypes;

	public enum ArgType {
		STRING, INT, PLAYER;

		@SuppressLint("DefaultLocale")
		public static ArgType getArgTypeFromString(String sArgType) {
			String arg = sArgType.toLowerCase();
			if (arg.equals("string")) {
				return STRING;
			} else if (arg.equals("int")) {
				return INT;
			} else if (arg.equals("player")) {
				return PLAYER;
			} else {
				return STRING;
			}
		}
	}

	public Command(String name, List<String> arguments,
			List<ArgType> argumentTypes) {
		this.name = name;
		this.arguments = new LinkedList<String>();
		Collections.copy(this.arguments, arguments);
		this.argumentTypes = new LinkedList<ArgType>();
		Collections.copy(this.argumentTypes, argumentTypes);
	}

	/**
	 * Returns a JSONObject with the method and params set
	 * 
	 * @param A
	 *            mapping of parameter names to values, while not checked, these
	 *            should respect the ArgType
	 * @return A JSON object with the method and params values set
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createJSONObject(@SuppressWarnings("rawtypes") Map params) {
		JSONObject json = new JSONObject();
		JSONObject parameters = new JSONObject();
		for (Object key : params.keySet()) {
			parameters.put(key, params.get(key));
		}
		json.put("method", this.name);
		json.put("params", parameters);
		Log.d("Command", "Created Command JSON of " + json.toJSONString());
		return json;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the arguments
	 */
	public List<String> getArguments() {
		return this.arguments;
	}

	/**
	 * @param arguments
	 *            the arguments to set
	 */
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	/**
	 * @return the argumentTypes
	 */
	public List<ArgType> getArgumentTypes() {
		return this.argumentTypes;
	}

	/**
	 * @param argumentTypes
	 *            the argumentTypes to set
	 */
	public void setArgumentTypes(List<ArgType> argumentTypes) {
		this.argumentTypes = argumentTypes;
	}
}
