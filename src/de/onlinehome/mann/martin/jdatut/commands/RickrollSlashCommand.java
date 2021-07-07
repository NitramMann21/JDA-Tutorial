package de.onlinehome.mann.martin.jdatut.commands;

import de.onlinehome.mann.martin.jdatut.commands.types.SlashCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

public class RickrollSlashCommand implements SlashCommand {

	@Override
	public void performCommand(SlashCommandEvent event, Member m, TextChannel channel) {
		event.reply("Hier ein Rickroll!")
				.addActionRow(// Button.link("https://www.youtube.com/watch?v=dQw4w9WgXcQ", "Hier klicken!"),
						SelectionMenu.create("menu")
								.addOption("Schön!", "good")
								.addOption("Nicht gut.", "bad")
								.build())
				.queue();
	}

}
