package act2_2;

import java.io.*;
import java.net.*;

public class ServeurTCPObjets {
    private static final int PORT = 12345;
    
    public static void main(String[] args) {
        try {
         
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur TCP (Objets) démarré sur le port " + PORT);
            System.out.println("En attente de connexions clients...");
            
            while (true) {
                // Attente de connexion client
                Socket clientSocket = serverSocket.accept();
                System.out.println("\nNouveau client connecté: " + clientSocket.getInetAddress());
                
                // Gestion du client dans un thread séparé
                new Thread(new ClientHandler(clientSocket)).start();
            }
            
        } catch (IOException e) {
            System.err.println("Erreur du serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (
             
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ) {
                Object receivedObject;
                
               
                while ((receivedObject = in.readObject()) != null) {
                    if (receivedObject instanceof Operation) {
                        Operation operation = (Operation) receivedObject;
                        System.out.println("Opération reçue du client: " + 
                                          operation.getOperand1() + " " + 
                                          operation.getOperator() + " " + 
                                          operation.getOperand2());
                        
                        
                        operation.calculate();
                        
                        // Envoi de l'objet résultat au client
                        out.writeObject(operation);
                        out.flush();
                        System.out.println("Résultat envoyé au client: " + operation.getResult());
                    } else if (receivedObject instanceof String) {
                        String message = (String) receivedObject;
                        if (message.equalsIgnoreCase("quit")) {
                            System.out.println("Client a demandé la déconnexion");
                            break;
                        }
                    }
                }
                
            } catch (EOFException e) {
                System.out.println("Client déconnecté");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur avec le client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Connexion avec le client fermée");
                } catch (IOException e) {
                    System.err.println("Erreur lors de la fermeture du socket: " + e.getMessage());
                }
            }
        }
    }
}