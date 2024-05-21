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
        // Ajout de quelques livres pour tester1
        gestionnaire.ajouterLivre(new Livre("Les Misérables", "Victor Hugo", "Folio", 1862, "Roman", "A1"));
        gestionnaire.ajouterLivre(new Livre("Vieller sur elle", "Jean-Baptiste Andrea", "L'iconoclaste", 2023, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Des diables et des saints", "Jean-Baptiste Andrea", "Collection proche", 2022, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Psychopompe", "Amélie Nothomb", "Albin Michel", 2023, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Changer l'eau des fleurs", "Valérie Perrin", "Albin Michel", 2018, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Les oubliés du dimanche", "Valérie Perrin", "Le livre de poche", 2017, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Au revoir de là-haut", "Pierre Lemaître", "Le livre de poche", 2015, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Les yeux de Mona", "Thomas Schlesser", "Albin Michel", 2024, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Ce qu'il reste à faire", "Marie de Chassey", "Alma Editeur", 2023, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Les tourmentés", "Lucas Belvaux", "Alma Editeur", 2022, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Sur la plage", "Juliette Willerval", "Alma Editeur", 2024, "Littérature", "A1"));
        gestionnaire.ajouterLivre(new Livre("Oskar et le comte", "Jean-Baptiste Drouot", "Les fourmis rouges", 2024, "Jeunesse", "A2"));
        gestionnaire.ajouterLivre(new Livre("Rendez-vous à la piscine", "Jean-Baptiste Drouot", "Helium", 2023, "Jeunesse", "A2"));
        gestionnaire.ajouterLivre(new Livre("J'ai oublié mon exposé parce que …", "D Cali, B Chaud", "Helium", 2024, "Jeunesse", "A2"));
        gestionnaire.ajouterLivre(new Livre("Sois jeune et tais toi: réponse à ceux qui critiquent la jeunesse", "Salomé Saqué", "Payot", 2024, "SHS", "A3"));
        gestionnaire.ajouterLivre(new Livre("Le muguet rouge", "Christian Bobin", "Folio", 2024, "Poésie", "A4"));
        gestionnaire.ajouterLivre(new Livre("Paroles", "Jacques Prévert", "Folio", 1976, "Poésie", "A4"));
        gestionnaire.ajouterLivre(new Livre("L'Ame du monde - 1", "Frédéric Lenoir", "Poket", 2014, "Conte philisophique", "A5"));
        gestionnaire.ajouterLivre(new Livre("L'Ame du monde - 2", "Frédéric Lenoir", "Poket", 2023, "Conte philisophique", "A5"));
        gestionnaire.ajouterLivre(new Livre("Les quatre accords toltèques", "Miguel Ruiz", "Poche Jouvence", 2016, "Développement personnel", "A6"));
        gestionnaire.ajouterLivre(new Livre("Jung. Un voyage vers soi", "Frédéric Lenoir", "Le livre de poche", 2023, "SHS", "A3"));

        // Ajout de quelques utilisateurs pour tester
        gestionnaire.inscrireUtilisateur(new Utilisateur("Alice", "Smith", "U001"));
        gestionnaire.inscrireUtilisateur(new Utilisateur("Bob", "Jones", "U002"));
    }
}
