import java.io.Serializable;
public class Livre implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titre;
    private String auteur;
    private String ISBN;
    private boolean estReserve;

    public Livre(String titre, String auteur, String ISBN) {
        this.titre = titre;
        this.auteur = auteur;
        this.ISBN = ISBN;
        this.estReserve = false;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isReserve() {
        return estReserve;
    }

    public void reserver() {
        if (!estReserve) {
            estReserve = true;
            System.out.println("Le livre a été réservé.");
        } else {
            System.out.println("Ce livre est déjà réservé.");
        }
    }

    public void setReserve(boolean estReserve) {
        this.estReserve = estReserve;
    }
}
