package com.corelate.rbac.service;

import com.corelate.rbac.entity.AuditLog;
import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.repository.AuditLogRepository;
import com.corelate.rbac.util.TenantContext;
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
