package ui;

import modelo.Musica;

import javax.swing.*;
import java.awt.*;

public class RenderizadorMusica extends JLabel implements ListCellRenderer<Musica> {

    public RenderizadorMusica() {

        setLayout(new BorderLayout());

        barraLateral.setOpaque(true);
        barraLateral.setPreferredSize(new Dimension(4,0));

        numero.setFont(FontePixel.obter(8));
        numero.setHorizontalAlignment(SwingConstants.CENTER);
        numero.setPreferredSize(new Dimension(35, 0));

        titulo.setFont(FontePixel.obter(9));
        artista.setFont(FontePixel.obter(7));

        JPanel textoCentral = new JPanel();
        textoCentral.setLayout(new BoxLayout(textoCentral, BoxLayout.Y_AXIS));
        textoCentral.setOpaque(false);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        artista.setAlignmentX(Component.LEFT_ALIGNMENT);
        textoCentral.add(titulo);
        textoCentral.add(artista);

        tempo.setFont(FontePixel.obter(8));
        tempo.setHorizontalAlignment(SwingConstants.RIGHT);
        tempo.setPreferredSize(new Dimension(55, 0));
        tempo.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setOpaque(false);
        conteudo.add(numero, BorderLayout.WEST);
        conteudo.add(textoCentral, BorderLayout.CENTER);
        conteudo.add(tempo, BorderLayout.EAST);
        conteudo.setBorder(BorderFactory.createEmptyBorder(8,0,8,0));

        add(barraLateral, BorderLayout.WEST);
        add(conteudo, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Musica> list,
            Musica musica,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        numero.setText(String.format("%02d", index + 1));
        titulo.setText(musica.getNome());
        titulo.setFont(FontePixel.paraTexto(musica.getNome(), 9));
        artista.setText(musica.getArtista() != null ? musica.getArtista() : "Artista desconhecido");
        artista.setFont(FontePixel.paraTexto(musica.getArtista(), 9));
        tempo.setText(formatarTempo(musica.getDuracaoSegundos()));

        Color fundo = isSelected ? FUNDO_SELECIONADO : FUNDO;

        setOpaque(true);
        setBackground(fundo);

        titulo.setForeground(isSelected ? TITULO_SELECIONADO : Color.WHITE);
        numero.setForeground(NUMERO_COR);
        artista.setForeground(ARTISTA_COR);
        tempo.setForeground(TEMPO_COR);

        barraLateral.setBackground(isSelected ? BARRA_SELECAO : fundo);

        setBorder(BorderFactory.createMatteBorder(1,0,1,0, BORDA_SEPARADOR));

        return this;
    }

    private String formatarTempo(int segundosTotais) {

        int minutos = segundosTotais / 60;
        int segundos = segundosTotais % 60;

        return String.format("%d:%02d", minutos, segundos);
    }

    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color FUNDO_SELECIONADO = new Color(0, 60, 45);
    private static final Color TITULO = new Color(0,255,170);
    private static final Color TITULO_SELECIONADO = new Color(0,255,170);
    private static final Color ARTISTA_COR = new Color(80,190,255);
    private static final Color NUMERO_COR = new Color(120, 130, 145);
    private static final Color TEMPO_COR = new Color(150,160,175);
    private static final Color BARRA_SELECAO = new Color(0,255,170);
    private static final Color BORDA_SEPARADOR = new Color(30,40,55);

    private final JLabel numero = new JLabel();
    private final JLabel titulo = new JLabel();
    private final JLabel artista = new JLabel();
    private final JLabel tempo = new JLabel();
    private final JLabel barraLateral = new JLabel();
}
