package br.com.mda;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import br.com.mda.model.Funcionario;


@SpringBootApplication
public class MDAApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MDAApplication.class, args);
	}
	
	
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

	@Bean
	public DataSource dataSource() throws Exception {
		try {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
	    dataSourceBuilder.url("jdbc:mysql://localhost:3306/MDA");
	    dataSourceBuilder.username("root");
	    dataSourceBuilder.password("");
	        	
	    return dataSourceBuilder.build();
		}catch (Exception e) {
			throw new Exception();
		}
	}
	
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		private static final String USUARIO_POR_LOGIN = "SELECT login, senha, ativo FROM usuario "
				+ "WHERE login = ?";
		

		private static final String PERMISSAO_POR_USUARIO = "SELECT login, senha, ativo FROM usuario "
				+ "WHERE login = ?";		
		
		
		@Autowired
		private DataSource dataSource;

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http
	    	.httpBasic().and()
	    	.authorizeRequests()
	        	.antMatchers("/lib/**", "/js/**", "/plugins/**", "/", "/index.html").permitAll()
	        	.anyRequest().authenticated().and()
	        .formLogin()
				.loginPage("/index.html")
				.usernameParameter("login")
				.passwordParameter("senha").and()
	        .logout().and()
	        .csrf()
	        	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());	
			
		}
		
		
		
 		@Override
		public void configure(AuthenticationManagerBuilder builder) throws Exception {
			builder
        	.jdbcAuthentication()
        		.dataSource(dataSource)
        		.passwordEncoder(new BCryptPasswordEncoder())
        		.usersByUsernameQuery(USUARIO_POR_LOGIN)
        		.authoritiesByUsernameQuery(PERMISSAO_POR_USUARIO)
        	.rolePrefix("ROLE_");
			System.out.println(builder);
		}
	}
}
