package com.devsuperior.bds04.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public List<EventDTO> findAll(){
		List<Event> entities = eventRepository.findAll();
		return entities.stream().map(entity -> new EventDTO(entity)).toList();
	}
	
	@Transactional(readOnly = true)
	public EventDTO findById(Long id) {
		Optional<Event> obj = eventRepository.findById(id);
		return new EventDTO(obj.get());
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
		Event entity = eventRepository.getOne(id);
		copyDtoToEntity(dto, entity);
		entity = eventRepository.save(entity);
		return new EventDTO(entity);
	}
	
	public void delete(Long id) {
		eventRepository.deleteById(id);
	}
	
	
	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		
		City city = cityRepository.getOne(dto.getCityId());
		entity.setCity(city);
	}
}
