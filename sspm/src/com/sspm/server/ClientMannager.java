package com.sspm.server;

import java.io.IOException;
import java.util.Vector;

public class ClientMannager {
	public static Vector<ClientLink> sockets = new Vector<>();
	
	public static void sendAll(Object object) {
        for (ClientLink socket : sockets) {
        	try {
				socket.getOos().writeObject(object);
				socket.getOos().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}
