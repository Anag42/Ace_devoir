package com.cigma.ace.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("serial")
@Entity
@Table(name = "carts")
@Getter
@Setter
@Proxy(lazy = false)
public class Cart implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonBackReference(value = "user-cart")
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@JsonManagedReference(value = "cart-product")
	@OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private Set<CartProduct> cartProducts = new HashSet<>();
}
