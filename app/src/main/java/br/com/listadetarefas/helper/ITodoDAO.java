package br.com.listadetarefas.helper;

import java.util.List;

import br.com.listadetarefas.model.Do;

public interface ITodoDAO {

    public boolean save(Do dos);
    public boolean upgrade(Do does);
    public boolean delete(Do does);
    public List<Do> list();
}
