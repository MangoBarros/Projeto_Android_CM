package intro.multiecras.miguel_barros_android.DB.Categorias;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoriaDao {
    @Insert
    void insert(Categoria categoria);

    @Query("DELETE FROM categorias")
    void deleteAll();

    @Delete
    void deleteOne(Categoria categoria);

    @Query("SELECT * FROM categorias")
    LiveData<List<Categoria>> getAllCategorias();


}
