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

        add(new PainelEsquerdo(), BorderLayout.WEST);
        painelDireito = new PainelDireito(janela);

        add(painelDireito, BorderLayout.CENTER);
    }

    public PainelDireito getPainelDireito() {
        return painelDireito;
    }

    private PainelDireito painelDireito;
}
