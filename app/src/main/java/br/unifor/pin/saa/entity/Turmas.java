package br.unifor.pin.saa.entity;

import java.io.Serializable;

/**
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class Turmas implements Serializable{

    private Integer id;
    private String descricao;
    private String instituicao;
    private String semestre;
    private String disciplina;
    private Usuarios professor;

    public Turmas() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Usuarios getProfessor() {
        return professor;
    }

    public void setProfessor(Usuarios professor) {
        this.professor = professor;
    }
}
