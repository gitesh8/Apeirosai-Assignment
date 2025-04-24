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
		
		for(OrderItems item: ordersItems) {
			
			
			Optional<Product> product = productRepo.findById(item.getProductId());
			
			if(product.isPresent() && product.get().getStock()>=item.getQuantity()) {
				
				totalAmount += product.get().getPrice()*item.getQuantity();
				
				
			}
			product.get().setStock(product.get().getStock() - item.getQuantity());
			item.setProductName(product.get().getName());
			
			// updating product stock
			productRepo.save(product.get());
			
			// updating order items
			orderItemsRepo.save(item);
			
			
		}
		
		String OrderId = "ORD_"+UUID.randomUUID().toString().replace("-", "").substring(0,7);
		String QRCodebase64Url = createQrCodeBase64Url(OrderId,customerName, totalAmount);
		
		// creating Order
		Orders order = new Orders();
		order.setCustomerName(customerName);
		order.setItems(ordersItems);
		order.setOrderId(OrderId);
		order.setTotalAmount(totalAmount);
		
		
		
		Orders updatedOrders = orderRepo.save(order);
		updatedOrders.setBas64QRCode(QRCodebase64Url);
		
		return updatedOrders;
	}

	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		
		List<Orders> orders = orderRepo.findAll();
		
		return orders;
	}

	@Override
	public String createQrCodeBase64Url(String orderId, String customerName, Double totalPrice) {
		// TODO Auto-generated method stub
		
		String details = String.format("OrderID: %s\nTotal: Rs %s\nCustomer Name: %s", orderId,totalPrice,customerName);
		
		Integer width = 300;
		Integer height = 300;
		String imageFormat = "png";
		
		Map<EncodeHintType,Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix bitmatrix = new MultiFormatWriter().encode(details,BarcodeFormat.QR_CODE,width,height,hints);
			
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitmatrix);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, imageFormat, baos);
			
			byte[] imageBytes = baos.toByteArray();
			
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
