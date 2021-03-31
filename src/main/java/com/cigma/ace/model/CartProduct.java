package com.cigma.ace.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "carts_products")
@Getter
@Setter
@NoArgsConstructor
public class CartProduct implements Serializable {
	
	@Id
	@GeneratedValue
	@JsonIgnore
    private Long id;
	@Transient
	@JsonIgnore
	private Long cartId;
	@Transient
	private Long productId;
    private Integer quantity;
	
    @JsonIgnore
	@ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
	
	@JsonBackReference(value = "cart-product")
	@ManyToOne(optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;
	
	
	public Long getProductId() {
	       return product.getId();
	    }

    public Long getCartId() {
       return cart.getId();
    }
}
