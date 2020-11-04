package com.example.parceiro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Servico {

    @SerializedName("dia")
    @Expose
    private String dia;
    @SerializedName("endereco")
    @Expose
    private Endereco endereco;
    @SerializedName("foto")
    @Expose
    private Foto foto;
    @SerializedName("horario")
    @Expose
    private String horario;
    @SerializedName("idCliente")
    @Expose
    private Integer idCliente;
    @SerializedName("idServico")
    @Expose
    private Integer idServico;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("nota")
    @Expose
    private String nota;
    @SerializedName("quantidadeHoras")
    @Expose
    private Integer quantidadeHoras;
    @SerializedName("telefone")
    @Expose
    private String telefone;
    @SerializedName("valor")
    @Expose
    private double valor;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
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

    public Integer getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(Integer quantidadeHoras) {
        this.quantidadeHoras = quantidadeHoras;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
