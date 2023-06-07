package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Sale {
	private int saleid;
	private String userid;
	private Date saledate;
	
	private User user;
	
	private List<SaleItem> itemList = new ArrayList<>(); //주문 상품 목록
	
	public int getTotal() {
		int sum = 0;
//		for(SaleItem s : itemList) {
//			sum += s.getItem().getPrice() * s.getQuantity();
//		}
//		return sum;
		return itemList.stream().mapToInt(s->s.getItem().getPrice() * s.getQuantity()).sum();
	}	
}	
