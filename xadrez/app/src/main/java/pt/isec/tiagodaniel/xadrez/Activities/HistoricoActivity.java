package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Historico.Historico;
import pt.isec.tiagodaniel.xadrez.Logic.XadrezApplication;
import pt.isec.tiagodaniel.xadrez.R;

public class HistoricoActivity extends Activity implements Constantes {
    ArrayList<HashMap<String, Object>> dados;
    ArrayList<Historico> listaHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        XadrezApplication xadrezApplication = ((XadrezApplication) this.getApplication());
        this.listaHistorico = xadrezApplication.getHistoricList();

        dados = new ArrayList<>();
        this.mapearHistoricoParaDados();

        ListView lv = findViewById(R.id.lista);
        ViewGroup header = (ViewGroup)getLayoutInflater().inflate(R.layout.header_historico, null);
        lv.addHeaderView(header);
        lv.setAdapter(new MyAdapter());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), HistoricoJogadasActivity.class);
                intent.putExtra(PUT_EXTRA_JOGADAS, listaHistorico.get((int)l).getListaJogadas());
                startActivity(intent);
            }
        });
    }

    private void mapearHistoricoParaDados() {
        Date data = null;
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
                arrayList.add(0, R.mipmap.ic_face_black_18dp);
                arrayList.add(1, R.mipmap.ic_face_black_18dp);
            }
            case JOGADOR_VS_COMPUTADOR: {
                arrayList.clear();
                arrayList.add(0, R.mipmap.ic_face_black_18dp);
                arrayList.add(1, R.mipmap.ic_computer_black_18dp);
            }
            case CRIAR_JOGO_REDE: {
                arrayList.clear();
                arrayList.add(0, R.mipmap.ic_wifi_black_18dp);
                arrayList.add(1, R.mipmap.ic_face_black_18dp);
            }
            case JUNTAR_JOGO_REDE: {
                arrayList.clear();
                arrayList.add(0, R.mipmap.ic_face_black_18dp);
                arrayList.add(1, R.mipmap.ic_wifi_black_18dp);
            }
            default: {
                arrayList.clear();
                arrayList.add(0, R.mipmap.ic_face_black_18dp);
                arrayList.add(1, R.mipmap.ic_face_black_18dp);
            }
        }

        return arrayList;
    }
}
