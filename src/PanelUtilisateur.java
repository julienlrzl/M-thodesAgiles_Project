import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PanelUtilisateur extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards = new JPanel(cardLayout); // Panel that uses CardLayout
    private Gestionnaire gestionnaire;
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

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(showAllBooksButton);
        bottomPanel.add(reserveButton);
        bottomPanel.add(quitButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void connecterUtilisateur(String userId) {
        Utilisateur utilisateur = gestionnaire.getUtilisateurs().stream()
            .filter(u -> u.getId().equals(userId))
            .findFirst()
            .orElse(null);

        if (utilisateur != null) {
            JOptionPane.showMessageDialog(this, "Utilisateur connecté: " + utilisateur.getNom());
            cardLayout.show(cards, "Function");
        } else {
            JOptionPane.showMessageDialog(this, "Utilisateur non trouvé.");
        }
    }

    private void afficherTousLesLivres() {
        StringBuilder sb = new StringBuilder();
        gestionnaire.getLivres().forEach(livre -> sb.append(livre.getTitre()).append(" - ").append(livre.getAuteur()).append("\n"));
        textArea.setText(sb.toString());
    }

    private void rechercherLivres(ActionEvent e) {
        String titre = searchTitleField.getText();
        String auteur = searchAuthorField.getText();
        List<Livre> resultats = gestionnaire.rechercherLivres(auteur);
        StringBuilder sb = new StringBuilder();
        resultats.forEach(livre -> sb.append(livre.getTitre()).append(" - ").append(livre.getAuteur()).append("\n"));
        textArea.setText(sb.toString());
    }

    private void reserverLivre(ActionEvent e) {
        String titre = searchTitleField.getText();
        String auteur = searchAuthorField.getText();
        Utilisateur utilisateurCourant = gestionnaire.getUtilisateurs().get(0);
        gestionnaire.reserverLivre(titre, auteur, utilisateurCourant);
        JOptionPane.showMessageDialog(this, "Livre réservé: " + titre);
    }
}
