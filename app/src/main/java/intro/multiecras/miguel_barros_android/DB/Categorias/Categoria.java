package intro.multiecras.miguel_barros_android.DB.Categorias;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "categorias")
public class Categoria {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "titulo")
    private String mTitulo;

    public Categoria(@NonNull String titulo){

        this.mTitulo = titulo;
    }

    public String getTitulo(){
        return this.mTitulo;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
}