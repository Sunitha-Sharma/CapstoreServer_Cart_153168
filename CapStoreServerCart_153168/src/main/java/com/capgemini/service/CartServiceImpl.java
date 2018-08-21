package com.capgemini.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.model.Cart;
import com.capgemini.model.Customer;
import com.capgemini.model.Product;
import com.capgemini.repository.CartCustomer;
import com.capgemini.repository.CartProductRepository;
import com.capgemini.repository.CartRepository;


@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	CartRepository repo;
	@Autowired
	CartProductRepository repoProd;
	@Autowired
	CartCustomer repoCust;

	
	
	@Override
	public Product addCartItem(int pid, int custid) {
		Product product = repoProd.getOne(pid);
		Customer customer = repoCust.getOne(custid);
		Cart cart = repo.getOne(customer.getCart().getId());
		List<Product> prod = cart.getProduct();
		prod.add(product);
		cart.setProduct(prod);
		cart.setQuantity(cart.getQuantity() + 1);
		//product.setQuantity(product.getQuantity()-1);
		cart.setStartTime(Date.valueOf(LocalDate.now()));
		repo.save(cart);
		
		return product;

	}

	@Override
	public void addCart(int custid) {
		Customer customer = repoCust.getOne(custid);
		Cart cart = new Cart();
		repo.save(cart);
		customer.setCart(repo.getOne(cart.getId()));
		repoCust.save(customer);

	}

	@Override
	public void removeCartItem(int pid, int custid) {
		Product product = repoProd.getOne(pid);
		Customer customer = repoCust.getOne(custid);
		Cart cart = repo.getOne(customer.getCart().getId());
		List<Product> prod = cart.getProduct();
		prod.remove(product);
		cart.setProduct(prod);
		cart.setQuantity(cart.getQuantity() - 1);
		repo.save(cart);
	}

	/*@Override
	public Set<Product> viewCart(int custid) {
		Customer customer = repocust.getOne(custid);
			List<Product> l=repo.getOne(customer.getCart().getId()).getProduct();
			Set<Product> p=new HashSet<Product>();
			for(Product l1:l)
			{
				l1.setCart(null);
				p.add(l1);
			}
		return p;
	}*/
	
	@Override
	public List<Product> viewCart(int custid) {
		Customer customer = repoCust.getOne(custid);
			List<Product> l=repo.getOne(customer.getCart().getId()).getProduct();
			List<Product> p=new ArrayList<Product>();
			for(Product l1:l)
			{
				l1.setCart(null);
				p.add(l1);
			}
		return p;
	}

	@Override
	public Boolean minAmountCheck(int custid) {
		Customer customer = repoCust.getOne(custid);
		Cart cart = repo.getOne(customer.getCart().getId());
		List<Product> prod = cart.getProduct();
		float amount = 0;
		for (Product product : prod) {
			amount += product.getCost();
		}
		if (amount > 100) {
			return true;
		} else {
			return false;
		}
	}

}
