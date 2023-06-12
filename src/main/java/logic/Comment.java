package logic;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {
	private int num;
	private int seq;
	private String writer;
	private String pass;
	private String content;
	private Date regdate;
}
