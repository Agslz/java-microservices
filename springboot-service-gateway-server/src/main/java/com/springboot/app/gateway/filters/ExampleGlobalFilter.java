package com.springboot.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class ExampleGlobalFilter implements GlobalFilter, Ordered {

	private final Logger logger = LoggerFactory.getLogger(ExampleGlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		logger.info("Running pre Filter");

		exchange.getRequest().mutate().headers(h -> h.add("token", "1234556"));

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("Running post Filter");
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(value -> {
				exchange.getResponse().getHeaders().add("token", value);
			});
			exchange.getResponse().getCookies().add("colour", ResponseCookie.from("colour", "red").build());
//			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
