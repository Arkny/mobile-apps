package persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static int VERSION=1;
    public static String NOME_DB = "DB_SEUBANCO";
    public static String TABELA_CONTATO = "contato";

    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="CREATE TABLE IF NOT EXISTS " +TABELA_CONTATO
                +" ( id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "nomeCOntato TEXT NOT NULL, telContato TEXT);";
        try{
            db.execSQL(sql);
            Log.i("InfoDB","Tabela criada com sucesso");
        }catch (Exception e){
            Log.i("InfoDB","Erro ao criar a tabela erro: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS " + TABELA_CONTATO+";";
        try{
            db.execSQL(sql);
            onCreate(db);
            Log.i("InfoDB","Tabela atualizada com sucesso!");
        }catch (Exception e){
            Log.i("InfoDB","Erro ao atualizar a tabela erro: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

