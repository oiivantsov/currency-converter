package dao;

import entity.Transaction;
import jakarta.persistence.EntityManager;

public class TransactionDao {
    public void persist(Transaction transaction) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }

    public Transaction find(int id) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        Transaction transaction = em.find(Transaction.class, id);
        return transaction;
    }

    public void update(Transaction transaction) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(transaction);
        em.getTransaction().commit();
    }

    public void delete(Transaction transaction) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(transaction);
        em.getTransaction().commit();
    }
}
