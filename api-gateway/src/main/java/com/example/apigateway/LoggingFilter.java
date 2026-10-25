package com.example.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component // Bắt buộc phải có để Spring Boot nhận diện
public class LoggingFilter implements GlobalFilter, Ordered {

    // Khởi tạo Logger để in ra console (giống trong ảnh)
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. Lấy thông tin Path của Request
        // exchange.getRequest().getURI().getPath() sẽ trả về ví dụ: /api/v1/auth/login
        String path = exchange.getRequest().getURI().getPath();

        // 2. In ra Console theo yêu cầu
        log.info("Incoming request to: {}", path);

        // 3. Cho phép Request đi tiếp đến Service đích
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // Trả về -1 để filter này chạy sớm nhất có thể (Pre-filter)
        // Số càng nhỏ thì độ ưu tiên càng cao
        return -1;
    }
}
