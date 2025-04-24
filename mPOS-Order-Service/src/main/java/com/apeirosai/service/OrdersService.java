package com.apeirosai.service;

import java.util.List;

import com.apeirosai.model.OrderItems;
import com.apeirosai.model.Orders;

public interface OrdersService {

	public Orders createOrder(String customerName, List<OrderItems> ordersItems);

	public List<Orders> getAllOrders();
	
	public String createQrCodeBase64Url(String orderId, String customerName, Double totalPrice);
}
