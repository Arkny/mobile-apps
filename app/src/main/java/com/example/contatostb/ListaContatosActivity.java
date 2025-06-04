package com.example.contatostb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterContato;
import model.Contato;
import persistencia.ContatoDAO;

public class ListaContatosActivity extends AppCompatActivity {
    Button btnAdicionarContato;
    RecyclerView recyclerViewContatos;
    private AdapterContato contatoAdapter;
    private List<Contato> listaContato = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_contatos);
        recyclerViewContatos = (RecyclerView) findViewById(R.id.rvListaContatos);
        btnAdicionarContato = (Button) findViewById(R.id.cmdAdicionarContato);
        btnAdicionarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencao = new Intent(ListaContatosActivity.this, AdicionarContatoActivity.class);
                startActivity(intencao);
            }
        });
    }
    public void carregarListaContato(){
        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
        listaContato = contatoDAO.listar();
        //Configurar o adapter com os dados vindo do SQLite//
        contatoAdapter = new AdapterContato(listaContato);
        //configurar o listener dos cliques do recyclerView diretamente do adapter
        contatoAdapter.setOnItemClickListener(new AdapterContato.OnItemClickListener() {
            @Override
            public void onItemClick(Contato contato, int position) {
                Intent intencao = new Intent(ListaContatosActivity.this, AdicionarContatoActivity.class);
                intencao.putExtra("contatoSelecionado", contato);
                startActivity(intencao);
            }

            @Override
            public void onItemLongClick(Contato contato, int position) {
                mostraDialogoExclusao(contato);
            }
        });
        //Configurar o LayoutManager  ao RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewContatos.setLayoutManager(layoutManager);
        recyclerViewContatos.setHasFixedSize(true);
        recyclerViewContatos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewContatos.setAdapter(contatoAdapter);
    }
    private void mostraDialogoExclusao(Contato contato){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ListaContatosActivity.this);
        dialog.setTitle("Confirma a exclusão");
        dialog.setMessage("Deseja excluir o contato:" + contato.getNomeContato()+ "?");
        dialog.setPositiveButton("Sim", (dialogInteface,i) -> {
            ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
            if(contatoDAO.excluir(contato)){
                carregarListaContato();
                Toast.makeText(ListaContatosActivity.this, "Sucesso ao excluir o registro",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ListaContatosActivity.this, "Erro ao excluir o registro",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Não", null);
        dialog.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        carregarListaContato();
        super.onStart();
    }
}