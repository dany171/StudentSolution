package com.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static String host = "localhost";
	private static int port = 6789;

	private static boolean runClient = true;

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		while (runClient) {
			String inputString = scanner.nextLine();
			switch (inputString) {

			case "exit":
				runClient = false;
				sendCommand(inputString + " persist=true");
				break;
			case "host":
				host = inputString;
				break;
			default:
				sendCommand(inputString);
			}
		}
		scanner.close();
	}

	public static void sendCommand(String command) {
		try {
			Socket clientSocket = new Socket(host, port);

			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());

			outToServer.writeBytes(command + '\n');

			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			System.out.println(inFromServer.readLine());
			clientSocket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}