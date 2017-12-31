package pt.isec.tiagodaniel.xadrez.Logic;

import java.util.Comparator;

/**
 * Created by Tiago Coutinho on 30/12/2017.
 */

public class ComparadorJogadasPC implements Comparator<Jogada>
{
    @Override
    public int compare(Jogada p1, Jogada p2)
    {
        int ret;
        int rand=(int)(Math.random() * ((1 - 0) + 1));
        if(rand==0)
            ret=-1;
        else
            ret=1;

        int pErro=1 + (int)(Math.random() * ((10 - 1) + 1));

        Peca temp1=p1.getPosicaoDestino().getPeca();
        Peca temp2=p2.getPosicaoDestino().getPeca();

        p1.getPosicaoDestino().setPeca(p1.getPosicaoOriginal().getPeca());
        p2.getPosicaoDestino().setPeca(p2.getPosicaoOriginal().getPeca());

        p1.getTabuleiro().trocaJogadorActual();

        if((p1.getTabuleiro().ficaEmCheckJogadorAtual(p1.getPosicaoOriginal(),p1.getPosicaoOriginal().getPeca()) && p2.getTabuleiro().ficaEmCheckJogadorAtual(p2.getPosicaoOriginal(),p2.getPosicaoOriginal().getPeca()))||
                (!p1.getTabuleiro().ficaEmCheckJogadorAtual(p1.getPosicaoOriginal(),p1.getPosicaoOriginal().getPeca()) && !p2.getTabuleiro().ficaEmCheckJogadorAtual(p2.getPosicaoOriginal(),p2.getPosicaoOriginal().getPeca())))
        {
            p1.getTabuleiro().trocaJogadorActual();
            p1.getPosicaoDestino().setPeca(temp1);
            p2.getPosicaoDestino().setPeca(temp2);

            if(pErro>8)
                return ret;

            if(!p1.getPosicaoDestino().isOcupado() && !p2.getPosicaoDestino().isOcupado())
            {
                return ret;
            }
            else if(p1.getPosicaoDestino().isOcupado() && p2.getPosicaoDestino().isOcupado())
            {
                Peca peca1=p1.getPosicaoDestino().getPeca();
                Peca peca2=p2.getPosicaoDestino().getPeca();

                if(peca1 instanceof Rainha)
                {
                    if (peca2 instanceof Rainha)
                        return ret;
                    else
                        return -1;
                }
                else if(peca1 instanceof Torre)
                {
                    if(peca2 instanceof Rainha)
                        return 1;
                    else if(peca2 instanceof Torre)
                        return ret;
                    else
                        return -1;
                }
                else if(peca1 instanceof Bispo)
                {
                    if(peca2 instanceof Rainha)
                        return 1;
                    else if(peca2 instanceof Torre)
                        return 1;
                    else if(peca2 instanceof Bispo)
                        return ret;
                    else
                        return -1;
                }
                else if(peca1 instanceof Cavalo)
                {
                    if(peca2 instanceof Rainha)
                        return 1;
                    else if(peca2 instanceof Torre)
                        return 1;
                    else if(peca2 instanceof Bispo)
                        return 1;
                    else if(peca2 instanceof Cavalo)
                        return ret;
                    else
                        return -1;
                }
                else
                {
                    if(peca2 instanceof Rainha)
                        return 1;
                    else if(peca2 instanceof Torre)
                        return 1;
                    else if(peca2 instanceof Bispo)
                        return 1;
                    else if(peca2 instanceof Cavalo)
                        return 1;
                    else
                        return ret;
                }

            }
            else if(!p1.getPosicaoDestino().isOcupado() && p2.getPosicaoDestino().isOcupado())
            {
                if(pErro>8)
                    return ret;
                return 1;
            }
            else
            {
                if(pErro>8)
                    return ret;

                return -1;
            }
        }
        else if(p1.getTabuleiro().ficaEmCheckJogadorAtual(p1.getPosicaoOriginal(),p1.getPosicaoOriginal().getPeca()))
        {
            p1.getTabuleiro().trocaJogadorActual();
            p1.getPosicaoDestino().setPeca(temp1);
            p2.getPosicaoDestino().setPeca(temp2);

            if(pErro>8)
                return ret;

            return 1;
        }
        else
        {
            p1.getTabuleiro().trocaJogadorActual();
            p1.getPosicaoDestino().setPeca(temp1);
            p2.getPosicaoDestino().setPeca(temp2);

            if(pErro>8)
                return ret;
            return -1;
        }
    }
}
