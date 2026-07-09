import javax.swing.*;

public class Main {

    public static void main (String[] args) {

        Janela();
    }

    public static void Janela() {

        JFrame janela = new JFrame();

        janela.setSize(800, 600);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
}
