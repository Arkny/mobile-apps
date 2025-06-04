package com.example.contatostb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ListaContatosActivity extends AppCompatActivity {
    Button btnAdicionarContato;
    RecyclerView recyclerViewContatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_contatos);
        recyclerViewContatos =(RecyclerView) findViewById(R.id.rvListaContatos);
        btnAdicionarContato = (Button) findViewById(R.id.cmdAdicionarContato);
        btnAdicionarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencao = new Intent(ListaContatosActivity.this, AdicionarContatoActivity.class);


                startActivity(intencao);
            }

        });
    }
}