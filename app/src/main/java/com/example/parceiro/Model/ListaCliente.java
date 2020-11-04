package com.example.parceiro.Model;

import com.google.gson.annotations.SerializedName;

public class ListaCliente {

    @SerializedName("idCliente")
    private int idCliente;
    @SerializedName("idServico")
    private int idServico;
    @SerializedName("nome")
    private String nome;
    @SerializedName("nota")
    private String nota;
    @SerializedName("foto")
    private Foto foto;


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idParceiro) {
        this.idCliente = idParceiro;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "ListaCliente{" +
                "idParceiro=" + idCliente +
                ", idServico=" + idServico +
                ", nome='" + nome + '\'' +
                ", nota='" + nota + '\'' +
                ", foto=" + foto.getData() +
                '}';
    }
}
