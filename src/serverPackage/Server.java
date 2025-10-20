package serverPackage;
import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) {
		try {
         
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente de la connexion d'un client...");

            
            Socket socket = server.accept();
            System.out.println("Un client est connecté !");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

       
			String operation = in.readLine();
            System.out.println("Reçu du client : " + operation);

            String[] parts = operation.split(" ");
            if (parts.length == 3) {
                int a = Integer.parseInt(parts[0].trim());
                int b = Integer.parseInt(parts[2].trim());
                String operateur = parts[1];
                int result = 0;
                switch (operateur) {
                case "+": result =a+b;break;
                case "-": result =a-b;break;
                case "*": result =a*b;break;
                case "/": result =a/b;break;
                }
                System.out.println("Résultat = " + result);
                out.println("Résultat = " + result);
            } else {
                out.println("Format d’opération invalide !");
            }

            in.close();
            out.close();
            socket.close();
            server.close();
            System.out.println("Connexion terminée. Serveur fermé.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

}
