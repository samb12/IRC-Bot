import java.io.IOException;

import org.jibble.pircbot.IrcException;
//the main class creates a chatbot instance which extends from the pircbot class.This class
//also instructs the bot to connect to the server and join the channel #sujan.
public class ChatBotMain {

	public static void main(String[] args) throws IrcException, IOException {
        //start the bot up.
		ChatBot chatBot = new ChatBot();

		// Enable debugging output
		chatBot.setVerbose(true);
		
		//Connect to the IRC server.
		chatBot.connect("irc.freenode.net");
       
		//Join the #sujan Channel
		chatBot.joinChannel("#sujan");
	}

}
