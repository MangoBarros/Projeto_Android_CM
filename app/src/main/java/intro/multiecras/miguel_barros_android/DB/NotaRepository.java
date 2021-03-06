package intro.multiecras.miguel_barros_android.DB;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import intro.multiecras.miguel_barros_android.DB.Categorias.Categoria;
import intro.multiecras.miguel_barros_android.DB.Categorias.CategoriaDao;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;
import intro.multiecras.miguel_barros_android.DB.Notas.NotaDao;

public class NotaRepository {
    private NotaDao mNotaDao;
    private CategoriaDao mCategoriaDao;

    private LiveData<List<Nota>> mAllNotas;
    private LiveData<List<Categoria>> mAllCategorias;


    public NotaRepository(Application application){
        NotaRoomDatabase db = NotaRoomDatabase.getDatabase(application);
        mNotaDao = db.notaDao();
        mCategoriaDao = db.categoriaDao();

        mAllNotas = mNotaDao.getAllNotas();
        mAllCategorias = mCategoriaDao.getAllCategorias();
    }

    public LiveData<List<Nota>> getAllNotas(){
        return mAllNotas;
    }
    public LiveData<List<Categoria>> getAllCategorias(){
        return mAllCategorias;
    }


    public void insert(Nota nota){
        new insertAsyncTaskNota(mNotaDao).execute(nota);
    }

    public void deleteNota(Nota nota){
        new deleteNotaAsyncTask(mNotaDao).execute(nota);
    }

    public void updateNota(Nota nota){ new updateNotaAsyncTask(mNotaDao).execute(nota);}

    public void insert(Categoria categoria){
        new insertAsyncTaskCategoria(mCategoriaDao).execute(categoria);
    }

    public void deleteAll() {
        new deleteAllNotasAsyncTask(mNotaDao).execute();
    }

    public Nota getNota(Integer id) throws ExecutionException, InterruptedException {
        Integer[] ids = {id};
        return new getAsyncTaskNota(mNotaDao).execute(ids).get();


    }

    private static class updateNotaAsyncTask extends AsyncTask<Nota, Void, Void>{
        private NotaDao mAsyncTaskDao;

        updateNotaAsyncTask(NotaDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Nota... notas) {
            mAsyncTaskDao.updateNota(notas[0]);
            return null;
        }
    }

    private static class getAsyncTaskNota extends AsyncTask<Integer, Void,Nota>{
        private NotaDao mAsyncTaskDao;

        getAsyncTaskNota(NotaDao dao){
            mAsyncTaskDao = dao;
        };

        @Override
        protected Nota doInBackground(Integer... integers) {
            Log.i("wow", String.valueOf(integers.length));

            return mAsyncTaskDao.getNotaById(integers[0]);
        }
    }

    //Insert Async to nota
    private static class insertAsyncTaskNota extends AsyncTask<Nota, Void, Void> {
        private NotaDao mAsyncTaskDao;

        insertAsyncTaskNota(NotaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Nota... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //Insert Async to Categoria
    private static class insertAsyncTaskCategoria extends AsyncTask<Categoria, Void, Void> {
        private CategoriaDao mAsyncTaskDao;

        insertAsyncTaskCategoria(CategoriaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Categoria... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //Delete Aync to Nota
    private static class deleteAllNotasAsyncTask extends AsyncTask<Void, Void, Void>{
        private NotaDao mAsyncNotaDao;

        deleteAllNotasAsyncTask(NotaDao dao){
            mAsyncNotaDao = dao;
        }

        protected Void doInBackground(Void... voids){
            mAsyncNotaDao.deleteAll();
            return null;

        }
    }
    private static class deleteNotaAsyncTask extends AsyncTask<Nota, Void, Void>{
        private NotaDao mAsyncTaskDao;

        deleteNotaAsyncTask(NotaDao dao){

            mAsyncTaskDao = dao;

        }
        @Override
        protected Void doInBackground(final Nota... params) {
            mAsyncTaskDao.deleteOne(params[0]);
            return null;
        }
    }
}
