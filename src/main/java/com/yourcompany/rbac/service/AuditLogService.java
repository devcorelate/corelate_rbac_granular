package com.yourcompany.rbac.service;

import com.yourcompany.rbac.entity.AuditLog;
import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.repository.AuditLogRepository;
import com.yourcompany.rbac.util.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String action, String actor, String details) {
        ClientApp clientApp = TenantContext.getTenant();
        if (clientApp == null) {
            return;
        }
        AuditLog log = new AuditLog();
        log.setClientApp(clientApp);
        log.setAction(action);
        log.setActor(actor);
        log.setDetails(details);
        auditLogRepository.save(log);
    }
}
