package com.cigma.ace.repository;

import com.cigma.ace.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {
}
