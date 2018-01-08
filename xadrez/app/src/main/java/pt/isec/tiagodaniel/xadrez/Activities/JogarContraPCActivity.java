package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.io.Serializable;
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
    private Chronometer CronometroJogBrancas, CronometroJogPretas;
    private int modoJogo;
    private boolean flagSouEuAJogar = false;
    private boolean flagAltereiModoJogo = false;

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

        try {
            ll = findViewById(R.id.tabuleiro);

            CronometroJogBrancas = findViewById(R.id.tempoJogBrancas);
            CronometroJogPretas = findViewById(R.id.tempoJogPretas);

            this.ferramentas = new Ferramentas(this);

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

    public void onClickQuadrado(View v) {
        ArrayList<Posicao> posicoesDisponiveis = new ArrayList<>();
        String res = getResources().getResourceEntryName(v.getId());

        int linha = Character.getNumericValue(res.charAt(1));
        char coluna = res.charAt(0);

        gameModel.seguinte(linha, coluna);
    }

    public void setPosicoesJogaveis(ArrayList<Posicao> posicoesDisponiveis) {
        ImageView iv;
        for (Posicao p : posicoesDisponiveis) {
            iv = findViewById(resources.getIdentifier("" + p.getColuna() + p.getLinha(), "id", getBaseContext().getPackageName()));
            iv.setBackgroundColor(Color.GREEN);
            this.posicoesDisponiveisAnteriores.add(p);
        }
    }

    public void resetPosicoesDisponiveisAnteriores() {
        ImageView pecaImageView;
        ColorDrawable drawable;
        for (Posicao posicao : this.posicoesDisponiveisAnteriores) {
            pecaImageView = findViewById(getResources().getIdentifier("" + posicao.getColuna() + posicao.getLinha(), "id", getBaseContext().getPackageName()));
            drawable = (ColorDrawable) pecaImageView.getBackground();
            if (drawable.getColor() == Color.GREEN) resetCor(posicao, pecaImageView);
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
        if (Check != null) resetCheck();
        reiCheck = PosicaoRei;
        Check = findViewById(resources.getIdentifier("" + PosicaoRei.getColuna() + PosicaoRei.getLinha(), "id", getBaseContext().getPackageName()));
        Check.setBackgroundColor(Color.RED);
    }

    public void resetCheck() {
        if (Check != null && reiCheck != null) resetCor(reiCheck, Check);
        Check = null;
        reiCheck = null;
    }

    public void peaoUltimaLinha(Posicao posicao, Jogador atual) {
        peaoSubstituir = posicao;
        this.atual = atual;
        startActivityForResult(new Intent(JogarContraPCActivity.this, ActivityPromocaoPeao.class), 1);
    }

    public void mostrarVencedor(String vencedor) {
        String titulo;


        titulo = vencedor + " " + getString(R.string.win_title);

        WinDialog winDialog = new WinDialog(this, titulo);
        winDialog.show(getFragmentManager(), WIN_DIALOG);
    }

    public void mostrarEmpate() {
        DrawDialog drawDialog = new DrawDialog(this);
        drawDialog.show(getFragmentManager(), DRAW_DIALOG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gameModel.substituiPeao(resultCode, peaoSubstituir, atual, false);
    }

    @Override
    public void onBackPressed() {
        if (this.gameModel.getModoJogo() == JUNTAR_JOGO_REDE || this.gameModel.getModoJogo() == CRIAR_JOGO_REDE) {
            if (this.xadrezApplication.getJogadorServidor() != this.gameModel.getTabuleiro().getJogadorAtual()) {
                return;
            }

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
        this.xadrezApplication = (XadrezApplication) getApplication();
        Intent intent = getIntent();

        if (intent.getAction().equals("")) {
            finish();
        } else if (intent.getAction().equals(ACTION_JOGvsPC)) {
            this.modoJogo = JOGADOR_VS_COMPUTADOR;
            this.configuraJogador2(true, null, false);
            LinearLayout cronometros = findViewById(R.id.cronometros);
            cronometros.setVisibility(View.GONE);

        } else if (intent.getAction().equals(ACTION_JOGvsJOG)) {
            this.modoJogo = JOGADOR_VS_JOGADOR;
            this.configuraJogador2(false, intent.getExtras(), false);
            this.configuraTempo(intent.getExtras());

        } else if (intent.getAction().equals(ACTION_CRIAR_JOGO_REDE)) {
            this.modoJogo = CRIAR_JOGO_REDE;
            this.configuraTempo(intent.getExtras());
        } else if (intent.getAction().equals(ACTION_JUNTAR_JOGO_REDE)) {
            this.modoJogo = JUNTAR_JOGO_REDE;
        }

        this.configuraJogador1();
    }

    private void configuraJogador1() {
        this.mTxtNomeJogador1 = findViewById(R.id.txtNomeJogador1);
        this.mImvFotoJogador1 = findViewById(R.id.imvFotoJogador1);

        this.mTxtNomeJogador1.setText(ferramentas.getSavedName());
        ferramentas.setPic(this.mImvFotoJogador1, ferramentas.getSavedPhotoPath());
    }

    /**
     * Função para apresentar os dados do utilizador 2 na view
     *
     * @param bot            flag para saber se o jogador 2 é o BOT
     * @param bundle         bundle com as informações para aprensentar na view
     * @param flagGameThread flag para saber se o bundle foi enviado da Thread, neste caso o tratamento da foto é diferente
     */
    public void configuraJogador2(boolean bot, Bundle bundle, boolean flagGameThread) {
        if (bot) return;
        if (bundle == null) finish();

        this.mTxtNomeJogador2 = findViewById(R.id.txtNomeJogador2);
        this.mImvFotoJogador2 = findViewById(R.id.imvFotoJogador2);

        String nome = bundle.getString(NOME_JOGADOR2);
        if (nome.equals("")) {
            nome = getString(R.string.perfil_name_hint);
        }
        this.mTxtNomeJogador2.setText(nome);
        this.xadrezApplication.setNomeJogador2(nome);

        if (flagGameThread) {
            if (bundle.getByteArray(FOTO_JOGADOR2) == null) {
                this.mImvFotoJogador2.setImageResource(R.drawable.computador);
            } else {
                byte[] bitmapdata = bundle.getByteArray(FOTO_JOGADOR2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                this.mImvFotoJogador2.setImageBitmap(bitmap);
                this.xadrezApplication.setFotoJogador2(bitmap);
            }
        } else {
            if (bundle.getString(FOTO_JOGADOR2).equals("")) {
                this.mImvFotoJogador2.setImageResource(R.drawable.computador);
            } else {
                this.ferramentas.setPic(this.mImvFotoJogador2, bundle.getString(FOTO_JOGADOR2));
            }
            this.xadrezApplication.setPathFotoJogador2(bundle.getString(FOTO_JOGADOR2));
        }
    }

    private void configuraTempo(Bundle bundle) {
        if (this.getGameModel().getModoJogo() != JOGADOR_VS_COMPUTADOR && bundle.getBoolean(TEMPO_JOGO_JOGvsJOG)) {
            this.jogoComTempo = true;
            this.tempoMaximo = bundle.getLong(TEMPO_MAX_JOGO_JOGvsJOG);
            tempoMaximo *= 60000;
            this.tempoGanho = bundle.getLong(TEMPO_GANHO_JOGO_JOGvsJOG);
            tempoGanho *= 1000;
            inicializaTempos(false);
        } else {
            LinearLayout cronometros = findViewById(R.id.cronometros);
            cronometros.setVisibility(View.GONE);
        }
    }

    public void configuraTempo(boolean jogoComTempo, long tempoMaximo, long tempoGanho) {
        this.jogoComTempo = jogoComTempo;
        this.tempoMaximo = tempoMaximo;
        this.tempoGanho = tempoGanho;
        inicializaTempos(false);
    }

    public void inicializaTempos(boolean orientacaoMudou) {
        if (!orientacaoMudou)
            xadrezApplication.resetTempos();

        CronometroJogPretas.setBase(SystemClock.elapsedRealtime() + xadrezApplication.getCronometroJogPretasTempoStop());
        CronometroJogPretas.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long passado = SystemClock.elapsedRealtime() - chronometer.getBase();
                if (passado >= tempoMaximo) {
                    if(modoJogo==JUNTAR_JOGO_REDE)
                    {
                        getGameModel().getTabuleiro().setVencedorJogo(mTxtNomeJogador2.getText().toString(), atual, false);
                        mostrarVencedor(mTxtNomeJogador2.getText().toString());
                    }
                    else
                    {
                        getGameModel().getTabuleiro().setVencedorJogo(mTxtNomeJogador1.getText().toString(), atual, false);
                        mostrarVencedor(mTxtNomeJogador1.getText().toString());
                    }

                    CronometroJogPretas.stop();
                    CronometroJogBrancas.stop();
                }
            }
        });

        CronometroJogBrancas.setBase(SystemClock.elapsedRealtime() + xadrezApplication.getCronometroJogBrancasTempoStop());
        CronometroJogBrancas.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long passado = SystemClock.elapsedRealtime() - chronometer.getBase();
                if (passado >= tempoMaximo) {
                    if(modoJogo==JUNTAR_JOGO_REDE)
                    {
                        getGameModel().getTabuleiro().setVencedorJogo(mTxtNomeJogador1.getText().toString(), atual, false);
                        mostrarVencedor(mTxtNomeJogador1.getText().toString());
                    }
                    else
                    {
                        getGameModel().getTabuleiro().setVencedorJogo(mTxtNomeJogador2.getText().toString(), atual, false);
                        mostrarVencedor(mTxtNomeJogador2.getText().toString());
                    }

                    CronometroJogPretas.stop();
                    CronometroJogBrancas.stop();
                }
            }
        });

        if (gameModel.getTabuleiro().getJogadorAtual() instanceof JogadorLight) {
            CronometroJogBrancas.start();
            CronometroJogPretas.stop();
        } else {
            CronometroJogPretas.start();
            CronometroJogBrancas.stop();
        }
    }

    public void paraTempo(Jogador jogador, boolean layoutChange) {
        long res = 0;
        if (jogador instanceof JogadorLight) {
            if (layoutChange)
                res = CronometroJogBrancas.getBase() - SystemClock.elapsedRealtime();
            else
                res = CronometroJogBrancas.getBase() - SystemClock.elapsedRealtime() + tempoGanho;

            if (res > 0)
                res = 0;
            xadrezApplication.setCronometroJogBrancasTempoStop(res);
            comecaTempo(jogador);
            CronometroJogBrancas.stop();
        } else {
            if (layoutChange)
                res = CronometroJogPretas.getBase() - SystemClock.elapsedRealtime();
            else
                res = CronometroJogPretas.getBase() - SystemClock.elapsedRealtime() + tempoGanho;

            if (res > 0)
                res = 0;

            xadrezApplication.setCronometroJogPretasTempoStop(res);
            comecaTempo(jogador);
            CronometroJogPretas.stop();
        }
    }

    public void comecaTempo(Jogador jogador) {
        if(jogador==null)
            System.out.println("Jogador a null");
        if (jogador instanceof JogadorLight) {
            CronometroJogBrancas.setBase(SystemClock.elapsedRealtime() + xadrezApplication.getCronometroJogBrancasTempoStop());
            CronometroJogBrancas.start();
        } else {
            CronometroJogPretas.setBase(SystemClock.elapsedRealtime() + xadrezApplication.getCronometroJogPretasTempoStop());
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

            if (this.gameModel.getModoJogo() == CRIAR_JOGO_REDE || this.gameModel.getModoJogo() == JUNTAR_JOGO_REDE) {
                if (this.xadrezApplication.getJogadorServidor() != this.gameModel.getTabuleiro().getJogadorAtual()) {
                    return super.onOptionsItemSelected(item);
                }
            }

            if (this.gameModel.getModoJogo() == JOGADOR_VS_COMPUTADOR) {
                message = getString(R.string.question_message_alterar_jogo_para_contra_humano);
            } else {
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
                    this.guardarHistorico(true);
                } else if (tag.equals(TAG_ALTERAR_JOGO)) {
                    this.guardarHistorico(false);
                    LinearLayout cronometros = findViewById(R.id.cronometros);
                    cronometros.setVisibility(View.GONE);
                    SocketHandler.closeSocket();
                    this.alterarModoJogo();
                } else if (tag.equals(TAG_SAIR_JOGO_REDE)) {
                    this.guardarHistorico(true);
                    this.flagAltereiModoJogo = true;
                    SocketHandler.closeSocket();
                    this.finish();
                }
                break;
            }
            case ALERT_OK:
                LinearLayout cronometros = findViewById(R.id.cronometros);
                cronometros.setVisibility(View.GONE);
                break;
            case QUESTION_CANCELAR: {
                break;
            }
            case ERROR_OK: {
                this.finish();
                break;
            }
            case DRAW_OK:
            case WIN_OK: {
                this.guardarHistorico(true);
                break;
            }
        }
    }

    private void alterarModoJogo() {
        jogoComTempo = false;
        switch (this.gameModel.getModoJogo()) {
            case JUNTAR_JOGO_REDE:
            case CRIAR_JOGO_REDE:
            case JOGADOR_VS_JOGADOR: {
                LinearLayout cronometros = findViewById(R.id.cronometros);
                cronometros.setVisibility(View.GONE);
                this.gameModel.setModoJogo(JOGADOR_VS_COMPUTADOR);
                this.flagSouEuAJogar = true;
                this.flagAltereiModoJogo = true;
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Jogador jogador = getGameModel().getTabuleiro().getJogadorAtual();
        paraTempo(jogador, true);

        setContentView(R.layout.activity_jogar_contra_pc);

        ll = findViewById(R.id.tabuleiro);

        if (jogoComTempo) {
            CronometroJogBrancas = findViewById(R.id.tempoJogBrancas);
            CronometroJogPretas = findViewById(R.id.tempoJogPretas);

            inicializaTempos(true);
        } else {
            LinearLayout cronometros = findViewById(R.id.cronometros);
            cronometros.setVisibility(View.GONE);
        }

        gameModel.setView(ll);
        gameModel.desenhaPecas();

        this.mTxtNomeJogador2 = findViewById(R.id.txtNomeJogador2);
        this.mImvFotoJogador2 = findViewById(R.id.imvFotoJogador2);
        if (this.gameModel.getModoJogo() == Constantes.CRIAR_JOGO_REDE || this.gameModel.getModoJogo() == JUNTAR_JOGO_REDE) {
            this.mTxtNomeJogador2.setText(this.xadrezApplication.getNomeJogador2());
            this.mImvFotoJogador2.setImageBitmap(this.xadrezApplication.getFotoJogador2());
        } else if (this.gameModel.getModoJogo() == Constantes.JOGADOR_VS_JOGADOR) {
            this.mTxtNomeJogador2.setText(this.xadrezApplication.getNomeJogador2());
            if (this.xadrezApplication.getPathFotoJogador2().equals("")) {
                this.mImvFotoJogador2.setImageResource(R.drawable.computador);
            } else {
                this.ferramentas.setPic(this.mImvFotoJogador2, this.xadrezApplication.getPathFotoJogador2());
            }
        }
        this.mTxtNomeJogador1 = findViewById(R.id.txtNomeJogador1);
        this.mImvFotoJogador1 = findViewById(R.id.imvFotoJogador1);
        this.mTxtNomeJogador1.setText(this.ferramentas.getSavedName());
        this.ferramentas.setPic(this.mImvFotoJogador1, this.ferramentas.getSavedPhotoPath());
    }

    /**
     * Guarda o histórico nas shared preferences
     *
     * @param isToBackPress true = fazer backPress
     */
    public void guardarHistorico(boolean isToBackPress) {
        try {
            this.xadrezApplication.guardarHistorico(this.gameModel.getHistorico());
            if (isToBackPress) {
                super.onBackPressed();
            }
        } catch (IOException e) {
            ErrorDialog errorDialog = new ErrorDialog(this, getString(R.string.error_save_historic));
            errorDialog.show(getFragmentManager(), ERROR_DIALOG);
        }
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public boolean isFlagSouEuAJogar() {
        return this.flagSouEuAJogar;
    }

    //region Funções privadas
    private void setModoJogo() {
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
        TextView nome = ((TextView) findViewById(R.id.txtNomeJogador1));
        return nome.getText().toString();
    }

    public String getNomeJogador2() {
        return ((TextView) findViewById(R.id.txtNomeJogador2)).getText().toString();
    }

    public boolean isFlagAltereiModoJogo() {
        return this.flagAltereiModoJogo;
    }
    //endregion


    public long getTempoMaximo() {
        return tempoMaximo;
    }

    public long getTempoGanho() {
        return tempoGanho;
    }
}
