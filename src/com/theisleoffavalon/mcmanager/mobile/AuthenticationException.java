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

/**
 * This exception is thrown when an error occurs with authentication on the
 * server
 * 
 * @author Henkelj
 */
public class AuthenticationException extends Exception {

	/**
	 * @param reason
	 *            The reason why the Authentication Exception is being thrown
	 */
	public AuthenticationException(String reason) {
		super(reason);
	}

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8182617303356496762L;

}
