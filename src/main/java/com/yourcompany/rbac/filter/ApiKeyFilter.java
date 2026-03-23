package com.yourcompany.rbac.filter;

import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.service.ApiKeyService;
import com.yourcompany.rbac.util.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

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
}
