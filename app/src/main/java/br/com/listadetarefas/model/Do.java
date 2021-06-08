package br.com.listadetarefas.model;

import java.io.Serializable;

public class Do implements Serializable {
    private Long id;
    private String nameDo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDo() {
        return nameDo;
    }

    public void setNameDo(String nameDo) {
        this.nameDo = nameDo;
    }
}
