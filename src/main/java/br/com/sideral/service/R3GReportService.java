package br.com.sideral.service;
import java.util.Date; import java.util.List; import br.com.sideral.domain.R3GReport; import br.com.sideral.repository.R3GReportRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class R3GReportService {
 @Autowired private R3GReportRepository repository;
 @Transactional(readOnly=true) public List<R3GReport> list(){return repository.findAll();}
 @Transactional public R3GReport generate(String period,String area,String analysis,String decisions){R3GReport r=new R3GReport();r.setTitle("R3G - "+area);r.setPeriod(period);r.setArea(area);r.setAnalysis(analysis);r.setDecisions(decisions);r.setGeneratedAt(new Date());return repository.save(r);}
}