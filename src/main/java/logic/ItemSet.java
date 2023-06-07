package logic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //멤버 변수값으로 생성자 구현
public class ItemSet {
	private Item item;	
	private Integer quantity;
}
