package com.marketplace.webhook.controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.marketplace.webhook.Model.Event;
import com.marketplace.webhook.service.WebSocketService;

@RestController
public class WebhookController {

	@Autowired
	private WebSocketService webSocketService;

	@GetMapping("/old-data")
	public ResponseEntity<?> getOldData() {
		List<Event> events = readEventData();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	private List<Event> readEventData() {
		List<Event> events = new ArrayList<Event>();
		File folderLocaltion = FileUtils.getFile("/home/marketplace/");
		if (!folderLocaltion.exists()) {
			return events;
		}

		File fileName = FileUtils.getFile(folderLocaltion, "event-log.txt");
		if (!fileName.exists()) {
			return events;
		}

		try (FileInputStream fileIn = new FileInputStream(fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			events = (List<Event>) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return events;
	}

	@PostMapping("/api/webhook")
	public ResponseEntity<?> handleEvent(@RequestBody String contentEvent) {
		String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		Event event = new Event(currentTime, contentEvent);
		webSocketService.sendMessage("/topic/webhook", event);
		saveEventContent(event);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	private void saveEventContent(Event event) {
		File folderLocation = new File("E:/marketplace/");
		if (!folderLocation.exists()) {
			folderLocation.mkdirs();
		}

		File fileName = new File(folderLocation, "event-log.txt");
		List<Event> events = new ArrayList<>();
		if (fileName.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
				events = (List<Event>) in.readObject();
			} catch (EOFException e) {
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}
		}

		events.add(event);

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
			out.writeObject(events);
			System.out.println("Serialized data is saved in event-log.txt");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
}
