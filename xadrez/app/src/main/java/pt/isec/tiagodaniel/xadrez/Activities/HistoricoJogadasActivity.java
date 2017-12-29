package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Historico.Jogada;
import pt.isec.tiagodaniel.xadrez.R;

public class HistoricoJogadasActivity extends Activity implements Constantes {
    private ArrayList<Jogada> listaJogadas;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_jogadas);
        this.listaJogadas = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            this.listaJogadas = (ArrayList<Jogada>) bundle.get(PUT_EXTRA_JOGADAS);

            recyclerView = findViewById(R.id.rvLista);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true); //Opcional, caso sejam todos do mesmo tamanho
            recyclerView.setAdapter(new MyRecyclerAdapter());
        } else {
            finish();
        }
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_historico_jogada, parent, false);
            MyViewHolder pvh = new MyViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tvOrigem.setText(listaJogadas.get(position).getOrigem());
            holder.tvDestino.setText(listaJogadas.get(position).getDestino());
            //holder.tvData.setText(listaJogadas.get(position).data.toString());
        }

        @Override
        public int getItemCount() {
            return listaJogadas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvOrigem, tvDestino;
            ImageView ivPeca;
            LinearLayout ll;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvOrigem = itemView.findViewById(R.id.historicoJogadaOrigem);
                tvDestino = itemView.findViewById(R.id.historicoJogadaDestino);
                ivPeca = itemView.findViewById(R.id.historicoJogadaPeca);
            }
        }
    }
}
