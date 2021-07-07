package de.onlinehome.mann.martin.jdatut;

import java.time.LocalDateTime;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

public class DiscordLogger {
	
	public void sendLogError(String title, String msg, MessageChannel channel) {
		sendLogInfo(title, msg, channel, 0xdf0101);
	}
	
	public void sendLogError(String title, String msg, MessageChannel channel, int color) {
		sendLogMessage("ERROR", title, msg, channel, color);
	}
	
	public void sendLogInfo(String title, String msg, MessageChannel channel) {
		sendLogInfo(title, msg, channel, 0x00ff44);
	}
	
	public void sendLogInfo(String title, String msg, MessageChannel channel, int color) {
		sendLogMessage("INFO", title, msg, channel, color);
	}
	
	public void sendLogMessage(String title, String subtitle, String msg, MessageChannel channel, int color) {
		EmbedBuilder builder = new EmbedBuilder().setTitle(title).setColor(null).setFooter(getDateAndTimeNow());
		if(subtitle.equals(""))
			builder.setDescription(msg);
		else
			builder.setDescription("**" + subtitle + "**\n" + msg);
		channel.sendMessage(new MessageBuilder(builder.build()).build()).queue();
	}
	
	public static String getDateAndTimeNow() {
		LocalDateTime now = LocalDateTime.now();
		StringBuilder sb = new StringBuilder();
		sb.append(now.getDayOfMonth()).append(".").append(now.getMonthValue()).append(".").append(now.getYear()).append(" ").append(now.getHour())
				.append(":").append(now.getMinute()).append(":").append(now.getSecond());
		return sb.toString();
	}
	
}
