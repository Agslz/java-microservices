package com.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTimeTranscurredFilter extends ZuulFilter {
	
	private static Logger log = LoggerFactory.getLogger(PostTimeTranscurredFilter.class);

	@Override
	public boolean shouldFilter() {
		return false;
	}

	@Override
	public Object run() throws ZuulException {
				
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("%s Entering to post filter %s");
		
		Long iniceTime = (Long) request.getAttribute("iniceTime");
		Long finalTime = System.currentTimeMillis();
		Long transcurredTime = finalTime - iniceTime;
		
		log.info(String.format("Transcurred time in seconds %s seg.", transcurredTime.doubleValue()/1000.00));
		log.info(String.format("Transcurred time in miliseconds %s ms.", transcurredTime.doubleValue()));

		
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
