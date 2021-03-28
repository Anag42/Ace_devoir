package com.cigma.ace.dto.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cigma.ace.dto.ProductCreateDTO;
import com.cigma.ace.dto.ProductDTO;
import com.cigma.ace.dto.ProductUpdateDTO;
import com.cigma.ace.model.Product;


@Mapper(componentModel = "spring")
public interface ProductMapper {
	ProductDTO toProductDTO(Product product);

    List<ProductDTO> toProductDTOs(List<Product> products);

    Product toProduct(ProductDTO productDTO);
    
    ProductCreateDTO toProductCreateDTO(Product product);

    Product toProduct(ProductCreateDTO productCreateDTO);
    
    ProductUpdateDTO toProductUpdateDTO(Product product);

    Product toProduct(ProductUpdateDTO productUpdateDTO);
}
