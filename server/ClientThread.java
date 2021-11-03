package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {

	private Socket client;
	private Scanner in;
	private PrintWriter out;
	
	private boolean fired = false;
	private int n;
	
	public ClientThread(Socket client, int n) {
		this.client = client;
		this.n = n;
	}

	public void run() {
		if (fired)
			return;
		fired = true;
		
		try {
			in = new Scanner(client.getInputStream());
			out = new PrintWriter(client.getOutputStream());
			System.out.println("THREAD " + n + " - gestiti gli stream");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			String msg_in = in.nextLine();
			System.out.println("THREAD " + n + " - Ricevuto messaggio " + msg_in);
			if (msg_in.equals("quit")) {
				System.out.println("THREAD " + n + " - Connessione interrotta dal client");
				close();
				break;
			}
			out.println(msg_in.toUpperCase());
			out.flush();
			System.out.println("THREAD " + n + " - Inviato messaggio di risposta");
		}
	}
	
	private void close() {
		in.close();
		out.close();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("THREAD " + n + " - Disconnessione");
	}
	
}
