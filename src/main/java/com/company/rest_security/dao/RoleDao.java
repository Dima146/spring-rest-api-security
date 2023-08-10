package com.company.rest_security.dao;

import com.company.rest_security.entity.Role;
import java.util.Optional;

public interface RoleDao {

    Optional<Role> findRoleByName(String name);
}
