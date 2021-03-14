package com.cigma.ace.dto;

import com.cigma.ace.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setTelephone(user.getTelephone());
        
        return userDTO;
    }

    @Override
    public List<UserDTO> toUserDTOs(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toUserDTO( user ) );
        }

        return list;
    }

    @Override
    public User toUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
		user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setTelephone(userDTO.getTelephone());
        
        return user;
    }
}
