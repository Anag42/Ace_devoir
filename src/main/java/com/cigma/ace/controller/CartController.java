package com.cigma.ace.controller;

import java.io.IOException;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigma.ace.dto.AddProductToCartDTO;
import com.cigma.ace.exception.InvalidQuantityException;
import com.cigma.ace.exception.ModelNotFoundException;
import com.cigma.ace.mail.InvoiceMail;
import com.cigma.ace.model.Cart;
import com.cigma.ace.model.CartProduct;
import com.cigma.ace.model.Product;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.CartProductRepository;
import com.cigma.ace.repository.CartRepository;
import com.cigma.ace.service.implementations.EmailService;
import com.cigma.ace.service.implementations.ProductService;
import com.cigma.ace.service.implementations.UserService;
import com.cigma.ace.util.CartExcelExporter;

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
	EmailService emailService;
	
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
		
		if(product.getStock() < addProductToCartDTO.getQuantity()){
			throw new InvalidQuantityException(product.getStock()); 
		}

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
	
	@PreAuthorize("hasRole('CLIENT')")
	@DeleteMapping("/remove/product/{product_id}")
	public ResponseEntity<?> remove(@PathVariable(name = "product_id") Long id){
		Product product = productService.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Product.class, id));

		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Cart cart = authenticatedUser.getCart();

		CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId());
		
		if(cartProduct != null){
			cartProductRepository.deleteById(cartProduct.getId());
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
	}
	
	@PreAuthorize("hasRole('CLIENT')")
	@GetMapping("/order")
	public Object order() throws MessagingException, IOException{
		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Cart cart = authenticatedUser.getCart();
    	CartExcelExporter exporter = new CartExcelExporter(cart);
    	byte[] excelFileAsBytes = exporter.generateFileAsBytes();

		cart.getCartProducts().forEach((cartProduct) -> {
			Product product = cartProduct.getProduct();
			product.setStock(product.getStock() > cartProduct.getQuantity() ? product.getStock() - cartProduct.getQuantity() : 0);
			productService.update(product);
			cartProductRepository.deleteById(cartProduct.getId());
		});
		
		String subject = "Order Invoice!";
    	String body = InvoiceMail.build();
		ByteArrayResource resource = new ByteArrayResource(excelFileAsBytes);
		emailService.sendHtmlMailWithAttachment(authenticatedUser.getEmail(), subject, body, "Invoice.xlsx", resource);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

}
