package dao;

import entity.Currency;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CurrencyDao {

    public void persist(Currency c) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    public Currency find(String code) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        Currency c = em.find(Currency.class, code);
        return c;
    }

    public List<String> findAllCodes() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        List<String> codes = em.createQuery("select c.code from Currency c", String.class).getResultList();
        return codes;
    }

    // Updates the entity if it exists, or creates a new one if it does not
    public void update(Currency c) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
    }

    public void delete(Currency c) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
    }

}
