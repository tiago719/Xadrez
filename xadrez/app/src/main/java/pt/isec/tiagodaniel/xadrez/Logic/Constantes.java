/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

/**
 * @author Tiago Coutinho
 */
public interface Constantes {
    int TABULEIRO_LINHAS = 8;
    int TABULEIRO_COLUNAS = 8;

    int TABULEIRO_COLUNAS_SUP_CHAR_ASCII = 104;
    int TABULEIRO_COLUNAS_INF_CHAR_ASCII = 97;

    String PHOTO_FORMAT = ".jpg";
    String PREFERENCES = "AMOV";
    String PHOTO_NOT_FOUND = "Photo not Found";

    /**Dialogs**/
    String ERROR_DIALOG = "Error Dialog";
    String QUESTION_DIALOG = "Question Dialog";
    int QUESTION_OK_SAIR = 1;
    int QUESTION_CANCELAR_SAIR = 2;
    int ERROR_OK = 3;

    /**Modos de jogo**/
    int JOGADOR_VS_JOGADOR = 0;
    int JOGADOR_VS_COMPUTADOR = 1;
    int CRIAR_JOGO_REDE = 2;
    int JUNTAR_JOGO_REDE = 3;

    /**Peças**/
    String PEAO = "PEAO";
    String BISPO = "BISPO";
    String CAVALO = "CAVALO";
    String RAINHA = "RAINHA";
    String REI = "REI";
    String TORRE = "TORRE";

    /**Jogadores**/
    String PECAS_BRANCAS = "Peças brancas";
    String PECAS_PRETAS = "Peças pretas";
    String EMPATE = "Empate";
    String DESISTIU = "Desistiu";

    /**Histórico**/
    String HISTORIC_FILE_NAME = "historico.bin";
}
