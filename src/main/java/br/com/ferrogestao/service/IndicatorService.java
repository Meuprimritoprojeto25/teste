package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.ControlItem;
import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.KpiSnapshot;
import br.com.ferrogestao.domain.enums.Direction;
import br.com.ferrogestao.domain.enums.TrafficStatus;
import br.com.ferrogestao.repository.ControlItemRepository;
import br.com.ferrogestao.repository.FollowUpRepository;
import br.com.ferrogestao.repository.KpiRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndicatorService {
    @Autowired private ControlItemRepository itemRepository;
    @Autowired private FollowUpRepository followUpRepository;
    @Autowired private KpiRepository kpiRepository;

    @Transactional(readOnly = true)
    public List<ControlItem> listItems() { return itemRepository.findActive(); }
    @Transactional(readOnly = true)
    public ControlItem getItem(Long id) { return itemRepository.find(id); }
    @Transactional(readOnly = true)
    public List<FollowUp> latest(int limit) { return followUpRepository.findLatest(limit); }
    @Transactional(readOnly = true)
    public List<FollowUp> allFollowUps() { return followUpRepository.findAll(); }
    @Transactional(readOnly = true)
    public Map<TrafficStatus, Long> statusCounts() { return followUpRepository.countLatestByStatus(); }
    @Transactional(readOnly = true)
    public Map<String, Long> statusCountsByName() {
        Map<String, Long> result = new HashMap<String, Long>();
        for (Map.Entry<TrafficStatus, Long> entry : followUpRepository.countLatestByStatus().entrySet()) {
            result.put(entry.getKey().name(), entry.getValue());
        }
        return result;
    }
    @Transactional(readOnly = true)
    public long itemCount() { return itemRepository.count(); }

    @Transactional
    public ControlItem saveItem(ControlItem item) {
        if (item.getTarget() == null || item.getWarningLimit() == null) {
            throw new BusinessException("Meta e limite de atenção são obrigatórios.");
        }
        if (item.getDirection() == Direction.RANGE && item.getLowerLimit() == null) {
            throw new BusinessException("Indicadores por faixa exigem um limite inferior.");
        }
        return itemRepository.save(item);
    }

    @Transactional
    public FollowUp record(FollowUp followUp) {
        if (followUp.getItem() == null || followUp.getItem().getId() == null) {
            throw new BusinessException("Selecione o item de controle.");
        }
        ControlItem item = itemRepository.find(followUp.getItem().getId());
        if (item == null || !item.isActive()) { throw new BusinessException("Item de controle inválido ou inativo."); }
        if (followUp.getActualValue() == null) { throw new BusinessException("Informe o valor realizado."); }
        if (followUp.getReferenceDate() == null) { followUp.setReferenceDate(new Date()); }
        followUp.setItem(item);
        followUp.setStatus(calculateStatus(item, followUp.getActualValue()));
        followUpRepository.save(followUp);

        KpiSnapshot snapshot = new KpiSnapshot();
        snapshot.setItem(item);
        snapshot.setReferenceDate(followUp.getReferenceDate());
        snapshot.setTargetValue(item.getTarget());
        snapshot.setActualValue(followUp.getActualValue());
        snapshot.setAchievement(calculateAchievement(item, followUp.getActualValue()));
        snapshot.setStatus(followUp.getStatus());
        kpiRepository.save(snapshot);
        return followUp;
    }

    public TrafficStatus calculateStatus(ControlItem item, double actual) {
        if (item.getDirection() == Direction.HIGHER_IS_BETTER) {
            if (actual >= item.getTarget()) { return actual > item.getTarget() * 1.05 ? TrafficStatus.BLUE : TrafficStatus.GREEN; }
            return actual >= item.getWarningLimit() ? TrafficStatus.YELLOW : TrafficStatus.RED;
        }
        if (item.getDirection() == Direction.LOWER_IS_BETTER) {
            if (actual <= item.getTarget()) { return actual < item.getTarget() * 0.95 ? TrafficStatus.BLUE : TrafficStatus.GREEN; }
            return actual <= item.getWarningLimit() ? TrafficStatus.YELLOW : TrafficStatus.RED;
        }
        if (actual >= item.getLowerLimit() && actual <= item.getTarget()) { return TrafficStatus.GREEN; }
        double tolerance = Math.abs(item.getWarningLimit() - item.getTarget());
        return actual >= item.getLowerLimit() - tolerance && actual <= item.getWarningLimit()
                ? TrafficStatus.YELLOW : TrafficStatus.RED;
    }

    public double calculateAchievement(ControlItem item, double actual) {
        if (item.getTarget() == 0d) { return actual == 0d ? 100d : 0d; }
        if (item.getDirection() == Direction.LOWER_IS_BETTER) {
            return actual == 0d ? 100d : item.getTarget() / actual * 100d;
        }
        if (item.getDirection() == Direction.RANGE) {
            return calculateStatus(item, actual) == TrafficStatus.GREEN ? 100d : 0d;
        }
        return actual / item.getTarget() * 100d;
    }
}