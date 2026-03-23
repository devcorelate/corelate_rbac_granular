package com.corelate.rbac.filter;

import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.service.ApiKeyService;
import com.corelate.rbac.util.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_PATH_PREFIXES = List.of(
            "/actuator",
            "/api/health",
            "/api/auth",
            "/v3/api-docs",
            "/swagger-ui");

    private final ApiKeyService apiKeyService;

    @Value("${rbac.api-key-header:X-API-Key}")
    private String apiKeyHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String apiKey = request.getHeader(apiKeyHeader);
            if (apiKey == null || apiKey.isBlank()) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing API key");
                return;
            }
            ClientApp clientApp = apiKeyService.validateApiKey(apiKey);
            TenantContext.setTenant(clientApp);
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return EXCLUDED_PATH_PREFIXES.stream().anyMatch(path::startsWith) || "/swagger-ui.html".equals(path);
    }
}
