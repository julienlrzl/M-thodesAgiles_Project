import java.util.Scanner;

/**
 * Classe principale qui lance l'application de gestion de bibliothèque.
 * Cette classe offre un menu permettant de choisir entre l'interface utilisateur
 * et l'interface administrateur, pour gérer les livres et les utilisateurs.
 */
public class Main {
    /**
     * Méthode principale qui sert de point d'entrée pour l'application.
     * Elle offre un choix entre l'interface utilisateur et l'interface administrateur.
     *
     * @param args Les arguments de ligne de commande, non utilisés dans cette application.
     */
    public static void main(String[] args) {
        Gestionnaire gestionnaire = new Gestionnaire(); // Création de l'instance du gestionnaire

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Bienvenue dans le système de gestion de la bibliothèque!");
            System.out.println("Tapez '1' pour l'interface utilisateur ou '2' pour l'interface administrateur:");

            String choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    // Création et lancement de l'interface utilisateur
                    TerminalUtilisateur terminalUtilisateur = new TerminalUtilisateur(gestionnaire);
                    terminalUtilisateur.lancerInterface();
                    break;
                case "2":
                    // Création et lancement de l'interface administrateur
                    TerminalAdministrateur terminalAdministrateur = new TerminalAdministrateur(gestionnaire);
                    terminalAdministrateur.lancerInterface();
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez redémarrer l'application.");
                    break;
            }
        } // Le scanner est automatiquement fermé ici grâce au try-with-resources
    }
}
