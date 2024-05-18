import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gestionnaire gestionnaire = new Gestionnaire(); // Création de l'instance du gestionnaire
        initialiserDonnees(gestionnaire); // Méthode optionnelle pour pré-remplir des données

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans le système de gestion de la bibliothèque!");
        System.out.println("Tapez '1' pour l'interface utilisateur ou '2' pour l'interface administrateur:");

        String choix = scanner.nextLine();
        if ("1".equals(choix)) {
            TerminalUtilisateur terminalUtilisateur = new TerminalUtilisateur(gestionnaire); // Création de l'instance du terminal utilisateur
            terminalUtilisateur.lancerInterface(); // Lancement de l'interface utilisateur
        } else if ("2".equals(choix)) {
            TerminalAdministrateur terminalAdministrateur = new TerminalAdministrateur(gestionnaire); // Création de l'instance du terminal administrateur
            terminalAdministrateur.lancerInterface(); // Lancement de l'interface administrateur
        } else {
            System.out.println("Choix non valide. Veuillez redémarrer l'application.");
        }
        scanner.close();
    }

    private static void initialiserDonnees(Gestionnaire gestionnaire) {
        // Ajout de quelques livres pour tester
        gestionnaire.ajouterLivre(new Livre("Les Misérables", "Victor Hugo", "12345"));
        gestionnaire.ajouterLivre(new Livre("Le Petit Prince", "Antoine de Saint-Exupéry", "12346"));
        gestionnaire.ajouterLivre(new Livre("1984", "George Orwell", "12347"));

        // Ajout de quelques utilisateurs pour tester
        gestionnaire.inscrireUtilisateur(new Utilisateur("Alice", "Smith", "U001"));
        gestionnaire.inscrireUtilisateur(new Utilisateur("Bob", "Jones", "U002"));
    }
}
