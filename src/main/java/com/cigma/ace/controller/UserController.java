package com.cigma.ace.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigma.ace.dto.UserDTO;
import com.cigma.ace.dto.UserMapper;
import com.cigma.ace.enums.Role;
import com.cigma.ace.exception.ModelNotFoundException;
import com.cigma.ace.model.User;
import com.cigma.ace.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	UserMapper userMapper;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok(userMapper.toUserDTOs(userService.findByRole(Role.CLIENT)));
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
		userService.save(userMapper.toUser(userDTO));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);

		if (!user.isPresent())
			throw new ModelNotFoundException(User.class, id);

		return ResponseEntity.ok(userMapper.toUserDTO(user.get()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, @RequestBody UserDTO userDTO) {
		Optional<User> user = userService.findById(id);

		if (!user.isPresent())
			throw new ModelNotFoundException(User.class, id);
		
		User requestUser = userMapper.toUser(userDTO);

		requestUser.setId(id);
		userService.save(requestUser);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);

		if (!user.isPresent())
			throw new ModelNotFoundException(User.class, id);

		userService.deleteById(id);

		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
