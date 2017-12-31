package pt.isec.tiagodaniel.xadrez.Logic;

import android.bluetooth.BluetoothClass;

/**
 * Created by Tiago Coutinho on 31/12/2017.
 */

public class JogaPC extends Thread
{
    private GameModel gameModel;

    public JogaPC(GameModel game)
    {
        gameModel = game;
    }

    @Override
    public void run()
    {
        gameModel.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Posicao posicaoPeao;

                try
                {
                    sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                gameModel.getTabuleiro().getJogadorAtual().joga();

                if ((posicaoPeao = gameModel.getTabuleiro().isPeaoUltimaLinha()) != null)
                {
                    gameModel.getTabuleiro().getJogadorAtual().addPecaMorta(posicaoPeao.getPeca());
                    posicaoPeao.apagaPeca();

                    int rand = 1 + (int) (Math.random() * ((10 - 1) + 1));

                    if (rand < 6)
                        posicaoPeao.setPeca(new Rainha(gameModel.getTabuleiro(), gameModel.getTabuleiro().getJogadorAtual()));
                    else if (rand < 7)
                        posicaoPeao.setPeca(new Torre(gameModel.getTabuleiro(), gameModel.getTabuleiro().getJogadorAtual()));
                    else if (rand < 8)
                        posicaoPeao.setPeca(new Bispo(gameModel.getTabuleiro(), gameModel.getTabuleiro().getJogadorAtual()));
                    else
                        posicaoPeao.setPeca(new Cavalo(gameModel.getTabuleiro(), gameModel.getTabuleiro().getJogadorAtual()));

                    gameModel.getTabuleiro().getJogadorAtual().addPeca(posicaoPeao.getPeca());

                    posicaoPeao.desenhaPeca();
                }

                if(gameModel.verificaCheck(gameModel.getTabuleiro().getJogadorAtual()))
                {
                    return;
                }
                gameModel.getTabuleiro().trocaJogadorActual();

                return;
            }
        });

    }
}
