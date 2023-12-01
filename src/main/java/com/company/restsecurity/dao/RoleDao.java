package com.company.restsecurity.dao;

import com.company.restsecurity.entity.Role;
import java.util.Optional;

public interface RoleDao {

    Optional<Role> findRoleByName(String name);
}
