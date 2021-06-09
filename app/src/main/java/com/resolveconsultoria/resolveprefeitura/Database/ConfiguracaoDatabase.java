package com.resolveconsultoria.resolveprefeitura.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.resolveconsultoria.resolveprefeitura.Database.CONVERT.DataConvertCategoria;
import com.resolveconsultoria.resolveprefeitura.Database.CONVERT.DataConvertServico;
import com.resolveconsultoria.resolveprefeitura.Database.CONVERT.RoomTypeConverters;
import com.resolveconsultoria.resolveprefeitura.Database.DAO.CategoriaDAO;
import com.resolveconsultoria.resolveprefeitura.Database.DAO.ServicoDAO;
import com.resolveconsultoria.resolveprefeitura.Model.Categoria;
import com.resolveconsultoria.resolveprefeitura.Model.Servico;

@TypeConverters({ RoomTypeConverters.class, DataConvertCategoria.class, DataConvertServico.class })
@Database(entities = {Categoria.class, Servico.class}, version = 1)
public abstract class ConfiguracaoDatabase extends RoomDatabase {

    private static final  String DB_NAME = "Prefeituras";
    private static ConfiguracaoDatabase instance;

    public static synchronized ConfiguracaoDatabase getInstance(Context context ){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ConfiguracaoDatabase.class,DB_NAME)
                           .fallbackToDestructiveMigration()
                           .build();
        }
        return instance;
    }

    public abstract CategoriaDAO categoriaDAO();
    public abstract ServicoDAO servicoDAO();

}
