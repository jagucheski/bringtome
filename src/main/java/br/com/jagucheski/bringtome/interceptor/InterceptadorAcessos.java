package br.com.jagucheski.bringtome.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Classe Interceptor que captura o tempo de duração entre a solicitação e o acesso 
 * para que este interceptador posso ser reconhecido pelo Spring foi criada a classe WebConfig
 * com a notação @Configuration e que extende a classe WebMvcConfigurationSupport
 * */
public class InterceptadorAcessos extends HandlerInterceptorAdapter {

	/**atributo somente para teste de desenvolvimento, 
	 * Em produção deve-se salvar os acessos na base de dados, pois ocassionará estouro de memória*/
	public static List<Acesso> acessos = new ArrayList<Acesso>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Acesso acesso = new Acesso();
		acesso.path = request.getRequestURI();
		acesso.data = LocalDateTime.now();
		request.setAttribute("acesso", acesso);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		Acesso acesso = (Acesso) request.getAttribute("acesso");
		acesso.duracao = Duration.between(acesso.data, LocalDateTime.now());
		acessos.add(acesso);
	}

	
	/**Classe somente para teste de desenvolvimento, 
	 * Para colocar em produção deve-se salvar os acessos na base de dados, 
	 * pois ocassionará estouro de memória*/
	public static class Acesso {
		private String path;
		private LocalDateTime data;
		private Duration duracao;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public LocalDateTime getData() {
			return data;
		}

		public void setData(LocalDateTime data) {
			this.data = data;
		}

		public Duration getDuracao() {
			return duracao;
		}

		public void setDuracao(Duration duracao) {
			this.duracao = duracao;
		}

	}

}
