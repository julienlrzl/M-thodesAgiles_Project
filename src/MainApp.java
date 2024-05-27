import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Gestion de Bibliothèque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);  // Centrer la fenêtre

        // Création des onglets pour utilisateur et administrateur
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panneaux pour utilisateur et administrateur
        JPanel panelUtilisateur = new PanelUtilisateur(new Gestionnaire());
        JPanel panelAdministrateur = new PanelAdministrateur(new Gestionnaire());

        tabbedPane.addTab("Utilisateur", panelUtilisateur);
        tabbedPane.addTab("Administrateur", panelAdministrateur);

        // Ajout des onglets à la fenêtre principale
        frame.add(tabbedPane);

        // Affichage de la fenêtre
        frame.setVisible(true);
    }
}
