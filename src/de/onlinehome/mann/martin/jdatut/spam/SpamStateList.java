package de.onlinehome.mann.martin.jdatut.spam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.onlinehome.mann.martin.jdatut.YamlUtil;
import net.dv8tion.jda.api.entities.Member;

public class SpamStateList {
	
	private List<SpamState> list = new ArrayList<>();
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
	
	public void updateSpamState(Member member, String lastMsg) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				spamState.updateSpamState(lastMsg);
				return;
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
	}
	
	public void setMuteEnd(Member member, long muteEnd) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				spamState.getData().setMuteEnd(muteEnd);
				return;
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
	}
	
	public int getSpamState(Member member) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				return spamState.getData().getMsgCount();
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
		return 0;
	}
	
	public int getWarnings(Member member) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				return spamState.getData().getWarningCount();
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
		return 0;
	}
	
	public long getMuteEnd(Member member) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				return spamState.getData().getMuteEnd();
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
		return 0L;
	}
	
	public void resetSpamState(Member member) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				spamState.getData().setMsgCount(0);
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
	}
	
	public void resetWarnings(Member member) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				spamState.getData().setWarningCount(0);
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
	}
	
	public void resetMuteEnd(Member member) {
		for (SpamState spamState : list) {
			if(spamState.getData().getMemberId() == member.getIdLong()) {
				spamState.getData().setMuteEnd(0);
			}
		}
		list.add(new SpamState(YamlUtil.getSpamStateDataByMember(member)));
	}
	
	public void startUnmuteTest() {
		scheduler.scheduleWithFixedDelay(() -> {
			for (SpamState spamState : list) {
				if(spamState.shouldUnmute()) {
					spamState.unmute();
				}
			}
		}, 1L, 1L, TimeUnit.MINUTES);
	}
	
}
