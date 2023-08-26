package com.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreTimeTranscurredFilter extends ZuulFilter {
	
	private static Logger Log = LoggerFactory.getLogger(PreTimeTranscurredFilter.class);

	@Override
	public boolean shouldFilter() {
		return false;
	}

	@Override
	public Object run() throws ZuulException {
				
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		Log.info(String.format("%s Request enruted at %s", request.getMethod(), request.getRequestURL().toString()));
		
		Long iniceTime = System.currentTimeMillis();
		request.setAttribute("iniceTime", iniceTime);
		
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
