package br.com.jagucheski.bringtome.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jagucheski.bringtome.dto.RequisicaoNovoUsuario;
import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.model.StatusPedido;
import br.com.jagucheski.bringtome.model.User;
import br.com.jagucheski.bringtome.repository.PedidoRepository;
import br.com.jagucheski.bringtome.repository.UserRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Apresenta a página home do usuário com seus pedidos 
	 * */
	@GetMapping("pedido")
	public String home(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findAllByUsuario(principal.getName());
		model.addAttribute("pedidos", pedidos);
		return "usuario/home";
	}
	
	/**
  	 * Apresenta a página home do usuário com seus pedidos filtrando pelo status selecionado 
	 * */
	@GetMapping("pedido/{status}")
	public String porStatus(@PathVariable("status") String status, Model model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();		
		User user = userRepository.findByUsername(username);
		
		Iterable<Pedido> pedidos = pedidoRepository.findByStatusAndUser(StatusPedido.valueOf(status.toUpperCase()), user);
		
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", status);
		return "usuario/home";
	}
	
	/**
  	 * Apresenta a página de formulário de cadastro de usuário 
	 * */
	@GetMapping("formulario")
	public String formulario(RequisicaoNovoUsuario requisicaoAtualizaUsuario) {
		return "usuario/formulario";
	}

	/**
	 * Apresenta a página de consulta de usuários 
	 * */
	@GetMapping("visualiza")
	public String visualiza(Model model) {
		List<User> usuarios = userRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		return "usuario/visualiza";
	}

	/**
  	 * Action que atualiza dados do usuário 
	 * */
	@GetMapping("atualiza/{username}")
	public String atualiza(@PathVariable("username") String username, RequisicaoNovoUsuario requisicaoNovoUsuario) {
		userRepository.findByUsername(username).toRequisicaoNovoUsuario(requisicaoNovoUsuario);
		return "usuario/atualiza";
	}
	
	/**
	 * Action que excluir usuário 
	 * */
	@GetMapping("excluir/{username}")
	public String excluir(@PathVariable("username") String username, Model model) {
		userRepository.deleteById(username);
		List<User> usuarios = userRepository.findAll();
		model.addAttribute("usuarioMessage", true);
		model.addAttribute("mensagem", "Usuário cadastrado com sucesso!");
		model.addAttribute("usuarios", usuarios);
		return "usuario/visualiza";
	}
	
	/**
  	 * Action para salvar cadastro de novo usuário 
	 * */
	@PostMapping("novo")
	public String novoUsuario(@Valid RequisicaoNovoUsuario requisicaoNovoUsuario, BindingResult result, RedirectAttributes redirectAttrs) {
		if(result.hasErrors()) {
			return "usuario/formulario";
		}
		User user = requisicaoNovoUsuario.toUser();
		userRepository.save(user);
		redirectAttrs.addFlashAttribute("usuarioCad", true);
		redirectAttrs.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return "redirect:/usuario/formulario";
	}
	
	@PostMapping("atualizar")
	public String atualizarUsuario(@Valid RequisicaoNovoUsuario requisicaoNovoUsuario, BindingResult result, RedirectAttributes redirectAttrs) {
		if(result.hasErrors()) {
			return "/usuario/atualiza";
		}
		
		User user = userRepository.findByUsername(requisicaoNovoUsuario.getUserName());
		user.setNome(requisicaoNovoUsuario.getNome());
		user.setEnabled(requisicaoNovoUsuario.getEnabled());		
		userRepository.save(user);
		redirectAttrs.addFlashAttribute("usuarioAtualiza", true);
		redirectAttrs.addFlashAttribute("mensagem", "Usuário Atualizado com sucesso!");
		return "redirect:/usuario/atualiza/"+user.getUsername();
	}
	
	/**
	 * Este método captura possíveis erros no mapeamento /usuario/*
	 * Direcionando para a pagina /home
	 * */
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/usuario/home";
	}
}
