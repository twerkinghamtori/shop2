package logic;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
/*
 * lombok : setter, getter, toString, 생성자 등을 자동으로 생성해주는 유틸리티.
 * 
 * @Setter
 * @Getter
 * @ToString
 * @EqualsAndHashCode : equals 함수와 hashCode 함수를 자동으로 override
 * @Data : getter, setter, toString, EqualsAndHashCode, RequiredArgConstructor 생성
 * @AllArgsConstructor : 모든 멤버를 매개변수로 가지고 있는 생성자 구현
 * @NoArgsConstructor : 매개변수가 없는 생성자 구현
 * @RequiredArgConstructor : final, @NotNull 인 멤버변수만 매개변수로 갖는 생성자 구현
 */
@Data
public class Board {
	private int num;
	private String boardid;
	@NotEmpty(message="작성자를 입력하세요.")
	private String writer;
	@NotEmpty(message="비밀번호를 입력하세요.")
	private String pass;
	@NotEmpty(message="제목을 입력하세요.")
	private String title;
	@NotEmpty(message="내용을 입력하세요.")
	private String content;
	private MultipartFile file1;
	private String fileurl;	
	private Date regdate;
	private int readcnt;
	private int grp;
	private int grplevel;
	private int grpstep;
	private int commcnt; //db에 없지만 댓글 수를 저장하기 위한 공간.
}
