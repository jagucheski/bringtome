package br.com.jagucheski.bringtome.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.model.StatusPedido;
import br.com.jagucheski.bringtome.repository.PedidoRepository;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping
	public String home(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findByStatusOrderByIdDesc(StatusPedido.ENTREGUE, PageRequest.of(0, 10));
		model.addAttribute("pedidos", pedidos);
		return "/home";
	}
}