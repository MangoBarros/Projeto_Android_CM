package intro.multiecras.miguel_barros_android;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

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


    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteNota(Nota nota){
        mRepository.deleteNota(nota);
    }

    public Nota getNotaById(Integer id) throws ExecutionException, InterruptedException {

        Nota nota = mRepository.getNota(id);
        return nota;

    }

    public void update(Nota nota) {
        mRepository.updateNota(nota);
    }
}

