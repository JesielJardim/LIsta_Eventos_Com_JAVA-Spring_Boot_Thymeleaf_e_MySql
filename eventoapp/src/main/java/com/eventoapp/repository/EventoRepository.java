package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String> { //ja vai utilizar metodos prontos com essa extensão

	Evento findByCodigo(long codigo); //criado evento para pesquisarpor codigo
}
