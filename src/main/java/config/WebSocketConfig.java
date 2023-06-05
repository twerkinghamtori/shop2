package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import websocket.EchoHandler; //내가 작성한 거임

@Configuration
@EnableWebSocket //web socket 사용할거임
public class WebSocketConfig implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new EchoHandler(), "chatting")
		.setAllowedOrigins("*"); //브라우저 환경(모든 브라우저에서)
		//allowedOrigins("http://196.168...:8080")
		//allowedOrigins("http://localhost:8080")
	}
}
