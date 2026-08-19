package br.com.ferrogestao.repository;

import br.com.ferrogestao.domain.ControlItem;
import java.util.List;

public interface ControlItemRepository extends CrudRepository<ControlItem> {
    List<ControlItem> findActive();
}