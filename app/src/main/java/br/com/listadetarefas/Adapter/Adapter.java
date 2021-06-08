package br.com.listadetarefas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.listadetarefas.R;
import br.com.listadetarefas.model.Do;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Do> listDos;
    public Adapter(List<Do> list) {
        this.listDos = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_todo_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Do dos = listDos.get(position);
        holder.todo.setText(dos.getNameDo());
    }

    @Override
    public int getItemCount() {
        return this.listDos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView todo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.todo = itemView.findViewById(R.id.textView);
        }
    }
}
