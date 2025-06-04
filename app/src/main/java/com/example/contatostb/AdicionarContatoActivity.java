package com.example.contatostb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import model.Contato;
import persistencia.ContatoDAO;

public class AdicionarContatoActivity extends AppCompatActivity {

    EditText edtNomeContato, edtTelefoneContato;

    Button btnSalvar;
    private Contato contatoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_contato);
    edtNomeContato = (EditText) findViewById(R.id.txtNomeContato);
    edtTelefoneContato = (EditText) findViewById(R.id.txtNomeContato);
    btnSalvar = (Button) findViewById(R.id.cmdSalvar);
    contatoAtual = (Contato)getIntent().getSerializableExtra("contatoSelecionado");
    if (contatoAtual!=null){
        edtNomeContato.setText(contatoAtual.getNomeContato());
        edtTelefoneContato.setText(contatoAtual.getTelContato());
    }
    btnSalvar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
            String nomeContato = edtNomeContato.getText().toString();
            String telContato = edtTelefoneContato.getText().toString();
            if (contatoAtual!=null) {

                //Atualizará um registro existente
                if(!nomeContato.isEmpty()){
                    Contato contato = new Contato();
                    contato.setNomeContato(nomeContato);
                    contato.setTelContato(telContato);
                    contato.setId(contatoAtual.getId());
                    if (contatoDAO.atualizar(contato)){
                        finish();
                        Toast.makeText(getApplicationContext(), "Sucesso ao atualizar o registro.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao atualizar o registro.", Toast.LENGTH_LONG).show();
                    }
                }
            } else {

                //Criará um novo registro
                if(!nomeContato.isEmpty()){
                    Contato contato = new Contato();
                    contato.setNomeContato(nomeContato);
                    contato.setTelContato(telContato);
                    if (contatoDAO.salvar(contato)){
                        finish();
                        Toast.makeText(getApplicationContext(), "Sucesso ao salvar o registro.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao salvar o registro.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    });
    }
}