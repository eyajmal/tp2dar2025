package clientPackage;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			System.out.println("Je suis un client pas encore connecté…");
			Socket socket = new Socket("localhost",1234);
			
			System.out.println("je suis un client connecté");
			
			Scanner scanner = new Scanner(System.in);
			System.out.print("Entrez une opération (ex: 34 * 55) : ");
	        String operation = scanner.nextLine();
			
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
            out.println(operation);

            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response);

            in.close();
            out.close();
            socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}

	}

}
