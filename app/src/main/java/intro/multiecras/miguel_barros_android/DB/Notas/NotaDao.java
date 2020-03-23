package intro.multiecras.miguel_barros_android.DB.Notas;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotaDao {
    @Insert
    void insert(Nota nota);

    @Query("DELETE FROM notas")
    void deleteAll();

    @Delete
    void deleteOne(Nota nota);

    @Query("SELECT * FROM notas")
    LiveData<List<Nota>> getAllNotas();
    }


