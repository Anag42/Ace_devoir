package com.cigma.ace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cigma.ace.enums.Role;
import com.cigma.ace.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	<Optional>User findByUsername(String username);
	<Optional>User findByEmail(String email);
	List<User> findByRole(Role role);
	Boolean existsByEmail(String string);
	Boolean existsByUsername(String string);
}
