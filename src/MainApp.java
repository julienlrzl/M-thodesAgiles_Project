import javax.swing.*;
import java.awt.*;

/**
 * Classe principale de l'application, fournissant l'interface graphique pour accéder
 * aux fonctionnalités utilisateur et administrateur dans le système de gestion de bibliothèque.
 */
public class MainApp extends JFrame {
    /**
     * Constructeur qui initialise la fenêtre principale de l'application.
     * Configure la fenêtre, ses composants et les actions associées aux boutons.
     */
    public MainApp() {
        super("Système de Gestion de Bibliothèque"); // Titre de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Définit la taille de la fenêtre
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        getContentPane().setBackground(new Color(50, 50, 50)); // Couleur de fond pour le contraste
        setLayout(new GridBagLayout()); // Utilise GridBagLayout pour une disposition flexible des composants

        // Configuration des boutons
        JButton btnUser = new JButton("Accès Utilisateur");
        JButton btnAdmin = new JButton("Accès Administrateur");

        // Style des boutons
        btnUser.setBackground(new Color(70, 130, 180)); // Bleu foncé
        btnUser.setForeground(Color.WHITE);
        btnUser.setFont(new Font("Arial", Font.BOLD, 18));
        btnUser.setFocusPainted(false);
        btnUser.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        btnAdmin.setBackground(new Color(220, 20, 60)); // Rouge foncé
        btnAdmin.setForeground(Color.WHITE);
        btnAdmin.setFont(new Font("Arial", Font.BOLD, 18));
        btnAdmin.setFocusPainted(false);
        btnAdmin.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 30, 15, 30);

        // Ajout des boutons à la fenêtre
        add(btnUser, gbc);
        add(btnAdmin, gbc);

        // Gestionnaires d'événements pour les boutons
        btnUser.addActionListener(e -> showUserPanel());
        btnAdmin.addActionListener(e -> showAdminLogin());
    }

    /**
     * Affiche le panel utilisateur en remplaçant le contenu de la fenêtre.
     * Ce panel permet à l'utilisateur de gérer les fonctions basiques de la bibliothèque.
     */
    private void showUserPanel() {
        PanelUtilisateur panelUtilisateur = new PanelUtilisateur(new Gestionnaire());
        setContentPane(panelUtilisateur);
        validate();
    }

    /**
     * Affiche une boîte de dialogue d'authentification pour l'accès administrateur.
     * En cas de succès, le panel administrateur est affiché.
     */
    private void showAdminLogin() {
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Veuillez entrer le mot de passe:", passwordField};

        int option = JOptionPane.showConfirmDialog(this, message, "Authentification Administrateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if ("admin123".equals(password)) {  // Assurez-vous d'utiliser un mot de passe sécurisé et de le stocker de manière sécurisée
                PanelAdministrateur panelAdministrateur = new PanelAdministrateur(new Gestionnaire());
                setContentPane(panelAdministrateur);
                validate();
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Point d'entrée principal de l'application.
     * @param args Arguments de ligne de commande non utilisés ici.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp mainFrame = new MainApp();
            mainFrame.setVisible(true);
        });
    }
}
