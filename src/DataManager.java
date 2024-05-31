import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour gérer la persistance des données des livres et des utilisateurs.
 * Elle permet de sauvegarder et de charger des objets {@link Livre} et {@link Utilisateur} dans des fichiers sérialisés.
 */
public class DataManager {
    private static final String LIVRE_FILE = "livres.ser"; // Le chemin du fichier pour sauvegarder les livres
    private static final String UTILISATEUR_FILE = "utilisateurs.ser"; // Le chemin du fichier pour sauvegarder les utilisateurs

    /**
     * Sauvegarde une liste de livres dans un fichier sérialisé.
     *
     * @param livres La liste des livres à sauvegarder.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant la sauvegarde.
     */
    public static void sauvegarderLivres(List<Livre> livres) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LIVRE_FILE))) {
            oos.writeObject(livres);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des livres: " + e.getMessage());
        }
    }

    /**
     * Charge la liste des livres à partir d'un fichier sérialisé.
     *
     * @return La liste des livres chargés.
     * @throws FileNotFoundException Si le fichier des livres n'est pas trouvé.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant le chargement.
     * @throws ClassNotFoundException Si la classe des objets sérialisés n'est pas trouvée.
     */
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

    /**
     * Sauvegarde une liste d'utilisateurs dans un fichier sérialisé.
     *
     * @param utilisateurs La liste des utilisateurs à sauvegarder.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant la sauvegarde.
     */
    public static void sauvegarderUtilisateurs(List<Utilisateur> utilisateurs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(UTILISATEUR_FILE))) {
            oos.writeObject(utilisateurs);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs: " + e.getMessage());
        }
    }

    /**
     * Charge la liste des utilisateurs à partir d'un fichier sérialisé.
     *
     * @return La liste des utilisateurs chargés.
     * @throws FileNotFoundException Si le fichier des utilisateurs n'est pas trouvé.
     * @throws IOException Si une erreur d'entrée/sortie se produit pendant le chargement.
     * @throws ClassNotFoundException Si la classe des objets sérialisés n'est pas trouvée.
     */
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
