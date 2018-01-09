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
    String WIN_DIALOG = "Win Dialog";
    String DRAW_DIALOG = "Draw Dialog";
    String IP_DIALOG = "IP Dialog";
    String ALERT_DIALOG = "Alert Dialog";
    int QUESTION_OK = 1;
    int QUESTION_CANCELAR = 2;
    int ERROR_OK = 3;
    int DRAW_OK = 4;
    int WIN_OK = 5;
    int IP_OK = 6;
    int ALERT_OK = 7;
    String TAG_EMPTY = "EMPTY_TAG";
    String TAG_ALTERAR_JOGO = "ALTERARJOGO";
    String TAG_SAIR_JOGO = "SAIRJOGO";
    String TAG_SAIR_JOGO_REDE = "SAIRJOGOREDE";

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
    String PECAS_BRANCAS = "Brancas";
    String PECAS_PRETAS = "Pretas";
    String PC = "Computador";
    String EMPATE = "Empate";
    String DESISTIU = "Desistiu";

    /**Histórico**/
    String HISTORIC_FILE_NAME = "historico.bin";
    int HISTORICO_TOTAL_JOGOS = 10;
    String HISTORICO_DATA = "DATA";
    String HISTORICO_MODO_JOGO1 = "MODOJOGO1";
    String HISTORICO_MODO_JOGO2 = "MODOJOGO2";
    String HISTORICO_VENCEDOR = "VENCEDOR";

    /**Intents**/
    String PUT_EXTRA_JOGADAS = "JOGADAS";
    String NOME_JOGADOR2 = "NOMEJOGADOR2";
    String FOTO_JOGADOR2 = "FOTOJOGADOR2";
    String TEMPO_JOGO_JOGvsJOG = "TEMPOJOGO";
    String TEMPO_MAX_JOGO_JOGvsJOG = "TEMPOMAXIMO";
    String TEMPO_GANHO_JOGO_JOGvsJOG = "TEMPOGANHO";

    /**Actions**/
    String ACTION_JOGvsPC = "JOGADORVSBOT";
    String ACTION_JOGvsJOG = "JOGADORVSJOGADOR";
    String ACTION_CRIAR_JOGO_REDE = "CRIARJOGOREDE";
    String ACTION_JUNTAR_JOGO_REDE = "JUNTARJOGOREDE";

    /**Jogador VS Jogador**/
    int TEMPO_MAXIMO_MIN = 2;
    int TEMPO_MAXIMO_MAX = 20;
    String MINUTOS = "m";
    int TEMPO_GANHO_MIN = 0;
    int TEMPO_GANHO_MAX = 60;
    String SEGUNDOS = "s";

    /**Request Codes**/
    int PHOTO_REQUEST = 20;

    /**Promocao do Peao**/
    int RAINHA_ESCOLHIDA=1;
    int BISPO_ESCOLHIDA=2;
    int TORRE_ESCOLHIDA=3;
    int CAVALO_ESCOLHIDA=4;

    /**Sockets**/
    int SERVER_PORT = 6000;
    String DEVICE_TYPE = "DEVICETYPE";
    int CLIENTE = 1;
    int SERVIDOR = 2;
    int TIMEOUT = 5000; //5 segundos

    /**BUNDLE verificaCheck**/
    String LINHADESTINO = "LINHADESTINO";
    String COLUNADESTINO = "COLUNADESTINO";
    String LINHAORIGEM = "LINHAORIGEM";
    String COLUNAORIGEM = "COLUNAORIGEM";

    int FOTO_WIDTH = 300;
    int FOTO_HEIGHT = 300;

    String IP_DEFAULT = "10.65.134.217";
}
