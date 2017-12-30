package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Ferramentas;
import pt.isec.tiagodaniel.xadrez.R;

public class Modo2JogadoresActivity extends Activity implements Constantes {

    TextView mTxtNomeJogador1, mTxtNomeJogador2;
    ImageView mImvFotoJogador1, mImvFotoJogador2;
    Chronometer mCronometroJogador1, mCronometroJogador2;
    Ferramentas ferramentas;
    String nomeJogador2;
    String fotoJogador2;
    boolean jogarComTempo = false;
    long tempoMaximo, tempoGanho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo2_jogadores);

        this.receberBundle();

        try {
            this.ferramentas = new Ferramentas(this);
        } catch (NullSharedPreferencesException e) {
            e.printStackTrace();
        }

        this.configurarJogador1();
        this.configurarJogador2();
    }

    public void onClickQuadrado(View v) {

    }

    private void configurarJogador1() {
        this.mTxtNomeJogador1 = findViewById(R.id.txtNomeJogador1_JOGvsJOG);
        this.mImvFotoJogador1 = findViewById(R.id.imvFotoJogador1_JOGvsJOG);
        this.mCronometroJogador1 = findViewById(R.id.cronometroJogador1_JOGvsJOG);

        this.mTxtNomeJogador1.setText(this.ferramentas.getSavedName());
        ferramentas.setPic(this.mImvFotoJogador1, ferramentas.getSavedPhotoPath());
        if (this.jogarComTempo) {
            this.mCronometroJogador1.setBase(this.tempoMaximo);
        }
    }

    private void configurarJogador2() {
        this.mTxtNomeJogador2 = findViewById(R.id.txtNomeJogador2_JOGvsJOG);
        this.mImvFotoJogador2 = findViewById(R.id.imvFotoJogador2_JOGvsJOG);
        this.mCronometroJogador2 = findViewById(R.id.cronometroJogador2_JOGvsJOG);

        this.mTxtNomeJogador2.setText(this.nomeJogador2);
        if (this.fotoJogador2.equals("")) {
            this.mImvFotoJogador2.setImageResource(R.drawable.computador);
        } else {
            this.ferramentas.setPic(this.mImvFotoJogador2, this.fotoJogador2);
        }

        if (this.jogarComTempo) {
            this.mCronometroJogador2.setBase(this.tempoMaximo);
        }
    }

    private void receberBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            finish();
        }
        this.nomeJogador2 = bundle.getString(NOME_JOGADOR2_JOGvsJOG);
        this.fotoJogador2 = bundle.getString(FOTO_JOGADOR2_JOGvsJOG);
        this.jogarComTempo = bundle.getBoolean(TEMPO_JOGO_JOGvsJOG);
        if (this.jogarComTempo) {
            this.tempoMaximo = bundle.getLong(TEMPO_MAX_JOGO_JOGvsJOG);
            this.tempoGanho = bundle.getLong(TEMPO_GANHO_JOGO_JOGvsJOG);
        }

    }
}
