package com.yourcompany.rbac.repository;

import com.yourcompany.rbac.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
