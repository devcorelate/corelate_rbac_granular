package com.corelate.rbac.service;

import com.corelate.rbac.dto.CreateUserRequest;
import com.corelate.rbac.entity.ClientApp;
import com.corelate.rbac.entity.Role;
import com.corelate.rbac.entity.User;
import com.corelate.rbac.exception.NotFoundException;
import com.corelate.rbac.repository.RoleRepository;
import com.corelate.rbac.repository.UserRepository;
import com.corelate.rbac.util.TenantContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> list() {
        ClientApp clientApp = TenantContext.getTenant();
        return userRepository.findAllByClientApp(clientApp);
    }

    public User create(CreateUserRequest request) {
        ClientApp clientApp = TenantContext.getTenant();
        User user = new User();
        user.setClientApp(clientApp);
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        return userRepository.save(user);
    }

    public User assignRole(Long userId, Long roleId) {
        ClientApp clientApp = TenantContext.getTenant();
        User user = userRepository.findByIdAndClientApp(userId, clientApp)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Role role = roleRepository.findByIdAndClientApp(roleId, clientApp)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
