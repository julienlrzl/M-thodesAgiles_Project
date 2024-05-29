import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    public MainApp() {
        super("Système de Gestion de Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Taille augmentée
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(50, 50, 50)); // Fond sombre pour le contraste
        setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour un meilleur contrôle

        JButton btnUser = new JButton("Accès Utilisateur");
        JButton btnAdmin = new JButton("Accès Administrateur");

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

        add(btnUser, gbc);
        add(btnAdmin, gbc);

        btnUser.addActionListener(e -> showUserPanel());
        btnAdmin.addActionListener(e -> showAdminLogin());
    }

    private void showUserPanel() {
        // Initialisation du Panel utilisateur
        PanelUtilisateur panelUtilisateur = new PanelUtilisateur(new Gestionnaire());
        setContentPane(panelUtilisateur);
        validate();
    }

    private void showAdminLogin() {
        // Affichage de la boîte de dialogue d'authentification
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Veuillez entrer le mot de passe:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Authentification Administrateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if ("admin123".equals(password)) { // Utilisez votre mot de passe
                PanelAdministrateur panelAdministrateur = new PanelAdministrateur(new Gestionnaire());
                setContentPane(panelAdministrateur);
                validate();
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp mainFrame = new MainApp();
            mainFrame.setVisible(true);
        });
    }
}
