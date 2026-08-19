package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.enums.TrafficStatus;
import br.com.ferrogestao.service.IndicatorService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired private IndicatorService service;
    @RequestMapping(value = "/farol", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> trafficLight() {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<TrafficStatus, Long> counts = service.statusCounts();
        response.put("counts", counts);
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (FollowUp value : service.latest(100)) {
            Map<String, Object> row = new HashMap<String, Object>();
            row.put("code", value.getItem().getCode()); row.put("name", value.getItem().getName());
            row.put("target", value.getItem().getTarget()); row.put("actual", value.getActualValue());
            row.put("status", value.getStatus()); row.put("referenceDate", value.getReferenceDate());
            items.add(row);
        }
        response.put("items", items);
        return response;
    }
}