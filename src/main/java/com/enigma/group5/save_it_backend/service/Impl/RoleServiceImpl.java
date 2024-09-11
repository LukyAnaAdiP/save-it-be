package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.UserRole;
import com.enigma.group5.save_it_backend.entity.Role;
import com.enigma.group5.save_it_backend.repository.RoleRepository;
import com.enigma.group5.save_it_backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(
                        Role.builder()
                                .role(role)
                                .build()
                ));
    }
}
