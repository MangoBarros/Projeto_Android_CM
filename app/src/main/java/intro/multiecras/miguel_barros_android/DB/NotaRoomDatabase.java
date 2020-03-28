package intro.multiecras.miguel_barros_android.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import intro.multiecras.miguel_barros_android.DB.Categorias.Categoria;
import intro.multiecras.miguel_barros_android.DB.Categorias.CategoriaDao;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;
import intro.multiecras.miguel_barros_android.DB.Notas.NotaDao;

@Database(entities = {Nota.class, Categoria.class}, version = 2, exportSchema = false)
public abstract class NotaRoomDatabase extends RoomDatabase {

    public abstract NotaDao notaDao();
    public abstract CategoriaDao categoriaDao();

    private static NotaRoomDatabase INSTANCE;

    public static NotaRoomDatabase getDatabase(final Context context){
        if( INSTANCE == null){
            synchronized (NotaRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotaRoomDatabase.class, "nota_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final NotaDao mDao;
        String[] titulos = {"dolphin", "crocodile", "cobra","dolphin", "crocodile", "cobra","dolphin", "crocodile", "cobra"};
        String[] descricoes ={"Yes","No","Maybe", "Yes","No","Maybe","Yes","No","Maybe"};
        String[] cidades = {"porto","Viana","Braga","porto","Viana","Braga","porto","Viana","Braga"};

        PopulateDbAsync(NotaRoomDatabase db) {
            mDao =db.notaDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            //mDao.deleteAll();
            if(mDao.getAnyNota().length < 1){
                for (int i = 0; i <= titulos.length - 1; i++) {
                    Nota nota = new Nota(titulos[i],descricoes[i], cidades[i],1);
                    mDao.insert(nota);
                }
            }

            return null;
        }
    }
}
