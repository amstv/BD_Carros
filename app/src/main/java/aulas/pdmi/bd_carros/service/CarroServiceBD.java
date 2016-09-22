package aulas.pdmi.bd_carros.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aulas.pdmi.bd_carros.model.Carro;

/**
 * Esta classe cria o banco de dados e fornece métodos para operações CRUD.
 * Created by vagner on 06/09/16.
 */
public class CarroServiceBD extends SQLiteOpenHelper {

    private static String TAG = "bdcarros";
    private static String NAME = "carro.sqlite";
    private static int VERSION = 1;
    private static CarroServiceBD carroServiceBD = null;


    /*
        Construtor
     */
    private CarroServiceBD(Context context) {
        super(context, NAME, null, VERSION);
        getWritableDatabase(); //abre a conexão com o bd, utilizado pelo onCreate()
    }

    //Singleton
    public static CarroServiceBD getInstance(Context context){
        if(carroServiceBD == null){
            return carroServiceBD = new CarroServiceBD(context);
        }

        return carroServiceBD;
    }


    /*
        Métodos do ciclo de vida.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //cria a tabela
        String sql = "create table if not exists carro " +
                "(_id integer primary key autoincrement, " +
                "nome text, " +
                "desc text, " +
                "tipo text, " +
                "url_foto text, " +
                "url_video text, " +
                "latitude text, " +
                "longitude text);";
        Log.d(TAG, "Criando a tabela carro. Aguarde ...");
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Tabela carro criada com sucesso.");
        new Task().execute(); //popula o bd
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
        CRUD
     */
    public List<Carro> getAll(){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try {
            return toList(sqLiteDatabase.rawQuery("select * from carro", null));
        }finally {
            sqLiteDatabase.close(); //libera o recurso
        }

    }

    public List<Carro> getByTipo(String tipo){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try{
            return toList(sqLiteDatabase.rawQuery("select * from carro where tipo = '" + tipo + "'", null));
        }finally {
            sqLiteDatabase.close(); //libera o recurso
        }

    }

    public long save(Carro carro){

        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco

        try{
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("nome", carro.nome);
            values.put("desc", carro.desc);
            values.put("tipo", carro.tipo);
            values.put("url_foto", carro.urlFoto);
            values.put("url_video", carro.urlVideo);
            values.put("latitude", carro.latitude);
            values.put("longitude", carro.longitude);

            //realiza a operação
            if(carro.id == null){
                //insere no banco de dados
                return db.insert("carro", null, values);
            }else{
                //altera no banco de dados
                values.put("_id", carro.id);
                return db.update("carro", values, "_id=" + carro.id, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close(); //libera o recurso
        }

        return 0L; //caso não realize a operação
    }

    public long delete(Carro carro){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("carro", "_id=?", new String[]{String.valueOf(carro.id)});
        }
        finally {
            db.close(); //libera o recurso
        }
    }

    /*
        Utilitários
     */
    //converte de Cursor em uma List
    private List<Carro> toList(Cursor c) {
        List<Carro> carros = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Carro carro = new Carro();

                // recupera os atributos do cursor para o carro
                carro.id = c.getLong(c.getColumnIndex("_id"));
                carro.nome = c.getString(c.getColumnIndex("nome"));
                carro.desc = c.getString(c.getColumnIndex("desc"));
                carro.tipo = c.getString(c.getColumnIndex("tipo"));
                carro.urlFoto = c.getString(c.getColumnIndex("url_foto"));
                carro.urlVideo = c.getString(c.getColumnIndex("url_video"));
                carro.latitude = c.getString(c.getColumnIndex("latitude"));
                carro.longitude = c.getString(c.getColumnIndex("longitude"));

                carros.add(carro);

            } while (c.moveToNext());
        }

