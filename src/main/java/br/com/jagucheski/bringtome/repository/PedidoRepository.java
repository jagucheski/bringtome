package br.com.jagucheski.bringtome.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jagucheski.bringtome.model.Pedido;
import br.com.jagucheski.bringtome.model.StatusPedido;
import br.com.jagucheski.bringtome.model.User;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	/**Busca lista de pedidos por status e com paginacao*/
	@Cacheable("pedidosDataEntrega")
	List<Pedido> findByStatusOrderByDataEntregaDesc(StatusPedido status, Pageable page);
	
	/**Busca lista de pedidos por status e com paginacao*/
	@Cacheable("pedidosID")
	List<Pedido> findByStatusOrderByIdDesc(StatusPedido status, Pageable page);

	List<Pedido> findByStatusAndUser(StatusPedido status, User user);
	
	@Query("Select p from Pedido p join p.user u where u.username = :username")
	List<Pedido> findAllByUsuario(@Param("username") String username);
	
	List<Pedido> findByUser(User user);

}
