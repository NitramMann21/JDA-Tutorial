package de.onlinehome.mann.martin.jdatut.commands;

import de.onlinehome.mann.martin.jdatut.commands.types.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class TalkCommand implements Command {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		try {
			String[] args = message.getContentDisplay().split(" ", 3);
			Guild g = channel.getGuild();
			Category category = g.getCategoriesByName(args[1], true).get(0);

			category.createVoiceChannel(args[2]).queue();
		} catch (ArrayIndexOutOfBoundsException e) {
			channel.sendMessage(new MessageBuilder(new EmbedBuilder().setTitle("Fehler").setColor(0xdf0101).setAuthor("JDATutorial")
					.setFooter("Fehler im !talk").setDescription("Zu wenig Argumente!").build()).build()).queue();
		}
	}

}
