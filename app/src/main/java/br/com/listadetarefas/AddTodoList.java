package br.com.listadetarefas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.listadetarefas.helper.TodoDAO;
import br.com.listadetarefas.model.Do;

public class AddTodoList extends AppCompatActivity {

    private TextInputEditText editTodo;
    private Do doActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_list);

        editTodo = findViewById(R.id.item_todo);

        //recuperar tarefa se for edicao
        doActual = (Do) getIntent().getSerializableExtra("doSelect");

        //config caixa de texto
        if (doActual != null){
            editTodo.setText(doActual.getNameDo());
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_todo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSave :
                //executa a acao pra salvar o item
                TodoDAO todoDAO = new TodoDAO(getApplicationContext());
                if (doActual != null){
                    String nameTodo = editTodo.getText().toString();
                    if (!nameTodo.isEmpty()){
                        Do dos = new Do();
                        dos.setNameDo(nameTodo);
                        dos.setId(doActual.getId());

                        //atualuza no banco
                        if (todoDAO.upgrade(dos)){
                            finish();
                        }else {

                        }
                    }
                }else{
                    String nameTodo = editTodo.getText().toString();
                    if (!nameTodo.isEmpty()){
                        Do dos =  new Do();
                        dos.setNameDo(nameTodo);
                        todoDAO.save(dos);
                        finish();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}