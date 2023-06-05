package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
import logic.Mail;
import logic.ShopService;
import logic.User;
/*모든 메서드는 관리자로 로그인했을 때만 실행 => AOP */
@Controller
@RequestMapping("admin")
public class AdminController {
	@Autowired
	private ShopService service;
	
	@RequestMapping("list")
	public ModelAndView list(String sort, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<User> userList = service.selectUserAll();
		if(sort != null) {
			switch(sort) {
			case "10" : 
//				Collections.sort(userList, (u1,u2) -> u1.getUserid().compareTo(u2.getUserid()));
				Collections.sort(userList, new Comparator<User>() {
					@Override
					public int compare(User u1, User u2) {
						return u1.getUserid().compareTo(u2.getUserid());
					}
				});
				
				break;
			case "11" : 
				Collections.sort(userList, (u1,u2) -> u2.getUserid().compareTo(u1.getUserid()));
				break;
			case "20" : 
				Collections.sort(userList, (u1,u2)-> u1.getUsername().compareTo(u2.getUsername()));
				break;
			case "21" : 
				Collections.sort(userList, (u1,u2)-> u2.getUsername().compareTo(u1.getUsername()));
				break;
			case "30" : 
				Collections.sort(userList, (u1,u2)-> u1.getPhoneno().compareTo(u2.getPhoneno()));
				break;
			case "31" : 
				Collections.sort(userList, (u1,u2)-> u2.getPhoneno().compareTo(u1.getPhoneno()));
				break;
			case "40" : 
				Collections.sort(userList, (u1,u2)-> u1.getBirthday().compareTo(u2.getBirthday()));
				break;
			case "41" : 
				Collections.sort(userList, (u1,u2)-> u2.getBirthday().compareTo(u1.getBirthday()));
				break;
			case "50" : 
				Collections.sort(userList, (u1,u2)-> u1.getEmail().compareTo(u2.getEmail()));
				break;
			case "51" : 
				Collections.sort(userList, (u1,u2)-> u2.getEmail().compareTo(u1.getEmail()));
				break;
			}
		}
		mav.addObject("list",userList); 
		return mav;
	}
	
	@RequestMapping("mailForm")	
	public ModelAndView mailForm(String[] idchks, HttpSession session) { //String[] idchks = request.getParameterValues
		//idchks 배열말고 String으로 받으면 ,로 붙어서 들어옴
		ModelAndView mav = new ModelAndView("admin/mail");
		if(idchks == null || idchks.length == 0) {
			throw new LoginException("메일을 보낼 대상을 선택하세요.", "list");
		}
		List<User> list = service.getUserList(idchks);
		mav.addObject("list",list);
		return mav;
	}
	
	public final class MyAuthenticator extends Authenticator {
		private String id;
		private String pw;
		   
		public MyAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}
		   
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id,pw);
		}
	}
	
	@RequestMapping("mail")
	public ModelAndView mail(Mail mail, HttpSession session) {
		ModelAndView mav = new ModelAndView("alert");
		Properties prop = new Properties();
		try {
			//java, resources 폴더의 내용은 : WEB-INF/classes 에 복사됨.
			 FileInputStream fis = new FileInputStream(session.getServletContext().getRealPath("/") + "WEB-INF/classes/mail.properties"); //resources 밑에
			 prop.load(fis);
			 prop.put("mail.smtp.user", mail.getNaverid());
//			 System.out.println(prop);
		} catch(IOException e) {
			 e.printStackTrace();
		}
		mailSend(mail, prop);
		mav.addObject("message", "메일 전송이 완료되었습니다.");
		mav.addObject("url", "list");
		return mav;
	}

	private void mailSend(Mail mail, Properties prop) {
		MyAuthenticator auth = new MyAuthenticator(mail.getNaverid(), mail.getNaverpw());	  

		Session s = Session.getInstance(prop, auth); 

		MimeMessage msg = new MimeMessage(s);		
		   
		try {
			msg.setFrom(new InternetAddress(mail.getNaverid() + "@naver.com")); 
			List<InternetAddress> addrs = new ArrayList<InternetAddress>();
			String[] emails = mail.getRecipient().split(",");
			for(String email : emails) {
				try {
					addrs.add(new InternetAddress(new String(email.getBytes("utf-8"), "8859_1")));
				} catch(UnsupportedEncodingException ue) {
					ue.printStackTrace();
				}			   
			}
			InternetAddress[] address = new InternetAddress[emails.length];
			for(int i=0; i<addrs.size(); i++) {
				address[i] = addrs.get(i);
			}			
			msg.setRecipients(Message.RecipientType.TO, address); 
			msg.setSubject(mail.getTitle());
			msg.setSentDate(new Date());
			
			MimeMultipart multipart = new MimeMultipart(); //내용 따로, 첨부파일 따로 해주는거
			MimeBodyPart body = new MimeBodyPart();
			body.setContent(mail.getContents(), mail.getMtype());
			multipart.addBodyPart(body);
			msg.setContent(multipart);
			
			//첨부파일 파일 추가
			for(MultipartFile mf : mail.getFile1()) {
				if ((mf != null) && (!mf.isEmpty())) {
					multipart.addBodyPart(bodyPart(mf));
				}
			}
			msg.setContent(multipart);
			
			//메일 전송
			Transport.send(msg);
		} catch(MessagingException me) {
			System.out.println(me.getMessage());
			me.printStackTrace();
		}
	}

	private BodyPart bodyPart(MultipartFile mf) {
		MimeBodyPart body = new MimeBodyPart();
		String orgFile = mf.getOriginalFilename();
		String path = "c:/mailupload/";
		File f1 = new File(path);
		if(!f1.exists()) f1.mkdirs();
		File f2 = new File(path + orgFile);
		try {
			mf.transferTo(f2); // path에 파일업로드
			body.attachFile(f2); // 이메일에 첨부
			body.setFileName(new String(orgFile.getBytes("UTF-8"), "8859_1")); //첨부된 파일의 파일 명을 인식할 수 있도록 바꿔서 삽입.
		} catch(Exception e) {
			e.printStackTrace();
		}
		return body;
	}
}
