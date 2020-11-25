package br.com.jagucheski.bringtome.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.model.StatusPedido;
import br.com.jagucheski.bringtome.repository.PedidoRepository;

//Por default no @RestController os metodos com retorno são devolvidos em fomato json
@RestController
@RequestMapping("/api/pedidos")
public class PedidosRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;  
	
	@GetMapping("aguardando") 
	public List<Pedido> getPedidosAguardando(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return pedidoRepository.findByStatusAndUserUsernameNotOrderByIdDesc(StatusPedido.AGUARDANDO, username, PageRequest.of(0, 10));
	}

}
