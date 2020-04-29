package intro.multiecras.miguel_barros_android.Mapas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import intro.multiecras.miguel_barros_android.API.GetData;
import intro.multiecras.miguel_barros_android.API.Nota;
import intro.multiecras.miguel_barros_android.API.RetrofitClientInstance;
import intro.multiecras.miguel_barros_android.API.User;
import intro.multiecras.miguel_barros_android.Offline.MainActivity;
import intro.multiecras.miguel_barros_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static intro.multiecras.miguel_barros_android.Account.LoginActivity.SHARED_PREFS;

public class MakeNota extends AppCompatActivity {

    private TextView mail;
    private TextView  wow;
    private String coordenates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_nota);
        Intent i = getIntent();
        coordenates = i.getStringExtra("coordenates");
        Log.i("tag", coordenates);

    }

    public void createNota(View view) {
        GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Nota nota = new Nota(sharedPreferences.getInt("id", 0),"emergency","Buraco", "Grande Descricao","wow.png", coordenates);
        Call<Nota> call = service.createNota(sharedPreferences.getString("token",""), nota);
        Log.i("tag", nota.getTitulo());
        Log.i("tag", nota.getCategoria());
        Log.i("tag", nota.getCoordenates());
        Log.i("tag", nota.getFoto());
        Log.i("tag", nota.getUserId().toString());
        Log.i("tag", String.valueOf(nota.getId()));
        call.enqueue(new Callback<Nota>() {

            @Override
            public void onResponse(Call<Nota> call, Response<Nota> response) {
                if(response.body().getTitulo() != null){
                    Log.i("tag", "created nota");
                    Intent intent = new Intent(getApplicationContext(), MapaActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Log.i("tag", "not created nota");
                    Toast.makeText(getApplicationContext(), "Algo correu mal tente novamente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Nota> call, Throwable t) {
                Log.i("tag", "Error on create");
            }
        });

        }
    }


