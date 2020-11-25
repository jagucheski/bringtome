package br.com.jagucheski.bringtome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.jagucheski.bringtome.model.Oferta;
import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.repository.OfertaRepository;
import br.com.jagucheski.bringtome.repository.PedidoRepository;

@Controller
@RequestMapping("/oferta")
public class OfertaController {
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	

	@GetMapping
	public String getFormulariParaOfertas() {
		return "oferta/home";
	}
	
	@GetMapping("visualizaOfertasPedido/{pedidoId}")
	public String visualizaOfertasPedido(@PathVariable("pedidoId") Long pedidoId, Model model) {
		Pedido pedido = pedidoRepository.findById(pedidoId).get();
		List<Oferta> ofertas = ofertaRepository.findByPedidoId(pedidoId);
		model.addAttribute("pedido", pedido);
		model.addAttribute("ofertas", ofertas);
		return "pedido/visualizaOfertas";
	}
}
