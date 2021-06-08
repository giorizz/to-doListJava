package br.com.listadetarefas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.listadetarefas.Adapter.Adapter;
import br.com.listadetarefas.helper.DbHelper;
import br.com.listadetarefas.helper.RecyclerItemClickListener;
import br.com.listadetarefas.helper.TodoDAO;
import br.com.listadetarefas.model.Do;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Do> listDo = new ArrayList<>();
    private Do doesSelectesl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //config do recycler
        recyclerView = findViewById(R.id.recyclerView);

        //configurar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //recupera a tarefa pra edicao
                                Do doSelect = listDo.get(position);

                                //enviar para próxima tela
                                Intent intent = new Intent(MainActivity.this, AddTodoList.class);
                                intent.putExtra("doSelect", doSelect);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //recuperar tarefa seleciomada
                                doesSelectesl= listDo.get(position);
                                //delete item list
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //config titulo e msg
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + doesSelectesl.getNameDo()+"?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TodoDAO todoDAO = new TodoDAO(getApplicationContext());
                                        if (todoDAO.delete(doesSelectesl)){
                                            loadToDoList();
                                        }else{

                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                dialog.create().show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTodoList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadToDoList();
    }

    public void loadToDoList() {

        //listar tarefas
        TodoDAO todoDAO = new TodoDAO(getApplicationContext());
        listDo = todoDAO.list();
        //exibir a lista de tarefas
        //configurar um adapter
        adapter = new Adapter(listDo);

        //configurar um recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}