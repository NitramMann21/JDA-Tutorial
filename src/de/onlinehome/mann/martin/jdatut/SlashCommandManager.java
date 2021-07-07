package de.onlinehome.mann.martin.jdatut;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.onlinehome.mann.martin.jdatut.commands.RickrollSlashCommand;
import de.onlinehome.mann.martin.jdatut.commands.SaySlashCommand;
import de.onlinehome.mann.martin.jdatut.commands.types.SlashCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class SlashCommandManager extends ListenerAdapter {
	
	private Map<String, SlashCommand> commandsMap;
	
	public SlashCommandManager() {
		commandsMap = new ConcurrentHashMap<>();
		
		commandsMap.put("say", new SaySlashCommand());
		commandsMap.put("rickroll", new RickrollSlashCommand());
		
		CommandListUpdateAction commands = JDATutorial.jda.updateCommands();
		
		commands.addCommands(new CommandData("say", "Resends your message")
				.addOptions(new OptionData(OptionType.STRING, "msg", "The message to resend.").setRequired(true)));
		commands.addCommands(new CommandData("rickroll", "Rickrolls you :P"));
		
		commands.queue();
	}
	
	@Override
	public void onSlashCommand(SlashCommandEvent event) {
		String commandName = event.getName();
		
		SlashCommand command;
		
		if((command = commandsMap.get(commandName)) != null) {
			command.performCommand(event, event.getMember(), event.getTextChannel());
		}
	}
	
}
