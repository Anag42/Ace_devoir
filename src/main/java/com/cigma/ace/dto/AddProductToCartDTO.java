package com.cigma.ace.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductToCartDTO {
	@NotNull(message="Please provide a product id")
	private Long productId;
	@NotNull(message="Please provide a quantity")
	@DecimalMin(message="Quantity must be superior or equals 1", value="1")
	private Integer quantity;
}
