import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Panel fournissant l'interface administrative pour la gestion de la bibliothèque.
 * Permet aux administrateurs de gérer les comptes utilisateurs, les livres et les réservations.
 */
public class PanelAdministrateur extends JPanel {
    private Gestionnaire gestionnaire;

    /**
     * Constructeur qui initialise le panel administrateur avec un gestionnaire.
     * @param gestionnaire Le gestionnaire contenant la logique métier de gestion des livres et utilisateurs.
     */
    public PanelAdministrateur(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
        initUI();
    }

    /**
     * Initialise l'interface utilisateur du panel administrateur avec tous les composants nécessaires.
     */
    private void initUI() {
        setLayout(new GridLayout(6, 2, 10, 10)); // Configuration du layout pour organiser les boutons

        // Création et ajout des boutons
        JButton btnCreateUser = new JButton("Créer un compte utilisateur");
        JButton btnViewReservations = new JButton("Consulter les réservations");
        JButton btnModifierReservation = new JButton("Modifier Réservation");
        JButton btnValidateReturn = new JButton("Valider les retours de livres");
        JButton btnAddBook = new JButton("Ajouter un nouveau livre");
        JButton btnModifyBook = new JButton("Modifier les informations d'un livre");
        JButton btnSupprimerLivre = new JButton("Supprimer Livre");
        JButton btnExit = new JButton("Quitter");

        add(btnCreateUser);
        add(btnViewReservations);
        add(btnModifierReservation);
        add(btnValidateReturn);
        add(btnAddBook);
        add(btnModifyBook);
        add(btnSupprimerLivre);
        add(btnExit);

        // Configuration des écouteurs pour les actions des boutons
        btnCreateUser.addActionListener(e -> createUser());
        btnViewReservations.addActionListener(e -> viewReservations());
        btnModifierReservation.addActionListener(e -> modifierReservation());
        btnValidateReturn.addActionListener(e -> validateReturn());
        btnAddBook.addActionListener(e -> addBook());
        btnModifyBook.addActionListener(e -> modifyBook());
        btnSupprimerLivre.addActionListener(e -> supprimerLivre());
        btnExit.addActionListener(e -> System.exit(0));
    }

    /**
     * Crée un nouvel utilisateur via une boîte de dialogue.
     */
    private void createUser() {
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField idField = new JTextField();
        final JComponent[] inputs = {
            new JLabel("Nom"),
            nameField,
            new JLabel("Prénom"),
            surnameField,
            new JLabel("ID"),
            idField
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Créer un compte utilisateur", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Utilisateur newUser = new Utilisateur(nameField.getText(), surnameField.getText(), idField.getText());
            gestionnaire.inscrireUtilisateur(newUser);
            JOptionPane.showMessageDialog(this, "Utilisateur créé avec succès: " + newUser.getNom());
        }
    }

