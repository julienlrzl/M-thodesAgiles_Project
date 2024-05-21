import java.util.List;
import java.util.stream.Collectors;

public class Gestionnaire {
    private List<Livre> livres;
    private List<Utilisateur> utilisateurs;

    public Gestionnaire() {
        // Charger les données au démarrage
        this.livres = DataManager.chargerLivres();
        this.utilisateurs = DataManager.chargerUtilisateurs();
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

    public List<Livre> rechercherLivres(String critere) {
        return livres.stream()
            .filter(livre -> livre.getTitre().contains(critere) || livre.getAuteur().contains(critere))
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

    public void reserverLivre(String titre, String auteur) {
        Livre livre = trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre != null && !livre.isReserve()) {
            livre.reserver();
            DataManager.sauvegarderLivres(livres);
            System.out.println("Vous avez réservé le livre: " + livre.getTitre());
        } else {
            System.out.println("Livre non trouvé ou déjà réservé.");
        }
    }

    private Livre trouverLivreParTitreEtAuteur(String titre, String auteur) {
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
}
