package br.unifor.pin.saa.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class Aula implements Serializable{



    private Integer id;
    private Turmas turma;
    private String descricao;
  //  private Date dataAula;
    private Double media;
    private  Long sequencia;
    private Boolean ativo;


    public Aula() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Turmas getTurma() {
        return turma;
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

//    public Date getDataAula() {
//        return dataAula;
//    }
//
//    public void setDataAula(Date dataAula) {
//        this.dataAula = dataAula;
//    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public Long getSequencia() {
        return sequencia;
    }

    public void setSequencia(Long sequencia) {
        this.sequencia = sequencia;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
