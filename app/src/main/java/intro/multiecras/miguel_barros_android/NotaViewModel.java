package intro.multiecras.miguel_barros_android;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import intro.multiecras.miguel_barros_android.DB.Categorias.Categoria;
import intro.multiecras.miguel_barros_android.DB.NotaRepository;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;

public class NotaViewModel extends AndroidViewModel {
    private NotaRepository mRepository;
    private LiveData<List<Nota>> mAllNotas;
    private LiveData<List<Categoria>> mAllCategorias;

    public NotaViewModel (Application application){
        super(application);
        mRepository = new NotaRepository(application);
        mAllNotas = mRepository.getAllNotas();
        mAllCategorias = mRepository.getAllCategorias();
    }



    LiveData<List<Nota>> getAllNotas(){
        return mAllNotas;
    }

    public void insert(Nota nota){
        mRepository.insert(nota);
    }

}

