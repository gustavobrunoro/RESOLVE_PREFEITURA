package com.resolveconsultoria.resolveprefeitura.Database.CONVERT;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resolveconsultoria.resolveprefeitura.Model.Servico;

import java.lang.reflect.Type;
import java.util.List;

public class DataConvertServico {

    @TypeConverter
    public String fromList(List<Servico> servicos) {
        if (servicos == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Servico>>() {}.getType();
        String json = gson.toJson(servicos, type);
        return json;
    }

    @TypeConverter
    public List<Servico> toList(String lista) {
        if (lista == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Servico>>() {}.getType();
        List<Servico> servicos = gson.fromJson(lista, type);
        return servicos;
    }

}
