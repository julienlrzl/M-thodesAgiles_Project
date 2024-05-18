import java.util.Scanner;

public class TerminalAdministrateur {
    private Gestionnaire gestionnaire;

    public TerminalAdministrateur(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public void lancerInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Interface Administrateur de la Bibliothèque");

        boolean continuer = true;
        while (continuer) {
            System.out.println("\nQue souhaitez-vous faire?");
            System.out.println("1. Créer un compte utilisateur");
            System.out.println("2. Consulter les réservations");
            System.out.println("3. Valider les retours de livres");
            System.out.println("4. Ajouter un nouveau livre");
            System.out.println("5. Modifier les informations d'un livre");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option: ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    creerCompteUtilisateur(scanner);
                    break;
                case "2":
                    consulterReservations();
                    break;
                case "3":
                    validerRetourLivre(scanner);
                    break;
                case "4":
                    ajouterLivre(scanner);
                    break;
                case "5":
                    modifierLivre(scanner);
                    break;
                case "6":
                    continuer = false;
                    break;
                default:
                    System.out.println("Option non reconnue, veuillez réessayer.");
                    break;
            }
        }
        scanner.close();
    }

    private void creerCompteUtilisateur(Scanner scanner) {
        System.out.println("Entrer le nom de l'utilisateur:");
        String nom = scanner.nextLine();
        System.out.println("Entrer le prénom de l'utilisateur:");
        String prenom = scanner.nextLine();
        System.out.println("Entrer l'ID de l'utilisateur:");
        String id = scanner.nextLine();
        gestionnaire.inscrireUtilisateur(new Utilisateur(nom, prenom, id));
        System.out.println("Utilisateur créé avec succès.");
    }

    private void consulterReservations() {
        System.out.println("Liste des livres réservés:");
        gestionnaire.getLivres().stream()
            .filter(Livre::isReserve)
            .forEach(livre -> System.out.println(livre.getTitre() + " par " + livre.getAuteur() + " - ISBN: " + livre.getISBN()));
    }

    private void validerRetourLivre(Scanner scanner) {
        System.out.println("Entrer l'ISBN du livre retourné:");
        String isbn = scanner.nextLine();
        gestionnaire.validerRetour(isbn);
    }

    private void ajouterLivre(Scanner scanner) {
        System.out.println("Entrer le titre du livre:");
        String titre = scanner.nextLine();
        System.out.println("Entrer l'auteur du livre:");
        String auteur = scanner.nextLine();
        System.out.println("Entrer l'ISBN du livre:");
        String isbn = scanner.nextLine();
        gestionnaire.ajouterLivre(new Livre(titre, auteur, isbn));
        System.out.println("Livre ajouté avec succès.");
    }

    private void modifierLivre(Scanner scanner) {
        System.out.println("Entrer l'ISBN du livre à modifier:");
        String isbn = scanner.nextLine();
        System.out.println("Entrer le nouveau titre:");
        String nouveauTitre = scanner.nextLine();
        System.out.println("Entrer le nouvel auteur:");
        String nouvelAuteur = scanner.nextLine();
        gestionnaire.modifierLivre(isbn, nouveauTitre, nouvelAuteur);
        System.out.println("Livre modifié avec succès.");
    }
}
