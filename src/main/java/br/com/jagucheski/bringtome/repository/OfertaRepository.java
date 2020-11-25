package br.com.jagucheski.bringtome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jagucheski.bringtome.model.Oferta;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {

	List<Oferta> findByPedidoId(Long pedidoId);
}
