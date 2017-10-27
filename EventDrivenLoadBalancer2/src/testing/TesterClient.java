package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * A simple client for testing plain
 * socket connections
 * 
 * arg[0] = hostname
 * args[1] = port
 */
public class TesterClient {
	public static void main(String[] args) {
		try {
			Socket s = new Socket(args[0], Integer.parseInt(args[1]));
			OutputStream os = s.getOutputStream();
			InputStream in = s.getInputStream();
			PrintStream out = new PrintStream(os);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			out.println("Hello World");
			out.flush();
			
			String reader = br.readLine();
			System.out.println(reader);
			s.close();
			s = null;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
