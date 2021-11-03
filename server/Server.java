package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server implements Runnable {

	private ServerSocket server;
	private Set<ClientThread> clients = new HashSet<ClientThread>();
	private int port = 2400;
		
	public void run() {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server collegato alla porta " + port);
		
		Socket client = null;
		int n = 0;
		while (true) {
			try {
				client = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Connessione con un client stabilita");
			
			ClientThread runnable = new ClientThread(client,n);
			Thread thread = new Thread(runnable);
			clients.add(runnable);
			thread.start();
			System.out.println("Inizializzato thread " + n);
			++n;
		}
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		Thread t = new Thread(s);
		t.start();
	}

}
