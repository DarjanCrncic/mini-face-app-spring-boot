package com.example.minifaceapp.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(value="/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class Websocket {
	
	static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	
	static Map<String, Session> users = new HashMap<>();
	
	@OnOpen
	public void onOpen(Session session) {	
		peers.add(session);		
	}
	
	@OnMessage
	public void onMessage(JSONObject message, Session session) throws IOException, EncodeException{
		
		if(message.get("init").equals("true")) { 			
			// when user first connects, client sends a message with init set to true
			// every other user is then sent a message notifying them of a new user in chat
			users.put(message.get("sender").toString(), session);
			for(Session peer: peers) {
				peer.getBasicRemote().sendObject(message.put("onlineUsers", users.keySet()));
			}
			
		}else {
			if(users.get(message.get("receiver")) != null && users.get(message.get("receiver")).isOpen()) {
				message.put("sender", getKey(session));
				users.get(message.get("receiver")).getBasicRemote().sendObject(message);
			}	
		}
		
	}
	
	@OnClose
	public void onClose(Session session) throws IOException, EncodeException{
		// when user logs out from application, on close method is manually invoked
		// notify all users that the logged out user is no longer available for chatting
		JSONObject message = new JSONObject();
		message.put("init", "delete");
		message.put("content", getKey(session));
		
		peers.remove(session);
		users.remove(getKey(session), session);
		for(Session peer: peers) {
			peer.getBasicRemote().sendObject(message);
		}
	}
	
	public String getKey(Session value) {
		for(Entry<String, Session> user: users.entrySet()) {
			if(user.getValue().getId().equals(value.getId())) {
				return user.getKey();
			}
		}
		return null;
	}
	
	
}