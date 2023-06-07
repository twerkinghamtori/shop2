package logic;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class Item {
	private int id;	
	@NotBlank(message="상품명을 입력하세요.") // "" || null || " "  (x) 
	private String name;	
//	@Pattern(regexp = "^[0-9]+$", message="숫자만 가능합니다.")
	@Min(value=10, message="10원 이상만 가능합니다.")
	@Max(value=100000, message="10만원 이하만 가능합니다.")
	private int price;	
	@NotEmpty(message="상품설명을 입력하세요.")
	private String description;	
	private String pictureUrl;
	private MultipartFile picture; //picture file에서 업로드된 파일의 내용
}
