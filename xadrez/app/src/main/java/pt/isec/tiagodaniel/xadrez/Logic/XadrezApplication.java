package pt.isec.tiagodaniel.xadrez.Logic;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.Historico.Historico;

/**
 * Created by drmoreira on 29-12-2017.
 */

public class XadrezApplication extends Application {
    private static XadrezApplication singleton;
    private ArrayList<Historico> historicList;
    private int modoJogo;

    public static XadrezApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        this.historicList = new ArrayList<>();
        this.getHistoricList();
    }

    public ArrayList<Historico> getHistoricList() {

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

    public void saveHistoricList(Historico historico) throws IOException {
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

    public void removeHistoric() throws IOException {
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
}
