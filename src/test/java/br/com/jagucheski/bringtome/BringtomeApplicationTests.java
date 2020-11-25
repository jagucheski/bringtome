package br.com.jagucheski.bringtome;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.model.StatusPedido;
import br.com.jagucheski.bringtome.repository.PedidoRepository;

@SpringBootTest
class BringtomeApplicationTests {

	@Autowired
	private PedidoRepository pedidoRepository;  
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void buscaOfertasDiferenteNomeUsuario(){
		String nomeUsuario = "Admin";
		List<Pedido> pedidos =  pedidoRepository.findByStatusAndUserNomeNotOrderByIdDesc(StatusPedido.AGUARDANDO, nomeUsuario, PageRequest.of(0, 10));
		assertEquals(2, pedidos.size());
	}
	
	@Test
	void buscaOfertasDiferenteUsernameUsuario(){
		String username = "joao";
		List<Pedido> pedidos =  pedidoRepository.findByStatusAndUserUsernameNotOrderByIdDesc(StatusPedido.AGUARDANDO, username, PageRequest.of(0, 10));
		assertEquals(5, pedidos.size());
	}
	

}
