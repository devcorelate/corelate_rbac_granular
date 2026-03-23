package com.corelate.rbac.repository;

import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByClientApp(ClientApp clientApp);
    Optional<Role> findByIdAndClientApp(Long id, ClientApp clientApp);
}
