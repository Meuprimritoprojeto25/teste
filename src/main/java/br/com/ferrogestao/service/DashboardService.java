package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.enums.TrafficStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {
    @Autowired private IndicatorService indicators;
    @Autowired private UserService users;
    @Autowired private TrainingService training;
    @Autowired private ProductionService production;

    @Transactional(readOnly = true)
    public Map<String, Object> summary() {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<TrafficStatus, Long> lights = indicators.statusCounts();
        long evaluated = 0L;
        for (Long value : lights.values()) { evaluated += value; }
        long healthy = lights.get(TrafficStatus.GREEN) + lights.get(TrafficStatus.BLUE);
        data.put("itemCount", indicators.itemCount());
        data.put("userCount", users.count());
        data.put("evaluatedCount", evaluated);
        data.put("healthyPercent", evaluated == 0 ? 0L : Math.round(healthy * 100d / evaluated));
        data.put("lights", indicators.statusCountsByName());
        data.put("expiringTrainings", training.expiring());
        data.put("productionTotals", production.totalsByPlant());
        return data;
    }

    @Transactional(readOnly = true)
    public List<FollowUp> recent() { return indicators.latest(8); }
}