import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe représentant un livre dans une bibliothèque, avec des détails sur le livre et son état de réservation.
 */
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

    /**
     * Constructeur pour créer un nouveau livre avec tous les détails nécessaires.
     *
     * @param titre Le titre du livre.
     * @param auteur L'auteur du livre.
     * @param edition L'édition du livre.
     * @param anneeParution L'année de parution du livre.
     * @param genre Le genre du livre.
     * @param emplacement L'emplacement du livre dans la bibliothèque.
     */
    public Livre(String titre, String auteur, String edition, int anneeParution, String genre, String emplacement) {
        this.titre = titre;
        this.auteur = auteur;
        this.edition = edition;
        this.anneeParution = anneeParution;
        this.genre = genre;
        this.emplacement = emplacement;
    }

    // Getters et Setters

    /**
     * Retourne le titre du livre.
     * @return Le titre du livre.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Modifie le titre du livre.
     * @param titre Le nouveau titre du livre.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne le nom de l'auteur du livre.
     * @return L'auteur du livre.
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * Modifie l'auteur du livre.
     * @param auteur Le nouvel auteur du livre.
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * Retourne l'édition du livre.
     * @return L'édition du livre.
     */
    public String getEdition() {
        return edition;
    }

    /**
     * Modifie l'édition du livre.
     * @param edition La nouvelle édition du livre.
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }

    /**
     * Retourne l'année de parution du livre.
     * @return L'année de parution du livre.
     */
    public int getAnneeParution() {
        return anneeParution;
    }

    /**
     * Modifie l'année de parution du livre.
     * @param anneeParution La nouvelle année de parution du livre.
     */
    public void setAnneeParution(int anneeParution) {
        this.anneeParution = anneeParution;
    }

    /**
     * Retourne le genre du livre.
     * @return Le genre du livre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Modifie le genre du livre.
     * @param genre Le nouveau genre du livre.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Retourne l'emplacement du livre dans la bibliothèque.
     * @return L'emplacement du livre.
     */
    public String getEmplacement() {
        return emplacement;
    }

    /**
     * Modifie l'emplacement du livre.
     * @param emplacement Le nouvel emplacement du livre.
     */
    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    /**
     * Vérifie si le livre est actuellement réservé.
     * @return true si le livre est réservé, false sinon.
     */
    public boolean isReserve() {
        return estReserve;
    }

    /**
     * Réserve le livre pour un utilisateur, enregistrant l'utilisateur et la date de fin de réservation.
     * @param utilisateur L'utilisateur qui réserve le livre.
     */
    public void reserver(Utilisateur utilisateur) {
        if (!estReserve) {
            estReserve = true;
            reservedBy = utilisateur;
            dateFinReservation = LocalDate.now().plusWeeks(3);
            System.out.println("Le livre a été réservé jusqu'au " + dateFinReservation + " par " + utilisateur.getNom());
        } else {
            System.out.println("Ce livre est déjà réservé jusqu'au " + dateFinReservation + " par " + reservedBy.getNom());
        }
    }

    /**
     * Retourne l'utilisateur ayant réservé le livre.
     * @return L'utilisateur ayant réservé le livre.
     */
    public Utilisateur getReservedBy() {
        return reservedBy;
    }

    /**
     * Retourne la date de fin de réservation du livre.
     * @return La date à laquelle la réservation du livre prend fin.
     */
    public LocalDate getDateFinReservation() {
        return dateFinReservation;
    }

    /**
     * Modifie la date de fin de réservation du livre.
     * @param dateFinReservation La nouvelle date de fin de réservation.
     */
    public void setDateFinReservation(LocalDate dateFinReservation) {
        this.dateFinReservation = dateFinReservation;
    }

    /**
     * Modifie l'état de réservation du livre.
     * @param estReserve Nouvel état de réservation du livre.
     */
    public void setReserve(boolean estReserve) {
        this.estReserve = estReserve;
    }
}
