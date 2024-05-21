import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String LIVRE_FILE = "livres.ser";
    private static final String UTILISATEUR_FILE = "utilisateurs.ser";

    public static void sauvegarderLivres(List<Livre> livres) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LIVRE_FILE))) {
            oos.writeObject(livres);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des livres: " + e.getMessage());
        }
    }

    public static List<Livre> chargerLivres() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(LIVRE_FILE))) {
            return (List<Livre>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Fichier non trouvé, retourne une liste vide pour initialisation
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des livres: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void sauvegarderUtilisateurs(List<Utilisateur> utilisateurs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(UTILISATEUR_FILE))) {
            oos.writeObject(utilisateurs);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs: " + e.getMessage());
        }
    }

    public static List<Utilisateur> chargerUtilisateurs() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(UTILISATEUR_FILE))) {
            return (List<Utilisateur>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Fichier non trouvé, retourne une liste vide pour initialisation
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des utilisateurs: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
