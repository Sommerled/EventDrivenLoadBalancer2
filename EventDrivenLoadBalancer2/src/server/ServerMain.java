package server;

import context.ContextLoader;

public class ServerMain {

	public static void main(String[] args) {
		String configFile = "";
		String keystorePath = "";
		
		if(args.length > 0){
			configFile = args[0];
		}else{
			configFile = System.getProperty("user.home") + "\\AppData\\Roaming\\EDLB\\Connections.xml";
		}
		
		if(args.length > 1){
			keystorePath = args[1];
		}else{
			keystorePath = System.getProperty("user.home") + "\\AppData\\Roaming\\EDLB\\keystores";
		}
		
		try {
			ContextLoader.loadContexts(configFile, keystorePath);
			new Server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
