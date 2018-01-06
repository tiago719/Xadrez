package pt.isec.tiagodaniel.xadrez.Logic.Historico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Jogador;
import pt.isec.tiagodaniel.xadrez.Logic.JogadorLight;

public class Historico implements Serializable, Constantes {
    private Date dataJogo;
    private String vencedorJogo;
    private int modoJogo;
    private ArrayList<Jogada> jogadasJogo;

    public Historico(Date dataJogo) {
        this.dataJogo = dataJogo;
        this.jogadasJogo = new ArrayList<>();
        this.vencedorJogo = DESISTIU;
        this.modoJogo = JOGADOR_VS_COMPUTADOR;
    }

    public void addJogadasJogo(Jogada jogada) {
        this.jogadasJogo.add(jogada);
    }

    public void setModoJogo(int modoJogo) {
        this.modoJogo = modoJogo;
    }

    public void setVencedorJogo(String nomeVencedor, Jogador jogadorActual, boolean empate) {

        if (empate) {
            this.vencedorJogo = EMPATE;
            return;
        }

        switch (this.modoJogo) {
            case JOGADOR_VS_JOGADOR: {
                if (jogadorActual instanceof JogadorLight) {
                    this.vencedorJogo = PECAS_BRANCAS;
                } else {
                    this.vencedorJogo = PECAS_PRETAS;
                }
                break;
            }
            case JOGADOR_VS_COMPUTADOR: {
                if (jogadorActual instanceof JogadorLight) {
                    this.vencedorJogo = nomeVencedor;
                } else {
                    this.vencedorJogo = PC;
                }
                break;
            }
        }
    }

    public Date getDataJogo() {
        return this.dataJogo;
    }

    public String getVencedorJogo() {
        return this.vencedorJogo;
    }

    public ArrayList<Jogada> getListaJogadas() {
        return this.jogadasJogo;
    }

    /**
     * Função usada na HistoricoActivity para mostrar o drawable do vencedo
     * @return int modo de jogo
     */
    public int getModoJogo() {
        return this.modoJogo;
    }
}
