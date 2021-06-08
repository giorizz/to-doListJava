package br.com.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.listadetarefas.model.Do;

public class TodoDAO implements ITodoDAO{

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public TodoDAO(Context context) {
        DbHelper db = new DbHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean save(Do dos) {
        ContentValues cv = new ContentValues();
        cv.put("nome", dos.getNameDo());
        try {
            write.insert(DbHelper.TABELA_TAREFAS, null, cv);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean upgrade(Do does) {
        String[] args = {does.getId().toString()};
        ContentValues cv = new ContentValues();
        cv.put("nome", does.getNameDo());
        try {
            write.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Do does) {
        String[] args = {does.getId().toString()};

        try {
            write.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
        }catch (Exception e){
            return false;
        }
        return true;    }

    @Override
    public List<Do> list() {

        List<Do> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()){
            Do dos = new Do();

            Long id = c.getLong(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("nome"));
            dos.setId(id);
            dos.setNameDo(name);

            tarefas.add(dos);
        }

        return tarefas;
    }
}
