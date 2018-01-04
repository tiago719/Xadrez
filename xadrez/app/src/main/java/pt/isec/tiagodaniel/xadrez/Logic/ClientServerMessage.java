package pt.isec.tiagodaniel.xadrez.Logic;

import java.io.Serializable;

/**
 * Created by drmoreira on 04-01-2018.
 */

public class ClientServerMessage implements Serializable {
    static final long serialVersionUID = 1L;
    private String nomeJogador;

    public ClientServerMessage(String nomeJogador) {
        this.setNomeJogador(nomeJogador);
    }

    public String getNomeJogador() {
        return this.nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        if(nomeJogador.equals("")){
            // TODO meter aqui o nome da constante, ver se dá por causa do Serializable
            nomeJogador = "JOGADOR";
        }
        this.nomeJogador = nomeJogador;
    }

    public void resetDados() {
        this.nomeJogador = "";
    }
}
