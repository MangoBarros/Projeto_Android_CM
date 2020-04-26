package intro.multiecras.miguel_barros_android.Offline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;
import intro.multiecras.miguel_barros_android.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditNotaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextView NotaItemView;
    private TextView NotaCidadeView;
    private TextView NotaDescView;
    private String id;
    private Spinner mCategoriaView;

    private Integer selected = 1;
    private Integer old;

    private Button voltar;
    private Button guardar;

    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        String[] nota = getIntent().getStringArrayExtra("notaParams");
        SetParams(nota);

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


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltarIntent = new Intent();
                setResult(RESULT_FIRST_USER, voltarIntent);
                finish();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(NotaItemView.getText()) && TextUtils.isEmpty(NotaCidadeView.getText()) && TextUtils.isEmpty(NotaDescView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String titulo = NotaItemView.getText().toString();
                    String cidade = NotaCidadeView.getText().toString();
                    String descricao = NotaDescView.getText().toString();
                    String categoriaId = String.valueOf(selected);
                    String[] nota = {id,titulo,descricao,cidade,categoriaId};

                    //id = 0, titulo = 1, descricao = 2, cidade = 3
                    replyIntent.putExtra(EXTRA_REPLY, nota);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }

        });

    }

    public void SetParams(String[] notaParams){
        voltar=findViewById(R.id.btn_voltar);
        guardar=findViewById(R.id.btn_create);

        guardar.setText("guardar");

        NotaItemView = findViewById(R.id.input_title);
        NotaCidadeView = findViewById(R.id.input_cidade);
        NotaDescView = findViewById(R.id.input_desc);

        //id = 0, titulo = 1, descricao = 2, cidade = 3
        NotaItemView.setText(notaParams[1]);
        NotaDescView.setText(notaParams[2]);
        NotaCidadeView.setText(notaParams[3]);

        id = notaParams[0];
        old = Integer.valueOf(notaParams[4]);
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
