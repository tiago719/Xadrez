package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import pt.isec.tiagodaniel.xadrez.Dialogs.ErrorDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.SocketHandler;
import pt.isec.tiagodaniel.xadrez.R;

public class ConfigurarJogoRedeActivity extends Activity implements Constantes {
    SeekBar mSeekBarTempoMaximo, mSeekBarTempoGanho;
    TextView mTxtTempoMaximo, mTxtTempoGanho;
    Switch mSwitchJogarComTempo;
    boolean jogarComTempo = false;

    private ServerSocket serverSocket = null;
    private Socket socketGame = null;
    private Handler procMsg = null;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_jogo_rede);

        this.procMsg = new Handler();

        this.configurarJocarComTempo();
        this.configurarTempoMaximo();
        this.configurarTempoGanho();
    }

    public void onComecarJogo_JOG_REDE(View v) {
        this.showProgressDialog();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(SERVER_PORT);
                    socketGame = serverSocket.accept();
                    serverSocket.close();
                    serverSocket = null;
                    SocketHandler.setClientSocket(socketGame);
                    startGame();
                } catch (Exception e) {
                    e.printStackTrace();
                    socketGame = null;
                }
                procMsg.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (socketGame == null) {
                            finish();
                        }
                    }
                });
            }
        });
        t.start();
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
        this.mSeekBarTempoMaximo.setProgress(TEMPO_MAXIMO_MAX/2);
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

    private void showProgressDialog() {
        String ip = this.getLocalIpAddress();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_message) + "\n(IP: " + ip
                + ")");
        progressDialog.setTitle(R.string.progress_title);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();

                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        ErrorDialog errorDialog = new ErrorDialog((OnCompleteListener)getBaseContext(), getString(R.string.error_socket_message));
                        errorDialog.show(getFragmentManager(), ERROR_DIALOG);
                    }
                    serverSocket = null;
                }

            }
        });
        progressDialog.show();
    }

    private void startGame() {
        Intent intent = new Intent(this, JogarContraPCActivity.class);
        Bundle bundle = new Bundle();

        bundle.putBoolean(TEMPO_JOGO_JOGvsJOG, jogarComTempo);
        if (jogarComTempo) {
            int tempoMax=this.mSeekBarTempoMaximo.getProgress();
            if(tempoMax<2)
                tempoMax=2;
            bundle.putLong(TEMPO_MAX_JOGO_JOGvsJOG, tempoMax);
            bundle.putLong(TEMPO_GANHO_JOGO_JOGvsJOG, this.mSeekBarTempoGanho.getProgress());
        }

        intent.putExtras(bundle);
        intent.setAction(Constantes.ACTION_CRIAR_JOGO_REDE);
        intent.putExtra(DEVICE_TYPE, SERVIDOR);
        startActivity(intent);
        this.finish();
    }

    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
