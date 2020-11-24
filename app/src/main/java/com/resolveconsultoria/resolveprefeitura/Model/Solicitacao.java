package com.resolveconsultoria.resolveprefeitura.Model;

public class Solicitacao {

    private int SolicitacaoID;
    private int NumeroSolicitacao;
    private int ClienteIDFK;
    private int CategoriaIDFK;
    private int ServicoIDFK;
    private int UsuarioIDFK;
    private String Descricao;
    private String DataSolicitacao;
    private String Foto;
    private String Video;
    private double Latitude;
    private double Longitude;
    private int Status;

    public Solicitacao () {
    }

    public int getSolicitacaoID ()  {
        return SolicitacaoID;
    }

    public void setSolicitacaoID (int solicitacaoID) {
        SolicitacaoID = solicitacaoID;
    }

    public int getNumeroSolicitacao () {
        return NumeroSolicitacao;
    }

    public void setNumeroSolicitacao (int numeroSolicitacao) {
        NumeroSolicitacao = numeroSolicitacao;
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

    public int getServicoIDFK () {
        return ServicoIDFK;
    }

    public void setServicoIDFK (int servicoIDFK) {
        ServicoIDFK = servicoIDFK;
    }

    public int getUsuarioIDFK () {
        return UsuarioIDFK;
    }

    public void setUsuarioIDFK (int usuarioIDFK) {
        UsuarioIDFK = usuarioIDFK;
    }

    public String getDescricao () {
        return Descricao;
    }

    public void setDescricao (String descricao) {
        Descricao = descricao;
    }

    public String getDataSolicitacao () {
        return DataSolicitacao;
    }

    public void setDataSolicitacao (String dataSolicitacao) {
        DataSolicitacao = dataSolicitacao;
    }

    public String getFoto () {
        return Foto;
    }

    public void setFoto (String foto) {
        Foto = foto;
    }

    public String getVideo () {
        return Video;
    }

    public void setVideo (String video) {
        Video = video;
    }

    public double getLatitude () {
        return Latitude;
    }

    public void setLatitude (double latitude) {
        Latitude = latitude;
    }

    public double getLongitude () {
        return Longitude;
    }

    public void setLongitude (double longitude) {
        Longitude = longitude;
    }

    public int getStatus () {
        return Status;
    }

    public void setStatus (int status) {
        Status = status;
    }
}
