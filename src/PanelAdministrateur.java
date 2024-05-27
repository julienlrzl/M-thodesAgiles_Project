import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PanelAdministrateur extends JPanel {
    private Gestionnaire gestionnaire;

    public PanelAdministrateur(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(6, 2, 10, 10)); // Layout to organize buttons

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

        btnCreateUser.addActionListener(e -> createUser());
        btnViewReservations.addActionListener(e -> viewReservations());
        btnModifierReservation.addActionListener(e -> modifierReservation());
        btnValidateReturn.addActionListener(e -> validateReturn());
        btnAddBook.addActionListener(e -> addBook());
        btnModifyBook.addActionListener(e -> modifyBook());
        btnSupprimerLivre.addActionListener(e -> supprimerLivre());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void createUser() {
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField idField = new JTextField();
        final JComponent[] inputs = new JComponent[] {
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

    private void viewReservations() {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        StringBuilder reservations = new StringBuilder();
        for (Livre livre : gestionnaire.getLivres()) {
            if (livre.isReserve()) {
                Utilisateur user = livre.getReservedBy();
                reservations.append(livre.getTitre()).append(" - ").append(livre.getAuteur())
                             .append(", Réservé par: ").append(user != null ? user.getNom() + " " + user.getPrenom() : "Inconnu")
                             .append(", Jours restants: ").append(livre.getDateFinReservation().minusDays(LocalDate.now().toEpochDay()))
                             .append("\n");
            }
        }
        textArea.setText(reservations.toString());
        JOptionPane.showMessageDialog(this, scrollPane, "Réservations actuelles", JOptionPane.INFORMATION_MESSAGE);
    }
    

    private void validateReturn() {
        String titre = JOptionPane.showInputDialog("Entrer le titre du livre à valider:");
        String auteur = JOptionPane.showInputDialog("Entrer l'auteur du livre à valider:");
        gestionnaire.validerRetour(titre, auteur);
    }

    private void addBook() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField editionField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField locationField = new JTextField();
        
        final JComponent[] inputs = new JComponent[] {
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
    
    private void modifyBook() {
        // Demande à l'utilisateur de saisir le titre et l'auteur pour identifier le livre à modifier
        String titre = JOptionPane.showInputDialog(this, "Entrer le titre du livre à modifier:");
        String auteur = JOptionPane.showInputDialog(this, "Entrer l'auteur du livre à modifier:");
    
        // Trouver le livre correspondant
        Livre livre = gestionnaire.trouverLivreParTitreEtAuteur(titre, auteur);
        if (livre == null) {
            JOptionPane.showMessageDialog(this, "Livre non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Création des champs de texte avec les valeurs actuelles du livre
        JTextField titleField = new JTextField(livre.getTitre());
        JTextField authorField = new JTextField(livre.getAuteur());
        JTextField editionField = new JTextField(livre.getEdition());
        JTextField yearField = new JTextField(String.valueOf(livre.getAnneeParution()));
        JTextField genreField = new JTextField(livre.getGenre());
        JTextField locationField = new JTextField(livre.getEmplacement());
    
        final JComponent[] inputs = new JComponent[] {
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
    
        // Affiche un dialogue de modification avec les champs pré-remplis
        int result = JOptionPane.showConfirmDialog(this, inputs, "Modifier le livre", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Mise à jour du livre avec les nouvelles valeurs
                livre.setTitre(titleField.getText());
                livre.setAuteur(authorField.getText());
                livre.setEdition(editionField.getText());
                livre.setAnneeParution(Integer.parseInt(yearField.getText()));
                livre.setGenre(genreField.getText());
                livre.setEmplacement(locationField.getText());
    
                // Sauvegarder les changements
                gestionnaire.sauvegarderModifications();  // Assurez-vous que cette méthode existe pour sauvegarder les changements dans le gestionnaire
                JOptionPane.showMessageDialog(this, "Livre modifié avec succès.", "Modification Réussie", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'année de parution doit être un nombre valide.", "Erreur de Format", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierReservation() {
        JTextField titreField = new JTextField();
        JTextField auteurField = new JTextField();
        JTextField dateField = new JTextField("yyyy-mm-dd");
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
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Format de date invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
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
