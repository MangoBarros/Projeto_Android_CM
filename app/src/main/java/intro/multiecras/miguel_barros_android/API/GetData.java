package intro.multiecras.miguel_barros_android.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetData {

    @GET("/notas")
    Call<List<Nota>> getAllNotas();


    @GET("/notas/{id}")
    Call<Nota> getNota();



}
