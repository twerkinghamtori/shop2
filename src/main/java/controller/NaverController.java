package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CipherUtil;

@Controller
@RequestMapping("naver")
public class NaverController {
	@Autowired
	private CipherUtil cipher;
	
	@GetMapping("*")
	public String naver() {
		return null;
	}
	
	@RequestMapping("naversearch")
	@ResponseBody //뷰없이 데이터만 전송할 때 
	public JSONObject naversearch(String data, int display, int start, String type) throws Exception {
		JSONObject jsonData = null;
		String clientId = "ajaWNgFy9gvIJ77K43cD";
		String clientSecret = "0mD0OkDSBx";
		StringBuffer sb = new StringBuffer();
		int cnt = (start-1) * display + 1;
		String text = URLEncoder.encode(data, "UTF-8");
		String apiURL = "https://openapi.naver.com/v1/search/"+ type + ".json?query=" + text + "&display=" + display + "&start="+cnt;
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-NAVER-Client-Id", clientId);
		con.setRequestProperty("X-NAVER-Client-Secret", clientSecret);
		int responseCode = con.getResponseCode(); //응답 코드
		BufferedReader br;
		if(responseCode == 200) { //정상 응답
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		} else { //에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8")); 
		}
		String inputLine;
		while((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		br.close();
		JSONParser parser = new JSONParser();
		jsonData = (JSONObject)parser.parse(sb.toString());
		return jsonData;
	}
}
