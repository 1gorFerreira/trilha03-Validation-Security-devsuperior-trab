package com.devsuperior.bds04.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.DataBaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll(){
		List<City> entities = cityRepository.findAll(Sort.by("name"));
		return entities.stream().map(entity -> new CityDTO(entity)).toList();
	}
	
	@Transactional(readOnly = true)
	public CityDTO findById(Long id) {
		Optional<City> obj = cityRepository.findById(id);
		City entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		return new CityDTO(entity);
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		copyDtoToEntity(dto, entity);
		entity = cityRepository.save(entity);
		return new CityDTO(entity);
	}
	
	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		try {
			City entity = cityRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = cityRepository.save(entity);
			return new CityDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}
	
	
	private void copyDtoToEntity(CityDTO dto, City entity) {
		entity.setName(dto.getName());
		
		entity.getEvents().clear();
		for(EventDTO eventsDto : dto.getEvents()) {
			Event event = eventRepository.getOne(eventsDto.getId());
			entity.getEvents().add(event);
		}
	}
}
