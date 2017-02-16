package com.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.search.BasicStudentSearchService;
import com.server.data.DataServiceInjector;
import com.server.data.FileDataServiceInjector;

public class Server {
	
	private static int port = 6789;

	DataManager dataManager;
	public static void main(String[] args) throws IOException {
		{
			DataServiceInjector injector = null;
			DataManager dataManager = null;
			
			injector = new FileDataServiceInjector();
			dataManager = (DataManager) injector.getConsumer();
			dataManager.setStudentSearchService(new BasicStudentSearchService());
			
			String clientCommand;
			String capitalizedSentence;
			ServerSocket welcomeSocket = new ServerSocket(port);

			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));

				DataOutputStream outToClient = new DataOutputStream(
						connectionSocket.getOutputStream());

				clientCommand = inFromClient.readLine();
				String commandResponse = dataManager.query(clientCommand);
				System.out.println("Received: " + clientCommand);
				
				outToClient.writeBytes(commandResponse+'\n');
				connectionSocket.close();
			}
		}
	}

}