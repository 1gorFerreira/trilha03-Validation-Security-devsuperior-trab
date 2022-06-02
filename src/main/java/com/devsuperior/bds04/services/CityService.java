package com.devsuperior.bds04.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

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
		return new CityDTO(obj.get());
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
		City entity = cityRepository.getOne(id);
		copyDtoToEntity(dto, entity);
		entity = cityRepository.save(entity);
		return new CityDTO(entity);
	}
	
	public void delete(Long id) {
		cityRepository.deleteById(id);
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
