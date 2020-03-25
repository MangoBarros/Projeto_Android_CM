package intro.multiecras.miguel_barros_android.DB.Notas;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Update;
import intro.multiecras.miguel_barros_android.DB.Categorias.Categoria;

@Entity(tableName = "notas")
public class Nota {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "titulo")
    private String mTitulo;

    @NonNull
    @ColumnInfo(name = "descricao")
    private String mDescricao;

    @NonNull
    @ColumnInfo(name = "cidade")
    private String mCidade;

    public Nota(@NonNull String titulo, @NonNull String descricao, @NonNull String cidade){
        this.mTitulo = titulo;
        this.mDescricao = descricao;
        this.mCidade= cidade;

    }


    public String getTitulo(){
        return this.mTitulo;
    }

    @NonNull
    public String getDescricao() {
        return this.mDescricao;
    }

    @NonNull
    public String getCidade() {
        return this.mCidade;
    }


    public int getId(){
        return this.id;
    }

    public void setTitulo(String Titulo){
        this.mTitulo= Titulo;
    }
    public void setCidade(String Cidade){
        this.mCidade= Cidade;
    }
    public void setmDescricao(String Descricao){
        this.mDescricao= Descricao;
    }

    public void setId(int id){
        this.id = id;
    }


}
