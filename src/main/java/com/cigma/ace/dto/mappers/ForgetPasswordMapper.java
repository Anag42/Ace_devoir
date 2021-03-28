package com.cigma.ace.dto.mappers;

import org.mapstruct.Mapper;

import com.cigma.ace.dto.ForgetPasswordDTO;
import com.cigma.ace.model.PasswordReset;

@Mapper
public interface ForgetPasswordMapper {
	ForgetPasswordDTO toForgetPasswordDTO(PasswordReset passwordReset);

	PasswordReset toPasswordReset(ForgetPasswordDTO forgetPasswordDTO);
}
