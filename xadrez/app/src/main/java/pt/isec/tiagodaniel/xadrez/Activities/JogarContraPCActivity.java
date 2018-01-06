package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
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
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.Logic.SocketHandler;
import pt.isec.tiagodaniel.xadrez.Logic.XadrezApplication;
import pt.isec.tiagodaniel.xadrez.R;

public class JogarContraPCActivity extends Activity implements OnCompleteListener, Constantes
{
    private LinearLayout ll;
    private GameModel gameModel;
    private ArrayList<Posicao> posicoesDisponiveisAnteriores = null;
    private Resources resources;
    private ImageView Check = null;
    private Posicao reiCheck = null, peaoSubstituir;
    private Ferramentas ferramentas;
    private boolean jogoComTempo = false, orientacaoMudou=false;
    private long tempoMaximo, tempoGanho, cronometroJogBrancasTempoStop, cronometroJogPretasTempoStop;
    private TextView mTxtNomeJogador1, mTxtNomeJogador2;
    private ImageView mImvFotoJogador1, mImvFotoJogador2;
    XadrezApplication xadrezApplication;
    private Jogador atual;
    Chronometer CronometroJogBrancas, CronometroJogPretas;
    private int modoJogo;

    public ImageView getCheck()
    {
        return Check;
    }

    public void setCheck(ImageView check)
    {
        Check = check;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_contra_pc);

