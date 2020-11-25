package br.com.jagucheski.bringtome;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	/**
	 * Configuracoes de autorizacao
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/home/**").permitAll()
			.antMatchers("/usuario/formulario").hasRole("ADM")
			.antMatchers("/usuario/atualiza").hasRole("ADM")
			.antMatchers("/usuario/novo").hasRole("ADM")
			.antMatchers("/usuario/excluir").hasRole("ADM")
			
			.anyRequest().authenticated()
			.and().exceptionHandling().accessDeniedPage("/403")
			.and() 
				.formLogin(form -> form
		            .loginPage("/login")
		            .failureUrl("/login-error")
		            .defaultSuccessUrl("/usuario/pedido",true).permitAll())
				.logout(logout -> logout.logoutUrl("/logout")
						.logoutSuccessUrl("/home"))
				.csrf().disable();
	}

	
	/**
     * Configuracoes de autenticacao
     */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder());
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
//	Metodo somente para demonstração, utiliza usuario em memoria
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//		UserDetails user = 
//				User.withDefaultPasswordEncoder().
//					username("maicon").
//					password("123").
//					roles("ADMIN").
//					build();
//		return new InMemoryUserDetailsManager(user);
//	}

//  Configuracoes de autenticacao
//  Método com UserDetails que insere usuario no BD.
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		UserDetails user = 
//				User.builder().
//					username("maria").
//					password(passwordEncoder().encode("123")).
//					roles("ADM").
//					build();
//		
//		
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.passwordEncoder(passwordEncoder())
//			.withUser(user);
//	}
    
}
