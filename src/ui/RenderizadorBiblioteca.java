package ui;

import app.Janela;
import modelo.Musica;

import javax.swing.*;
import java.awt.*;

public class RenderizadorBiblioteca extends JPanel implements ListCellRenderer<Musica> {

    public RenderizadorBiblioteca(Janela janela) {

        this.janela = janela;

        setLayout(new BorderLayout());

        numero.setFont(FontePixel.obter(8));
        numero.setHorizontalAlignment(SwingConstants.CENTER);
        numero.setPreferredSize(new Dimension(35,0));
        numero.setForeground(NUMERO_COR);

        titulo.setFont(FontePixel.obter(9));
        titulo.setForeground(TITULO);
        artista.setFont(FontePixel.obter(7));
        artista.setForeground(ARTISTA_COR);

        JPanel textoCentral = new JPanel();
        textoCentral.setLayout(new BoxLayout(textoCentral, BoxLayout.Y_AXIS));
        textoCentral.setOpaque(false);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        artista.setAlignmentX(Component.LEFT_ALIGNMENT);
        textoCentral.add(titulo);
        textoCentral.add(artista);

        tempo.setFont(FontePixel.obter(8));
        tempo.setForeground(TEMPO_COR);
        tempo.setHorizontalAlignment(SwingConstants.CENTER);
        tempo.setPreferredSize(new Dimension(70,0));

        ImageIcon icon = new ImageIcon("Resource/AdicionarMusica.png");
        botaoMais.setIcon(icon);
        botaoMais.setFont(FontePixel.obter(7));
        botaoMais.setForeground(COR_MAIS);
        botaoMais.setHorizontalAlignment(SwingConstants.CENTER);
        botaoMais.setPreferredSize(new Dimension(LARGURA_ZONA_MAIS, 0));
        botaoMais.setOpaque(false);
        botaoMais.setBackground(new Color(0,60,45));
        botaoMais.setBorder(BorderFactory.createLineBorder(COR_MAIS,1));

        JPanel direita = new JPanel(new BorderLayout());
        direita.setOpaque(false);

        direita.add(tempo, BorderLayout.CENTER);
        direita.add(botaoMais, BorderLayout.EAST);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setOpaque(false);
        conteudo.add(numero, BorderLayout.WEST);
        conteudo.add(textoCentral, BorderLayout.CENTER);
        conteudo.add(direita, BorderLayout.EAST);
        conteudo.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));

        add(conteudo, BorderLayout.NORTH);
    }

    @Override
    public  Component getListCellRendererComponent(
            JList<? extends Musica> list,
            Musica musica,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        numero.setText(String.format("%02d", index +1));
        titulo.setText(musica.getNome());
        artista.setText(musica.getArtista() != null ? musica.getArtista() : "Artista desconhecido");
        tempo.setText(formatarTempo(musica.getDuracaoSegundos()));
        botaoMais.setVisible(!janela.estaNaPlaylist(musica));

        setOpaque(true);
        setBackground(isSelected ? FUNDO_HOVER : FUNDO);

        setBorder(BorderFactory.createMatteBorder(1,0,1,0, BORDA_SEPARADOR));

        return this;
    }

    private String formatarTempo(int segundosTotais) {
        int minutos = segundosTotais / 60;
        int segundos = segundosTotais % 60;
        return String.format("%d:%02d", minutos, segundos);
    }

    private Janela janela;

    private static final Color FUNDO = new Color(14,20,34);
    private static final Color FUNDO_HOVER = new Color(20,30,45);
    private static final Color TITULO = Color.WHITE;
    private static final Color ARTISTA_COR = new Color(80,190,255);
    private static final Color NUMERO_COR = new Color(120,130,145);
    private static final Color TEMPO_COR = new Color(150,160,175);
    private static final Color BORDA_SEPARADOR = new Color(30,40,55);
    private static final Color COR_MAIS = new Color(0,255,130);

    public static final int LARGURA_ZONA_MAIS = 30;

    private final JLabel numero = new JLabel();
    private final JLabel titulo = new JLabel();
    private final JLabel artista = new JLabel();
    private final JLabel tempo = new JLabel();
    private final JButton botaoMais = new JButton();
}
