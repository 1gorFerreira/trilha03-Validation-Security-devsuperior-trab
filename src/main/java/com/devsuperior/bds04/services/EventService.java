package com.devsuperior.bds04.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.DataBaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAllPaged(Pageable pageable){
		Page<Event> page = eventRepository.findAll(pageable);
		return page.map(x -> new EventDTO(x));
	}
	
	@Transactional(readOnly = true)
	public EventDTO findById(Long id) {
		Optional<Event> obj = eventRepository.findById(id);
		Event entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		return new EventDTO(entity);
	}
	
	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		copyDtoToEntity(dto, entity);
		entity = eventRepository.save(entity);
		return new EventDTO(entity);
	}
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = eventRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = eventRepository.save(entity);
			return new EventDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	
	}
	
	public void delete(Long id) {
		try {
			eventRepository.deleteById(id);	
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation");
		}
	}
	
	
	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		
		City city = cityRepository.getOne(dto.getCityId());
		entity.setCity(city);
	}
}
