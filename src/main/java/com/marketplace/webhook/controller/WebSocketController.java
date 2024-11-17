package com.marketplace.webhook.controller;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.marketplace.webhook.Model.Event;

@Controller
public class WebSocketController {

	@SendTo("/topic/webhook")
	public Event greeting(@Payload Event event) throws Exception {
		return event;
	}
}
