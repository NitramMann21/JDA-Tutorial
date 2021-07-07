package de.onlinehome.mann.martin.jdatut.commands;

import java.util.HashMap;
import java.util.Map;

import de.onlinehome.mann.martin.jdatut.commands.types.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TemptalkCommand extends ListenerAdapter implements Command {

	private Map<Long, Member> tempTalks = new HashMap<>();
	
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		String[] args = message.getContentDisplay().split(" ", 3);
		Guild g = channel.getGuild();
		
		try {
			if(args[1].equals("create")) {
				VoiceChannel vc = g.getCategoriesByName("Sprachkanäle", false).get(0).createVoiceChannel(args[2] + " | Temptalk").complete();
				tempTalks.put(vc.getIdLong(), m);
				channel.sendMessage("Temptalk erstellt!").queue();
			} else if(args[1].equals("delete")) {
				if(tempTalks.get(m.getVoiceState().getChannel().getIdLong()).equals(m) || m.hasPermission(Permission.MANAGE_CHANNEL)) {
					tempTalks.remove(m.getVoiceState().getChannel().getIdLong());
					m.getVoiceState().getChannel().delete().queue();
					channel.sendMessage("Temptalk gelöscht!").queue();
				} else {
					channel.sendMessage("Keine Berechtigungen").queue();
				}
			} else if(args[1].equals("size")) {
				if(tempTalks.get(m.getVoiceState().getChannel().getIdLong()).equals(m) || m.hasPermission(Permission.MANAGE_CHANNEL)) {
					m.getVoiceState().getChannel().getManager().setUserLimit(Integer.parseInt(args[2])).queue();
					channel.sendMessage("Größe gesetzt!").queue();
				} else {
					channel.sendMessage("Keine Berechtigungen").queue();
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			channel.sendMessage("Nicht genügend Argumente!").queue();
		} catch(NullPointerException e) {
			channel.sendMessage("Du bist in keinem Temptalk!").queue();
		} catch(IllegalArgumentException e) {
			channel.sendMessage(args[2] + " ist entweder keine Zahl oder nicht im Bereich von 0 - 99!").queue();
		}
	}
	
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		VoiceChannel channel = event.getChannelLeft();
		
		if(channel.getMembers().size() == 0) {
			if(tempTalks.containsKey(channel.getIdLong())) {
				tempTalks.remove(channel.getIdLong());
				channel.delete().queue();
			}
		}
	}

}
