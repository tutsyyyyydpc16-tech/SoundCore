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

        painelEsquerdo = new PainelEsquerdo(janela);
        painelDireito = new PainelDireito(janela);

        add(painelDireito, BorderLayout.CENTER);
        add(painelEsquerdo, BorderLayout.WEST);
    }

    public PainelDireito getPainelDireito() {
        return painelDireito;
    }

    private PainelDireito painelDireito;

    public PainelEsquerdo getPainelEsquerdo() {
        return painelEsquerdo;
    }

    private PainelEsquerdo painelEsquerdo;
}
