package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	Account findByUsername(String username);
	Optional<Account> findById(Integer id);
	@Query(value = "select ac.* from  employee em\r\n"
			+ "join account ac on\r\n"
			+ "em.account_id = ac.account_id\r\n"
			+ "where ac.account_username = :un and em.employee_id != :id",nativeQuery = true)
	Account findByUsernameNotId(@Param("un") String username, @Param("id") Integer id);
}
