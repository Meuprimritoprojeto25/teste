package br.com.ferrogestao.repository;

import br.com.ferrogestao.domain.ProductionRecord;
import java.util.List;

public interface ProductionRepository extends CrudRepository<ProductionRecord> {
    List<Object[]> totalsByPlant();
}