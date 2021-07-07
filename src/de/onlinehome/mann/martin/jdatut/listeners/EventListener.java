package de.onlinehome.mann.martin.jdatut.listeners;

import de.onlinehome.mann.martin.jdatut.JDATutorial;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		event.getGuild().getSystemChannel().sendMessage(event.getMember().getEffectiveName() + " ist soeben beigetreten!").queue();
	}
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
		event.getGuild().getSystemChannel().sendMessage(event.getMember().getEffectiveName() + " ist dem Talk " + event.getChannelJoined().getName()
				+ " beigetreten.").queue();
	}
	
	@Override
	public void onRoleCreate(RoleCreateEvent event) {
		event.getGuild().getSystemChannel().sendMessage("Rolle " + event.getRole().getName() + " (Farbe: " + event.getRole().getColorRaw() + ") "
				+ "wurde erstellt.").queue();
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		System.out.printf("Verfügbar: %s, Nicht verfügbar: %s, Insgesamt: %s%n", event.getGuildAvailableCount(), event.getGuildUnavailableCount(), 
				event.getGuildTotalCount());
		JDATutorial.logger.sendLogInfo("", "Bot online.", JDATutorial.jda.getGuildsByName("Nitram's Testserver", false).get(0)
				.getTextChannelsByName("lobby", false).get(0));
	}
	
	@Override
	public void onSelectionMenu(SelectionMenuEvent event) {
		event.reply(event.getMember().getEffectiveName() + " hat im Selection Menu " + event.getSelectedOptions().get(0).getLabel() + " angeklickt.")
				.queue();
	}
	
}
