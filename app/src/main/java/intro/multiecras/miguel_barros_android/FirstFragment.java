package intro.multiecras.miguel_barros_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;


public class FirstFragment extends Fragment {
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




        //ola 2
        //ola 3
        // Inflate the layout for this fragment
        return listFragment;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController( FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

            }
        });


    }


}
