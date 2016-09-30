package aulas.pdmi.bd_carros.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import aulas.pdmi.bd_carros.R;
import aulas.pdmi.bd_carros.model.Carro;


/**
 * Esta classe realiza a adaptação dos dados entre a RecyclerView <-> List.
 * Neste projeto a List está sendo alimentada com dados oriundos de um banco de dados SQLite.
 * @author Vagner Pinto da Silva
 */
public class CarrosAdapter extends RecyclerView.Adapter<CarrosAdapter.CarrosViewHolder> {
    protected static final String TAG = "CarrosAdapter";
    private final List<Carro> carros;
    private final Context context;

    private CarroOnClickListener carroOnClickListener;

    public CarrosAdapter(Context context, List<Carro> carros, CarroOnClickListener carroOnClickListener) {
        this.context = context;
        this.carros = carros;
        this.carroOnClickListener = carroOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.carros != null ? this.carros.size() : 0;
    }

    @Override
    public CarrosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_carros, viewGroup, false);

        // Cria o ViewHolder
        CarrosViewHolder holder = new CarrosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarrosViewHolder holder, final int position) {
        // Atualiza a view
        Carro c = carros.get(position);
        Log.d(TAG, "Carro no Adapter da RecyclerView: " + c.toString());

        Log.d(TAG, c.toString());

        holder.tNome.setText(c.nome);
        holder.progress.setVisibility(View.VISIBLE);
        if(c.urlFoto != null){
            holder.img.setImageURI(Uri.parse(c.urlFoto));
        }else{
            holder.img.setImageResource(R.drawable.car_background);
        }

        // Click
        if (carroOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carroOnClickListener.onClickCarro(holder.itemView, position); // A variável position é final
                }
            });
        }

        holder.progress.setVisibility(View.INVISIBLE);
    }

    public interface CarroOnClickListener {
        public void onClickCarro(View view, int idx);
    }

    // ViewHolder com as views
    public static class CarrosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        ImageView img;
        ProgressBar progress;

        public CarrosViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.textView_card_adaptercarro);
            img = (ImageView) view.findViewById(R.id.imageView_card_adaptercarro);
            progress = (ProgressBar) view.findViewById(R.id.progressBar_card_adaptercarro);
        }
    }
}
