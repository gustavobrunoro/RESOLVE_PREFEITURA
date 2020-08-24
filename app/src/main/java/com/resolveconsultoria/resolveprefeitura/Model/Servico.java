package com.resolveconsultoria.resolveprefeitura.Model;

import java.io.Serializable;

public class Servico implements Serializable {

    private int ServicoID;
    private int ClienteIDFK;
    private int CategoriaIDFK;
    private int ResponsavelIDFK;
    private String Descricao;
    private String FontAwesome;
    private String PaginaWEB;
    private int Ativo;

    public Servico () {
    }

    public int getServicoID () {
        return ServicoID;
    }

    public void setServicoID (int servicoID) {
        ServicoID = servicoID;
    }

    public int getClienteIDFK () {
        return ClienteIDFK;
    }

    public void setClienteIDFK (int clienteIDFK) {
        ClienteIDFK = clienteIDFK;
    }

    public int getCategoriaIDFK () {
        return CategoriaIDFK;
    }

    public void setCategoriaIDFK (int categoriaIDFK) {
        CategoriaIDFK = categoriaIDFK;
    }

    public int getResponsavelIDFK () {
        return ResponsavelIDFK;
    }

    public void setResponsavelIDFK (int responsavelIDFK) {
        ResponsavelIDFK = responsavelIDFK;
    }

    public String getDescricao () {
        return Descricao;
    }

    public void setDescricao (String descricao) {
        Descricao = descricao;
    }

    public String getFontAwesome () {
        return FontAwesome;
    }

    public void setFontAwesome (String fontAwesome) {
        FontAwesome = fontAwesome;
    }

    public String getPaginaWEB () {
        return PaginaWEB;
    }

    public void setPaginaWEB (String paginaWEB) {
        PaginaWEB = paginaWEB;
    }

    public int getAtivo () {
        return Ativo;
    }

    public void setAtivo (int ativo) {
        Ativo = ativo;
    }
}
