package com.devsuperior.bds04.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.services.EventService;

@RestController
@RequestMapping(value = "/events")
public class EventResource {

	@Autowired
	private EventService eventService;
	
	@GetMapping
	public ResponseEntity<Page<EventDTO>> findAll(Pageable pageable){
		Page<EventDTO> pageDto = eventService.findAllPaged(pageable);
		return ResponseEntity.ok().body(pageDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EventDTO> findById(@PathVariable Long id){
		EventDTO dto = eventService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<EventDTO> insert(@RequestBody EventDTO dto){
		EventDTO newDto = eventService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO dto){
		EventDTO newDto = eventService.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<EventDTO> delete(@PathVariable Long id){
		eventService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
