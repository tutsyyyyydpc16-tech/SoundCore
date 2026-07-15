package ui;

import app.Janela;
import modelo.Musica;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PainelBiblioteca extends JPanel {

    public PainelBiblioteca(Janela janela) {

        this.janela = janela;

        setLayout(new BorderLayout());
        setBackground(FUNDO);

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        topo.add(criarCabecalho(), BorderLayout.NORTH);
        topo.add(criarBusca(), BorderLayout.CENTER);
        topo.add(criarCabecalhoColunas(), BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);
        add(criarLista(), BorderLayout.CENTER);
    }

    private JPanel criarCabecalho() {

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(PAINEL);
        cabecalho.setPreferredSize(new Dimension(0,50));

        JButton voltar = new JButton("< VOLTAR");
        voltar.setFont(FontePixel.obter(9));
        voltar.setForeground(TEXTO);
        voltar.setBackground(PAINEL);
        voltar.setBorderPainted(false);
        voltar.setFocusPainted(false);
        voltar.addActionListener(e -> janela.voltarAoPlayer());
        voltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel titulo = new JLabel("BIBLIOTECA");
        titulo.setFont(FontePixel.obter(12));
        titulo.setForeground(TEXTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JButton adicionar = new JButton("+ MÚSICA");
        adicionar.setFont(FontePixel.obter(9));
        adicionar.setForeground(TEXTO);
        adicionar.setBackground(PAINEL);
        adicionar.setBorderPainted(false);
        adicionar.setFocusPainted(false);
        adicionar.addActionListener(e -> janela.AbrirMusicaNaBiblioteca());
        adicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cabecalho.add(voltar, BorderLayout.WEST);
        cabecalho.add(titulo, BorderLayout.CENTER);
        cabecalho.add(adicionar, BorderLayout.EAST);

        return cabecalho;
    }

    private JPanel criarBusca() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(PAINEL);
        painel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        JLabel label = new JLabel("BUSCAR: ");
        label.setFont(FontePixel.obter(8));
        label.setForeground(TEXTO_SECUNDARIO);

        campoBusca = new JTextField();
        campoBusca.setFont(FontePixel.obter(9));
        campoBusca.setForeground(Color.WHITE);
        campoBusca.setBackground(FUNDO);
        campoBusca.setCaretColor(TEXTO);
        campoBusca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        campoBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA,1),
                BorderFactory.createEmptyBorder(6,10,6,10)
        ));

        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });

        JPanel linha = new JPanel(new BorderLayout());
        linha.setOpaque(false);
        linha.add(label, BorderLayout.WEST);
        linha.add(campoBusca, BorderLayout.CENTER);

        painel.add(linha, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarCabecalhoColunas() {

        JPanel colunas = new JPanel(new BorderLayout());
        colunas.setBackground(PAINEL);
        colunas.setBorder(BorderFactory.createMatteBorder(0,0,2,0,BORDA));
        colunas.setPreferredSize(new Dimension(0,28));

        JLabel titulo = new JLabel("TÍTULO / ARTISTA");
        titulo.setFont(FontePixel.obter(7));
        titulo.setForeground(TEXTO_SECUNDARIO);

        JLabel tempo = new JLabel("TEMPO");
        tempo.setFont(FontePixel.obter(7));
        tempo.setForeground(TEXTO_SECUNDARIO);
        tempo.setHorizontalAlignment(SwingConstants.CENTER);
        tempo.setPreferredSize(new Dimension(70,0));

        JLabel acao = new JLabel("AÇÃO  ");
        acao.setFont(FontePixel.obter(7));
        acao.setForeground(TEXTO_SECUNDARIO);
        acao.setHorizontalAlignment(SwingConstants.CENTER);
        acao.setPreferredSize(new Dimension(RenderizadorBiblioteca.LARGURA_ZONA_MAIS, 0));

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        direita.setOpaque(false);
        direita.add(tempo);
        direita.add(acao);

        colunas.add(titulo, BorderLayout.WEST);
        colunas.add(direita, BorderLayout.EAST);

        return colunas;
    }

    private JScrollPane criarLista() {

        bibliotecaModel = new DefaultListModel<>();
        listaBiblioteca = new JList<>(bibliotecaModel);

        listaBiblioteca.setBackground(FUNDO);
        listaBiblioteca.setFixedCellHeight(48);
        listaBiblioteca.setCellRenderer(new RenderizadorBiblioteca(janela));
        listaBiblioteca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        listaBiblioteca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int indice = listaBiblioteca.locationToIndex(e.getPoint());

                if (indice == -1) {
                    return;
                }

                Rectangle bounds = listaBiblioteca.getCellBounds(indice,indice);

                if (bounds == null) {
                    return;
                }

                int xRelativo = e.getX() - bounds.x;

                if (xRelativo >= bounds.width - RenderizadorBiblioteca.LARGURA_ZONA_MAIS) {

                    Musica musica = bibliotecaModel.getElementAt(indice);
                    janela.adicionarAPlaylist(musica);

                    listaBiblioteca.revalidate();
                    listaBiblioteca.repaint();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(listaBiblioteca);
        scroll.getViewport().setBackground(FUNDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return scroll;
    }

    private void filtrar() {

        String termo = campoBusca.getText().trim().toLowerCase();

        bibliotecaModel.clear();

        for (Musica m : bibliotecaCompleta) {

            String nome = m.getNome() != null ? m.getNome().toLowerCase() : "";
            String artista = m.getArtista() != null ? m.getArtista().toLowerCase() : "";

            if (termo.isEmpty() || nome.contains(termo) || artista.contains(termo)) {
                bibliotecaModel.addElement(m);
            }

        }
    }

    public void adicionarMusica(Musica musica) {
        bibliotecaCompleta.add(musica);
        filtrar();
    }

    public List<Musica> getBibliotecaCompleta() {
        return bibliotecaCompleta;
    }

    public DefaultListModel<Musica> getBibliotecaModel() {
        return bibliotecaModel;
    }

    private static final Color PAINEL = new Color(18, 27, 46);
    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color BORDA = new Color(0 , 255, 170);
    private static final Color TEXTO = new Color(0 , 255, 170);
    private static final Color TEXTO_SECUNDARIO = new Color(150,160,175);

    private final Janela janela;
    private final List<Musica> bibliotecaCompleta = new ArrayList<>();
    public DefaultListModel<Musica> bibliotecaModel;
    private JList<Musica> listaBiblioteca;
    private JTextField campoBusca;
}
