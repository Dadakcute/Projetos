import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Não foi possível aplicar o look and feel do sistema.");
            }
            new TelaPrincipal();
        });
    }
}
