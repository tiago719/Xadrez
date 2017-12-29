package pt.isec.tiagodaniel.xadrez.Logic.Historico;

import java.io.Serializable;

/**
 * Created by drmoreira on 29-12-2017.
 */

public class Jogada implements Serializable {
    private String posicaoAnterior;
    private String posicaoNova;
    private String nomePeca;

    /**
     * Construtor para a Jogada
     * @param nomePeca nome da peça
     * @param posicaoAnterior posição anterior da peça
     * @param posicaoNova posição nova da peça
     */
    public Jogada(String nomePeca, String posicaoAnterior, String posicaoNova) {
        this.nomePeca = nomePeca;
        this.posicaoAnterior = posicaoAnterior;
        this.posicaoNova = posicaoNova;
    }

    @Override
    public String toString() {
        return nomePeca + ": " + posicaoAnterior + " --> " + posicaoNova;
    }
}
