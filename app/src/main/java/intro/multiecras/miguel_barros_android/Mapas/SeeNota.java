package intro.multiecras.miguel_barros_android.Mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;

import org.w3c.dom.Text;

import java.util.List;

import intro.multiecras.miguel_barros_android.API.GetData;
import intro.multiecras.miguel_barros_android.API.Nota;
import intro.multiecras.miguel_barros_android.API.RetrofitClientInstance;
import intro.multiecras.miguel_barros_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static intro.multiecras.miguel_barros_android.Account.LoginActivity.SHARED_PREFS;

public class SeeNota extends AppCompatActivity {

    Integer id;
    Nota nota;

    TextView titulo;
    TextView categoria;
    TextView descricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_nota);

        Intent i = getIntent();
        id = i.getIntExtra("id", -1);

        getNota(id);
    }

    private void fillNota(Nota nota) {

        titulo = findViewById(R.id.tituloNota);
        categoria = findViewById(R.id.Categoria);
        descricao = findViewById(R.id.descricao);


        titulo.setText(nota.getTitulo());
        categoria.setText(nota.getCategoria());
        descricao.setText(nota.getDescricao());

    }

    private void getNota(Integer id) {
        GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Call<Nota> call = service.getNota(id,sharedPreferences.getString("token", ""));
        call.enqueue(new Callback<Nota>() {

            @Override
            public void onResponse(Call<Nota> call, Response<Nota> response) {
                if(response.body() != null){
                    nota = new Nota(
                            response.body().getUserId(),
                            response.body().getCategoria(),
                            response.body().getTitulo(),
                            response.body().getDescricao(),
                            response.body().getFoto(),
                            response.body().getCoordenates()
                    );
                    fillNota(nota);
                }else {
                    Toast.makeText(getApplicationContext(),"Esta Nota não Está disponivel", Toast.LENGTH_SHORT);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Nota> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Esta Nota não Está disponivel", Toast.LENGTH_SHORT);
                //finish();
            }
        });
    }

    public void editNota(View view) {

        Intent i = new Intent(getApplicationContext(),EditNota.class);
        i.putExtra("id", nota.getId());
        startActivity(i);
    }
}
