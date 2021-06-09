package com.resolveconsultoria.resolveprefeitura.Database.CONVERT;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resolveconsultoria.resolveprefeitura.Model.Categoria;

import java.lang.reflect.Type;
import java.util.List;

public class DataConvertCategoria {

    @TypeConverter
    public String fromList(List<Categoria> categorias) {
        if (categorias == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Categoria>>() {}.getType();
        String json = gson.toJson(categorias, type);
        return json;
    }

    @TypeConverter
    public List<Categoria> toList(String lista) {
        if (lista == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Categoria>>() {}.getType();
        List<Categoria> categorias = gson.fromJson(lista, type);
        return categorias;
    }

}
