package de.onlinehome.mann.martin.jdatut.spam;

import static de.onlinehome.mann.martin.jdatut.YamlUtil.saveYAML;

import de.onlinehome.mann.martin.jdatut.YamlUtil.YamlData.SpamStateData;

public class SpamState {
	
	private SpamStateData data;
	
	public SpamState(SpamStateData data) {
		this.data = data;
	}
	
	public void updateSpamState(String lastMsg) {
		if((System.currentTimeMillis() - data.getLastMsgSentWhen()) >= 600000) {
			data.setMsgCount(1);
		}
		
		if(data.getLastMsg().equals(lastMsg)) {
			data.setMsgCount(data.getMsgCount() + 1);
		} else {
			data.setLastMsg(lastMsg);
			data.setMsgCount(1);
		}
		
		if(data.getMsgCount() >= 5) {
			data.setWarningCount(data.getWarningCount() + 1);
		}
		
		data.setLastMsgSentWhen(System.currentTimeMillis());
		
		saveYAML();
	}
	
	public SpamStateData getData() { return data; }
	
	public boolean shouldUnmute() {
		return ((data.getMuteEnd() != -1) && (System.currentTimeMillis() >= data.getMuteEnd()));
	}

	public void unmute() {
		System.out.println("Entmutet.");
	}
	
}
