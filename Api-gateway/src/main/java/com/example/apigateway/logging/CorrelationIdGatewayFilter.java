package com.example.apigateway.logging;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdGatewayFilter implements GlobalFilter, Ordered {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID"; // Standard header name

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders().getFirst(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId == "") { // Use == "" for empty string check
            correlationId = UUID.randomUUID().toString();
        }

        final String finalCorrelationId = correlationId;

        // Add the correlation ID to the outgoing request headers for downstream services
        exchange.getRequest().mutate().header(CORRELATION_ID_HEADER, finalCorrelationId).build();

        // Put the correlation ID into MDC for logging within the API Gateway itself
        // Use contextWrite for Reactor contexts to ensure MDC is set and cleaned up correctly.
        return chain.filter(exchange)
                .contextWrite(context -> {
                    MDC.put("correlationId", finalCorrelationId); // Key for MDC
                    return context;
                })
                .doFinally(signalType -> MDC.remove("correlationId")); // IMPORTANT: Clean up MDC
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // Ensure this filter runs very early
    }
}

