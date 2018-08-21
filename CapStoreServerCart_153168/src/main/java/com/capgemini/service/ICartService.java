package com.capgemini.service;


import java.util.List;
import java.util.Set;
import com.capgemini.model.Cart;
import com.capgemini.model.Product;

public interface ICartService {

	public Product addCartItem(int pid, int custid);

	public void addCart(int custid);

	public void removeCartItem(int pid, int custid);

	public List<Product> viewCart(int custid);

	public Boolean minAmountCheck(int custid);

}
