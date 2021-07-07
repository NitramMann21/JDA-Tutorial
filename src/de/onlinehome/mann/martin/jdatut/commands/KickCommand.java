package de.onlinehome.mann.martin.jdatut.commands;

import de.onlinehome.mann.martin.jdatut.commands.types.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class KickCommand implements Command {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		// *kick MEMBER(MENTION) REASON
		String[] args = message.getContentDisplay().split(" ", 3);
		Member toKick = message.getMentionedMembers().get(0);
		try {
			if(args.length == 3)
				toKick.kick(args[2]).queue();
			else
				toKick.kick().queue();
		} catch (ArrayIndexOutOfBoundsException e) {
			channel.sendMessage("Zu wenig Argumente!").queue();
		}
	}

}
