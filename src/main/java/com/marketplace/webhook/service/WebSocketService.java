package com.marketplace.webhook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.marketplace.webhook.Model.Event;

@Service
public class WebSocketService {

	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	public WebSocketService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public void sendMessage(String destination, Event event) {
		messagingTemplate.convertAndSend(destination, event);
	}
}
