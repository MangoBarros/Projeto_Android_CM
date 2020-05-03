package intro.multiecras.miguel_barros_android.Mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import intro.multiecras.miguel_barros_android.API.GetData;
import intro.multiecras.miguel_barros_android.API.Nota;
import intro.multiecras.miguel_barros_android.API.RetrofitClientInstance;
import intro.multiecras.miguel_barros_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static intro.multiecras.miguel_barros_android.Account.LoginActivity.SHARED_PREFS;

public class EditNota extends AppCompatActivity {

    private TextView NotaItemView;
    private TextView NotaDescView;
    private Integer id;
    SharedPreferences sharedPreferences;

    Nota nota;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nota);
        Intent i = getIntent();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id = i.getIntExtra("id", -1);
        NotaItemView = findViewById(R.id.input_edit_title);
        NotaDescView = findViewById(R.id.input_edit_desc);
        getNota(id);
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
                    NotaItemView.setText(nota.getTitulo());
                    NotaDescView.setText(nota.getDescricao());
                }else {
                    Toast.makeText(getApplicationContext(),"Esta Nota não Está disponivel", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Nota> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Esta Nota não Está disponivel", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
    }

    public void editNota(View view) {
        if (NotaItemView.length()>0 && NotaDescView.length() > 0) {
            GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
            Nota updateNota = new Nota(sharedPreferences.getInt("id", -1), "categoria", NotaItemView.getText().toString(), NotaDescView.getText().toString(), "pick", nota.getCoordenates());
            Call<Nota> call = service.updateNota(id, sharedPreferences.getString("token", ""), updateNota);
            call.enqueue(new Callback<Nota>() {

                @Override
                public void onResponse(Call<Nota> call, Response<Nota> response) {
                    if (response.body() != null) {
                        try {
                            nota = new Nota(
                                    response.body().getUserId(),
                                    response.body().getCategoria(),
                                    response.body().getTitulo(),
                                    response.body().getDescricao(),
                                    response.body().getFoto(),
                                    response.body().getCoordenates()
                            );
                            finish();
                            Toast.makeText(getApplicationContext(), "Nota editada com sucesso", Toast.LENGTH_SHORT);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Alguma coisa correu mal, tente novamente", Toast.LENGTH_SHORT);
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Alguma coisa correu mal, tente novamente", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<Nota> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Alguma coisa correu mal, tente novamente", Toast.LENGTH_SHORT);
                    //finish();
                }
            });

        }

    }
}
