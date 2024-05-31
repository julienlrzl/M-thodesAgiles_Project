import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe Gestionnaire qui gère les listes de livres et d'utilisateurs,
 * offrant des méthodes pour ajouter, rechercher, modifier, et supprimer des livres et des utilisateurs,
 * ainsi que pour valider les retours et gérer les réservations.
 */
public class Gestionnaire {
    private List<Livre> livres; // Liste des livres dans la bibliothèque
    private List<Utilisateur> utilisateurs; // Liste des utilisateurs de la bibliothèque

    /**
     * Constructeur qui initialise le gestionnaire en chargeant les livres et les utilisateurs existants
     * depuis la base de données.
     */
    public Gestionnaire() {
        livres = DataManager.chargerLivres();
        utilisateurs = DataManager.chargerUtilisateurs();
    }

    /**
     * Ajoute un livre à la liste et sauvegarde la modification.
     *
     * @param livre Le livre à ajouter.
     */
    public void ajouterLivre(Livre livre) {
        livres.add(livre);
        DataManager.sauvegarderLivres(livres);
        System.out.println("Livre ajouté: " + livre.getTitre());
    }

    /**
     * Inscrit un nouvel utilisateur et sauvegarde la modification.
     *
     * @param utilisateur L'utilisateur à inscrire.
     */
    public void inscrireUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
        DataManager.sauvegarderUtilisateurs(utilisateurs);
        System.out.println("Utilisateur inscrit: " + utilisateur.getNom());
    }

    /**
     * Recherche des livres correspondant aux critères de titre et d'auteur.
     *
     * @param titreCritere Critère de recherche sur le titre.
     * @param auteurCritere Critère de recherche sur l'auteur.
     * @return Une liste de livres correspondant aux critères.
     */
    public List<Livre> rechercherLivres(String titreCritere, String auteurCritere) {
        return livres.stream()
            .filter(livre -> livre.getTitre().toLowerCase().contains(titreCritere.toLowerCase()) &&
                             livre.getAuteur().toLowerCase().contains(auteurCritere.toLowerCase()))
            .collect(Collectors.toList());
    }

    /**
     * Affiche les informations de tous les livres.
     */
    public void afficherLivres() {
        for (Livre livre : livres) {
            System.out.println(livre.getTitre() + " par " + livre.getAuteur() + " - " + (livre.isReserve() ? "Réservé" : "Disponible"));
        }
    }

    /**
     * Modifie les informations d'un livre spécifique et sauvegarde les modifications.
     *
     * @param titre L'ancien titre du livre pour identification.
     * @param auteur L'ancien auteur du livre pour identification.
     * @param nouveauLivre Les nouvelles informations du livre.
     */
    public void modifierLivre(String titre, String auteur, Livre nouveauLivre) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null) {
            livre.setTitre(nouveauLivre.getTitre());
            livre.setAuteur(nouveauLivre.getAuteur());
            livre.setEdition(nouveauLivre.getEdition());
            livre.setAnneeParution(nouveauLivre.getAnneeParution());
            livre.setGenre(nouveauLivre.getGenre());
            livre.setEmplacement(nouveauLivre.getEmplacement());
            DataManager.sauvegarderLivres(livres);
            System.out.println("Livre modifié: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé.");
        }
    }

    /**
     * Valide le retour d'un livre réservé.
     *
     * @param titre Titre du livre.
     * @param auteur Auteur du livre.
     */
    public void validerRetour(String titre, String auteur) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null && livre.isReserve()) {
            livre.setReserve(false);
            DataManager.sauvegarderLivres(livres);
            System.out.println("Retour validé pour le livre: " + livre.getTitre());
        } else {
            System.out.println("Ce livre n'était pas réservé.");
        }
    }

    /**
     * Réserve un livre pour un utilisateur donné et sauvegarde la modification.
     *
     * @param titre Titre du livre à réserver.
     * @param auteur Auteur du livre à réserver.
     * @param utilisateur Utilisateur qui réserve le livre.
     */
    public void reserverLivre(String titre, String auteur, Utilisateur utilisateur) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null && !livre.isReserve()) {
            livre.reserver(utilisateur);
            DataManager.sauvegarderLivres(livres);
            System.out.println("Vous avez réservé le livre: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé ou déjà réservé.");
        }
    }

    /**
     * Trouve un livre par son titre et auteur.
     *
     * @param titre Titre du livre.
     * @param auteur Auteur du livre.
     * @return Le livre correspondant ou null s'il n'est pas trouvé.
     */
    public Livre trouverLivreParTitreEtAuteur(String titre, String auteur) {
        return livres.stream()
            .filter(livre -> livre.getTitre().equals(titre) && livre.getAuteur().equals(auteur))
            .findFirst()
            .orElse(null);
    }

    /**
     * Retourne la liste des livres gérés par le gestionnaire.
     *
     * @return La liste des livres.
     */
    public List<Livre> getLivres() {
        return livres;
    }

    /**
     * Retourne la liste des utilisateurs gérés par le gestionnaire.
     *
     * @return La liste des utilisateurs.
     */
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    /**
     * Sauvegarde toutes les modifications apportées aux livres et aux utilisateurs.
     */
    public void sauvegarderModifications() {
        DataManager.sauvegarderLivres(livres);
        DataManager.sauvegarderUtilisateurs(utilisateurs);
    }

    /**
     * Modifie la date de fin de réservation d'un livre et sauvegarde la modification.
     *
     * @param titre Titre du livre dont la date de réservation doit être modifiée.
     * @param auteur Auteur du livre dont la date de réservation doit être modifiée.
     * @param nouvelleDate La nouvelle date de fin de réservation.
     */
    public void modifierDateFinReservation(String titre, String auteur, LocalDate nouvelleDate) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null && livre.isReserve()) {
            livre.setDateFinReservation(nouvelleDate);
            DataManager.sauvegarderLivres(livres);
            System.out.println("Date de fin de réservation modifiée pour le livre: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé ou non réservé.");
        }
    }

    /**
     * Supprime un livre de la liste des livres gérés et sauvegarde la modification.
     *
     * @param titre Titre du livre à supprimer.
     * @param auteur Auteur du livre à supprimer.
     */
    public void supprimerLivre(String titre, String auteur) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null) {
            livres.remove(livre);
            DataManager.sauvegarderLivres(livres);
            System.out.println("Livre supprimé: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé.");
        }
    }
}
