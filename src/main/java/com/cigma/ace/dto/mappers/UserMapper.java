package com.cigma.ace.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;

import com.cigma.ace.dto.UserDTO;
import com.cigma.ace.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    User toUser(UserDTO userDTO);
}
