package br.com.jagucheski.bringtome.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jagucheski.bringtome.interceptor.InterceptadorAcessos;
import br.com.jagucheski.bringtome.interceptor.InterceptadorAcessos.Acesso;

@RestController
@RequestMapping("acessos")
public class AcessosRest {
	
	@GetMapping
	public List<Acesso> acessos() {
		return InterceptadorAcessos.acessos;
	}

}
