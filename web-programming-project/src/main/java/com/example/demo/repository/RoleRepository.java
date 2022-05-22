package com.example.demo.repository;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query(value = "select * from role where role_id = :id",nativeQuery = true)
    Role findRoleById(@PathParam("id") Integer id);
	@Query(value = "select r.* from role r\r\n"
			+ "join account ac on\r\n"
			+ "	ac.role_id = r.role_id\r\n"
			+ "where ac.account_id = :id",nativeQuery = true)
	public List<Role> getRoleOfAccountByIdAccount(@Param("id") Integer id);
}
