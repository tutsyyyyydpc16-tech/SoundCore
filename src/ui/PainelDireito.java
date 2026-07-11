package ui;

import app.Janela;
import modelo.Musica;

import javax.swing.*;
import java.awt.*;

public class PainelDireito extends JPanel {

    public PainelDireito(Janela janela) {

        this.janela = janela;

        setLayout(new BorderLayout());

        painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(PAINEL);
        painelSuperior.setPreferredSize(new Dimension(0, 80));

        painelPlaylist = new JPanel(new BorderLayout());
        painelPlaylist.setBackground(FUNDO);
        add(painelSuperior, BorderLayout.NORTH);
        add(painelPlaylist, BorderLayout.CENTER);

        criarControles();
        criarPlaylist();
    }

    private void criarControles() {

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 8));
        controles.setBackground(new Color(18, 27, 46));

        playIcon = carregarIcon("Resource/Play.png", 100, 50);
        pauseIcon = carregarIcon("Resource/Pause.png", 100, 50);

        JButton adicionar = criarBotao("Resource/AdicionarMusica.png", 25, 25);
        JButton retrocederMusica = criarBotao("Resource/RetrocederMusica.png", 48, 28);
        JButton atrasarMusica = criarBotao("Resource/AtrasarMusica.png", 54, 34);
        play = criarBotao("Resource/Play.png", 100, 50);
        JButton seguinte = criarBotao("Resource/AdiantarMusica.png", 54, 34);
        JButton avancarMusica = criarBotao("Resource/AvancarMusica.png", 48, 28);

        controles.add(adicionar);
        controles.add(retrocederMusica);
        controles.add(atrasarMusica);
        controles.add(play);
        controles.add(seguinte);
        controles.add(avancarMusica);

        adicionar.addActionListener(e -> janela.AbrirMusica());
        retrocederMusica.addActionListener(e -> janela.tocarAnterior());
        atrasarMusica.addActionListener(e -> janela.atrasarMusica());
        play.addActionListener(e -> janela.alternarPlayPause());
        seguinte.addActionListener(e -> janela.adiantarMusica());
        avancarMusica.addActionListener(e -> janela.tocarProxima());

        painelSuperior.add(controles, BorderLayout.SOUTH);

        progressoBarrinha();

    }

    private void progressoBarrinha() {

        progresso = new JSlider();

        configurarBarra();

        painelSuperior.add(progresso, BorderLayout.NORTH);

    }

    private void configurarBarra() {

        progresso.setUI(new BarraProgressoUI(progresso));

        progresso.setPaintTicks(false);
        progresso.setPaintLabels(false);
        progresso.setFocusable(false);

        progresso.setBackground(PAINEL);
        progresso.setForeground(TEXTO);

        progresso.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

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

        JButton remover = new JButton("- Música");

        remover.addActionListener(e -> removerSelecionado());

        JPanel baixo = new JPanel(new BorderLayout());

        baixo.add(scroll,BorderLayout.CENTER);
        baixo.add(remover,BorderLayout.SOUTH);

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

    private Janela janela;

    private JButton play;

    private DefaultListModel<Musica> playlistModel;
    private JList<Musica> listaPlaylist;

    private JSlider progresso;

    private JPanel painelSuperior;
    private JPanel painelPlaylist;

    private static final Color PAINEL = new Color(18, 27, 46);
    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color TEXTO = new Color(0 , 255, 170);

    private ImageIcon playIcon;
    private ImageIcon pauseIcon;
}
