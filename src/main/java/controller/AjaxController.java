package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import logic.ShopService;
/*
 @Controller
 	호출되는 메서드의 리턴타입 : ModelAndView : 뷰이름 + 데이터.
 	호출되는 메서드의 리턴타입 : String : 뷰이름.
 @RestController
 	@Controller의 하위 인터페이스
 	Spring 4.0이후에 추가(이전에는 @ResponseBody)
 	기능 : @Controller + Component + 클라이언트에 데이터 직접 전달
 	호출되는 메서드의 리턴타입 : String : 클라이언트에 전달되는 문자열 값.
 	호출되는 메서드의 리턴타입 : Object : 클라이언트에 전달되는 값.(JSON 형태)
*/
@RestController
@RequestMapping("ajax")
public class AjaxController {
	@Autowired
	private ShopService service;
	
	@RequestMapping("select")
	public List<String> select(String si, String gu, HttpServletRequest request) {
		BufferedReader br = null;
		String path = request.getServletContext().getRealPath("/") + "file/sido.txt";
		try {
			br = new BufferedReader(new FileReader(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Set<String> set = new LinkedHashSet<>(); //LinkedHash : 순서유지
		String data = null;
		if(si==null && gu==null) {
			try {
				while((data=br.readLine()) != null) {
					String[] arr = data.split("\\s+"); // \\s+ 공백(\\s)1개 이상(+)
					if(arr.length >= 2 ) set.add(arr[0].trim());
//					set.add(arr[0].trim());
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else if(gu==null) {
			si = si.trim();
			try {
				while((data=br.readLine())!= null) {
					String[] arr = data.split("\\s+");
					if(arr.length >= 3 && arr[0].equals(si) && !arr[1].contains(arr[0])) set.add(arr[1].trim()); 
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else if(si != null && gu != null) {
			si = si.trim();
			gu = gu.trim();
			try {
				while((data=br.readLine())!= null) {
					String[] arr = data.split("\\s+");
					if(arr.length >= 3 
							&& arr[0].equals(si) && arr[1].equals(gu) //시,구 일치
							&& !arr[1].contains(arr[0]) && !arr[2].contains(arr[1])) { //겹치면x
						if(arr.length > 3) {
							if(arr[3].contains(arr[1])) continue;
							arr[2] += " " +  arr[3];
						}
							set.add(arr[2].trim()); 
					}						
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList<>(set);
		return list; //pom.xml의 faster.xml.jackson...의 설정에 의해서 브라우저(javascript)에서 배열로 인식
	}
	
	@RequestMapping(value="select2", produces="text/plain; charset=utf-8" )//한글깨짐. 클라이언트로 문자열 전송. 인코딩 설정이 필요.
	//produces : 클라이언트에 전달되는 데이터의 특징을 설정
	//	text/plain : 데이터 특징. 순수문자
	public String select2(String si, String gu, HttpServletRequest request) {
		BufferedReader br = null;
		String path = request.getServletContext().getRealPath("/") + "file/sido.txt";
		try {
			br = new BufferedReader(new FileReader(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Set<String> set = new LinkedHashSet<>(); 
		String data = null;
		if(si==null && gu==null) {
			try {
				while((data=br.readLine()) != null) {
					String[] arr = data.split("\\s+"); 
					if(arr.length >= 2 ) set.add(arr[0].trim());
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}	else if(gu==null) {
			si = si.trim();
			try {
				while((data=br.readLine())!= null) {
					String[] arr = data.split("\\s+");
					if(arr.length >= 3 && arr[0].equals(si) && !arr[1].contains(arr[0])) set.add(arr[1].trim()); 
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else if(si != null && gu != null) {
			si = si.trim();
			gu = gu.trim();
			try {
				while((data=br.readLine())!= null) {
					String[] arr = data.split("\\s+");
					if(arr.length >= 3 
							&& arr[0].equals(si) && arr[1].equals(gu) 
							&& !arr[1].contains(arr[0]) && !arr[2].contains(arr[1])) {
						if(arr.length > 3) {
							if(arr[3].contains(arr[1])) continue;
							arr[2] += " " +  arr[3];
						}
							set.add(arr[2].trim()); 
					}						
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList<>(set);
		return list.toString();
	}
	
	//https://www.koreaexim.go.kr/wg/HPHKWG057M01
	@RequestMapping(value="exchange", produces="text/plain; charset=utf-8" )
	public String exchange() {
		Document doc = null;
		List<List<String>> trlist = new ArrayList<>();
		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
		String exdate = null;
		try {
			doc = Jsoup.connect(url).get();
			Elements trs = doc.select("tr");
			exdate = doc.select("p.table-unit").html(); //조회기준일	
			for(Element tr : trs) {
				List<String> tdlist = new ArrayList<String>();
				Elements tds = tr.select("td");
				for(Element td : tds) {
					tdlist.add(td.html());
				}
				if(tdlist.size()>0) {
					if(tdlist.get(0).equals("EUR") || tdlist.get(0).equals("JPY(100)") || tdlist.get(0).equals("CNH") || tdlist.get(0).equals("USD")) {
						trlist.add(tdlist);
					}
				}				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<h3 class='w3-center'>수출입은행<br>" + exdate + "</h3>");
		sb.append("<table class='table table-hover table-bordered'>");
		sb.append("<tr class='table-dark'><th>통화</th><th>기준율</th><th>받으실때</th><th>보내실때</th></tr>");
		for(List<String> tds : trlist) {
			sb.append("<tr><td>" + tds.get(0) + "<br>" + tds.get(1) + "</td><td>" + tds.get(4) + "</td>");
			sb.append("<td>" + tds.get(2) + "</td><td>" + tds.get(3) + "</td></tr>");
		}
		
		sb.append("</table>");
		return sb.toString();
	}
	
	//https://www.koreaexim.go.kr/wg/HPHKWG057M01
	@RequestMapping("exchange2")
	public Map<String, Object> exchange2() {
		Document doc = null;
		List<List<String>> trlist = new ArrayList<>();
		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
		String exdate = null;
		try {
			doc = Jsoup.connect(url).get();
			Elements trs = doc.select("tr");
			exdate = doc.select("p.table-unit").html(); //조회기준일	
			for(Element tr : trs) {
				List<String> tdlist = new ArrayList<String>();
				Elements tds = tr.select("td");
				for(Element td : tds) {
					tdlist.add(td.html());
				}
				if(tdlist.size()>0) {
					if(tdlist.get(0).equals("EUR") || tdlist.get(0).equals("JPY(100)") || tdlist.get(0).equals("CNH") || tdlist.get(0).equals("USD")) {
						trlist.add(tdlist);
					}
				}				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("exdate", exdate);
		map.put("trlist", trlist);
		return map;
	}
	
	@RequestMapping("graph1") 
	public List<Map.Entry<String, Integer>> graph1(String id) {
		Map<String, Integer> map = service.graph1(id); //{"홍길동"=10, "김삿갓"=7,...}
		List<Map.Entry<String, Integer>> list = new ArrayList<>();
		for(Map.Entry<String, Integer> m : map.entrySet()) {
			list.add(m);
		}
		Collections.sort(list, (m1,m2)->m2.getValue() - m1.getValue()); //등록건수의 내림차순으로 정렬하여 client로 전송
		return list; //List 객체는 클라이언트(javascript)에서 배열객체로 전달
		//map.entry : Json 형식으로 클라이언트에 전달
	}
	
	@RequestMapping("graph2")
	public List<Map.Entry<String, Integer>> graph2(String id) {
		Map<String, Integer> map = service.graph2(id); 
		List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
		return list; 
	}
}
