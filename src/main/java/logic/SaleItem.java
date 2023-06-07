package logic;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //매개변수 없는 생성자
public class SaleItem {
	private int saleid; //주문번호
	private int seq;   //주문상품번호
	private int itemid; //상품아이디
	private int quantity;
	
	private Item item;
	
	public SaleItem(int saleid, int seq, ItemSet itemSet) {
		this.saleid = saleid;
		this.seq = seq;		
		this.itemid = itemSet.getItem().getId();
		this.quantity = itemSet.getQuantity();
		this.item = itemSet.getItem();
	}
}
