package com.caban.eazyschool.controller;

import com.caban.eazyschool.model.Holiday;
import com.caban.eazyschool.repository.HolidayRepository;
import com.caban.eazyschool.service.HolidayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping({"/holidays", "/holidays/", "/holidays/{display}"})
    public String displayHolidays(@PathVariable(required = false) Optional<String> display,
                                  Model model) {

        if (display.isEmpty())
            display = Optional.of("all");

        switch (display.toString()) {
            case "festival":
                model.addAttribute("festival", true);
                break;
            case "federal":
                model.addAttribute("federal", true);
                break;
            default:
                model.addAttribute("festival", true);
                model.addAttribute("federal", true);
                break;
        }

        List<Holiday> holidays = holidayService.findAllHolidays();
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).toList()));
        }
        return "holidays";
    }
}
