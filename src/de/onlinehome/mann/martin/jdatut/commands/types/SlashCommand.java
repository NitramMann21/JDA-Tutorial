package de.onlinehome.mann.martin.jdatut.commands.types;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public interface SlashCommand {
	public void performCommand(SlashCommandEvent event, Member m, TextChannel channel);
}
