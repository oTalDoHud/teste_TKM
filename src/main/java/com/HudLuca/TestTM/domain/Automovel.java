package com.HudLuca.TestTM.domain;

import com.HudLuca.TestTM.domain.enums.TempoHabilitacao;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_propriedade_automovel")
@JsonTypeName("propriedadeAutomovel")
public class Automovel extends Propriedade{

    private String placa;
    private String modelo;
    private Date anoFabricacao;
    private Integer quantidadeDeProprietarios;
    private String sexoProprietarioAtual;
    private Double quilometragem;
    private Integer tempoHabilitacaoProprietario;

    public Automovel
            (String nome, double valor, int quantidade, String placa, String modelo,
                     Date anoFabricacao, Integer quantidadeDeProprietarios, String sexoProprietarioAtual,
                     Double quilometragem, TempoHabilitacao tempoHabilitacaoProprietario) {
        super(nome, valor, quantidade);
        this.placa = placa;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.quantidadeDeProprietarios = quantidadeDeProprietarios;
        this.sexoProprietarioAtual = sexoProprietarioAtual;
        this.quilometragem = quilometragem;
        this.tempoHabilitacaoProprietario  = (tempoHabilitacaoProprietario == null) ? null : tempoHabilitacaoProprietario.getCd();
    }

    public Automovel() {
        super();
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Date anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getQuantidadeDeProprietarios() {
        return quantidadeDeProprietarios;
    }

    public void setQuantidadeDeProprietarios(Integer quantidadeDeProprietarios) {
        this.quantidadeDeProprietarios = quantidadeDeProprietarios;
    }

    public String getSexoProprietarioAtual() {
        return sexoProprietarioAtual;
    }

    public void setSexoProprietarioAtual(String sexoProprietarioAtual) {
        this.sexoProprietarioAtual = sexoProprietarioAtual;
    }

    public Double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public TempoHabilitacao getTempoHabilitacaoProprietario() {
        return TempoHabilitacao.toEnum(this.tempoHabilitacaoProprietario);
    }

    public void setTempoHabilitacaoProprietario(TempoHabilitacao tempoHabilitacaoProprietario) {
        this.tempoHabilitacaoProprietario = tempoHabilitacaoProprietario.getCd();
    }

    @Override
    public void valorAnual() {
        this.valor = 0.0;
    }
}