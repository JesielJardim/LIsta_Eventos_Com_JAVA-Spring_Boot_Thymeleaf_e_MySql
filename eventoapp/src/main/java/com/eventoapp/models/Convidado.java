package com.eventoapp.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity // anota para iformar que deve criar uma tabela
public class Convidado {

	@Id
	@NotEmpty
	private String rg;
	
	@NotEmpty
	private String nomeConvidado;
	
	@ManyToOne //anotação para fazer relaçao com a tabela de evendo, significa muitos convidados para um evento
	private Evento evento;
	
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getNomeConvidado() {
		return nomeConvidado;
	}
	public void setNomeConvidado(String nomeConvidado) {
		this.nomeConvidado = nomeConvidado;
	}
	
	
}