    /**
     * Affiche les réservations actuelles sous forme de texte.
     */
    private void viewReservations() {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        StringBuilder reservations = new StringBuilder();
        LocalDate today = LocalDate.now();
        for (Livre livre : gestionnaire.getLivres()) {
            if (livre.isReserve()) {
                Utilisateur user = livre.getReservedBy();
                long joursRestants = ChronoUnit.DAYS.between(today, livre.getDateFinReservation());
                reservations.append(livre.getTitre()).append(" - ").append(livre.getAuteur())
                            .append(", Réservé par: ").append(user != null ? user.getNom() + " " + user.getPrenom() : "Inconnu")
                            .append(", Jours restants: ").append(joursRestants)
                            .append("\n");
            }
        }
        textArea.setText(reservations.toString());
        JOptionPane.showMessageDialog(this, scrollPane, "Réservations actuelles", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Permet la validation des retours de livres à l'aide de boîtes de dialogue pour saisir le titre et l'auteur.
     */
    private void validateReturn() {
        String titre = JOptionPane.showInputDialog("Entrer le titre du livre à valider:");
        String auteur = JOptionPane.showInputDialog("Entrer l'auteur du livre à valider:");
        gestionnaire.validerRetour(titre, auteur);
    }

    /**
     * Ajoute un livre au système via une série de boîtes de dialogue pour saisir les détails nécessaires.
     */
    private void addBook() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField editionField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField locationField = new JTextField();

        final JComponent[] inputs = {
            new JLabel("Titre"),
            titleField,
            new JLabel("Auteur"),
            authorField,
            new JLabel("Édition"),
            editionField,
            new JLabel("Année de parution"),
            yearField,
            new JLabel("Genre"),
            genreField,
            new JLabel("Emplacement"),
            locationField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Ajouter un nouveau livre", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String titre = titleField.getText();
                String auteur = authorField.getText();
                String edition = editionField.getText();
                int anneeParution = Integer.parseInt(yearField.getText());  // Handle number format exception
                String genre = genreField.getText();
                String emplacement = locationField.getText();

                Livre nouveauLivre = new Livre(titre, auteur, edition, anneeParution, genre, emplacement);
                gestionnaire.ajouterLivre(nouveauLivre);
                JOptionPane.showMessageDialog(this, "Livre ajouté avec succès: " + titre, "Livre Ajouté", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erreur: L'année de parution doit être un nombre valide.", "Erreur de Format", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Modifie un livre existant en utilisant les informations fournies par l'utilisateur.
     */
    private void modifyBook() {
        String titre = JOptionPane.showInputDialog(this, "Entrer le titre du livre à modifier:");
        String auteur = JOptionPane.showInputDialog(this, "Entrer l'auteur du livre à modifier:");

        Livre livre = gestionnaire.trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre == null) {
            JOptionPane.showMessageDialog(this, "Livre non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField titleField = new JTextField(livre.getTitre());
        JTextField authorField = new JTextField(livre.getAuteur());
        JTextField editionField = new JTextField(livre.getEdition());
        JTextField yearField = new JTextField(String.valueOf(livre.getAnneeParution()));
        JTextField genreField = new JTextField(livre.getGenre());
        JTextField locationField = new JTextField(livre.getEmplacement());

        final JComponent[] inputs = {
            new JLabel("Titre"),
            titleField,
            new JLabel("Auteur"),
            authorField,
            new JLabel("Édition"),
            editionField,
            new JLabel("Année de parution"),
            yearField,
            new JLabel("Genre"),
            genreField,
            new JLabel("Emplacement"),
            locationField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Modifier le livre", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                livre.setTitre(titleField.getText());
                livre.setAuteur(authorField.getText());
                livre.setEdition(editionField.getText());
                livre.setAnneeParution(Integer.parseInt(yearField.getText()));
                livre.setGenre(genreField.getText());
                livre.setEmplacement(locationField.getText());

                gestionnaire.sauvegarderModifications();  // Ensure this method exists to save the changes in the manager
                JOptionPane.showMessageDialog(this, "Livre modifié avec succès.", "Modification Réussie", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'année de parution doit être un nombre valide.", "Erreur de Format", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Modifie la date de fin de réservation d'un livre réservé.
     */
    private void modifierReservation() {
        JTextField titreField = new JTextField();
        JTextField auteurField = new JTextField();
        JTextField dateField = new JTextField(LocalDate.now().plusDays(1).toString()); // Set default to tomorrow's date
        Object[] message = {
            "Titre:", titreField,
            "Auteur:", auteurField,
            "Nouvelle date de fin (yyyy-mm-dd):", dateField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Modifier la Réservation", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                LocalDate nouvelleDate = LocalDate.parse(dateField.getText());
                gestionnaire.modifierDateFinReservation(titreField.getText(), auteurField.getText(), nouvelleDate);
                JOptionPane.showMessageDialog(null, "Date de fin de réservation mise à jour.", "Mise à jour Réussie", JOptionPane.INFORMATION_MESSAGE);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Format de date invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Supprime un livre spécifié par l'utilisateur.
     */
    private void supprimerLivre() {
        JTextField titreField = new JTextField();
        JTextField auteurField = new JTextField();
        Object[] message = {
            "Titre:", titreField,
            "Auteur:", auteurField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Supprimer Livre", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            gestionnaire.supprimerLivre(titreField.getText(), auteurField.getText());
        }
    }
}
