package com.apeirosai.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apeirosai.model.OrderItems;
import com.apeirosai.model.Orders;
import com.apeirosai.model.Product;
import com.apeirosai.repository.OrderItemsRepository;
import com.apeirosai.repository.OrdersRepository;
import com.apeirosai.repository.ProductRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


@Service
public class OrderServiceImpl implements OrdersService {
	
	@Autowired
	private OrdersRepository orderRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private OrderItemsRepository orderItemsRepo;

	@Override
	public Orders createOrder(String customerName, List<OrderItems> ordersItems) {
		// TODO Auto-generated method stub
		
		double totalAmount = 0;
		
		
		// traversing ordersItems
		for(OrderItems item: ordersItems) {
			
			// extracting product with product id
			Optional<Product> product = productRepo.findById(item.getProductId());
			
			// checking product stock and product is present
			if(product.isPresent() && product.get().getStock()>=item.getQuantity()) {
				
				// updating total amount
				totalAmount += product.get().getPrice()*item.getQuantity();
				
				
			}
			// updating product stock
			product.get().setStock(product.get().getStock() - item.getQuantity());
			
			// updating product name for each item
			item.setProductName(product.get().getName());
			
			// updating product stock
			productRepo.save(product.get());
			
			// updating order items
			orderItemsRepo.save(item);
			
			
		}
		
		// generating order ID
		String OrderId = "ORD_"+UUID.randomUUID().toString().replace("-", "").substring(0,7);
		
		// generating QR Code Base64 URL
		String QRCodebase64Url = createQrCodeBase64Url(OrderId,customerName, totalAmount);
		
		// creating Orders object 
		Orders order = new Orders();
		order.setCustomerName(customerName);
		order.setItems(ordersItems);
		order.setOrderId(OrderId);
		order.setTotalAmount(totalAmount);
		order.setBas64QRCode(QRCodebase64Url);
		
		// storing updated orders 
		Orders updatedOrders = orderRepo.save(order);
		
		return updatedOrders;
	}

	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		
		// fetching all the orders
		List<Orders> orders = orderRepo.findAll();
		
		return orders;
	}

	@Override
	public String createQrCodeBase64Url(String orderId, String customerName, Double totalPrice) {
		// TODO Auto-generated method stub
		
		// QR Code Content
		String details = String.format("OrderID: %s\nTotal: Rs %s\nCustomer Name: %s", orderId,totalPrice,customerName);
		
		// QR Code Structure
		Integer width = 300;
		Integer height = 300;
		String imageFormat = "png";
		
		try {
			
			// Encoding content to QR Code
			Map<EncodeHintType,Object> hints = new HashMap<>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitmatrix = new MultiFormatWriter().encode(details,BarcodeFormat.QR_CODE,width,height,hints);
			
			// Converting to to BufferedImage
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitmatrix);
			
			// Converting to Base64
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, imageFormat, byteArrayOutputStream);
			byte[] imageBytes = byteArrayOutputStream.toByteArray();
			
			return Base64.getEncoder().encodeToString(imageBytes);
			
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

}
