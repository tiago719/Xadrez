package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Dialogs.ErrorDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Dialogs.QuestionDialog;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.Logic.XadrezApplication;
import pt.isec.tiagodaniel.xadrez.R;

public class JogarContraPCActivity extends Activity implements OnCompleteListener, Constantes {
    private LinearLayout ll;
    private GameModel gameModel;
    private ArrayList<Posicao> posicoesDisponiveisAnteriores = null;
    private Resources resources;
    private ImageView Check;
    private Posicao reiCheck, peaoSubstituir;

    public ImageView getCheck() {
        return Check;
    }

    public void setCheck(ImageView check) {
        Check = check;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_contra_pc);

        ll = findViewById(R.id.tabuleiro);
        Check = null;
        reiCheck = null;

        this.gameModel = new GameModel(this.ll, this);
        this.gameModel.getTabuleiro().getHistorico().setModoJogo(JOGADOR_VS_COMPUTADOR);

        this.posicoesDisponiveisAnteriores = new ArrayList<>();
        resources = getResources();
    }

    public void onClickQuadrado(View v) {
        ArrayList<Posicao> posicoesDisponiveis = new ArrayList<>();
        String res = getResources().getResourceEntryName(v.getId());
        ImageView iv;
        Peca pecaClick;
        Posicao posicaoDestino;

        int linha = Character.getNumericValue(res.charAt(1));
        char coluna = res.charAt(0);

        gameModel.seguinte(linha, coluna);
    }

    public void setPosicoesJogaveis(ArrayList<Posicao> posicoesDisponiveis) {
        ImageView iv;
        for (Posicao p : posicoesDisponiveis) {
            iv = findViewById(resources.getIdentifier("" + p.getColuna() + p.getLinha(), "id", getBaseContext().getPackageName()));
            iv.setBackgroundColor(Color.BLACK);
            this.posicoesDisponiveisAnteriores.add(p);
        }
    }

    public void resetPosicoesDisponiveisAnteriores() {
        ImageView pecaImageView;
        ColorDrawable drawable;
        for (Posicao posicao : this.posicoesDisponiveisAnteriores) {
            pecaImageView = findViewById(getResources().getIdentifier("" + posicao.getColuna()
                    + posicao.getLinha(), "id", getBaseContext().getPackageName()));
            drawable=(ColorDrawable) pecaImageView.getBackground();
            if(drawable.getColor()==Color.BLACK)
                resetCor(posicao, pecaImageView);
        }
    }

    public void resetCor(Posicao posicao, ImageView pecaImageView) {
        if ((int) posicao.getColuna() % 2 != 0) {
            if (posicao.getLinha() % 2 != 0) {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabLight));
            } else {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabDark));
            }
        } else {
            if (posicao.getLinha() % 2 != 0) {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabDark));
            } else {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabLight));
            }
        }
    }

    public void setReiCheck(Posicao PosicaoRei) {
        if (Check != null)
            resetCheck();
        reiCheck = PosicaoRei;
        Check = findViewById(resources.getIdentifier("" + PosicaoRei.getColuna() + PosicaoRei.getLinha(), "id", getBaseContext().getPackageName()));
        Check.setBackgroundColor(Color.RED);
    }

    public void resetCheck() {
        if (Check != null && reiCheck != null)
            resetCor(reiCheck, Check);
        Check = null;
        reiCheck = null;
    }

    public void peaoUltimaLinha(Posicao posicao)
    {
        peaoSubstituir=posicao;
        startActivityForResult(new Intent(JogarContraPCActivity.this,ActivityPromocaoPeao.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        gameModel.substituiPeao(resultCode,peaoSubstituir);
    }

    public void updateView()
    {
        ll.invalidate();
    }

    @Override
    public void onBackPressed() {
        QuestionDialog questionDialog = new QuestionDialog(getString(R.string.question_title_leave_game), getString(R.string.question_message_leave_game));
        questionDialog.show(getFragmentManager(), QUESTION_DIALOG);
    }

    @Override
    public void onComplete(int code) {
        switch (code) {
            case QUESTION_OK: {
                try {
                    XadrezApplication xadrezApplication = ((XadrezApplication) this.getApplication());
                    xadrezApplication.saveHistoricList(this.gameModel.getTabuleiro().getHistorico());
                    super.onBackPressed();
                } catch (IOException e) {
                    ErrorDialog errorDialog = new ErrorDialog(getString(R.string.error_save_historic));
                    errorDialog.show(getFragmentManager(), ERROR_DIALOG);
                }
            }
            case ERROR_OK: {
                this.finish();
            }
        }
    }
}
