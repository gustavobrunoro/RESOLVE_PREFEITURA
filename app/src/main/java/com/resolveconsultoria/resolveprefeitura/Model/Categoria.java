package com.resolveconsultoria.resolveprefeitura.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.resolveconsultoria.resolveprefeitura.Database.CONVERT.DataConvertServico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria implements Serializable {

    @PrimaryKey
    private int CategoriaID;
    private int ClienteIDFK;
    private int ResponsavelIDFK;
    private int SecretariaIDFK;
    private String Descricao;
    private String Icone;
    private int Ativo;
    @TypeConverters(DataConvertServico.class)
    private List<Servico> Servicos = new ArrayList<>();

    public Categoria () {
    }

    public int getCategoriaID () {
        return CategoriaID;
    }

    public void setCategoriaID (int categoriaID) {
        CategoriaID = categoriaID;
    }

    public int getClienteIDFK () {
        return ClienteIDFK;
    }

    public void setClienteIDFK (int clienteIDFK) {
        ClienteIDFK = clienteIDFK;
    }

    public int getResponsavelIDFK () {
        return ResponsavelIDFK;
    }

    public void setResponsavelIDFK (int responsavelIDFK) {
        ResponsavelIDFK = responsavelIDFK;
    }

    public int getSecretariaIDFK () {
        return SecretariaIDFK;
    }

    public void setSecretariaIDFK (int secretariaIDFK) {
        SecretariaIDFK = secretariaIDFK;
    }

    public String getDescricao () {
        return Descricao;
    }

    public void setDescricao (String descricao) {
        Descricao = descricao;
    }

    public String getIcone () {
        return Icone;
    }

    public void setIcone (String icone) {
        Icone = icone;
    }

    public int getAtivo () {
        return Ativo;
    }

    public void setAtivo (int ativo) {
        Ativo = ativo;
    }

    public List<Servico> getServicos () {
        return Servicos;
    }

    public void setServicos (List<Servico> servicos) {
        Servicos = servicos;
    }
}
