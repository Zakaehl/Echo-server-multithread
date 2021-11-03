package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

	private Socket server;
	private int port = 2400;
	private Scanner in;
	private PrintWriter out;
	private Scanner stdin;
	
	private void start() {
		try {
			server = new Socket(InetAddress.getLocalHost(), port);
			System.out.println("Connessione con il server stabilita");
			
			in = new Scanner(server.getInputStream());
			out = new PrintWriter(server.getOutputStream());
			stdin = new Scanner(System.in);
			System.out.println("Gestiti gli stream");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
	     	while (true) {
	     		System.out.print("Invia un messaggio: ");
		    	String msg_out = stdin.nextLine();
		    	System.out.println("Messaggio inviato");
		    	out.println(msg_out);
		    	out.flush();
		    	
		    	String msg_in = in.nextLine();
		    	System.out.println("Ricevuto messaggio " + msg_in);
	     	}
		} catch (NoSuchElementException e) {
			System.out.println("Connessione interrotta");
		}
		
		finally {
		    stdin.close();
		    in.close();
		    out.close();
		    try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Disconnessione");
		}
	}
	
	public static void main(String[] args) {
		Client c = new Client();
		c.start();
	}
	
}
