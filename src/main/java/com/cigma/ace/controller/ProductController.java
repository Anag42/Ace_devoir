package com.cigma.ace.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cigma.ace.dto.ProductCreateDTO;
import com.cigma.ace.dto.ProductDTO;
import com.cigma.ace.dto.ProductUpdateDTO;
import com.cigma.ace.dto.mappers.ProductMapper;
import com.cigma.ace.exception.DeleteProductStillInCartException;
import com.cigma.ace.exception.ModelNotFoundException;
import com.cigma.ace.model.Product;
import com.cigma.ace.service.implementations.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	@Autowired
	ProductService productService;

	@Autowired
	ProductMapper productMapper;

	@PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENT')")
	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {
		return ResponseEntity.ok(productMapper.toProductDTOs(productService.findAll()));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ProductCreateDTO> create(@Validated @RequestBody ProductCreateDTO productCreateDTO) throws IOException, MessagingException {
		
		productService.create(productMapper.toProduct(productCreateDTO));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreateDTO);
	}

	@PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENT')")
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		Optional<Product> product = productService.findById(id);
		
		if (!product.isPresent())
			throw new ModelNotFoundException(Product.class, id);

		return ResponseEntity.ok(productMapper.toProductDTO(product.get()));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ProductUpdateDTO> update(@Valid @PathVariable Long id, @RequestBody ProductUpdateDTO productUpdateDTO) {
		Optional<Product> product = productService.findById(id);

		if (!product.isPresent())
			throw new ModelNotFoundException(Product.class, id);
		
		Product requestProduct = productMapper.toProduct(productUpdateDTO);
		
		requestProduct.setId(id);
		requestProduct.setRef(product.get().getRef());
		productService.update(requestProduct);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productUpdateDTO);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Product> product = productService.findById(id);

		if (!product.isPresent())
			throw new ModelNotFoundException(Product.class, id);
		
		if (productService.presentInCart(id))
			throw new DeleteProductStillInCartException();
		

		productService.deleteById(id);

		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
