package com.cigma.ace.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigma.ace.dto.AddProductToCartDTO;
import com.cigma.ace.exception.ModelNotFoundException;
import com.cigma.ace.model.Cart;
import com.cigma.ace.model.CartProduct;
import com.cigma.ace.model.Product;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.CartProductRepository;
import com.cigma.ace.repository.CartRepository;
import com.cigma.ace.service.implementations.ProductService;
import com.cigma.ace.service.implementations.UserService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
	@Autowired
	UserService userService;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartProductRepository cartProductRepository;

	
	@Autowired
	ProductService productService;
	
	@PreAuthorize("hasRole('CLIENT')")
	@GetMapping
	public ResponseEntity<Set<CartProduct>> get(){
		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(authenticatedUser.getCart().getCartProducts());
	}
	
	@PreAuthorize("hasRole('CLIENT')")
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody AddProductToCartDTO addProductToCartDTO){
		Product product = productService.findById(addProductToCartDTO.getProductId())
				.orElseThrow(() -> new ModelNotFoundException(Product.class, addProductToCartDTO.getProductId()));

		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Cart cart = authenticatedUser.getCart();

		CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId());
		
		if(cartProduct == null) {
			cartProduct = new CartProduct();
		}
		
		cartProduct.setCart(cart);
		cartProduct.setProduct(product);
		cartProduct.setQuantity(addProductToCartDTO.getQuantity());
		cartProductRepository.save(cartProduct);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
