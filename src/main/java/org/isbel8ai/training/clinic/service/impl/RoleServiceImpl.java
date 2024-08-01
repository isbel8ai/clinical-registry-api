package org.isbel8ai.training.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.RoleRepository;
import org.isbel8ai.training.clinic.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
