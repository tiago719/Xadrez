package pt.isec.tiagodaniel.xadrez.Logic.Historico;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Ferramentas;
import pt.isec.tiagodaniel.xadrez.Logic.Jogador;
import pt.isec.tiagodaniel.xadrez.Logic.JogadorLight;

/**
 * Created by drmoreira on 29-12-2017.
 */

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

    public void setVencedorJogo(Activity activity, Jogador jogadorActual, boolean empate) {

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
                    try {
                        Ferramentas ferramentas = new Ferramentas(activity);
                        this.vencedorJogo = ferramentas.getSavedName();
                    } catch (NullSharedPreferencesException e) {
                        this.vencedorJogo = PECAS_BRANCAS;
                    }
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

    public int getModoJogo() {
        return this.modoJogo;
    }

    public String getVencedorJogo() {
        return this.vencedorJogo;
    }

    public ArrayList<Jogada> getListaJogadas() {
        return this.jogadasJogo;
    }
}
