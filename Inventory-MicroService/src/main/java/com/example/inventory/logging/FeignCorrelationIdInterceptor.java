package com.example.inventory.logging;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class FeignCorrelationIdInterceptor implements RequestInterceptor {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID"; // Consistent header name

    @Override
    public void apply(RequestTemplate template) {
        String correlationId = MDC.get("correlationId"); // Get ID from the current thread's MDC
        if (correlationId != null) {
            template.header(CORRELATION_ID_HEADER, correlationId); // Add it to the outgoing Feign request
        }
    }
}
