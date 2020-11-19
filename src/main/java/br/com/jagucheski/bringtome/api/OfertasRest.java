package br.com.jagucheski.bringtome.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.jagucheski.bringtome.dto.RequisicaoNovaOferta;
import br.com.jagucheski.bringtome.model.Oferta;
import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@PostMapping()
	@ResponseBody
	public Oferta criaOferta(@RequestBody @Valid RequisicaoNovaOferta requisicao) {
		Optional<Pedido> pedidoBase = pedidoRepository.findById(requisicao.getPedidoId());
		if(!pedidoBase.isPresent()) {
			return null;
		}
		
		Pedido pedido = pedidoBase.get();
		
		Oferta novaOferta = requisicao.toOferta();
		novaOferta.setPedido(pedido);		
		pedido.getOfertas().add(novaOferta);		
		pedidoRepository.save(pedido);
		
		return novaOferta;
	}
	
}
