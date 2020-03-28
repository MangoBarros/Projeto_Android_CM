package intro.multiecras.miguel_barros_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;


public class NotaListAdapter extends RecyclerView.Adapter<NotaListAdapter.NotaViewHolder>  {
    private final LayoutInflater mInflater;
    private List<Nota> mNotas;
    private static ClickListener clickListener;


    NotaListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.notalist_item, parent, false);
        return new NotaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotaViewHolder holder, int position) {
        if (mNotas != null) {
            Nota current = mNotas.get(position);
            holder.NotaItemView.setText(current.getTitulo());
            holder.NotaDescView.setText(current.getDescricao());
            holder.NotaCidadeView.setText(current.getCidade());
            // A P F T U
            switch (current.getCategoria()){
                case 0:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.AutomovelC));
                    break;
                case 1:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.PessoalC));
                    break;
                case 2:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.FazeresC));
                    break;
                case 3:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.TrabalhoC));
                    break;
                case 4:
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.UrgenteC));
                    break;
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.NotaItemView.setText("No Title");
        }
    }

    void setNotas(List<Nota> notas) {
        mNotas = notas;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNotas != null)
            return mNotas.size();
        else return 0;
    }

    class NotaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private final TextView NotaItemView;
        private final TextView NotaCidadeView;
        private final TextView NotaDescView;



        private NotaViewHolder(View itemView) {
            super(itemView);
            NotaItemView = itemView.findViewById(R.id.titulo);
            NotaCidadeView = itemView.findViewById(R.id.Cidade);
            NotaDescView = itemView.findViewById(R.id.Descricao);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        @Override
        public void onClick(View v){
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(),v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        NotaListAdapter.clickListener = clickListener;
    }

    public Nota getNotaAtPosition (int position){
        return mNotas.get(position);
    }

    public interface ClickListener{
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
