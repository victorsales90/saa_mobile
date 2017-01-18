package br.unifor.pin.saa.restInteface;


import java.util.List;

import br.unifor.pin.saa.entity.Anexos;
import br.unifor.pin.saa.entity.Aula;
import br.unifor.pin.saa.entity.Turmas;
import br.unifor.pin.saa.entity.Usuarios;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * classe que contem os metodos necessarios para a utilizacao do servico
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public interface RestInterface {

    /**
     * Metodo responsavel por retornar (via JSON)
     * Utiliza metodo GET com endpoint do servico
     * @param usuario
     * @param callback
     */
    @POST("/turma/listaTurma")
    void getTurmasJSON(@Body Usuarios usuario, Callback<List<Turmas>> callback);

    // lista aulas
    @POST("/aula/listaAulasTurma")
    void getAulasJSON(@Body Turmas turma, Callback<List<Aula>> callback);


    /**
     * Metodo responsavel por retornar (via JSON) uma lista de usuarios.
     * Utiliza matodo GET com endpoint do servico
     * @param usuarioCallback
     */
    @POST("/login/json")
    void getUsuariosJson(@Body Usuarios u, Callback<Usuarios> usuarioCallback);

    /**
     * Metodo responsavel por salvar uma turmas enviando o objeto (turmas) no corpo da requisicao via POST.
     * Utiliza metodo post com com endpoint do servico
     * @param turmas
     * @param callback
     */
    @POST("/turmas/salvarAvaliacao")
    void salvarAvaliacao(@Body Turmas turmas, Callback<Turmas> callback);

    @POST("/login/{id}/salvarImagemUsuario")
    void salvarImagemUsuario(@Path("id")String id, @Body Anexos anexos, Callback<Anexos> callback);

}
