import java.io.Serializable;
import java.time.LocalDate;

public class Livre implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titre;
    private String auteur;
    private String edition;
    private int anneeParution;
    private String genre;
    private String emplacement;
    private boolean estReserve = false;
    private LocalDate dateFinReservation;
    private Utilisateur reservedBy;

    public Livre(String titre, String auteur, String edition, int anneeParution, String genre, String emplacement) {
        this.titre = titre;
        this.auteur = auteur;
        this.edition = edition;
        this.anneeParution = anneeParution;
        this.genre = genre;
        this.emplacement = emplacement;
    }

    // Getters et Setters
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getAnneeParution() {
        return anneeParution;
    }

    public void setAnneeParution(int anneeParution) {
        this.anneeParution = anneeParution;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public boolean isReserve() {
        return estReserve;
    }

    public void reserver(Utilisateur utilisateur) {
        if (!estReserve) {
            estReserve = true;
            reservedBy = utilisateur;  // Enregistrement de l'utilisateur qui a réservé le livre
            dateFinReservation = LocalDate.now().plusWeeks(3);  // Réserver pour 3 semaines
            System.out.println("Le livre a été réservé jusqu'au " + dateFinReservation + " par " + utilisateur.getNom());
        } else {
            System.out.println("Ce livre est déjà réservé jusqu'au " + dateFinReservation + " par " + reservedBy.getNom());
        }
    }
    

    public Utilisateur getReservedBy() {
        return reservedBy;
    }

    public LocalDate getDateFinReservation() {
        return dateFinReservation;
    }

    public void setReserve(boolean estReserve) {
        this.estReserve = estReserve;
    }
}
