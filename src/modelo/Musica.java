package modelo;

public class Musica {

    private String nome;
    private String caminho;
    private byte[] capa;

    public Musica(String nome, String caminho) {

        int ponto = nome.lastIndexOf(".");

        if (ponto != -1) {
            nome = nome.substring(0, ponto);
        }
        this.nome = nome;
        this.caminho = caminho;
    }

    public String getNome() {
        return nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    @Override
    public String toString() {
        return nome;
    }

    public String getArtista() {
        return artistaMusica;
    }

    public void setArtista(String artistaMusica) {
        this.artistaMusica = artistaMusica;
    }

    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }

    private String artistaMusica;
    private int duracaoSegundos;
}
