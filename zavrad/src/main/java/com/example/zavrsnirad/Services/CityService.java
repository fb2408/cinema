package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.City;
import com.example.zavrsnirad.Repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> listAll() {
        return cityRepository.findAll();
    }
}
