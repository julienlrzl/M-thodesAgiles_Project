import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class PanelUtilisateur extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards = new JPanel(cardLayout); // Panel that uses CardLayout
    private Gestionnaire gestionnaire;
    private Utilisateur utilisateurCourant;  
    private JTextArea textArea; // Display book information
    private JTextField searchTitleField;
    private JTextField searchAuthorField;

    public PanelUtilisateur(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(cards, BorderLayout.CENTER);

        JPanel loginPanel = createLoginPanel();
        JPanel functionPanel = createFunctionPanel();

        cards.add(loginPanel, "Login");
        cards.add(functionPanel, "Function");
        cardLayout.show(cards, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField userIdField = new JTextField(20);
        JButton loginButton = new JButton("Connexion");
        loginButton.addActionListener(e -> connecterUtilisateur(userIdField.getText()));

        panel.add(new JLabel("ID Utilisateur:"), gbc);
        panel.add(userIdField, gbc);
        panel.add(loginButton, gbc);

        return panel;
    }

    private JPanel createFunctionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top panel for search
        JPanel topPanel = new JPanel();
        searchTitleField = new JTextField(20);
        searchAuthorField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher Livre");
        searchButton.addActionListener(this::rechercherLivres);
        topPanel.add(new JLabel("Titre:"));
        topPanel.add(searchTitleField);
        topPanel.add(new JLabel("Auteur:"));
        topPanel.add(searchAuthorField);
        topPanel.add(searchButton);

        // Text area for displaying books
        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton showAllBooksButton = new JButton("Voir tous les livres");
        showAllBooksButton.addActionListener(e -> afficherTousLesLivres());

        JButton reserveButton = new JButton("Réserver Livre");
        reserveButton.addActionListener(this::reserverLivre);

        JButton btnConsulterReservations = new JButton("Consulter mes réservations");
        btnConsulterReservations.addActionListener(e -> consulterReservationsUtilisateur());
        
        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(showAllBooksButton);
        bottomPanel.add(reserveButton);
        bottomPanel.add(quitButton);
        bottomPanel.add(btnConsulterReservations);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void connecterUtilisateur(String userId) {
        Utilisateur user = gestionnaire.getUtilisateurs().stream()
            .filter(u -> u.getId().equals(userId))
            .findFirst()
            .orElse(null);
    
        if (user != null) {
            utilisateurCourant = user; // Mise à jour de l'utilisateur courant
            JOptionPane.showMessageDialog(this, "Utilisateur connecté: " + utilisateurCourant.getNom());
            cardLayout.show(cards, "Function");  // Change to function panel if login is successful
        } else {
            utilisateurCourant = null; // Assurez-vous de réinitialiser si la connexion échoue
            JOptionPane.showMessageDialog(this, "Utilisateur non trouvé, veuillez réessayer.", "Erreur de Connexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void afficherTousLesLivres() {
        StringBuilder sb = new StringBuilder();
        for (Livre livre : gestionnaire.getLivres()) {
            sb.append(livre.getTitre()).append(" - ").append(livre.getAuteur());
            if (livre.isReserve()) {
                sb.append(" (Réservé jusqu'à ").append(livre.getDateFinReservation().toString()).append(")");
            } else {
                sb.append(" (Disponible)");
            }
            sb.append("\n");
        }
        textArea.setText(sb.toString());
    }
    

    private void rechercherLivres(ActionEvent e) {
        String titre = searchTitleField.getText();
        String auteur = searchAuthorField.getText();
        List<Livre> resultats = gestionnaire.rechercherLivres(titre, auteur);
        StringBuilder sb = new StringBuilder();
        if (resultats.isEmpty()) {
            sb.append("Aucun livre trouvé pour les critères donnés.");
        } else {
            for (Livre livre : resultats) {
                sb.append(livre.getTitre()).append(" - ").append(livre.getAuteur());
                if (livre.isReserve()) {
                    sb.append(" (Réservé jusqu'à ").append(livre.getDateFinReservation().toString()).append(")");
                } else {
                    sb.append(" (Disponible)");
                }
                sb.append("\n");
            }
        }
        textArea.setText(sb.toString());
    }

    private void reserverLivre(ActionEvent e) {
        if (utilisateurCourant == null) {
            JOptionPane.showMessageDialog(this, "Aucun utilisateur n'est connecté pour effectuer cette réservation.", "Erreur de Réservation", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String titre = searchTitleField.getText();
        String auteur = searchAuthorField.getText();
        gestionnaire.reserverLivre(titre, auteur, utilisateurCourant); // Passer l'utilisateur courant à la méthode de réservation
        JOptionPane.showMessageDialog(this, "Vous avez réservé le livre: " + titre, "Réservation Réussie", JOptionPane.INFORMATION_MESSAGE);
    }

    private void consulterReservationsUtilisateur() {
    if (utilisateurCourant == null) {
        JOptionPane.showMessageDialog(this, "Aucun utilisateur connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return;
    }

    StringBuilder sb = new StringBuilder();
    LocalDate today = LocalDate.now();
    boolean showAlert = false;

    for (Livre livre : gestionnaire.getLivres()) {
        if (livre.isReserve() && livre.getReservedBy().equals(utilisateurCourant)) {
            LocalDate dateFinReservation = livre.getDateFinReservation();
            sb.append(livre.getTitre()).append(" - ").append(livre.getAuteur())
              .append(" (Réservé jusqu'à ").append(dateFinReservation.toString()).append(")").append("\n");

            // Vérifie si la date de fin de réservation approche
            long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, dateFinReservation);
            if (daysLeft <= 3) {
                showAlert = true;
            }
        }
    }

    textArea.setText(sb.toString());

    // Affiche une alerte si un livre doit être retourné dans moins de trois jours
    if (showAlert) {
        JOptionPane.showMessageDialog(this, "Attention! Un ou plusieurs de vos livres réservés doivent être retournés dans les trois jours.", "Alerte de Retour", JOptionPane.WARNING_MESSAGE);
    }
}

}
