package com.resolveconsultoria.resolveprefeitura.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {

    private int clienteID;
    private String RazaoSocial;
    private String NomeFantasia;
    private String CNPJ;
    private String Endereco;
    private String Numero;
    private String Bairro;
    private String Cidade;
    private String Logo;
    private int ativo;
    private List<Categoria> Categoias = new ArrayList<>();

    public Cliente () {
    }

    public int getClienteID () {
        return clienteID;
    }

    public void setClienteID (int clienteID) {
        this.clienteID = clienteID;
    }

    public String getRazaoSocial () {
        return RazaoSocial;
    }

    public void setRazaoSocial (String razaoSocial) {
        RazaoSocial = razaoSocial;
    }

    public String getNomeFantasia () {
        return NomeFantasia;
    }

    public void setNomeFantasia (String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public String getCNPJ () {
        return CNPJ;
    }

    public void setCNPJ (String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getEndereco () {
        return Endereco;
    }

    public void setEndereco (String endereco) {
        Endereco = endereco;
    }

    public String getNumero () {
        return Numero;
    }

    public void setNumero (String numero) {
        Numero = numero;
    }

    public String getBairro () {
        return Bairro;
    }

    public void setBairro (String bairro) {
        Bairro = bairro;
    }

    public String getCidade () {
        return Cidade;
    }

    public void setCidade (String cidade) {
        Cidade = cidade;
    }

    public String getLogo () {
        return Logo;
    }

    public void setLogo (String logo) {
        Logo = logo;
    }

    public int getAtivo () {
        return ativo;
    }

    public void setAtivo (int ativo) {
        this.ativo = ativo;
    }

    public List<Categoria> getCategoias () {
        return Categoias;
    }

    public void setCategoias (List<Categoria> categoias) {
        Categoias = categoias;
    }
}
