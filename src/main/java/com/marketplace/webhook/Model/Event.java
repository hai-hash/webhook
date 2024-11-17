package com.marketplace.webhook.Model;

import java.io.Serializable;

public class Event implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eventTime;
	private String contentEvent;
	
	public Event() {
	}

	public Event(String eventTime, String contentEvent) {
		this.eventTime = eventTime;
		this.contentEvent = contentEvent;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getContentEvent() {
		return contentEvent;
	}

	public void setContentEvent(String contentEvent) {
		this.contentEvent = contentEvent;
	}

	@Override
	public String toString() {
		return "Event [eventTime=" + eventTime + ", contentEvent=" + contentEvent + "]";
	}
	
	
	

}
