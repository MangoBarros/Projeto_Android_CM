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

public class EditNota extends AppCompatActivity   implements AdapterView.OnItemSelectedListener{

    private TextView NotaItemView;
    private TextView NotaCidadeView;
    private TextView NotaDescView;
    private Integer id;
    private Spinner mCategoriaView;

    private Integer selected = 1;
    private Integer old;


    Nota nota;

    private Button voltar;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nota);
        Intent i = getIntent();
        id = i.getIntExtra("id", -1);

        getNota(id);
    }

    public void SetParams(Nota nota){
        voltar=findViewById(R.id.btn_voltar);
        guardar=findViewById(R.id.btn_create);

        guardar.setText(R.string.guardar);

        NotaItemView = findViewById(R.id.input_title);
        NotaDescView = findViewById(R.id.input_desc);
        //NotaCidadeView = findViewById(R.id.input_cidade);

        //id = 0, titulo = 1, descricao = 2, cidade = 3
        NotaItemView.setText(nota.getTitulo());
        NotaDescView.setText(nota.getDescricao());
        //NotaCidadeView.setText(nota.getFoto());

        id = nota.getId();
        old=nota.getId();

        // A P F T U
        List<String> categorias = new ArrayList<String>();
        categorias.add("Automovel");
        categorias.add("Pessoal");
        categorias.add("A-Fazeres");
        categorias.add("Trabalho");
        categorias.add("Urgente");

        mCategoriaView = (Spinner) findViewById(R.id.spin_categoria);

        mCategoriaView.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategoriaView.setAdapter(dataAdapter);

        mCategoriaView.post(new Runnable() {
            public void run() {
                mCategoriaView.setSelection(old);
            }
        });

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.selected = old;

    }
}
