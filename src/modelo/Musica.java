package modelo;

public class Musica {

    private String nome;
    private String caminho;
    private byte[] capa;

    public Musica(String nome, String caminho) {
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

    private String artistaMusica;
}
