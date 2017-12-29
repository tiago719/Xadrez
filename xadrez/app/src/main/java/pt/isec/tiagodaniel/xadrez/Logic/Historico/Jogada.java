package pt.isec.tiagodaniel.xadrez.Logic.Historico;

import java.io.Serializable;

/**
 * Created by drmoreira on 29-12-2017.
 */

public class Jogada implements Serializable {
    private String origem;
    private String destino;
    private String nomePeca;

    /**
     * Construtor para a Jogada
     *
     * @param nomePeca nome da peça
     * @param origem   posição anterior da peça
     * @param destino  posição nova da peça
     */
    public Jogada(String nomePeca, String origem, String destino) {
        this.nomePeca = nomePeca;
        this.origem = origem;
        this.destino = destino;
    }

    public String getOrigem() {
        return this.origem;
    }

    public String getDestino() {
        return this.destino;
    }

    public String getNomePeca() {
        return this.nomePeca;
    }

    @Override
    public String toString() {
        return nomePeca + ": " + origem + " --> " + destino;
    }
}
