package intro.multiecras.miguel_barros_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditNotaActivity extends AppCompatActivity{
    private TextView NotaItemView;
    private TextView NotaCidadeView;
    private TextView NotaDescView;
    private String id;

    private Button voltar;
    private Button guardar;

    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        String[] stuff = getIntent().getStringArrayExtra("notaParams");
        SetParams(stuff);
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
                    String[] nota = {id,titulo,cidade,descricao};
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

        NotaItemView = findViewById(R.id.input_title);
        NotaCidadeView = findViewById(R.id.input_cidade);
        NotaDescView = findViewById(R.id.input_desc);

        NotaItemView.setText(notaParams[1]);
        NotaCidadeView.setText(notaParams[3]);
        NotaDescView.setText(notaParams[2]);

        id = notaParams[0];
    }















}
