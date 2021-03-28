package com.cigma.ace.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.cigma.ace.annotation.Unique;
import com.cigma.ace.service.implementations.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO {
	@Unique(service = ProductService.class, fieldName = "title", message = "{title.unique.violation}")
	@NotBlank(message = "Provide a title!")
	private String title;
	@NotNull(message = "Provide a price!")
	@DecimalMin(value = "1")
	private Double price;
	@NotNull(message = "Provide available stock!")
	@DecimalMin(value = "1")
	private Integer stock;
}
