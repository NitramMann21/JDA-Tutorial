package de.onlinehome.mann.martin.jdatut.commands;

import de.onlinehome.mann.martin.jdatut.commands.types.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PingCommand implements Command {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		message.reply("Pong!").queue();
		channel.sendMessage(":ping_pong: " + m.getAsMention()).queue();
	}

}
