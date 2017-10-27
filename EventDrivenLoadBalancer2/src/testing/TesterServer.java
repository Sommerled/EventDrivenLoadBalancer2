package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * A simple service for testing
 * plain socket server connections
 *
 */
public class TesterServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
			while(true){
				Socket s = serverSocket.accept();
				OutputStream os = s.getOutputStream();
				InputStream in = s.getInputStream();
				PrintStream out = new PrintStream(os);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				String input = br.readLine();
				System.out.println("Received: " + input);
				System.out.println("sending: Hello Client from port " + args[0]);
				out.println("Hello Client from port " + args[0]);
				out.flush();
				s.close();
				s = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(serverSocket != null){
			try {
				serverSocket.close();
				serverSocket = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
