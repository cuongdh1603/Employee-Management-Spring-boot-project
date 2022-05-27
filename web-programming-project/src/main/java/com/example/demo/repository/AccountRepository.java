package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	@Query(value = "select * from account where account_active = 1 and account_username = :un",nativeQuery = true)
	Account findByUsername(@Param("un") String username);
	@Query(value = "select * from account where account_active = 1 and account_id = :id",nativeQuery = true)
	Optional<Account> findById(@Param("id") Integer id);
	@Query(value = "select ac.* from  employee em\r\n"
			+ "join account ac on\r\n"
			+ "em.account_id = ac.account_id\r\n"
			+ "where ac.account_active = 1 and ac.account_username = :un and em.employee_id != :id",nativeQuery = true)
	Account findByUsernameNotId(@Param("un") String username, @Param("id") Integer id);
}
