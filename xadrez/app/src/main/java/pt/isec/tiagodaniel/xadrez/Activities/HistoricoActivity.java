package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import pt.isec.tiagodaniel.xadrez.Dialogs.ErrorDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Dialogs.QuestionDialog;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Historico.Historico;
import pt.isec.tiagodaniel.xadrez.Logic.XadrezApplication;
import pt.isec.tiagodaniel.xadrez.R;

public class HistoricoActivity extends Activity implements Constantes, OnCompleteListener {
    ArrayList<HashMap<String, Object>> dados;
    ArrayList<Historico> listaHistorico;
    XadrezApplication xadrezApplication;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        this.xadrezApplication = (XadrezApplication) this.getApplication();
        this.listaHistorico = this.xadrezApplication.getHistorico();

        dados = new ArrayList<>();
        this.mapearHistoricoParaDados();

        ListView lv = findViewById(R.id.lista);
        ViewGroup header = (ViewGroup) getLayoutInflater().inflate(R.layout.header_historico, null);
        lv.addHeaderView(header);
        lv.setEmptyView(findViewById(R.id.empty_list_view));
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), HistoricoJogadasActivity.class);
                intent.putExtra(PUT_EXTRA_JOGADAS, listaHistorico.get((int) l).getListaJogadas());
                startActivity(intent);
            }
        });
    }

    private void mapearHistoricoParaDados() {
        Date data;
        ArrayList<Integer> modoJogoDrawableIDs;
        String vencedor;

        for (Historico historico : this.listaHistorico) {
            data = historico.getDataJogo();
            modoJogoDrawableIDs = this.getModoJogoDrawableID(historico.getModoJogo());
            vencedor = historico.getVencedorJogo();

            this.adiciona_elemento(data, modoJogoDrawableIDs.get(0), modoJogoDrawableIDs.get(1), vencedor);
        }
    }

    private void adiciona_elemento(Date data, int modoJogo1, int modoJogo2, String vencedor) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put(HISTORICO_DATA, data);
        hm.put(HISTORICO_MODO_JOGO1, modoJogo1);
        hm.put(HISTORICO_MODO_JOGO2, modoJogo2);
        hm.put(HISTORICO_VENCEDOR, vencedor);
        this.dados.add(hm);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dados.size();
        }

        @Override
        public Object getItem(int i) {
            return dados.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout = getLayoutInflater().inflate(R.layout.linha_historico, null);

            Date data = (Date) dados.get(i).get(HISTORICO_DATA);
            int modoJogo1 = (int) dados.get(i).get(HISTORICO_MODO_JOGO1);
            int modoJogo2 = (int) dados.get(i).get(HISTORICO_MODO_JOGO2);
            String vencedor = (String) dados.get(i).get(HISTORICO_VENCEDOR);

            ((TextView) layout.findViewById(R.id.historicoData)).setText(new SimpleDateFormat("dd-MM-yyyy").format(data));
            ((TextView) layout.findViewById(R.id.historicoVencedor)).setText(vencedor);
            ImageView iv1 = (ImageView) layout.findViewById(R.id.historicoModoJogo1);
            ImageView iv2 = (ImageView) layout.findViewById(R.id.historicoModoJogo2);
            iv1.setImageResource(modoJogo1);
            iv2.setImageResource(modoJogo2);

            return layout;
        }
    }

    private ArrayList<Integer> getModoJogoDrawableID(int modoJogo) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        switch (modoJogo) {
            case JOGADOR_VS_JOGADOR: {
                arrayList.clear();
                arrayList.add(0, R.drawable.ic_face_black_18dp);
                arrayList.add(1, R.drawable.ic_face_black_18dp);
                break;
            }
            case JOGADOR_VS_COMPUTADOR: {
                arrayList.clear();
                arrayList.add(0, R.drawable.ic_face_black_18dp);
                arrayList.add(1, R.drawable.ic_computer_black_18dp);
                break;
            }
            case CRIAR_JOGO_REDE: {
                arrayList.clear();
                arrayList.add(0, R.drawable.ic_wifi_black_18dp);
                arrayList.add(1, R.drawable.ic_face_black_18dp);
                break;
            }
            case JUNTAR_JOGO_REDE: {
                arrayList.clear();
                arrayList.add(0, R.drawable.ic_face_black_18dp);
                arrayList.add(1, R.drawable.ic_wifi_black_18dp);
                break;
            }
            default: {
                arrayList.clear();
                arrayList.add(0, R.drawable.ic_face_black_18dp);
                arrayList.add(1, R.drawable.ic_face_black_18dp);
            }
        }

        return arrayList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_historico, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.apagarHistorico) {
            QuestionDialog questionDialog = new QuestionDialog(
                    this,
                    getString(R.string.question_delete_historic_title),
                    getString(R.string.question_delete_historic_message),
                    QUESTION_DIALOG);
            questionDialog.show(getFragmentManager(), QUESTION_DIALOG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(int code, String tag) {
        switch (code) {
            case QUESTION_OK: {
                try {
                    if(!dados.isEmpty()) {
                        this.xadrezApplication.limpaHistorico();
                        this.dados.clear();
                        this.myAdapter.notifyDataSetChanged();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case QUESTION_CANCELAR: {
                return;
            }
            case ERROR_OK: {
                this.finish();
                break;
            }
        }
    }
}
