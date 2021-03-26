package com.cigma.ace.service.implementations;

import com.cigma.ace.enums.Role;
import com.cigma.ace.model.PasswordReset;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.PasswordResetRepository;
import com.cigma.ace.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class PasswordResetService {

	@Autowired
    PasswordResetRepository passwordResetRepository;

    public void create(PasswordReset passwordReset) {
        passwordResetRepository.save(passwordReset);
    }

}
