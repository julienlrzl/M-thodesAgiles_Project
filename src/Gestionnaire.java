import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Gestionnaire {
    private List<Livre> livres = new ArrayList<>();
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public void ajouterLivre(Livre livre) {
        livres.add(livre);
        System.out.println("Livre ajouté: " + livre.getTitre());
    }

    public void inscrireUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
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

    public void modifierLivre(String ISBN, String nouveauTitre, String nouvelAuteur) {
        for (Livre livre : livres) {
            if (livre.getISBN().equals(ISBN)) {
                livre.setTitre(nouveauTitre);
                livre.setAuteur(nouvelAuteur);
                System.out.println("Livre modifié: " + livre.getTitre());
                break;
            }
        }
    }

    public void validerRetour(String ISBN) {
        for (Livre livre : livres) {
            if (livre.getISBN().equals(ISBN)) {
                if (livre.isReserve()) {
                    livre.setReserve(false);
                    System.out.println("Retour validé pour le livre: " + livre.getTitre());
                } else {
                    System.out.println("Ce livre n'était pas réservé.");
                }
                break;
            }
        }
    }
}
