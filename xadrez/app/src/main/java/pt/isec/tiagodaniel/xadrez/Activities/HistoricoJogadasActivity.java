package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            holder.ivPeca.setImageResource(getPecaDrawableID(listaJogadas.get(position).getNomePeca(), position));
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

    private Integer getPecaDrawableID(String nomePeca, int posicao) {
        switch (nomePeca) {
            case PEAO: {
                return posicao % 2 == 0 ? R.drawable.b_peao : R.drawable.p_peao;
            }
            case BISPO: {
                return posicao % 2 == 0 ? R.drawable.b_bispo : R.drawable.p_bispo;
            }
            case CAVALO: {
                return posicao % 2 == 0 ? R.drawable.b_cavalo : R.drawable.p_cavalo;
            }
            case RAINHA: {
                return posicao % 2 == 0 ? R.drawable.b_rainha : R.drawable.p_rainha;
            }
            case REI: {
                return posicao % 2 == 0 ? R.drawable.b_rei : R.drawable.p_rei;
            }
            case TORRE: {
                return posicao % 2 == 0 ? R.drawable.b_torre : R.drawable.p_torre;
            }
            default: {
                return posicao % 2 == 0 ? R.drawable.b_peao : R.drawable.p_peao;
            }
        }

    }
}
