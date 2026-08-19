package br.com.sideral.infra;

import org.hibernate.Transaction;

public final class Transacao {
    public interface Operacao<T> { T executar(); }
    private Transacao() { }
    public static <T> T executar(Operacao<T> operacao) {
        Transaction tx = HibernateUtil.currentSession().beginTransaction();
        try {
            T resultado = operacao.executar();
            tx.commit();
            return resultado;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}