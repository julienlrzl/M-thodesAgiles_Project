import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Gestionnaire {
    private List<Livre> livres;
    private List<Utilisateur> utilisateurs;

    public Gestionnaire() {
        livres = DataManager.chargerLivres();
        utilisateurs = DataManager.chargerUtilisateurs();
    }

    public void ajouterLivre(Livre livre) {
        livres.add(livre);
        DataManager.sauvegarderLivres(livres);
        System.out.println("Livre ajouté: " + livre.getTitre());
    }

    public void inscrireUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
        DataManager.sauvegarderUtilisateurs(utilisateurs);
        System.out.println("Utilisateur inscrit: " + utilisateur.getNom());
    }

    public List<Livre> rechercherLivres(String titreCritere, String auteurCritere) {
        return livres.stream()
            .filter(livre -> livre.getTitre().toLowerCase().contains(titreCritere.toLowerCase()) &&
                             livre.getAuteur().toLowerCase().contains(auteurCritere.toLowerCase()))
            .collect(Collectors.toList());
    }

    public void afficherLivres() {
        for (Livre livre : livres) {
            System.out.println(livre.getTitre() + " par " + livre.getAuteur() + " - " + (livre.isReserve() ? "Réservé" : "Disponible"));
        }
    }

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

    public void validerRetour(String titre, String auteur) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null) {
            if (livre.isReserve()) {
                livre.setReserve(false);
                DataManager.sauvegarderLivres(livres);
                System.out.println("Retour validé pour le livre: " + livre.getTitre());
            } else {
                System.out.println("Ce livre n'était pas réservé.");
            }
        } else {
            System.out.println("Livre non trouvé.");
        }
    }

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
    

    public Livre trouverLivreParTitreEtAuteur(String titre, String auteur) {
        return livres.stream()
            .filter(livre -> livre.getTitre().equals(titre) && livre.getAuteur().equals(auteur))
            .findFirst()
            .orElse(null);
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void sauvegarderModifications() {
        DataManager.sauvegarderLivres(livres);
        DataManager.sauvegarderUtilisateurs(utilisateurs);
    }

    public void modifierDateFinReservation(String titre, String auteur, LocalDate nouvelleDate) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null && livre.isReserve()) {
            livre.setDateFinReservation(nouvelleDate);
            DataManager.sauvegarderLivres(livres); // Sauvegarder les modifications
            System.out.println("Date de fin de réservation modifiée pour le livre: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé ou non réservé.");
        }
    }

    public void supprimerLivre(String titre, String auteur) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null) {
            livres.remove(livre);
            DataManager.sauvegarderLivres(livres); // Mettre à jour la liste des livres
            System.out.println("Livre supprimé: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé.");
        }
    }
    

}
