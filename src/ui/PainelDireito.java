package ui;

import app.Janela;
import modelo.Musica;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PainelDireito extends JPanel {

    public PainelDireito(Janela janela) {

        this.janela = janela;

        setLayout(new BorderLayout());

        painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(PAINEL);
        painelSuperior.setPreferredSize(new Dimension(0, 130));

        painelPlaylist = new JPanel(new BorderLayout());
        painelPlaylist.setBackground(FUNDO);
        add(painelSuperior, BorderLayout.NORTH);
        add(painelPlaylist, BorderLayout.CENTER);

        criarControles();
        criarPlaylist();
    }

    private void criarControles() {

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 8));
        controles.setBackground(PAINEL);
        controles.setBorder(BorderFactory.createLineBorder(BORDA, 2));

        playIcon = carregarIcon("Resource/Play.png", 100, 50);
        pauseIcon = carregarIcon("Resource/Pause.png", 100, 50);

        JButton modoAleatorio = criarBotao("Resource/AleatorioMusica.png", 20, 20);
        JButton adicionar = criarBotao("Resource/AdicionarMusica.png", 25, 25);
        JButton retrocederMusica = criarBotao("Resource/RetrocederMusica.png", 48, 28);
        JButton atrasarMusica = criarBotao("Resource/AtrasarMusica.png", 54, 34);
        play = criarBotao("Resource/Play.png", 100, 50);
        JButton seguinte = criarBotao("Resource/AdiantarMusica.png", 54, 34);
        JButton avancarMusica = criarBotao("Resource/AvancarMusica.png", 48, 28);
        JButton repetirMusica = criarBotao("Resource/RepetirMusica.png", 20, 20);

        botaoAleatorio = modoAleatorio;
        botaoRepetir = repetirMusica;

        controles.add(modoAleatorio);
        controles.add(adicionar);
        controles.add(retrocederMusica);
        controles.add(atrasarMusica);
        controles.add(play);
        controles.add(seguinte);
        controles.add(avancarMusica);
        controles.add(repetirMusica);

        adicionar.addActionListener(e -> janela.AbrirMusica());
        retrocederMusica.addActionListener(e -> janela.tocarAnterior());
        atrasarMusica.addActionListener(e -> janela.atrasarMusica());
        play.addActionListener(e -> janela.alternarPlayPause());
        seguinte.addActionListener(e -> janela.adiantarMusica());
        avancarMusica.addActionListener(e -> janela.tocarProxima());
        modoAleatorio.addActionListener(e -> janela.alternarAleatorio());
        repetirMusica.addActionListener(e -> janela.alternarRepetir());

        configurarBarraProgresso();

        JPanel blocoTempo = criarBlocoTempo();

        painelSuperior.add(blocoTempo, BorderLayout.NORTH);
        painelSuperior.add(progresso, BorderLayout.CENTER);
        painelSuperior.add(controles, BorderLayout.SOUTH);
    }

    private void configurarBarraProgresso() {
        progresso = new JSlider();
        configurarBarra();
    }

    private JPanel criarBlocoTempo() {

        JPanel bloco = new JPanel(new BorderLayout());
        bloco.setBackground(FUNDO_TEMPO);
        bloco.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 0, 2, BORDA),
                BorderFactory.createEmptyBorder(10,15,5,15)
        ));

        tempoAtual = new JLabel("0:00");
        tempoAtual.setForeground(NEON_VERDE);
        tempoAtual.setFont(FontePixel.obter(20));

        JPanel tempos = new JPanel();
        tempos.setLayout(new BoxLayout(tempos, BoxLayout.Y_AXIS));
        tempos.setBackground(FUNDO_TEMPO);

        tempoRestante = new JLabel("-0:00");
        tempoRestante.setForeground(TEXTO_SECUNDARIO);
        tempoRestante.setFont(FontePixel.obter(10));
        tempoRestante.setAlignmentX(Component.RIGHT_ALIGNMENT);

        tempoTotal = new JLabel("0:00");
        tempoTotal.setForeground(TEXTO_SECUNDARIO);
        tempoTotal.setFont(FontePixel.obter(10));
        tempoTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel linhasTempo = new JPanel(new BorderLayout());
        linhasTempo.setBackground(FUNDO_TEMPO);
        linhasTempo.add(tempoAtual, BorderLayout.WEST);
        linhasTempo.add(tempos, BorderLayout.EAST);
        linhasTempo.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));

        tempos.add(tempoRestante);
        tempos.add(tempoTotal);

        bloco.add(tempoAtual, BorderLayout.WEST);
        bloco.add(tempos, BorderLayout.EAST);

        return bloco;
    }

    private void configurarBarra() {

        progresso.setUI(new BarraProgressoUI(progresso));

        progresso.setPaintTicks(false);
        progresso.setPaintLabels(false);
        progresso.setFocusable(false);

        progresso.setBackground(FUNDO_TEMPO);
        progresso.setForeground(TEXTO);

        progresso.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 2, 2, 2, BORDA),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        progresso.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                janela.setArrastando(true);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                janela.setArrastando(false);
                janela.irParaSegundos(progresso.getValue());
            }
        });
    }

    private void criarPlaylist() {

        playlistModel = new DefaultListModel<>();
        listaPlaylist = new JList<>(playlistModel);

        listaPlaylist.setBackground(FUNDO);
        listaPlaylist.setSelectionBackground(new Color(0, 60, 45));
        listaPlaylist.setSelectionForeground(NEON_VERDE);
        listaPlaylist.setFixedCellHeight(52);
        listaPlaylist.setCellRenderer(new RenderizadorMusica());

        listaPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (e.getClickCount() == 2) {

                    Musica musica = listaPlaylist.getSelectedValue();

                    if (musica != null) {
                        janela.tocarMusica(musica);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(listaPlaylist);
        scroll.getViewport().setBackground(FUNDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JButton remover = new JButton("- Música");
        remover.addActionListener(e -> removerSelecionado());
        remover.setFont(FontePixel.obter(9));
        remover.setForeground(new Color(255, 90,90));
        remover.setBackground(PAINEL);
        remover.setFocusPainted(false);
        remover.setBorder(BorderFactory.createMatteBorder(2,0,0,0, BORDA));

        JPanel baixo = new JPanel(new BorderLayout());
        baixo.add(scroll,BorderLayout.CENTER);
        baixo.add(remover,BorderLayout.SOUTH);
        baixo.setBorder(BorderFactory.createLineBorder(BORDA, 2));

        painelPlaylist.add(baixo, BorderLayout. CENTER);

    }

    private void removerSelecionado() {

        int indice = listaPlaylist.getSelectedIndex();

        if (indice == -1) {
            return;
        }

        playlistModel.remove(indice);
    }

    public void adicionarMusica(Musica musica){
        playlistModel.addElement(musica);
    }

    public DefaultListModel<Musica> getPlaylistModel(){
        return playlistModel;
    }

    public Musica getMusicaSelecionada() {
        return listaPlaylist.getSelectedValue();
    }

    public JList<Musica> getListaPlaylist() {
        return listaPlaylist;
    }

    public void configurarBotao(JButton botao) {

        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setOpaque(false);
    }

    private ImageIcon carregarIcon(String caminho, int largura, int altura) {

        Image imagem = new ImageIcon(caminho).getImage();

        imagem = imagem.getScaledInstance(largura ,altura, Image.SCALE_SMOOTH);

        return new ImageIcon(imagem);
    }

    private void efeitoClique(JButton botao) {

        Point posicaoOriginal = botao.getLocation();

        botao.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                botao.setLocation(botao.getX(), botao.getY() + 2);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                botao.setLocation(botao.getX(), botao.getY() - 2);
            }
        });
    }

    private JButton criarBotao(String caminho, int largura, int altura) {

        ImageIcon icon = carregarIcon(caminho, largura, altura);

        JButton botao = new JButton(icon);

        configurarBotao(botao);
        efeitoClique(botao);

        Dimension tamanho = new Dimension(largura, altura);

        botao.setPreferredSize(tamanho);
        botao.setMinimumSize(tamanho);
        botao.setMaximumSize(tamanho);

        return botao;
    }

    public void atualizarBotaoPlay(boolean tocando) {

        if (tocando) {
            play.setIcon(pauseIcon);
        }
        else {
            play.setIcon(playIcon);
        }
    }

    public JSlider getProgresso() {
        return progresso;
    }

    public void atualizarTempo(double atualSegundos, double totalSegundos) {

        tempoAtual.setText(formatarTempo(atualSegundos));
        tempoTotal.setText(formatarTempo(totalSegundos));

        double restante = totalSegundos - atualSegundos;
        tempoRestante.setText("-" + formatarTempo(restante));
    }

    private String formatarTempo(double segundosTotais) {

        int minutos = (int) segundosTotais / 60;
        int segundos = (int) segundosTotais % 60;

        return String.format("%d:%02d", minutos, segundos);
    }

    public void atualizarEstadoRepeat(boolean ativo) {
        aplicarEstadoBotao(botaoRepetir, ativo);
    }

    public void atualizarEstadoShuffle(boolean ativo) {
        aplicarEstadoBotao(botaoAleatorio, ativo);
    }

    private void aplicarEstadoBotao(JButton botao, boolean ativo) {

        if (ativo) {
            botao.setBorderPainted(true);
            botao.setBorder(BorderFactory.createLineBorder(NEON_VERDE, 2, true));
        }
        else {
            botao.setBorderPainted(false);
            botao.setBorder(null);
        }
    }

    private Janela janela;

    private JButton play;
    private JButton botaoAleatorio;
    private JButton botaoRepetir;

    private DefaultListModel<Musica> playlistModel;
    private JList<Musica> listaPlaylist;

    private JSlider progresso;

    private JPanel painelSuperior;
    private JPanel painelPlaylist;

    private static final Color PAINEL = new Color(18, 27, 46);
    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color TEXTO = new Color(0 , 255, 170);
    private static final Color FUNDO_TEMPO = new Color(8, 12, 20);
    private static final Color NEON_VERDE = new Color(0, 255,130);
    private static final Color TEXTO_SECUNDARIO = new Color(150,160,175);
    private static final Color BORDA = new Color(0 , 255, 170);

    private ImageIcon playIcon;
    private ImageIcon pauseIcon;

    private JLabel tempoAtual;
    private JLabel tempoRestante;
    private JLabel tempoTotal;
}
