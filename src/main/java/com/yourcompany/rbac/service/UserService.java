package com.yourcompany.rbac.service;

import com.yourcompany.rbac.dto.CreateUserRequest;
import com.yourcompany.rbac.entity.ClientApp;
import com.yourcompany.rbac.entity.Role;
import com.yourcompany.rbac.entity.User;
import com.yourcompany.rbac.exception.NotFoundException;
import com.yourcompany.rbac.repository.RoleRepository;
import com.yourcompany.rbac.repository.UserRepository;
import com.yourcompany.rbac.util.TenantContext;
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
