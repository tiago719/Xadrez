package pt.isec.tiagodaniel.xadrez.Logic;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.Historico.Historico;

public class XadrezApplication extends Application {
    private static XadrezApplication singleton;
    private ArrayList<Historico> historicList;
    private int modoJogo;
    private long cronometroJogBrancasTempoStop, cronometroJogPretasTempoStop;
    private Bitmap fotoJogador2;
    private String pathFotoJogador2;
    private String nomeJogador2;
    private Jogador jogadorServidor;

    public static XadrezApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        this.historicList = new ArrayList<>();
        this.getHistorico();
        cronometroJogBrancasTempoStop=0;
        cronometroJogPretasTempoStop=0;
    }

    public ArrayList<Historico> getHistorico() {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = this.openFileInput(Constantes.HISTORIC_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.historicList = (ArrayList<Historico>) objectInputStream.readObject();
            objectInputStream.close();
            return this.historicList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void guardarHistorico(Historico historico) throws IOException {
        this.addHistoric(historico);

        FileOutputStream fileOutputStream = this.openFileOutput(Constantes.HISTORIC_FILE_NAME, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.historicList);
        objectOutputStream.close();
    }

    private void addHistoric(Historico historico) {
        if (this.historicList.size() == 5) {
            this.historicList.remove(0);
        }

        this.historicList.add(historico);
    }

    public void limpaHistorico() throws IOException {
        String directory = getFilesDir().getAbsolutePath();
        File file = new File(directory, Constantes.HISTORIC_FILE_NAME);
        boolean result = file.delete();

        if (result) {
            this.historicList.clear();
        } else {
            throw new IOException();
        }
    }

    public void setModoJogo(int modoJogo) {
        this.modoJogo = modoJogo;
    }

    public int getModoJogo() {
        return this.modoJogo;
    }

    public long getCronometroJogBrancasTempoStop()
    {
        return cronometroJogBrancasTempoStop;
    }

    public void setCronometroJogBrancasTempoStop(long cronometroJogBrancasTempoStop)
    {
        this.cronometroJogBrancasTempoStop = cronometroJogBrancasTempoStop;
    }

    public long getCronometroJogPretasTempoStop()
    {
        return cronometroJogPretasTempoStop;
    }

    public void setCronometroJogPretasTempoStop(long cronometroJogPretasTempoStop)
    {
        this.cronometroJogPretasTempoStop = cronometroJogPretasTempoStop;
    }

    public Bitmap getFotoJogador2() {
        return fotoJogador2;
    }

    public void setFotoJogador2(Bitmap fotoJogador2) {
        this.fotoJogador2 = fotoJogador2;
    }

    public String getNomeJogador2() {
        return nomeJogador2;
    }

    public void setNomeJogador2(String nomeJogador2) {
        this.nomeJogador2 = nomeJogador2;
    }

    public String getPathFotoJogador2() {
        return pathFotoJogador2;
    }

    public void setPathFotoJogador2(String pathFotoJogador2) {
        this.pathFotoJogador2 = pathFotoJogador2;
    }

    public void resetTempos()
    {
        cronometroJogBrancasTempoStop=0;
        cronometroJogPretasTempoStop=0;
    }
    public Jogador getJogadorServidor() {
        return jogadorServidor;
    }

    public void setJogadorServidor(Jogador jogadorServidor) {
        this.jogadorServidor = jogadorServidor;
    }
}
