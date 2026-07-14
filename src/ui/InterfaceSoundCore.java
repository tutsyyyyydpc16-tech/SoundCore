package ui;

import app.Janela;
import modelo.Musica;

import javax.swing.*;
import java.awt.*;

public class InterfaceSoundCore extends JPanel {

    private Janela janela;

    public InterfaceSoundCore(Janela janela) {

        this.janela = janela;

        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        painelCards = new JPanel(cardLayout);

        JPanel viewPlayer = new JPanel(new BorderLayout());
        painelEsquerdo = new PainelEsquerdo(janela);
        painelDireito = new PainelDireito(janela);
        viewPlayer.add(painelEsquerdo, BorderLayout.WEST);
        viewPlayer.add(painelDireito, BorderLayout.CENTER);

        painelBiblioteca = new PainelBiblioteca(janela);

        painelCards.add(viewPlayer, "player");
        painelCards.add(painelBiblioteca, "biblioteca");

        add(painelCards, BorderLayout.CENTER);
    }

    public PainelDireito getPainelDireito() {
        return painelDireito;
    }

    public PainelEsquerdo getPainelEsquerdo() {
        return painelEsquerdo;
    }

    public PainelBiblioteca getPainelBiblioteca() {
        return painelBiblioteca;
    }

    public void mostrarPlayer() {
        cardLayout.show(painelCards, "player");
    }

    public void mostrarBiblioteca() {
        cardLayout.show(painelCards, "biblioteca");
    }

    private final CardLayout cardLayout;
    private final JPanel painelCards;
    private PainelDireito painelDireito;
    private PainelEsquerdo painelEsquerdo;
    private PainelBiblioteca painelBiblioteca;
}
