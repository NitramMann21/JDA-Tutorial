package de.onlinehome.mann.martin.jdatut.listeners;

import de.onlinehome.mann.martin.jdatut.JDATutorial;
import de.onlinehome.mann.martin.jdatut.YamlUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String prefix = "*";
		Member m = event.getMember();
		TextChannel channel = event.getChannel();
		Message message = event.getMessage();
		
		System.out.println(JDATutorial.spamStates.getSpamState(m) + " " + JDATutorial.spamStates.getWarnings(m));
		
		if(!event.getAuthor().isBot())
			JDATutorial.spamStates.updateSpamState(m, message.getContentDisplay());
		
		if(JDATutorial.spamStates.getSpamState(m) >= 5) {
			System.out.println("Verwarnung");
			JDATutorial.spamStates.resetSpamState(m);
			YamlUtil.saveYAML();
		}
		if(JDATutorial.spamStates.getWarnings(m)  >= 3) {
			System.out.println("Mute");
			JDATutorial.spamStates.setMuteEnd(m, System.currentTimeMillis() + 1000);
			JDATutorial.spamStates.resetWarnings(m);
			YamlUtil.saveYAML();
		}
		
		if(message.getContentDisplay().startsWith(prefix)) {
			JDATutorial.getCmdMan().performCommand(m, channel, message);
		}
	}
	
}
