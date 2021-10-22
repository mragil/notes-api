package com.examplemynotes.notes.repositories;

import com.examplemynotes.notes.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
