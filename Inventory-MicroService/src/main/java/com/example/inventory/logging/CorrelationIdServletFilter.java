package com.example.inventory.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Ensure this filter runs very early
public class CorrelationIdServletFilter implements Filter{

        public static final String CORRELATION_ID_HEADER = "X-Correlation-ID"; // Consistent header name

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);

            // If no correlation ID is found (e.g., direct access or first internal call), generate one
            if (correlationId == null || correlationId.isEmpty()) {
                correlationId = UUID.randomUUID().toString();
            }

            // Put the correlation ID into MDC for logging within this service
            MDC.put("correlationId", correlationId);

            try {
                // IMPORTANT: Re-add the header to the response (for clients to see it,
                // or if this service itself is a gateway to another internal component)
                // and for any subsequent filters/servlets.
                ((HttpServletResponse) response).setHeader(CORRELATION_ID_HEADER, correlationId);

                // Continue the filter chain
                chain.doFilter(request, response);
            } finally {
                // IMPORTANT: Always clear MDC entries to avoid memory leaks or incorrect context
                MDC.remove("correlationId");
            }
        }

    }
