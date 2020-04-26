package intro.multiecras.miguel_barros_android.Offline;

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
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MakeNotaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    private EditText mTitleView;
    private EditText mDescriptionView;
    private EditText mCidadeView;
    private Spinner mCategoriaView;
    private Button voltar;



    private Integer selected = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        voltar = findViewById(R.id.btn_voltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltarIntent = new Intent();
                setResult(RESULT_FIRST_USER, voltarIntent);
                finish();
            }
        });

        // A P F T U
        List<String> categorias = new ArrayList<String>();
        categorias.add("Automovel");
        categorias.add("Pessoal");
        categorias.add("A-Fazeres");
        categorias.add("Trabalho");
        categorias.add("Urgente");



        mTitleView = findViewById(R.id.input_title);
        mCidadeView = findViewById(R.id.input_cidade);
        mDescriptionView = findViewById(R.id.input_desc);
        mCategoriaView = (Spinner) findViewById(R.id.spin_categoria);

        mCategoriaView.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategoriaView.setAdapter(dataAdapter);



        final Button button = findViewById(R.id.btn_create);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(mTitleView.getText()) && TextUtils.isEmpty(mCidadeView.getText()) && TextUtils.isEmpty(mDescriptionView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);

                }else {
                    // A P F T U
                    //id = 0, titulo = 1, descricao = 2, cidade = 3
                    String titulo = mTitleView.getText().toString();
                    String cidade = mCidadeView.getText().toString();
                    String descricao = mDescriptionView.getText().toString();
                    String categoriaId = String.valueOf(selected);
                    String[] nota = {titulo,descricao,cidade,categoriaId};
                    replyIntent.putExtra(EXTRA_REPLY, nota);
                    setResult(RESULT_OK, replyIntent);


                }
                finish();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.selected = 1;
    }
}
