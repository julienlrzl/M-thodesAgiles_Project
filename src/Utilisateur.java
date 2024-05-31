import java.io.Serializable;

/**
 * Classe Utilisateur représentant un utilisateur du système de gestion de la bibliothèque.
 * Elle implémente l'interface Serializable pour permettre la sérialisation des objets Utilisateur.
 */
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;  // UID de sérialisation pour assurer la compatibilité.

    private String nom;       // Nom de l'utilisateur
    private String prenom;    // Prénom de l'utilisateur
    private String id;        // Identifiant unique de l'utilisateur

    /**
     * Constructeur pour créer un nouveau utilisateur avec un nom, un prénom, et un identifiant.
     * @param nom Le nom de famille de l'utilisateur.
     * @param prenom Le prénom de l'utilisateur.
     * @param id L'identifiant unique de l'utilisateur.
     */
    public Utilisateur(String nom, String prenom, String id) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
    }

    /**
     * Retourne le nom de l'utilisateur.
     * @return Le nom de l'utilisateur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le prénom de l'utilisateur.
     * @return Le prénom de l'utilisateur.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Retourne l'identifiant unique de l'utilisateur.
     * @return L'identifiant de l'utilisateur.
     */
    public String getId() {
        return id;
    }
}
