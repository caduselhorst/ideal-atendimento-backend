package br.com.developed.ideal.atendimento.core.interceptor;



import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

	@SuppressWarnings("null")
	@Override
	public @NonNull ClientHttpResponse intercept(@NonNull HttpRequest request, byte[] body, @NonNull ClientHttpRequestExecution execution)
			throws IOException {
		
		HttpHeaders headers = request.getHeaders();

		log.debug("URL: {}", request.getURI().toString());
		log.debug("Method: {}", request.getMethod().name());
		log.debug("Request body: {}", new String(body, "UTF-8"));
		headers.forEach((name, values) -> {
			
			if ("Authorization".equalsIgnoreCase(name) || "x-goog-api-key".equalsIgnoreCase(name)) {
		        log.debug("Header '{}' = [PROTECTED]", name);
		    } else {
		        log.debug("Header '{}' = {}", name, values);
		    }
		
		});
		
		ClientHttpResponse response = execution.execute(request, body);
		
		return response;
	}
	
	

}

