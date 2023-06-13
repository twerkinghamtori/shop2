package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
import logic.Sale;
import logic.ShopService;
import logic.User;
import util.CipherUtil;


@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private ShopService service;
	@Autowired
	private CipherUtil cipher;
	
	private String emailEncrypt(String email, String userid) throws Exception {
		String key = cipher.makehash(userid, "SHA-256");
		return cipher.emailEncrypt(email, key);
	}

	private String passwordHash(String password) throws Exception {
		return cipher.makehash(password, "SHA-512");
	}
	
	private String emailDecrypt(User u) throws Exception {
		return cipher.decrypt(u.getEmail(), cipher.makehash(u.getUserid(), "SHA-256"));
	}	
	
	@GetMapping("*") //controller에서 설정되지 않은 모든 요청시 호출되는 메서드
	public ModelAndView join() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new User()); // modelAttribute="user" => User객체 생성
		return mav;
	}
	
	@PostMapping("join")
	public ModelAndView userAdd(@Valid User user, BindingResult br) throws Exception {
		ModelAndView mav = new ModelAndView();
		//input check
		if(br.hasErrors()) {
			mav.getModel().putAll(br.getModel());
			br.reject("error.input.user"); ///reject(에러코드) 메서드 => global error에 추가
			br.reject("error.input.check");
			return mav;
		}
		//회원가입 => db의 usersecurity table에 저장
		try {
			/*
			 * password : SHA-512 해쉬값 변경
			 * email : AES 알고리즘으로 암호화
			 */
			user.setPassword(passwordHash(user.getPassword()));
			user.setEmail(emailEncrypt(user.getEmail(), user.getUserid()));
			service.userinsert(user);
			mav.addObject("user",user);
		} catch(DataIntegrityViolationException e) { //중복키 error
			e.printStackTrace();
			br.reject("error.duplicate.user"); //global 오류 등록
			mav.getModel().putAll(br.getModel());
			return mav;
		}
		mav.setViewName("redirect:login");
		return mav;
	}	
	
	@GetMapping("login")
	public ModelAndView loginForm(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//apiURL을 생성하여 view로 전달
		String clientId = "ajaWNgFy9gvIJ77K43cD";
		String redirectURI = null;
		try {
			redirectURI = URLEncoder.encode("http://localhost:8080/shop2/user/naverLogin","UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SecureRandom random = new SecureRandom(); //난수발생기
		String state = new BigInteger(130,random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state; 
		mav.addObject("apiURL",apiURL);
		mav.addObject(new User());
		session.getServletContext().setAttribute("state", state);
		System.out.println("sessionId : " + session.getId());
		return mav;
	}
	
	@RequestMapping("naverLogin")
	public String naverLogin(String code, String state, HttpSession session) throws Exception {
		System.out.println("2.sessionId : " + session.getId());
		String clientId = "ajaWNgFy9gvIJ77K43cD";
		String clientSecret = "0mD0OkDSBx";
		String redirectURI = URLEncoder.encode("YOUR_CALLBACK_URL", "UTF-8");
		  String apiURL;
		  apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		  apiURL += "client_id=" + clientId;
		  apiURL += "&client_secret=" + clientSecret;
		  apiURL += "&redirect_uri=" + redirectURI;
		  apiURL += "&code=" + code;
		  apiURL += "&state=" + state;
		  System.out.println("code=" + code + ", state=" + state);
		  String access_token = "";
		  String refresh_token = ""; //아마도 재로그인할 때 쓸듯..
		  StringBuffer sb = new StringBuffer();
		  System.out.println("apiURL="+apiURL);
		  try {
			  URL url = new URL(apiURL);
			  HttpURLConnection con = (HttpURLConnection) url.openConnection();
			  con.setRequestMethod("GET");
			  int responseCode = con.getResponseCode();
			  BufferedReader br;
			  System.out.println("responseCode=" + responseCode);
			  if(responseCode == 200) { 
					br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			  } else {
					br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8")); 
		      }
			  String inputLine;
			  while((inputLine = br.readLine()) != null) {
				  sb.append(inputLine);
			  }
			  br.close();
			  if(responseCode == 200) {
				  System.out.println("\n===========첫번째 요청에 대한 응답메세지 (sb 1):");
				  System.out.println("sb : " + sb.toString());
			  }
			  //sb(=response (네이버 응답 메세지) ) : JSON형태의 문자열 {"access_token":"AAAANxVDVPVQ-O....}
		  } catch(Exception e) {
			  System.out.println(e);
		  }
		  JSONParser parser = new JSONParser();
		  JSONObject json = (JSONObject)parser.parse(sb.toString()); //문자열 -> JSON객체
		  String token = (String)json.get("access_token"); //토큰
		  System.out.println("\n========token : " + token);
		  String header = "Bearer " + token;
		  try {
			  apiURL = "https://openapi.naver.com/v1/nid/me"; //두번째 요청. 프로필 정보 조회(토큰값을 이용해서)
			  URL url = new URL(apiURL);
			  HttpURLConnection con = (HttpURLConnection) url.openConnection();
			  con.setRequestMethod("GET");
			  con.setRequestProperty("Authorization", header); //토큰 전달. 인증정보
			  int responseCode = con.getResponseCode();
			  BufferedReader br;
			  sb = new StringBuffer();
			  if(responseCode == 200) { 
				  System.out.println("로그인 정보 정상 수신");
				  br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			  } else {
				  System.out.println("로그인 정보 수신 오류");
				  br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8")); 
		      }
			  String inputLine;
			  while((inputLine = br.readLine()) != null) {
				  sb.append(inputLine);
			  }
			  br.close();
			  System.out.println("sb.toString()");
		  } catch(Exception e) {
			  System.out.println(e);
		  }
		  json = (JSONObject)parser.parse(sb.toString());
		  System.out.println(json);
		  JSONObject jsondetail = (JSONObject)json.get("response");
		System.out.println(jsondetail.get("id"));
		System.out.println(jsondetail.get("name"));
		System.out.println(jsondetail.get("email"));
		return null;
	}
	
	@PostMapping("login")
	public ModelAndView login(@Valid User user, BindingResult br, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(br.hasErrors()) {
			mav.getModel().putAll(br.getModel());
			br.reject("error.login.input");
			return mav;
		}
		//mybatis에서는 데이터가 없어도 exception을 내지 않기 때문에 예외처리(EmptyResultDataAccessException)가 불가함
		User dbUser = service.selectUserOne(user.getUserid());
		if(dbUser == null) {
			br.reject("error.login.id");
			mav.getModel().putAll(br.getModel());
			return mav;
		}
		//비밀번호 검증
		String hashPass = cipher.makehash(user.getPassword(), "SHA-512");
		if(hashPass.equals(dbUser.getPassword())) {
			session.setAttribute("loginUser", dbUser);
			mav.addObject("userid",dbUser.getUserid());
			mav.setViewName("redirect:mypage");
		} else {
			br.reject("error.login.password");
			mav.getModel().putAll(br.getModel());
		}		
		return mav;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate(); //장바구니 정보(CART)도 함께 삭제해야함
		return "redirect:login";
	}
	
	@RequestMapping("mypage")
	public ModelAndView idCheckMypage(String userid, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<Sale> saleList = service.selectSaleList(userid);
		User user = service.selectUserOne(userid);			
		user.setEmail(cipher.decrypt(user.getEmail(), cipher.makehash(userid, "SHA-256")));
		mav.addObject("saleList", saleList);
		mav.addObject("user",user);
		return mav;
	}

	@GetMapping({"update", "delete"})
	public ModelAndView idCheckUpdateGet(String userid, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUserOne(userid);
		user.setEmail(cipher.decrypt(user.getEmail(), cipher.makehash(userid, "SHA-256")));
		mav.addObject("user",user);
		return mav;
	}
	
	@PostMapping("update")
	public ModelAndView idCheckUpdatePost(@Valid User user, BindingResult br, String userid, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		User sessionUser = (User)session.getAttribute("loginUser");
		User dbUser = service.selectUserOne(user.getUserid());
		if(!passwordHash(user.getPassword()).equals(dbUser.getPassword())) {
			mav.getModel().putAll(br.getModel());
			br.reject("error.login.password");			
			return mav;
		}
		if(br.hasErrors()) {
			mav.getModel().putAll(br.getModel());
			br.reject("error.input.check");
			return mav;
		}	
		try {			
			user.setEmail(emailEncrypt(user.getEmail(), user.getUserid()));
			service.userUpdate(user);
			if(!sessionUser.getUserid().equals("admin")) session.setAttribute("loginUser", dbUser);
			mav.addObject("user",user);		
		} catch(DataIntegrityViolationException e) { 
			e.printStackTrace();
			br.reject("error.duplicate.user"); 
			mav.getModel().putAll(br.getModel());
			return mav;
		}	
		mav.setViewName("redirect:mypage?userid="+userid);
		return mav;
	}
	
	@PostMapping("delete")
	public String idCheckDeletePost(String password, String userid, HttpSession session) {
		User sessionUser = (User)session.getAttribute("loginUser");
		if(userid.equals("admin")) {
			throw new LoginException("관리자는 탈퇴가 불가합니다.", "mypage?userid="+userid);
		}
		if(!password.equals(sessionUser.getPassword())) {
			throw new LoginException("비밀번호가 틀립니다.", "delete?userid="+userid);
		}
		try {
			service.userDelete(userid);
		} catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new LoginException("주문내역이 있는 회원은 탈퇴할 수 없습니다. 관리자에게 문의하세요.", "mypage?userid="+userid);
		} catch(Exception e) {
			e.printStackTrace();
			throw new LoginException("탈퇴시 오류 발생", "delete?userid="+userid);
		}
		if(sessionUser.getUserid().equals("admin")) {
			return "redirect:../admin/list";
		} else {
			session.invalidate();
			return "redirect:login";
		}
	}
	
	/*
	 UserLoginAspect.loginCheck() : UserController.loginCheck*(..)인 메서드. 마지막 매개변수 session인 메서드
	 */	
	@PostMapping("password")
	public String loginCheckPassword(String password, String chgpass, HttpSession session) throws Exception {
		User sessionUser = (User)session.getAttribute("loginUser");
		if(!passwordHash(password).equals(sessionUser.getPassword())) {
			throw new LoginException("기존 비밀번호가 틀립니다.", "password");
		} else {
			try {
				User user = service.changePass(passwordHash(chgpass), sessionUser.getUserid());
				session.setAttribute("loginUser", user);
				//sessionUser.setPassword(chgpass);
				return "redirect:mypage?userid="+user.getUserid();
			} catch(Exception e) {
				throw new LoginException("비밀번호 수정 오류 발생", "password");
			}
		}
	}
	
	//{url}search => {url} 지정되지 않음.	*search 요청시 호출되는 메서드.
	@PostMapping("{url}search")
	public ModelAndView search(User user, BindingResult br, @PathVariable String url) throws Exception { //@Valid User user => 다 걸리니까 쓸 수가 없음.
		//@PathVariable : {url} 의 이름을 매개변수로 전달.
		//		요청: idsearch : url => "id" // 요청: pwsearch : url => "pw"
		ModelAndView mav = new ModelAndView();
		String code = "error.userid.search";
		String title = "아이디";
		if(url.equals("pw")) {
			title = "비밀번호";
			code="error.pw.search";
			if(user.getUserid() == null || user.getUserid().trim().equals("")) {
				//reject : 전역오류(global error) => jsp의 <spring:hasBindErrors ... 부분에 오류출력
				//rejectValue : => jsp의 <form:errors path=... 부분에 오류 출력
				br.rejectValue("userid", "error.required"); //error.code = error.required.userid				
			}			
		}
		if(user.getEmail() == null || user.getEmail().trim().equals("")) {
			br.rejectValue("email", "error.required");
		}
		if(user.getPhoneno() == null || user.getPhoneno().trim().equals("")) {
			br.rejectValue("phoneno", "error.required");
		}
		if(br.hasErrors()) {
			mav.getModel().putAll(br.getModel());
			return mav;
		}
		//입력검증 통과. 
		//mybatis 구현시 해당 레코드가 없는 경우 결과값이 null임.
		//mybatis에서는 데이터가 없어도 exception을 내지 않기 때문에 예외처리(EmptyResultDataAccessException)가 불가함
		if(user.getUserid() != null && user.getUserid().trim().equals("")) user.setUserid(null);		
//		String result = service.getSearch(user);
		String result = null;
		if(user.getUserid() == null) {
			List<User> list = service.getUserList(user.getPhoneno());
			for(User u : list) {
				u.setEmail(this.emailDecrypt(u));
				if(u.getEmail().equals(user.getEmail())) {
					result = u.getUserid();
				}
			}			
		} else {
			user.setEmail(this.emailEncrypt(user.getEmail(), user.getUserid()));
			result = service.getSearch(user);
			if(result != null) { //비밀번호 검색 성공 => 비밀번호 초기화
				String pass = null;
				try {
					pass = cipher.makehash(user.getUserid(), "SHA-512");
				} catch(NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				int index = (int)(Math.random() * (pass.length() - 10));
				result = pass.substring(index, index+6);
				service.changePass(passwordHash(result), user.getUserid());
			}
		}
		if(result == null) {
			br.reject(code);
			mav.getModel().putAll(br.getModel());
			return mav;
		}	
		mav.addObject("result",result);
		mav.addObject("title", title);
		mav.setViewName("search");
		return mav;
	}
}
