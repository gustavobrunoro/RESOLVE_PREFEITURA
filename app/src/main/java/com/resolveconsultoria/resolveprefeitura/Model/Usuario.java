package com.resolveconsultoria.resolveprefeitura.Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int usuarioID;
    private int cliente;
    private String nome;
    private String CPF;
    private String email;
    private String senha;
    private String tokenNotificacaoMobile;
    private String api_token;

    public Usuario () {
    }

    public int getUsuarioID () {
        return usuarioID;
    }

    public void setUsuarioID (int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public int getCliente () {
        return cliente;
    }

    public void setCliente (int cliente) {
        this.cliente = cliente;
    }

    public String getNome () {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getCPF () {
        return CPF;
    }

    public void setCPF (String CPF) {
        this.CPF = CPF;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getSenha () {
        return senha;
    }

    public void setSenha (String senha) {
        this.senha = senha;
    }

    public String getTokenNotificacaoMobile () {
        return tokenNotificacaoMobile;
    }

    public void setTokenNotificacaoMobile (String tokenNotificacaoMobile) {
        this.tokenNotificacaoMobile = tokenNotificacaoMobile;
    }

    public String getApi_token () {
        return api_token;
    }

    public void setApi_token (String api_token) {
        this.api_token = api_token;
    }
}
