package com.cigma.ace.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Proxy;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "products")
@Getter
@Setter
@Proxy(lazy = false)
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String ref;
	@NotNull
	@Column(unique = true)
	private String title;
	@NotNull
	private Double price;
	@NotNull
	private Integer stock;
	
//	@JsonManagedReference(value = "product-cart")
//	@OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<CartProduct> cartProducts = new HashSet<>();
}
