package com.cigma.ace.repository;

import com.cigma.ace.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {
	<Optional>PasswordReset findByEmail(String email);
}
