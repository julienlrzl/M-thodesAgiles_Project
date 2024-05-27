import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gestionnaire gestionnaire = new Gestionnaire(); // Création de l'instance du gestionnaire

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
}
