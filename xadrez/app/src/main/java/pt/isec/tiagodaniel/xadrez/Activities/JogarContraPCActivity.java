package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Dialogs.DrawDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.ErrorDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Dialogs.QuestionDialog;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.Dialogs.WinDialog;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Ferramentas;
import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Jogador;
import pt.isec.tiagodaniel.xadrez.Logic.JogadorLight;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.Logic.XadrezApplication;
import pt.isec.tiagodaniel.xadrez.R;

public class JogarContraPCActivity extends Activity implements OnCompleteListener, Constantes {
    private LinearLayout ll;
    private GameModel gameModel;
    private ArrayList<Posicao> posicoesDisponiveisAnteriores = null;
    private Resources resources;
    private ImageView Check = null;
    private Posicao reiCheck = null, peaoSubstituir;
    private Ferramentas ferramentas;
    private boolean jogoComTempo = false;
    private long tempoMaximo, tempoGanho;
    private TextView mTxtNomeJogador1, mTxtNomeJogador2;
    private ImageView mImvFotoJogador1, mImvFotoJogador2;
    XadrezApplication xadrezApplication;
    private Jogador atual;

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
        this.gameModel = new GameModel(this.ll, this);

        this.configuracoesIniciais();
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
            drawable = (ColorDrawable) pecaImageView.getBackground();
            if (drawable.getColor() == Color.BLACK)
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

    public void peaoUltimaLinha(Posicao posicao, Jogador atual)
    {
        peaoSubstituir=posicao;
        this.atual=atual;
        startActivityForResult(new Intent(JogarContraPCActivity.this,ActivityPromocaoPeao.class), 1);
    }

    public void mostrarVencedor(Jogador vencedor)
    {
        String titulo;

        if(vencedor instanceof JogadorLight)
            titulo=PECAS_BRANCAS +" "+ getString(R.string.win_title);
        else
            titulo=PECAS_PRETAS + " " + getString(R.string.win_title);

        WinDialog winDialog = new WinDialog(titulo);
        winDialog.show(getFragmentManager(), WIN_DIALOG);
    }

    public void mostrarEmpate()
    {
        DrawDialog drawDialog = new DrawDialog();
        drawDialog.show(getFragmentManager(), DRAW_DIALOG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        gameModel.substituiPeao(resultCode,peaoSubstituir, atual);
    }

    public void updateView() {
        ll.invalidate();
    }

    @Override
    public void onBackPressed() {
        QuestionDialog questionDialog = new QuestionDialog(
                getString(R.string.question_title_leave_game),
                getString(R.string.question_message_leave_game),
                TAG_SAIR_JOGO);
        questionDialog.show(getFragmentManager(), QUESTION_DIALOG);
    }

    private void configuracoesIniciais() {
        this.xadrezApplication = ((XadrezApplication) this.getApplication());
        Intent intent = getIntent();
        if (intent.getAction().equals("")) {
            finish();
        } else if (intent.getAction().equals(ACTION_JOGvsPC)) {
            this.configuraJogador1();
            this.configuraJogador2(true, null);
            this.gameModel.getTabuleiro().getHistorico().setModoJogo(JOGADOR_VS_COMPUTADOR);
            this.xadrezApplication.setModoJogo(JOGADOR_VS_COMPUTADOR);
        } else if (intent.getAction().equals(ACTION_JOGvsJOG)) {
            this.configuraJogador1();
            this.configuraJogador2(false, intent.getExtras());
            this.configuraTempo(intent.getExtras());
            this.gameModel.getTabuleiro().getHistorico().setModoJogo(JOGADOR_VS_JOGADOR);
            this.xadrezApplication.setModoJogo(JOGADOR_VS_JOGADOR);
        }

    }

    private void configuraJogador1() {
        this.mTxtNomeJogador1 = findViewById(R.id.txtNomeJogador1);
        this.mImvFotoJogador1 = findViewById(R.id.imvFotoJogador1);

        try {
            this.ferramentas = new Ferramentas(this);
            this.mTxtNomeJogador1.setText(ferramentas.getSavedName());
            ferramentas.setPic(this.mImvFotoJogador1, ferramentas.getSavedPhotoPath());

        } catch (NullSharedPreferencesException e) {
            e.printStackTrace();
        }
    }

    private void configuraJogador2(boolean bot, Bundle bundle) {
        if (bot) return;
        if (bundle == null) finish();

        this.mTxtNomeJogador2 = findViewById(R.id.txtNomeJogador2);
        this.mImvFotoJogador2 = findViewById(R.id.imvFotoJogador2);

        this.mTxtNomeJogador2.setText(bundle.getString(NOME_JOGADOR2));
        if (bundle.getString(FOTO_JOGADOR2).equals("")) {
            this.mImvFotoJogador2.setImageResource(R.drawable.computador);
        } else {
            this.ferramentas.setPic(this.mImvFotoJogador2, bundle.getString(FOTO_JOGADOR2));
        }


    }

    private void configuraTempo(Bundle bundle) {
        this.jogoComTempo = bundle.getBoolean(TEMPO_JOGO_JOGvsJOG);
        if (this.jogoComTempo) {
            this.tempoMaximo = bundle.getLong(TEMPO_MAX_JOGO_JOGvsJOG);
            this.tempoGanho = bundle.getLong(TEMPO_GANHO_JOGO_JOGvsJOG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(this.xadrezApplication.getModoJogo() == JOGADOR_VS_JOGADOR) {
            MenuInflater mi = new MenuInflater(this);
            mi.inflate(R.menu.menu_jogo, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.alterarJogo) {
            QuestionDialog questionDialog = new QuestionDialog(
                    getString(R.string.question_title_alterar_jogo),
                    getString(R.string.question_message_alterar_jogo),
                    TAG_ALTERAR_JOGO);
            questionDialog.show(getFragmentManager(), QUESTION_DIALOG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(int code, String tag) {
        switch (code) {
            case QUESTION_OK: {
                if(tag.equals(TAG_SAIR_JOGO)) {
                    try {
                        this.xadrezApplication.saveHistoricList(this.gameModel.getTabuleiro().getHistorico());
                        super.onBackPressed();
                    } catch (IOException e) {
                        ErrorDialog errorDialog = new ErrorDialog(getString(R.string.error_save_historic));
                        errorDialog.show(getFragmentManager(), ERROR_DIALOG);
                    }
                } else if(tag.equals(TAG_ALTERAR_JOGO)) {
                    if(this.xadrezApplication.getModoJogo() == JOGADOR_VS_JOGADOR) {
                        this.xadrezApplication.setModoJogo(JOGADOR_VS_COMPUTADOR);
                        this.gameModel.getTabuleiro().getHistorico().setModoJogo(JOGADOR_VS_COMPUTADOR);
                    }
                }
                break;
            }
            case QUESTION_CANCELAR: {
                break;
            }
            case ERROR_OK:
            case DRAW_OK:
            case WIN_OK: {
                this.finish();
                break;
            }
        }
    }
}
