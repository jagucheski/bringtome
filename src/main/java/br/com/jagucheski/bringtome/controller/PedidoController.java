package br.com.jagucheski.bringtome.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.jagucheski.bringtome.dto.RequisicaoNovoPedido;
import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.repository.PedidoRepository;
import br.com.jagucheski.bringtome.repository.UserRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("formulario")
	public String formulario(RequisicaoNovoPedido requisicaoNovoPedido) {
		return "pedido/formulario";
	}
	
	@PostMapping("novo")
	public String novo(@Valid RequisicaoNovoPedido requisicao, BindingResult result) {
		if(result.hasErrors()) {
			return "pedido/formulario";
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Pedido pedido = requisicao.toPedido();
		pedido.setUser(userRepository.findByUsername(username));
		
		pedidoRepository.save(pedido);
		return "redirect:/usuario/pedido";
	}
	
}
