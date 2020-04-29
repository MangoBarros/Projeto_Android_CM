package intro.multiecras.miguel_barros_android.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetData {

    @GET("notas")
    Call<List<Nota>> getAllNotas(
            @Header("Authorization")
            String token
    );

    @GET("notas/{id}")
    Call<Nota> getNota(
            @Header("Authorization")
                    String token
    );

    @POST("notas")
    Call<Nota> createNota(
            @Header("Authorization")
                    String token,
            @Body()
                Nota nota
    );

    @POST("notas")
    Call<Nota> updateNota(
            @Header("Authorization")
                    String token,
            @Body()
                    Nota nota
    );


    @POST("login")
    Call<User> postLogin(
            @Body()
                    User user
    );

    @POST("logout")
    Call<User> postLogout(
            @Header("Authorization")
                    String token
    );

    @POST("register")
    Call<User> postRegister(
            @Body()
                    User user
    );



}
