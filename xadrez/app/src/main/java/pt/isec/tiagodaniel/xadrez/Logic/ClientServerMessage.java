package pt.isec.tiagodaniel.xadrez.Logic;

import java.io.Serializable;

/**
 * Created by drmoreira on 04-01-2018.
 */

public class ClientServerMessage implements Serializable {
    static final long serialVersionUID = 1L;
    private String nomeJogador;
    private PosicaoRede posicaoOriginal, posicaoDestino;

    public ClientServerMessage(String nomeJogador) {
        this.setNomeJogador(nomeJogador);
        this.posicaoOriginal = new PosicaoRede();
        this.posicaoDestino = new PosicaoRede();
    }

    public ClientServerMessage() {

    }

    public String getNomeJogador() {
        return this.nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        if (nomeJogador.equals("")) {
            // TODO meter aqui o nome da constante, ver se dá por causa do Serializable
            nomeJogador = "JOGADOR";
        }
        this.nomeJogador = nomeJogador;
    }

    public PosicaoRede getPosicaoOriginal() {
        return posicaoOriginal;
    }

    public void setPosicaoOriginal(int linha, char coluna) {
        this.posicaoOriginal.setLinha(linha);
        this.posicaoOriginal.setColuna(coluna);
    }

    public PosicaoRede getPosicaoDestino() {
        return posicaoDestino;
    }

    public void setPosicaoDestino(int linha, char coluna) {
        this.posicaoDestino.setLinha(linha);
        this.posicaoDestino.setColuna(coluna);
    }

    public void resetDados() {
        this.setNomeJogador("");
        this.setPosicaoOriginal(1, 'a');
        this.setPosicaoDestino(1, 'a');
    }
}