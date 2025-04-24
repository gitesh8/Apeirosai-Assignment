package com.apeirosai.model;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {

	private String customerName;
	private List<OrderItems> ordersItems;
}
