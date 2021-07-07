package de.onlinehome.mann.martin.jdatut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import de.onlinehome.mann.martin.jdatut.commands.TemptalkCommand;
import de.onlinehome.mann.martin.jdatut.listeners.EventListener;
import de.onlinehome.mann.martin.jdatut.listeners.MessageListener;
import de.onlinehome.mann.martin.jdatut.spam.SpamStateList;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class JDATutorial {
	
	public static JDA jda;
	public static CommandManager cmdMan;
	public static TemptalkCommand temptalkCommand;
	public static SpamStateList spamStates = new SpamStateList();
	public static DiscordLogger logger;
	public static SlashCommandManager slashCmdMan;
	
	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
	private static Activity[] activities = { Activity.playing("abc"), Activity.watching("Müll"), Activity.playing("nichts...") };
	
	public static void main(String[] args) {
		try {
			new JDATutorial();
		} catch (LoginException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public JDATutorial() throws LoginException, IOException {
		YamlUtil.load();
		Configuration.read();
		
		JDABuilder builder = JDABuilder.createDefault(Configuration.getToken());
		builder.setStatus(OnlineStatus.ONLINE);
		builder.setActivity(activities[0]);
		builder.enableCache(CacheFlag.VOICE_STATE);
		builder.addEventListeners(new MessageListener(), new EventListener(), temptalkCommand = new TemptalkCommand());
		
		jda = builder.build();
		cmdMan = new CommandManager();
		logger = new DiscordLogger();
		
		jda.addEventListener(slashCmdMan = new SlashCommandManager());
		
		spamStates.startUnmuteTest();
		
		consoleListener();
		activitySwitcher();
	}
	
	private void consoleListener() {
		new Thread(() -> {
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while((line = reader.readLine()) != null) {
					if(line.equalsIgnoreCase("exit") && jda != null) {
						for(int i = 5; i > 0; i--) {
							if(i != 1)
								System.out.println("Bot stops in " + i + " seconds.");
							else if(i == 1)
								System.out.println("Bot stops in " + i + " second.");
							Thread.sleep(1000);
						}
						jda.getPresence().setStatus(OnlineStatus.OFFLINE);
						jda.shutdown();
						System.exit(0);
					}
				}
			} catch(IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	private void activitySwitcher() {
		new Thread(() -> {
			scheduler.scheduleWithFixedDelay(new Runnable() {
				int previousIndex = 0;
				
				@Override
				public void run() {
					Random random = new Random();
					int index = 0;
					
					while((index = random.nextInt(activities.length - 1)) == previousIndex) {}
					
					jda.getPresence().setActivity(activities[index]);
					previousIndex = index;
					
					YamlUtil.getData().setInteger(YamlUtil.getData().getInteger() + 1);
					YamlUtil.saveYAML();
				}
			}, 0, 10, TimeUnit.SECONDS);
		}).start();
	}
	
	public static CommandManager getCmdMan() {
		return cmdMan;
	}
	
}