        return carros;
    }

    //Thread para executar a inserção de dados no bd.
    //Utilizada apenas na criação do bd
    private class Task extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
          return popularTabelaCarro();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Log.d(TAG, "Tabela carro populada com sucesso.");
            }
        }

        //popula a tabela carro
        private boolean popularTabelaCarro() {
            //abre a conexão com o bd
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            try {
                //registro 1
                ContentValues values = new ContentValues();
                values.put("nome", "Tucker 1948");
                values.put("desc", "Descrição Tucker 1948");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 2
                values = new ContentValues();
                values.put("nome", "Chevrolet Corvette");
                values.put("desc", "Descrição Chevrolet Corvette");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 3
                values = new ContentValues();
                values.put("nome", "Chevrolet Impala Coupe");
                values.put("desc", "Descrição Chevrolet Impala Coupe");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 4
                values = new ContentValues();
                values.put("nome", "Cadillac Deville Convertible");
                values.put("desc", "Descrição Cadillac Deville Convertible");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 5
                values = new ContentValues();
                values.put("nome", "Chevrolet Bel-Air");
                values.put("desc", "Descrição Chevrolet Bel-Air");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 6
                values = new ContentValues();
                values.put("nome", "Cadillac Eldorado");
                values.put("desc", "Descrição Cadillac Eldorado");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 7
                values = new ContentValues();
                values.put("nome", "Ferrari 250 GTO");
                values.put("desc", "Descrição Ferrari 250 GTO");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 8
                values = new ContentValues();
                values.put("nome", "Dodge Challenger");
                values.put("desc", "Descrição Dodge Challenger");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 9
                values = new ContentValues();
                values.put("nome", "Camaro SS 1969");
                values.put("desc", "Descrição Camaro SS 1969");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 10
                values = new ContentValues();
                values.put("nome", "Ford Mustang 1976");
                values.put("desc", "Descrição Ford Mustang 1976");
                values.put("tipo", "Clássicos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 11
                values = new ContentValues();
                values.put("nome", "Ferrari FF");
                values.put("desc", "Descrição Descrição Ferrari FF");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 12
                values = new ContentValues();
                values.put("nome", "AUDI GT Spyder");
                values.put("desc", "Descrição AUDI GT Spyder");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 13
                values = new ContentValues();
                values.put("nome", "Porsche Panamera");
                values.put("desc", "Descrição Porsche Panamera");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 14
                values = new ContentValues();
                values.put("nome", "Lamborghini Aventador");
                values.put("desc", "Descrição Lamborghini Aventador");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 15
                values = new ContentValues();
                values.put("nome", "Chevrolet Corvette Z06");
                values.put("desc", "Descrição Chevrolet Corvette Z06");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 16
                values = new ContentValues();
                values.put("nome", "BMW M5");
                values.put("desc", "Descrição BMW M5");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 17
                values = new ContentValues();
                values.put("nome", "Renault Megane RS Trophy");
                values.put("desc", "Descrição Renault Megane RS Trophy");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 18
                values = new ContentValues();
                values.put("nome", "Maserati Grancabrio Sport");
                values.put("desc", "Descrição Maserati Grancabrio Sport");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 19
                values = new ContentValues();
                values.put("nome", "McLAREN MP4-12C");
                values.put("desc", "Descrição McLAREN MP4-12C");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 20
                values = new ContentValues();
                values.put("nome", "MERCEDES-BENZ C63 AMG");
                values.put("desc", "Descrição MERCEDES-BENZ C63 AMG");
                values.put("tipo", "Esportivos");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 21
                values = new ContentValues();
                values.put("nome", "Bugatti Veyron");
                values.put("desc", "Descrição Bugatti Veyron");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 22
                values = new ContentValues();
                values.put("nome", "Ferrari Enzo");
                values.put("desc", "Descrição Ferrari Enzo");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 23
                values = new ContentValues();
                values.put("nome", "Lamborghini Reventon");
                values.put("desc", "Descrição Lamborghini Reventon");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 24
                values = new ContentValues();
                values.put("nome", "Leblanc Mirabeau");
                values.put("desc", "Descrição Leblanc Mirabeau");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 25
                values = new ContentValues();
                values.put("nome", "Shelby Supercars Ultimate");
                values.put("desc", "Descrição Shelby Supercars Ultimate");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 26
                values = new ContentValues();
                values.put("nome", "Pagani Zonda");
                values.put("desc", "Descrição Pagani Zonda");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 27
                values = new ContentValues();
                values.put("nome", "Koenigsegg CCX");
                values.put("desc", "Descrição Koenigsegg CCX");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 28
                values = new ContentValues();
                values.put("nome", "Mercedes SLR McLaren");
                values.put("desc", "Descrição Mercedes SLR McLaren");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 29
                values = new ContentValues();
                values.put("nome", "Rolls Royce Phantom");
                values.put("desc", "Descrição Rolls Royce Phantom");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

                //registro 30
                values = new ContentValues();
                values.put("nome", "Lexus LFA");
                values.put("desc", "Descrição Lexus LFA");
                values.put("tipo", "Luxo");
                values.put("latitude", "-31.3322593");
                values.put("longitude", "54.0718532");
                sqLiteDatabase.insert("carro", null, values);

            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
            finally {
                sqLiteDatabase.close(); //libera o recurso
            }

            return true;
        }
    }//fim classe interna

}
