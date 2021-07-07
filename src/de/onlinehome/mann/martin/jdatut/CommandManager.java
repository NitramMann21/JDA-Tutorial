package de.onlinehome.mann.martin.jdatut;

import java.util.HashMap;
import java.util.Map;

import de.onlinehome.mann.martin.jdatut.commands.KickCommand;
import de.onlinehome.mann.martin.jdatut.commands.PingCommand;
import de.onlinehome.mann.martin.jdatut.commands.StageCommand;
import de.onlinehome.mann.martin.jdatut.commands.TalkCommand;
import de.onlinehome.mann.martin.jdatut.commands.types.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {
	
	private Map<String, Command> commands;
	
	public CommandManager() {
		commands = new HashMap<>();
		
		commands.put("ping", new PingCommand());
		commands.put("talk", new TalkCommand());
		commands.put("temptalk", JDATutorial.temptalkCommand);
		commands.put("kick", new KickCommand());
		commands.put("stage", new StageCommand());
	}

	
	public void performCommand(Member m, TextChannel channel, Message message) {
		System.out.println("in CommandManager#performCommand()");
		
		String cmd = message.getContentDisplay().split(" ")[0].substring(1);
		
		Command command;
		
		if((command = commands.get(cmd)) != null)
			command.performCommand(m, channel, message);
	}
	
}
