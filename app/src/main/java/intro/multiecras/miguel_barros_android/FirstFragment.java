package intro.multiecras.miguel_barros_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;


import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FirstFragment extends Fragment {
    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";
    private static final int NEW_NOTA_ACTIVITY_REQUEST_CODE = 1;

    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private NotaListAdapter mAdapter;
    private NotaViewModel mNotaViewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        final View listFragment= inflater.inflate(R.layout.fragment_first, container, false);

        RecyclerView recyclerView = listFragment.findViewById(R.id.recyclerview);
        mAdapter = new NotaListAdapter(this.getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        mNotaViewModel = ViewModelProviders.of(this).get(NotaViewModel.class);

        mNotaViewModel.getAllNotas().observe(getViewLifecycleOwner(), new Observer<List<Nota>>() {
            @Override
            public void onChanged(List<Nota> notas) {
                if(notas != null){
                    mAdapter.setNotas(notas);
                }

            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Nota myNota = mAdapter.getNotaAtPosition(position);
                        Toast.makeText(getContext(),"Note Deleted",Toast.LENGTH_LONG).show();

                        // Delete the word
                        mNotaViewModel.deleteNota(myNota);
                    }
                });
        helper.attachToRecyclerView(recyclerView);




        //ola 2
        //ola 3
        // Inflate the layout for this fragment
        return listFragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] nota = data.getStringArrayExtra(MakeNotaActivity.EXTRA_REPLY);
            String titulo = nota[0];
            String cidade = nota[1];
            String descricao = nota[2];

            Nota notaFinal = new Nota(titulo,cidade,descricao);
            mNotaViewModel.insert(notaFinal);
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MakeNotaActivity.class);
                startActivityForResult(intent, NEW_NOTA_ACTIVITY_REQUEST_CODE);

            }
        });


    }


}
