package br.com.ferrogestao.repository;

import br.com.ferrogestao.domain.Driver;
import br.com.ferrogestao.domain.DriverTraining;
import java.util.List;

public interface TrainingRepository extends CrudRepository<DriverTraining> {
    List<DriverTraining> findExpiring(int days);
    List<Driver> findDrivers();
    Driver findDriver(Long id);
    Driver saveDriver(Driver driver);
}