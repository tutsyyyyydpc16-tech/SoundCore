package ui;

import modelo.Musica;

import javax.swing.*;
import java.awt.*;

public class RenderizadorMusica extends JLabel implements ListCellRenderer<Musica> {

    public RenderizadorMusica() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(6, 0,6,15));

        barraLateral.setPreferredSize(new Dimension(4,0));

        JPanel linhaTopo = new JPanel(new BorderLayout());
        linhaTopo.setOpaque(false);

        JPanel numeroETitulo = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
        numeroETitulo.setOpaque(false);

        numero.setFont(FontePixel.obter(8));
        titulo.setFont(FontePixel.obter(9));

        numeroETitulo.add(numero);
        numeroETitulo.add(titulo);

        tempo.setFont(FontePixel.obter(8));
        tempo.setHorizontalAlignment(SwingConstants.RIGHT);

        linhaTopo.add(numeroETitulo, BorderLayout.WEST);
        linhaTopo.add(tempo, BorderLayout.EAST);

        artista.setFont(FontePixel.obter(7));
        artista.setBorder(BorderFactory.createEmptyBorder(4,30,0,0));

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setOpaque(false);
        conteudo.add(linhaTopo);
        conteudo.add(artista);
        conteudo.setBorder(BorderFactory.createEmptyBorder(0,12,0,0));

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
        artista.setText(musica.getArtista() != null ? musica.getArtista() : "Artista desconhecido");
        tempo.setText(formatarTempo(musica.getDuracaoSegundos()));

        Color fundo = isSelected ? FUNDO_SELECIONADO : FUNDO;

        setBackground(fundo);
        conteudoDefinirFundo(fundo);

        titulo.setForeground(isSelected ? TITULO_SELECIONADO : Color.WHITE);
        numero.setForeground(NUMERO_COR);
        artista.setForeground(ARTISTA_COR);
        tempo.setForeground(TEMPO_COR);

        barraLateral.setBackground(isSelected ? BARRA_SELECAO : fundo);

        return this;
    }

    private void conteudoDefinirFundo(Color cor) {
        setOpaque(true);
        setBackground(cor);
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

    private final JLabel numero = new JLabel();
    private final JLabel titulo = new JLabel();
    private final JLabel artista = new JLabel();
    private final JLabel tempo = new JLabel();
    private final JLabel barraLateral = new JLabel();
}
