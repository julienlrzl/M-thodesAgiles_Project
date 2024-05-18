import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TerminalUtilisateur {
    private Gestionnaire gestionnaire;
    private Utilisateur utilisateurCourant;

    public TerminalUtilisateur(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public void lancerInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans le système de gestion de la bibliothèque!");
        System.out.println("Veuillez entrer votre ID utilisateur pour vous connecter:");
        String userId = scanner.nextLine();
        connecterUtilisateur(userId);

        boolean continuer = true;
        while (continuer) {
            System.out.println("\nQue souhaitez-vous faire?");
            System.out.println("1. Rechercher un livre");
            System.out.println("2. Réserver un livre");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option: ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    rechercherLivres(scanner);
                    break;
                case "2":
                    reserverLivre(scanner);
                    break;
                case "3":
                    continuer = false;
                    break;
                default:
                    System.out.println("Option non reconnue, veuillez réessayer.");
                    break;
            }
        }
        scanner.close();
    }

    private void connecterUtilisateur(String userId) {
        // Simulation simple: création d'un nouvel utilisateur avec l'ID fourni.
        utilisateurCourant = new Utilisateur("Nom_" + userId, "Prenom_" + userId, userId);
        System.out.println("Utilisateur connecté: " + utilisateurCourant.getNom());
    }

    private void rechercherLivres(Scanner scanner) {
        System.out.println("Entrez un critère de recherche (titre ou auteur):");
        String critere = scanner.nextLine();
        List<Livre> resultats = gestionnaire.rechercherLivres(critere);
        if (resultats.isEmpty()) {
            System.out.println("Aucun livre trouvé.");
        } else {
            System.out.println("Livres trouvés:");
            resultats.stream()
                .sorted(Comparator.comparing(Livre::getTitre))
                .forEach(livre -> System.out.println(livre.getTitre() + " par " + livre.getAuteur() + " - " + (livre.isReserve() ? "Réservé" : "Disponible")));
        }
    }

    private void reserverLivre(Scanner scanner) {
        System.out.println("Entrez l'ISBN du livre que vous souhaitez réserver:");
        String isbn = scanner.nextLine();
        List<Livre> livres = gestionnaire.getLivres();
        Livre livre = livres.stream()
                            .filter(l -> l.getISBN().equals(isbn))
                            .findFirst()
                            .orElse(null);
        if (livre != null && !livre.isReserve()) {
            livre.reserver();
            System.out.println("Vous avez réservé le livre: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé ou déjà réservé.");
        }
    }
}
