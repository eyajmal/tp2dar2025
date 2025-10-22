package act2_2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientTCPObjets {
    private static final String SERVEUR_HOST = "localhost";
    private static final int SERVEUR_PORT = 12345;
    
    public static void main(String[] args) {
        try (
           
            Socket socket = new Socket(SERVEUR_HOST, SERVEUR_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());    
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("Connecté au serveur " + SERVEUR_HOST + ":" + SERVEUR_PORT);
            System.out.println("Tapez 'quit' pour quitter");
            System.out.println("Format des opérations: nombre opérateur nombre");
            System.out.println("Exemples: 34 * 55, 100 - 25, 15 / 3");
            System.out.println("Opérateurs supportés: +, -, *, /");
            System.out.println("----------------------------------------");
            
            while (true) {
                // Saisie de l'utilisateur
                System.out.print("Entrez une opération: ");
                String input = scanner.nextLine();
                
                // Vérification pour quitter
                if (input.equalsIgnoreCase("quit")) {
                    out.writeObject("quit");
                    out.flush();
                    System.out.println("Déconnexion...");
                    break;
                }
                
                // Validation et parsing de l'opération
                try {
                    Operation operation = parseOperation(input);
                    if (operation != null) {
                        // Envoi de l'objet au serveur
                        out.writeObject(operation);
                        out.flush();
                        
                        // Réception de l'objet résultat
                        Object response = in.readObject();
                        if (response instanceof Operation) {
                            Operation resultOperation = (Operation) response;
                            System.out.println("Résultat: " + resultOperation);
                        }
                    } else {
                        System.out.println("Format invalide. Utilisez: nombre opérateur nombre");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erreur: Veuillez entrer des nombres valides");
                }
                
                System.out.println("----------------------------------------");
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu: " + SERVEUR_HOST);
        } catch (ConnectException e) {
            System.err.println("Impossible de se connecter au serveur. Vérifiez qu'il est démarré.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur E/S: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Client terminé.");
    }
    
   
    private static Operation parseOperation(String input) {
        try {
            
            String cleanedInput = input.trim().replaceAll("\\s+", " ");
            String[] parts = cleanedInput.split(" ");
            
            if (parts.length != 3) {
                return null;
            }
            
            double operand1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double operand2 = Double.parseDouble(parts[2]);
            
            // Validation de l'opérateur
            if (!operator.equals("+") && !operator.equals("-") && 
                !operator.equals("*") && !operator.equals("/")) {
                return null;
            }
            
            return new Operation(operand1, operator, operand2);
            
        } catch (Exception e) {
            return null;
        }
    }
}