package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.constant.UserRole;
import com.enigma.group5.save_it_backend.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
