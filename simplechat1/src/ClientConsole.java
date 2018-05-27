// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import client.*;
import client.ClientRequest.TYPE;
import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
	//Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;
	private static final String LOWER_WRITE_COMMAND = "write";
	private static final String LOWER_READ_COMMAND = "read";
	private static final String UPPER_WRITE_COMMAND = "WRITE";
	private static final String UPPER_READ_COMMAND = "READ";
	private static final String INVALID_COMMAND_MESSAGE = "Invalid Command!\nCommand Format is: read/write followed by valid sql query/update";
	
	//Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	ChatClient client;


	//Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientConsole(String host, int port) 
	{  
		try 
		{
			client= new ChatClient(host, port, this);
		} 
		catch(IOException exception) 
		{
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}

		/*TODO: add fields as needed in class*/
		/*TODO: extend the constructor to receive the needed info (user name, user id)*/
		/*TODO:check out AbstractClient API(NOTE ChatClient extends AbstractClient), to find out how to get the address of the client*/
	}


	//Instance methods ************************************************

	/**
	 * This method waits for input from the console.  Once it is 
	 * received, it sends it to the client's message handler.
	 */
	public void accept() 
	{
		try
		{
			BufferedReader fromConsole = 
					new BufferedReader(new InputStreamReader(System.in));
			String message;

			System.out.println("please write a message:");

			while (true) 
			{
				message = fromConsole.readLine();
				if(!isValidCommand(message)){
					System.out.println(INVALID_COMMAND_MESSAGE);
					continue;
				}
				String[] msgParts = message.toString().split("\\s+");
				String requestType = msgParts[0];
				String sqlCommand = message.toString().replaceAll(requestType,"").trim();
				TYPE type = requestType.equalsIgnoreCase(LOWER_READ_COMMAND) ? TYPE.Read : TYPE.Write;
				ClientRequest clientRequest = new ClientRequest(type, sqlCommand);
				client.handleMessageFromClientUI(clientRequest);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println
			("Unexpected error while reading from console!");
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface.  It
	 * displays a message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(String message) 
	{
		System.out.println("> " + message);
	}

	private boolean isValidCommand(String command) {
		boolean toRet = true;

		if(command == null || command.isEmpty()){
			toRet = false;
		}

		if(!command.contains(UPPER_READ_COMMAND) &&
				!command.contains(UPPER_WRITE_COMMAND) &&
				!command.contains(LOWER_READ_COMMAND) &&
				!command.contains(LOWER_WRITE_COMMAND)){
			toRet = false;
		}

		return toRet;
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0] The host to connect to.
	 */
	public static void main(String[] args) 
	{
		String host = "";
		int port = 0;  //The port number

		try
		{
			host = args[0];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			host = "localhost";
		}

		/*TODO: add code to get user info(user name, user id) and pass it, NOTE: no need to check if info is valid */

		ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
		chat.accept();  //Wait for console data
	}
}
//End of ConsoleChat class
