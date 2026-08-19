package br.com.ferrogestao.repository;

import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.enums.TrafficStatus;
import java.util.List;
import java.util.Map;

public interface FollowUpRepository extends CrudRepository<FollowUp> {
    List<FollowUp> findLatest(int limit);
    FollowUp findLatestForItem(Long itemId);
    Map<TrafficStatus, Long> countLatestByStatus();
}