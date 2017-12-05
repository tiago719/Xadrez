/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;
import java.util.ArrayList;

/**
 *
 * @author Tiago Coutinho
 */
public class Bispo extends Peca
{
    public Bispo(Tabuleiro t, Posicao p, Jogador j)
    {
        super(t,p,j);
    }

    @Override
    public ArrayList<Posicao> getDisponiveis()
    {
        ArrayList<Posicao> disponiveis=diagonal();
        
        return disponiveis;
    }
}
