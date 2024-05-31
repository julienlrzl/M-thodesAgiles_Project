import java.time.LocalDate;
import java.util.Scanner;

/**
 * Classe TerminalAdministrateur gère l'interface de ligne de commande pour les opérations administratives
 * dans le système de gestion de bibliothèque.
 */
public class TerminalAdministrateur {
    private Gestionnaire gestionnaire;

    /**
     * Constructeur qui initialise le gestionnaire.
     * @param gestionnaire Le gestionnaire utilisé pour opérer sur les données de livres et d'utilisateurs.
     */
    public TerminalAdministrateur(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    /**
     * Lance l'interface de ligne de commande pour l'administration.
     * Permet de créer des comptes utilisateurs, consulter les réservations, valider les retours de livres,
     * ajouter ou modifier des livres.
     */
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

    /**
     * Crée un compte utilisateur en demandant le nom, le prénom, et l'ID de l'utilisateur.
     * @param scanner L'outil de saisie pour entrer les informations utilisateur.
     */
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

    /**
     * Consulte et affiche les réservations actuelles, y compris les détails des livres et les jours restants
     * avant la fin de la réservation.
     */
    private void consulterReservations() {
        System.out.println("Liste des livres réservés:");
        gestionnaire.getLivres().stream()
            .filter(Livre::isReserve)
            .forEach(livre -> {
                LocalDate now = LocalDate.now();
                long joursRestants = livre.getDateFinReservation().toEpochDay() - now.toEpochDay();
                System.out.println("Livre: " + livre.getTitre() + " par " + livre.getAuteur() + ", Réservé par: " +
                                   livre.getReservedBy().getNom() + ", Jours restants: " + joursRestants);
            });
    }

    /**
     * Valide le retour d'un livre réservé en demandant son titre et son auteur.
     * @param scanner L'outil de saisie pour entrer les informations du livre.
     */
    private void validerRetourLivre(Scanner scanner) {
        System.out.println("Entrer le titre du livre à valider:");
        String titre = scanner.nextLine();
        System.out.println("Entrer l'auteur du livre à valider:");
        String auteur = scanner.nextLine();
        gestionnaire.validerRetour(titre, auteur);
    }

    /**
     * Ajoute un livre au système en demandant les informations nécessaires comme le titre, l'auteur,
     * l'édition, l'année de parution, le genre, et l'emplacement.
     * @param scanner L'outil de saisie pour entrer les informations du livre.
     */
    private void ajouterLivre(Scanner scanner) {
        System.out.println("Entrer le titre du livre:");
        String titre = scanner.nextLine();
        System.out.println("Entrer l'auteur du livre:");
        String auteur = scanner.nextLine();
        System.out.println("Entrer l'édition du livre:");
        String edition = scanner.nextLine();
        System.out.println("Entrer l'année de parution du livre:");
        int anneeParution = Integer.parseInt(scanner.nextLine());
        System.out.println("Entrer le genre du livre:");
        String genre = scanner.nextLine();
        System.out.println("Entrer l'emplacement du livre:");
        String emplacement = scanner.nextLine();
        Livre livre = new Livre(titre, auteur, edition, anneeParution, genre, emplacement);
        gestionnaire.ajouterLivre(livre);
    }

    /**
     * Modifie les informations d'un livre existant après avoir entré son titre et auteur pour l'identification.
     * @param scanner L'outil de saisie pour modifier les informations du livre.
     */
    private void modifierLivre(Scanner scanner) {
        System.out.println("Entrer le titre du livre à modifier:");
        String titre = scanner.nextLine();
        System.out.println("Entrer l'auteur du livre à modifier:");
        String auteur = scanner.nextLine();
        System.out.println("Entrer le nouveau titre du livre:");
        String nouveauTitre = scanner.nextLine();
        System.out.println("Entrer le nouvel auteur du livre:");
        String nouvelAuteur = scanner.nextLine();
        System.out.println("Entrer la nouvelle édition du livre:");
        String nouvelleEdition = scanner.nextLine();
        System.out.println("Entrer la nouvelle année de parution du livre:");
        int nouvelleAnneeParution = Integer.parseInt(scanner.nextLine());
        System.out.println("Entrer le nouveau genre du livre:");
        String nouveauGenre = scanner.nextLine();
        System.out.println("Entrer le nouvel emplacement du livre:");
        String nouvelEmplacement = scanner.nextLine();
        Livre nouveauLivre = new Livre(nouveauTitre, nouvelAuteur, nouvelleEdition, nouvelleAnneeParution, nouveauGenre, nouvelEmplacement);
        gestionnaire.modifierLivre(titre, auteur, nouveauLivre);
    }
}
