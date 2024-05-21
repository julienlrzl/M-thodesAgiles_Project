import java.io.Serializable;
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nom;
    private String prenom;
    private String id;

    public Utilisateur(String nom, String prenom, String id) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getId() {
        return id;
    }
}
