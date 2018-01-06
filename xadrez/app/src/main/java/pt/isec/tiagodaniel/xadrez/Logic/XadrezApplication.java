package pt.isec.tiagodaniel.xadrez.Logic;

import android.app.Application;
import android.content.Context;

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
    private ArrayList<Historico> historico;
    private int modoJogo;

    public static XadrezApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        this.historico = new ArrayList<>();
        try {
            this.getHistorico();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //TODO tratar error
            e.printStackTrace();
        }
    }

    public ArrayList<Historico> getHistorico() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = this.openFileInput(Constantes.HISTORIC_FILE_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        this.historico = (ArrayList<Historico>) objectInputStream.readObject();
        objectInputStream.close();
        return this.historico;

    }

    public void guardarHistorico(Historico historico) throws IOException {
        if (this.historico.size() == 5) {
            this.historico.remove(0);
        }
        this.historico.add(historico);

        FileOutputStream fileOutputStream = this.openFileOutput(Constantes.HISTORIC_FILE_NAME, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.historico);
        objectOutputStream.close();
    }

    public void limpaHistorico() throws IOException {
        String directory = getFilesDir().getAbsolutePath();
        File file = new File(directory, Constantes.HISTORIC_FILE_NAME);
        boolean result = file.delete();

        if (result) {
            this.historico.clear();
        } else {
            throw new IOException();
        }
    }

    /*
    public void setModoJogo(int modoJogo) {
        this.modoJogo = modoJogo;
    }

    public int getModoJogo() {
        return this.modoJogo;
    }
    */
}
