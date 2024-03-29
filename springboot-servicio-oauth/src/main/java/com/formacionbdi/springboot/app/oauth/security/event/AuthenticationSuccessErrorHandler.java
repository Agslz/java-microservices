package com.formacionbdi.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formacionbdi.springboot.app.oauth.services.IUsuarioService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private Tracer tracer;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {

		// if(authentication.getName().equalsIgnoreCase("frontendapp")){
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String messageString = "Sucess Login: " + userDetails.getUsername();
		System.out.println(messageString);
		log.info(messageString);

		Usuario usuario = usuarioService.findByUsername(authentication.getName());

		if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}

	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String messaString = "Error en el Login: " + exception.getMessage();
		log.error(messaString);
		System.out.println(messaString);

		try {
			
			
			StringBuilder errors = new StringBuilder();
			
			errors.append(messaString);
			
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if (usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}

			log.info("Intentos actual es de: " + usuario.getIntentos());
			
			usuario.setIntentos(usuario.getIntentos() + 1);
			
			log.info("Intentos despues es de: " + usuario.getIntentos());

			errors.append(" - Intentos del login: " + usuario.getIntentos());
			

			if (usuario.getIntentos() >= 3) {
				
				String errorMaxIntentos = String.format("El usuario %s deshabilitado por maximo de intentos", usuario.getUsername());
				
				log.error(errorMaxIntentos);
				
				errors.append(" - " + errorMaxIntentos); 
				
				usuario.setEnabled(false);
			}

			usuarioService.update(usuario, usuario.getId());
			
			tracer.currentSpan().tag("error.mensaje", errors.toString());

		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}

	}

}
