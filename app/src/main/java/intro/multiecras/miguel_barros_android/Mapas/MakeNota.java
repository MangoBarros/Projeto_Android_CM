package intro.multiecras.miguel_barros_android.Mapas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import intro.multiecras.miguel_barros_android.API.GetData;
import intro.multiecras.miguel_barros_android.API.Nota;
import intro.multiecras.miguel_barros_android.API.RetrofitClientInstance;
import intro.multiecras.miguel_barros_android.Offline.MainActivity;
import intro.multiecras.miguel_barros_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeNota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_nota);
    }

    public void createNota(View view) {
        GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<Nota>> call = service.getAllNotas("");
        call.enqueue(new Callback<List<Nota>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Nota>> call, Response<List<Nota>> response) {
                assert response.body() != null;
                //Log.i("tag", response.body().toString());
                List<Nota> notas = response.body();
                for( int i = 0; i< notas.size(); i++){
                    Log.i("tag",notas.get(i).getTitulo());
                }

                Toast.makeText(getApplicationContext(),"Nota Criada",Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onFailure(Call<List<Nota>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Algo correu mal tente novamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

