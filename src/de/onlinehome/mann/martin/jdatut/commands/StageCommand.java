package de.onlinehome.mann.martin.jdatut.commands;

import de.onlinehome.mann.martin.jdatut.commands.types.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.StageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class StageCommand implements Command {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		System.out.println("In !stage");
		
		VoiceChannel voice = m.getVoiceState().getChannel();
		
		if(!(voice instanceof StageChannel))
			return;
		
		channel.getGuild().getAudioManager().closeAudioConnection();
		
		StageChannel stage = (StageChannel) voice;
		
		AudioManager audioMan = stage.getGuild().getAudioManager();
		
		if(stage.getStageInstance() == null)
			stage.createStageInstance("Stage-Testing").queue();
		stage.getGuild().requestToSpeak();
		audioMan.openAudioConnection(stage);
	}

}