        try {
            ll = findViewById(R.id.tabuleiro);

            CronometroJogBrancas = findViewById(R.id.tempoJogBrancas);
            CronometroJogPretas = findViewById(R.id.tempoJogPretas);

            this.setModoJogo();

            this.gameModel = new GameModel(this.ll, this, CronometroJogBrancas, CronometroJogPretas, this.modoJogo);

            this.configuracoesIniciais();

            this.posicoesDisponiveisAnteriores = new ArrayList<>();
            resources = getResources();

        } catch (NullSharedPreferencesException e) {
            ErrorDialog mErrorDialog = new ErrorDialog(this, e.toString());
            mErrorDialog.show(getFragmentManager(), Constantes.ERROR_DIALOG);
        }
    }

    public void onClickQuadrado(View v)
    {
        ArrayList<Posicao> posicoesDisponiveis = new ArrayList<>();
        String res = getResources().getResourceEntryName(v.getId());

        int linha = Character.getNumericValue(res.charAt(1));
        char coluna = res.charAt(0);

        gameModel.seguinte(linha, coluna);
    }

    public void setPosicoesJogaveis(ArrayList<Posicao> posicoesDisponiveis)
    {
        ImageView iv;
        for (Posicao p : posicoesDisponiveis)
        {
            iv = findViewById(resources.getIdentifier("" + p.getColuna() + p.getLinha(), "id", getBaseContext().getPackageName()));
            iv.setBackgroundColor(Color.GREEN);
            this.posicoesDisponiveisAnteriores.add(p);
        }
    }

    public void resetPosicoesDisponiveisAnteriores()
    {
        ImageView pecaImageView;
        ColorDrawable drawable;
        for (Posicao posicao : this.posicoesDisponiveisAnteriores)
        {
            pecaImageView = findViewById(getResources().getIdentifier("" + posicao.getColuna() + posicao.getLinha(), "id", getBaseContext().getPackageName()));
            drawable = (ColorDrawable) pecaImageView.getBackground();
            if (drawable.getColor() == Color.GREEN) resetCor(posicao, pecaImageView);
        }
    }

    public void resetCor(Posicao posicao, ImageView pecaImageView)
    {
        if ((int) posicao.getColuna() % 2 != 0)
        {
            if (posicao.getLinha() % 2 != 0)
            {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabLight));
            }
            else
            {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabDark));
            }
        }
        else
        {
            if (posicao.getLinha() % 2 != 0)
            {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabDark));
            }
            else
            {
                pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabLight));
            }
        }
    }

    public void setReiCheck(Posicao PosicaoRei)
    {
        if (Check != null) resetCheck();
        reiCheck = PosicaoRei;
        Check = findViewById(resources.getIdentifier("" + PosicaoRei.getColuna() + PosicaoRei.getLinha(), "id", getBaseContext().getPackageName()));
        Check.setBackgroundColor(Color.RED);
    }

    public void resetCheck()
    {
        if (Check != null && reiCheck != null) resetCor(reiCheck, Check);
        Check = null;
        reiCheck = null;
    }

    public void peaoUltimaLinha(Posicao posicao, Jogador atual)
    {
        peaoSubstituir = posicao;
        this.atual = atual;
        startActivityForResult(new Intent(JogarContraPCActivity.this, ActivityPromocaoPeao.class), 1);
    }

    public void mostrarVencedor(Jogador vencedor) {
        String titulo;

        if (vencedor instanceof JogadorLight)
            titulo = PECAS_BRANCAS + " " + getString(R.string.win_title);
        else titulo = PECAS_PRETAS + " " + getString(R.string.win_title);

        WinDialog winDialog = new WinDialog(this, titulo);
        winDialog.show(getFragmentManager(), WIN_DIALOG);
    }

    public void mostrarEmpate() {
        DrawDialog drawDialog = new DrawDialog(this);
        drawDialog.show(getFragmentManager(), DRAW_DIALOG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gameModel.substituiPeao(resultCode, peaoSubstituir, atual);
    }

    @Override
    public void onBackPressed() {
        if(this.gameModel.getModoJogo() == JUNTAR_JOGO_REDE || this.gameModel.getModoJogo() == CRIAR_JOGO_REDE) {
            QuestionDialog questionDialog = new QuestionDialog(
                    this,
                    getString(R.string.question_title_leave_game_TCP),
                    getString(R.string.question_message_leave_game_TCP),
                    TAG_SAIR_JOGO_REDE);
            questionDialog.show(getFragmentManager(), QUESTION_DIALOG);
        } else {
            QuestionDialog questionDialog = new QuestionDialog(
                    this,
                    getString(R.string.question_title_leave_game),
                    getString(R.string.question_message_leave_game),
                    TAG_SAIR_JOGO);
            questionDialog.show(getFragmentManager(), QUESTION_DIALOG);
        }
    }

    private void configuracoesIniciais() {
        this.xadrezApplication = (XadrezApplication)getApplication();
        Intent intent = getIntent();

        if (intent.getAction().equals("")) {
            finish();
        } else if (intent.getAction().equals(ACTION_JOGvsPC)) {
            this.modoJogo = JOGADOR_VS_COMPUTADOR;
            this.configuraJogador2(true, null);

        } else if (intent.getAction().equals(ACTION_JOGvsJOG)) {
            this.modoJogo = JOGADOR_VS_JOGADOR;
            this.configuraJogador2(false, intent.getExtras());
            this.configuraTempo(intent.getExtras());

        } else if (intent.getAction().equals(ACTION_CRIAR_JOGO_REDE)) {
            this.modoJogo = CRIAR_JOGO_REDE;
        } else if (intent.getAction().equals(ACTION_JUNTAR_JOGO_REDE)) {
            this.modoJogo = JUNTAR_JOGO_REDE;
        }

        this.configuraJogador1();
    }

    private void configuraJogador1()
    {
        this.mTxtNomeJogador1 = findViewById(R.id.txtNomeJogador1);
        this.mImvFotoJogador1 = findViewById(R.id.imvFotoJogador1);

        try
        {
            this.ferramentas = new Ferramentas(this);
            this.mTxtNomeJogador1.setText(ferramentas.getSavedName());
            ferramentas.setPic(this.mImvFotoJogador1, ferramentas.getSavedPhotoPath());
            
        } catch (NullSharedPreferencesException e) 
        {
            ErrorDialog mErrorDialog = new ErrorDialog(this, e.toString());
            mErrorDialog.show(getFragmentManager(), Constantes.ERROR_DIALOG);
        }
    }

    public void configuraJogador2(boolean bot, Bundle bundle) {
        if (bot) return;
        if (bundle == null) finish();

        this.mTxtNomeJogador2 = findViewById(R.id.txtNomeJogador2);
        this.mImvFotoJogador2 = findViewById(R.id.imvFotoJogador2);

        String nome = bundle.getString(NOME_JOGADOR2);
        if (nome.equals("")) {
            nome = getString(R.string.perfil_name_hint);
        }
        this.mTxtNomeJogador2.setText(nome);

        if (bundle.getString(FOTO_JOGADOR2).equals("")) {
            this.mImvFotoJogador2.setImageResource(R.drawable.computador);
        }
        else
        {
            this.ferramentas.setPic(this.mImvFotoJogador2, bundle.getString(FOTO_JOGADOR2));
        }
    }
    private void configuraTempo(Bundle bundle) {
        if (this.getGameModel().getModoJogo() != JOGADOR_VS_COMPUTADOR && bundle.getBoolean(TEMPO_JOGO_JOGvsJOG)) {
            this.jogoComTempo = true;
            this.tempoMaximo = bundle.getLong(TEMPO_MAX_JOGO_JOGvsJOG);
            this.tempoGanho = bundle.getLong(TEMPO_GANHO_JOGO_JOGvsJOG);
            inicializaTempos();
        } else {
            LinearLayout cronometros= findViewById(R.id.cronometros);
            cronometros.setVisibility(View.GONE);
        }
    }

    public void inicializaTempos() {
        CronometroJogPretas.setBase(SystemClock.elapsedRealtime());
        CronometroJogPretas.stop();
        CronometroJogPretas.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                if (SystemClock.elapsedRealtime() == tempoMaximo)
                {
                    mostrarVencedor(gameModel.getTabuleiro().getJogadorAdversario());
                }
            }
        });

        CronometroJogBrancas.setBase(SystemClock.elapsedRealtime()+tempoPassadoBrancas);
        CronometroJogBrancas.start();
        CronometroJogPretas.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                if (SystemClock.elapsedRealtime() == tempoMaximo)
                {
                    mostrarVencedor(gameModel.getTabuleiro().getJogadorAdversario());
                }
            }
        });
    }

    public void paraTempo(Jogador jogador)
    {
        long res = 0;
        if (jogador instanceof JogadorLight)
        {
            cronometroJogBrancasTempoStop = CronometroJogBrancas.getBase() - SystemClock.elapsedRealtime();
            CronometroJogBrancas.stop();
            /*
            if(cronometroJogBrancasTempoStop-tempoGanho<0)
                res=0;
            else
                res=cronometroJogBrancasTempoStop-tempoGanho;
            CronometroJogBrancas.setBase(res);*/
        } else {
            cronometroJogPretasTempoStop = CronometroJogPretas.getBase() - SystemClock.elapsedRealtime();
            CronometroJogPretas.stop();
            /*
            if(cronometroJogPretasTempoStop-tempoGanho<0)
                res=0;
            else
                res=cronometroJogPretasTempoStop-tempoGanho;
            CronometroJogPretas.setBase(res);*/
        }
    }
    public void comecaTempo(Jogador jogador)
    {
        if (jogador instanceof JogadorLight)
        {
            CronometroJogBrancas.setBase(SystemClock.elapsedRealtime() + cronometroJogBrancasTempoStop);
            CronometroJogBrancas.start();
        }
        else
        {
            CronometroJogPretas.setBase(SystemClock.elapsedRealtime() + cronometroJogPretasTempoStop);
            CronometroJogPretas.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_jogo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.alterarJogo) {
            String message;

            if (this.gameModel.getModoJogo() == JOGADOR_VS_COMPUTADOR)
                message = getString(R.string.question_message_alterar_jogo_para_contra_humano);
            else {
                message = getString(R.string.question_message_alterar_jogo_para_contra_bot);
            }

            QuestionDialog questionDialog = new QuestionDialog(
                    this,
                    getString(R.string.question_title_alterar_jogo),
                    message,
                    TAG_ALTERAR_JOGO);
            questionDialog.show(getFragmentManager(), QUESTION_DIALOG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(int code, String tag) {
        switch (code) {
            case QUESTION_OK: {
                if (tag.equals(TAG_SAIR_JOGO)) {
                    this.guardarHistorico();
                } else if (tag.equals(TAG_ALTERAR_JOGO)) 
                {
                    LinearLayout cronometros= findViewById(R.id.cronometros);
                    cronometros.setVisibility(View.GONE);
                    this.alterarModoJogo();
                } else if(tag.equals(TAG_SAIR_JOGO_REDE)) {
                    if (SocketHandler.getClientSocket() != null) {
                        try {
                            SocketHandler.getClientSocket().close();
                        } catch (IOException ex1) {
                            // TODO errorDialog
                            System.err.println("[AttendTCPClientsThread]" + ex1);
                        }

                    }
                    this.gameModel.setModoJogo(JOGADOR_VS_COMPUTADOR);
                }
                break;
            }
            case ALERT_OK:
            case QUESTION_CANCELAR: {
                break;
            }
            case ERROR_OK: {
                this.finish();
                break;
            }
            case DRAW_OK:
            case WIN_OK: {
                this.guardarHistorico();
                break;
            }
        }
    }

    private void alterarModoJogo() {
        switch (this.gameModel.getModoJogo()) {
            case CRIAR_JOGO_REDE:
            case JUNTAR_JOGO_REDE:
            case JOGADOR_VS_JOGADOR: {
                CronometroJogBrancas.setVisibility(View.GONE);
                CronometroJogPretas.setVisibility(View.GONE);
                this.gameModel.setModoJogo(JOGADOR_VS_COMPUTADOR);
                break;
            }
            case JOGADOR_VS_COMPUTADOR: {
                this.gameModel.setModoJogo(JOGADOR_VS_JOGADOR);
                break;
            }
        }
    }

    public boolean isJogoComTempo() {
        return jogoComTempo;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_jogar_contra_pc);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_jogar_contra_pc);
        }

        ll = findViewById(R.id.tabuleiro);

        if(jogoComTempo)
        {
            CronometroJogBrancas = findViewById(R.id.tempoJogBrancas);
            CronometroJogPretas = findViewById(R.id.tempoJogPretas);

            inicializaTempos(cronometroJogBrancasTempoStop,cronometroJogPretasTempoStop);
        }
        else
        {
            LinearLayout cronometros= findViewById(R.id.cronometros);
            cronometros.setVisibility(View.GONE);
        }

        gameModel.setView(ll);
        gameModel.desenhaPecas();


    }

    private void guardarHistorico() {
        try {
            this.xadrezApplication.guardarHistorico(this.gameModel.getHistorico());
            super.onBackPressed();
        } catch (IOException e) {
            ErrorDialog errorDialog = new ErrorDialog(this, getString(R.string.error_save_historic));
            errorDialog.show(getFragmentManager(), ERROR_DIALOG);
        }
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    //region Funções privadas
    private void setModoJogo(){
        Intent intent = getIntent();

        if (intent.getAction().equals("")) {
            finish();
        } else if (intent.getAction().equals(ACTION_JOGvsPC)) {
            this.modoJogo = JOGADOR_VS_COMPUTADOR;
        } else if (intent.getAction().equals(ACTION_JOGvsJOG)) {
            this.modoJogo = JOGADOR_VS_JOGADOR;
        } else if (intent.getAction().equals(ACTION_CRIAR_JOGO_REDE)) {
            this.modoJogo = CRIAR_JOGO_REDE;
        } else if (intent.getAction().equals(ACTION_JUNTAR_JOGO_REDE)) {
            this.modoJogo = JUNTAR_JOGO_REDE;
        }
    }
    //endregion

    //region Funções usadas pelo Game Model
    public String getNomeJogador1() {
        return ((TextView)findViewById(R.id.nomeJogador1)).getText().toString();
    }

    public String getNomeJogador2() {
        return ((TextView)findViewById(R.id.nomeJogador2)).getText().toString();
    }
    //endregion
}
