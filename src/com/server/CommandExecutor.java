package com.server;

import command.CommandStrategy;

public class CommandExecutor {

	public void execute(final CommandStrategy cmd){
		cmd.execute();
	}
}
