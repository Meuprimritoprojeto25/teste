package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.Deployment;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.DeploymentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateDeploymentRepository extends AbstractHibernateRepository<Deployment> implements DeploymentRepository {
    public HibernateDeploymentRepository() { super(Deployment.class); }
}