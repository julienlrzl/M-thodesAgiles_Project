import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Classe TerminalUtilisateur gère l'interface utilisateur pour interagir avec le système de gestion de la bibliothèque.
 */
public class TerminalUtilisateur {
    private Gestionnaire gestionnaire;
    private Utilisateur utilisateurCourant;

    /**
     * Constructeur qui initialise le gestionnaire avec lequel interagir.
     * @param gestionnaire Le gestionnaire utilisé pour les opérations sur les livres et utilisateurs.
     */
    public TerminalUtilisateur(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    /**
     * Lance l'interface de ligne de commande pour l'utilisateur, permettant diverses opérations telles que la connexion, la recherche de livres,
     * la réservation de livres, et la consultation de tous les livres disponibles.
     */
    public void lancerInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans le système de gestion de la bibliothèque!");
        System.out.println("Veuillez entrer votre ID utilisateur pour vous connecter:");
        String userId = scanner.nextLine();
        connecterUtilisateur(userId);

        boolean continuer = true;
        while (continuer) {
            System.out.println("\nQue souhaitez-vous faire?");
            System.out.println("1. Tous les livres");
            System.out.println("2. Rechercher un livre");
            System.out.println("3. Réserver un livre");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option: ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    afficherTousLesLivres();
                    break;
                case "2":
                    rechercherLivres(scanner);
                    break;
                case "3":
                    reserverLivre(scanner);
                    break;
                case "4":
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
     * Connecte l'utilisateur au système en utilisant son ID. Si l'utilisateur n'est pas trouvé, il sera connecté comme invité.
     * @param userId L'ID de l'utilisateur pour la connexion.
     */
    private void connecterUtilisateur(String userId) {
        utilisateurCourant = gestionnaire.getUtilisateurs().stream()
            .filter(u -> u.getId().equals(userId))
            .findFirst()
            .orElse(null);
        if (utilisateurCourant != null) {
            System.out.println("Utilisateur connecté: " + utilisateurCourant.getNom());
        } else {
            System.out.println("Utilisateur non trouvé, connexion en tant qu'invité.");
            utilisateurCourant = new Utilisateur("Invité", "", userId); // Connexion en tant qu'invité
        }
    }

    /**
     * Recherche des livres basée sur un critère de recherche entré par l'utilisateur.
     * @param scanner L'outil de saisie pour entrer les critères de recherche.
     */
    private void rechercherLivres(Scanner scanner) {
        System.out.println("Entrez un critère de recherche (titre ou auteur):");
        String critere = scanner.nextLine();
        List<Livre> resultats = gestionnaire.rechercherLivres(critere, critere);
        if (resultats.isEmpty()) {
            System.out.println("Aucun livre trouvé.");
        } else {
            System.out.println("Livres trouvés:");
            resultats.stream()
                .sorted(Comparator.comparing(Livre::getTitre))
                .forEach(livre -> System.out.println(livre.getTitre() + " par " + livre.getAuteur() + " - " + (livre.isReserve() ? "Réservé" : "Disponible")));
        }
    }

    /**
     * Permet à l'utilisateur connecté de réserver un livre.
     * @param scanner L'outil de saisie pour entrer le titre et l'auteur du livre à réserver.
     */
    private void reserverLivre(Scanner scanner) {
        System.out.println("Entrez le titre du livre à réserver:");
        String titre = scanner.nextLine();
        System.out.println("Entrez l'auteur du livre à réserver:");
        String auteur = scanner.nextLine();
        if (utilisateurCourant != null) {
            gestionnaire.reserverLivre(titre, auteur, utilisateurCourant);
        } else {
            System.out.println("Aucun utilisateur connecté pour effectuer cette réservation.");
        }
    }
    
    /**
     * Affiche tous les livres disponibles dans la bibliothèque.
     */
    private void afficherTousLesLivres() {
        System.out.println("Liste de tous les livres disponibles:");
        gestionnaire.afficherLivres();
    }

    /**
     * Retourne l'utilisateur actuellement connecté.
     * @return L'utilisateur connecté.
     */
    public Utilisateur getUtilisateurCourant() {
        return utilisateurCourant;
    }
}
