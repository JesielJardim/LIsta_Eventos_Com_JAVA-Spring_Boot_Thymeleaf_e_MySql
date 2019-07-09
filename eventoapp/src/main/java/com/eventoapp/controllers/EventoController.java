package com.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired //anotação que faz a injeção de dependencia (cria uma nova instancia automatico quando vamos utilizar)
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() { //retrna um formulario de cadastro
		return "evento/formEvento"; //informando o local da pagina
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) { //recebe um evento
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento"; 
		}
		er.save(evento); //persistir no banco de dados (ja salva no banco).
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarEvento"; 
	}
	
	//metodo ara retornar a lista de eventos(itens)
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index"); // passa a pagina que deve ser inderizado os itens
		//buscando os dados no banco
		Iterable<Evento> eventos = er.findAll();
		//passando os dados para view
		mv.addObject("eventos", eventos);//o 1 paramentro colocar o mesmo nome que colocou na view na pagina index "${eventos}", o segundo e a lista
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo); //esta pesquisando por codigo no banco
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("eventos", evento);
		
		Iterable<Convidado> convidados = cr.findByEvento(evento); //busca uma lista de convidados
		mv.addObject("convidados", convidados);
		
		return mv;
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) { //deletar um item
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		Evento evento = er.findByCodigo(codigo); //faz a busca do codigo que esta sendo passado
		convidado.setEvento(evento); //passa o codigo encontrato para salvar junto com o convidado
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento(); //após deletar um convidado pega novamente a lista de evento atualizada para retornar
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong; //converte o codigo que é long
		return "redirect:/" + codigo; //ai rretorna
	}
	
	/*@RequestMapping("/cadastraEvento")
	public String form() { //retrna um formulario de cadastro
		return "evento/formEvento"; //informando o local da pagina
		
		
			@RequestMapping("/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo); //esta pesquisando por codigo no banco
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("eventos", evento);
		return mv;
	}
	}*/
}
