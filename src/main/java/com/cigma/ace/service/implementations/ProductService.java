package com.cigma.ace.service.implementations;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.cigma.ace.model.Product;
import com.cigma.ace.repository.ProductRepository;
import com.cigma.ace.service.IFieldValueExists;

@Service
public class ProductService implements IFieldValueExists {

	@Autowired 
	ProductRepository productRepository;
	
	public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void create(Product product) {
    	productRepository.save(product);
    }

    public void update(Product product){
    	productRepository.save(product);
    }
    
    public Boolean presentInCart(Long id) {
    	return productRepository.presentInCart(id);	
    }

    public void deleteById(Long id) {
    	productRepository.deleteById(id);	
    }

	@SuppressWarnings("deprecation")
	@Override
	public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
		Assert.notNull(fieldName);

        if (!fieldName.equals("title")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

        if (value == null) {
            return false;
        }

        if(fieldName.equals("title")) {
        	return this.productRepository.existsByTitle(value.toString());
        }
        
        return false;
	}

	public List<Product> findByTitleIgnoreCaseContaining(String keyword) {
		return productRepository.findByTitleIgnoreCaseContaining(keyword);
	}
}
