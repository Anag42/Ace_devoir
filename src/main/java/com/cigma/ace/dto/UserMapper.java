package com.cigma.ace.dto;

import java.util.List;

import org.mapstruct.Mapper;
import com.cigma.ace.model.User;

@Mapper
public interface UserMapper {
	UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    User toUser(UserDTO userDTO);
}
