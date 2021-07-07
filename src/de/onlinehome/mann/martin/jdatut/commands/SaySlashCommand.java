package de.onlinehome.mann.martin.jdatut.commands;

import de.onlinehome.mann.martin.jdatut.commands.types.SlashCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class SaySlashCommand implements SlashCommand {

	@Override
	public void performCommand(SlashCommandEvent event, Member m, TextChannel channel) {
		event.reply(event.getOption("msg").getAsString()).queue();
	}

}
