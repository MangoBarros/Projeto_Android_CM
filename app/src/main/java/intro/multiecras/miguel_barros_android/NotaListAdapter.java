package intro.multiecras.miguel_barros_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;


public class NotaListAdapter extends RecyclerView.Adapter<NotaListAdapter.NotaViewHolder> {
    private final LayoutInflater mInflater;
    private List<Nota> mNotas;


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

    class NotaViewHolder extends RecyclerView.ViewHolder {
        private final TextView NotaItemView;

        private NotaViewHolder(View itemView) {
            super(itemView);
            NotaItemView = itemView.findViewById(R.id.titulo);
        }
    }
}
