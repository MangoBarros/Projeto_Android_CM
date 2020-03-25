package intro.multiecras.miguel_barros_android.DB.Notas;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Nota nota);

    @Query("DELETE FROM notas")
    void deleteAll();

    @Delete
    void deleteOne(Nota nota);

    @Query("SELECT * FROM notas")
    LiveData<List<Nota>> getAllNotas();


    @Query("SELECT * FROM notas LIMIT 1")
    Nota[] getAnyNota();

    @Query("SELECT * FROM notas WHERE id = :notaId")
    public Nota getNotaById(int notaId);

    @Update
    public void updateNota(Nota nota);
    }


