package pt.isec.tiagodaniel.xadrez.Logic;

import java.io.Serializable;

public class ClientServerMessage implements Serializable {
    static final long serialVersionUID = 1L;
    private String nomeJogador;
    private int linhaOrigem, linhaDestino;
    private char colunaOrigem,colunaDestino;
    private byte[] fotoJogador;

    public ClientServerMessage() {
    }

    public String getNomeJogador() {
        return this.nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public int getLinhaOrigem() {
        return linhaOrigem;
    }

    public void setLinhaOrigem(int linhaOrigem) {
        this.linhaOrigem = linhaOrigem;
    }

    public int getLinhaDestino() {
        return linhaDestino;
    }

    public void setLinhaDestino(int linhaDestino) {
        this.linhaDestino = linhaDestino;
    }

    public char getColunaOrigem() {
        return colunaOrigem;
    }

    public void setColunaOrigem(char colunaOrigem) {
        this.colunaOrigem = colunaOrigem;
    }

    public char getColunaDestino() {
        return colunaDestino;
    }

    public void setColunaDestino(char colunaDestino) {
        this.colunaDestino = colunaDestino;
    }

    public void setFotoJogador(byte[] fotoJogador) {
        this.fotoJogador = fotoJogador;
    }

    public byte[] getFotoJogador() {
        return this.fotoJogador;
    }

    public void resetDados() {
        this.fotoJogador = null;
        this.nomeJogador = "";
    }
}