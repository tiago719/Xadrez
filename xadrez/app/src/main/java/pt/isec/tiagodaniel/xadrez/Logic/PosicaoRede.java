package pt.isec.tiagodaniel.xadrez.Logic;

import java.io.Serializable;

/**
 * Created by drmoreira on 04-01-2018.
 */

public class PosicaoRede implements Serializable {
    private int linha;
    private char coluna;

    public PosicaoRede() {}

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public void setColuna(char coluna) {
        this.coluna = coluna;
    }
}
