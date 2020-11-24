package com.resolveconsultoria.resolveprefeitura.Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int usuarioID;
    private int ClienteIDFK;
    private String Nome;
    private String CPF;
    private String DataNascimento;
    private String Telefone;
    private String CEP;
    private String Cidade;
    private String Bairro;
    private String Endereco;
    private String Numero;
    private String Complemento;
    private String Foto_URL;
    private String Email;
    private String Senha;
    private String Token_Mobile;

    public Usuario () {
    }

    public int getUsuarioID () {
        return usuarioID;
    }

    public void setUsuarioID (int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public int getClienteIDFK () {
        return ClienteIDFK;
    }

    public void setClienteIDFK (int clienteIDFK) {
        ClienteIDFK = clienteIDFK;
    }

    public String getNome () {
        return Nome;
    }

    public void setNome (String nome) {
        Nome = nome;
    }

    public String getCPF () {
        return CPF;
    }

    public void setCPF (String CPF) {
        this.CPF = CPF;
    }

    public String getDataNascimento () {
        return DataNascimento;
    }

    public void setDataNascimento (String dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public String getTelefone () {
        return Telefone;
    }

    public void setTelefone (String telefone) {
        Telefone = telefone;
    }

    public String getCEP () {
        return CEP;
    }

    public void setCEP (String CEP) {
        this.CEP = CEP;
    }

    public String getCidade () {
        return Cidade;
    }

    public void setCidade (String cidade) {
        Cidade = cidade;
    }

    public String getBairro () {
        return Bairro;
    }

    public void setBairro (String bairro) {
        Bairro = bairro;
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

    public String getComplemento () {
        return Complemento;
    }

    public void setComplemento (String complemento) {
        Complemento = complemento;
    }

    public String getFoto_URL () {
        return Foto_URL;
    }

    public void setFoto_URL (String foto_URL) {
        Foto_URL = foto_URL;
    }

    public String getEmail () {
        return Email;
    }

    public void setEmail (String email) {
        Email = email;
    }

    public String getSenha () {
        return Senha;
    }

    public void setSenha (String senha) {
        Senha = senha;
    }

    public String getToken_Mobile () {
        return Token_Mobile;
    }

    public void setToken_Mobile (String token_Mobile) {
        Token_Mobile = token_Mobile;
    }
}
