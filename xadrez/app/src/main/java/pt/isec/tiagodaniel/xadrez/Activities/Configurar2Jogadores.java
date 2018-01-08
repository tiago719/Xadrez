package pt.isec.tiagodaniel.xadrez.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.isec.tiagodaniel.xadrez.Dialogs.ErrorDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Ferramentas;
import pt.isec.tiagodaniel.xadrez.R;

public class Configurar2Jogadores extends Activity implements OnCompleteListener, Constantes {

    SeekBar mSeekBarTempoMaximo, mSeekBarTempoGanho;
    TextView mTxtTempoMaximo, mTxtTempoGanho, mTxtNomeJogador1;
    EditText mTxtNomeJogador2;
    ImageView mImvFotoJogador1, mImvFotoJogador2;
    Switch mSwitchJogarComTempo;
    boolean jogarComTempo = false;
    Ferramentas ferramentas;
    String fotoJogador2Path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar2_jogadores);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1234);
            }

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 4321);
            }
        }

        try {
            this.ferramentas = new Ferramentas(this);
        } catch (NullSharedPreferencesException e) {
            e.printStackTrace();
        }

        this.configurarJogador1();
        this.configurarJogador2();
        this.configurarJocarComTempo();
        this.configurarTempoMaximo();
        this.configurarTempoGanho();
    }

    public void onComecarJogo_JOGvsJOG(View v) {
        Intent intent = new Intent(this, JogarContraPCActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(NOME_JOGADOR2, this.mTxtNomeJogador2.getText().toString());
        bundle.putString(FOTO_JOGADOR2, this.fotoJogador2Path);
        bundle.putBoolean(TEMPO_JOGO_JOGvsJOG, jogarComTempo);
        if (jogarComTempo) {
            int tempoMax = this.mSeekBarTempoMaximo.getProgress();
            if (tempoMax < 2)
                tempoMax = 2;
            bundle.putLong(TEMPO_MAX_JOGO_JOGvsJOG, tempoMax);
            bundle.putLong(TEMPO_GANHO_JOGO_JOGvsJOG, this.mSeekBarTempoGanho.getProgress());
        }

        intent.putExtras(bundle);
        intent.setAction(Constantes.ACTION_JOGvsJOG);
        startActivity(intent);
        this.finish();
    }

    public void onTirarFoto(View v) throws IOException {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(name, Constantes.PHOTO_FORMAT, storageDir);

            this.fotoJogador2Path = image.getAbsolutePath();

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
            startActivityForResult(intent, PHOTO_REQUEST);
        } catch (Exception e) {
            ErrorDialog mErrorDialog = new ErrorDialog(this, getString(R.string.take_photo));
            mErrorDialog.show(getFragmentManager(), Constantes.ERROR_DIALOG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
            this.ferramentas.setPic(this.mImvFotoJogador2, this.fotoJogador2Path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onComplete(int code, String tag) {
        switch (code) {
            case ERROR_OK: {
                this.finish();
            }
        }
    }

    private void configurarJogador1() {
        this.mTxtNomeJogador1 = findViewById(R.id.nomeJogador1);
        this.mImvFotoJogador1 = findViewById(R.id.fotoJogador1);

        this.mTxtNomeJogador1.setText(ferramentas.getSavedName());
        this.ferramentas.setPic(this.mImvFotoJogador1, ferramentas.getSavedPhotoPath());

    }

    private void configurarJogador2() {
        this.mTxtNomeJogador2 = findViewById(R.id.nomeJogador2);
        this.mImvFotoJogador2 = findViewById(R.id.fotoJogador2);
    }

    private void configurarJocarComTempo() {
        this.mSwitchJogarComTempo = findViewById(R.id.switchJogarComTempo);
        this.mSwitchJogarComTempo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                jogarComTempo = isChecked;
                mSeekBarTempoMaximo.setEnabled(jogarComTempo);
                mSeekBarTempoGanho.setEnabled(jogarComTempo);
            }
        });
    }

    private void configurarTempoMaximo() {
        this.mTxtTempoMaximo = findViewById(R.id.txtTempoMaximo);
        this.mSeekBarTempoMaximo = findViewById(R.id.seekBarTempoMaximo);
        this.mSeekBarTempoMaximo.setEnabled(jogarComTempo);
        this.mSeekBarTempoMaximo.setMax(TEMPO_MAXIMO_MAX);
        this.mSeekBarTempoMaximo.setProgress(TEMPO_MAXIMO_MAX / 2);
        String minutos = String.valueOf(this.mSeekBarTempoMaximo.getProgress()) + MINUTOS;
        this.mTxtTempoMaximo.setText(minutos);
        this.mSeekBarTempoMaximo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String minutos = String.valueOf(progress) + MINUTOS;

                if (progress < TEMPO_MAXIMO_MIN) {
                    progress = TEMPO_MAXIMO_MIN;
                    minutos = String.valueOf(progress) + MINUTOS;
                }
                mTxtTempoMaximo.setText(minutos);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void configurarTempoGanho() {
        this.mTxtTempoGanho = findViewById(R.id.txtTempoGanho);
        this.mSeekBarTempoGanho = findViewById(R.id.seekBarTempoGanho);
        this.mSeekBarTempoGanho.setEnabled(jogarComTempo);
        this.mSeekBarTempoGanho.setMax(TEMPO_GANHO_MAX + TEMPO_GANHO_MIN);
        this.mSeekBarTempoGanho.setProgress((TEMPO_GANHO_MAX + TEMPO_GANHO_MIN) / 2);
        String segundos = String.valueOf(this.mSeekBarTempoGanho.getProgress()) + SEGUNDOS;
        this.mTxtTempoGanho.setText(segundos);
        this.mSeekBarTempoGanho.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String segundos = String.valueOf(progress) + SEGUNDOS;

                if (progress < TEMPO_GANHO_MIN) {
                    progress = TEMPO_GANHO_MIN;
                } else {
                    mTxtTempoGanho.setText(segundos);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
