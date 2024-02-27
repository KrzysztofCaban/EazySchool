package com.caban.eazyschool.service;

import com.caban.eazyschool.model.Holiday;
import com.caban.eazyschool.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public List<Holiday> findAllHolidays() {
        return holidayRepository.findAllHolidays();
    }
}
