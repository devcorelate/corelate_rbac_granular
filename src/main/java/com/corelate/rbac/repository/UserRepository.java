package com.corelate.rbac.repository;

import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByClientApp(ClientApp clientApp);
    Optional<User> findByIdAndClientApp(Long id, ClientApp clientApp);
    Optional<User> findByUsernameAndClientApp(String username, ClientApp clientApp);
}
