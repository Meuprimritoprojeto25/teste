package br.com.sideral.infra;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    private HibernateUtil() { }
    public static Session currentSession() { return SESSION_FACTORY.getCurrentSession(); }
    public static void shutdown() { SESSION_FACTORY.close(); }
}