package br.com.sideral.repository;
import br.com.sideral.domain.R3GReport; import org.springframework.stereotype.Repository;
@Repository public class R3GReportRepository extends HibernateRepository<R3GReport,Long> { public R3GReportRepository(){super(R3GReport.class);} }