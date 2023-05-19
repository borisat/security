package com.example.pp3.dao;

import com.example.pp3.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface RoleDAO extends JpaRepository<Role, Integer> {
    List<Role> findByName(String name);

//    Role findByName(String name);

}