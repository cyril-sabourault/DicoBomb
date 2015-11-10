package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Client {
	public static ClientRMIServer crmis;
	public static ClientRMIClient crmic;
	public static String myusername = null;
	public static String mygame = null;

	public Client(String ip,Integer port) {
		newServer(port);
		newClient(ip);
	}

	public void newServer(Integer port) {
		try {
			SecurityManager security = System.getSecurityManager();
			System.setProperty("java.rmi.server.hostname", "127.0.0.1");
			if (security != null) {
				System.setSecurityManager(new java.lang.SecurityManager());
			}
			LocateRegistry.createRegistry(port);
			crmis = new ClientRMIServer();
			Naming.rebind("Dicobomb_client", crmis);
			System.out.println("Serveur pret.");
		} catch (Exception e) {
			System.out.println("Erreur serveur : " + e);
		}
	}

	public void newClient(String ip) {
		crmic = new ClientRMIClient(ip);
	}

	public static String getMyUsername(){
		return Client.myusername;
	}
	
	public static void client1() {
		try {
			Client.crmic.newUser("benoit");
			Client.crmic.newGame("Partie1", "Dico", "benoit");
			Client.crmic.joinGame("benoit", "Partie1");
			Client.myusername = "benoit";
			Client.crmic.quitGame("benoit", "Partie1");

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void client2() {
		try {
			Client.crmic.newUser("pierre");
			Client.myusername = "pierre";
			Client.crmic.joinGame("pierre", "Partie1");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws RemoteException {
		Client client = new Client("127.0.0.1",1088);
		client1();
		//client2();

	}
}
