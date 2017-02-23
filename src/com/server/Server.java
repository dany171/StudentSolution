package com.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.data.DataServiceInjector;
import com.server.data.FileDataServiceInjector;
import com.server.search.BasicStudentSearchService;
/**
 * Represents the server.
 * 
 * This receives client's socket connections and answers them.
 * 
 * @author Daniel Echalar
 *
 */
public class Server {
	
	// CONSTANTS
	private static final int SERVER_PORT = 6789;
	
	// STATICS
	private static boolean active = true;
	
	public static void main(String[] args) throws IOException {
		{
			
			DataServiceInjector injector = null;
			CommandExecutor executor = null;
			
			injector = new FileDataServiceInjector();
			executor = (CommandExecutor) injector.getConsumer();
			executor.setStudentSearchService(new BasicStudentSearchService());
			
			String clientCommand;
			ServerSocket clientSocket = new ServerSocket(SERVER_PORT);

			while (active) {
				
				Socket connectionSocket = clientSocket.accept();
				
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));

				DataOutputStream outToClient = new DataOutputStream(
						connectionSocket.getOutputStream());

				clientCommand = inFromClient.readLine();
				String commandResponse = executor.execute(clientCommand);
				System.out.println("Received: " + clientCommand+" - Command executed");
				outToClient.writeBytes(commandResponse+'\n');
				
				connectionSocket.close();
			}
			clientSocket.close();
		}
	}
}