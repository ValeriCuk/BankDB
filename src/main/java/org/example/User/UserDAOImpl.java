package org.example.User;

import org.example.BankUtil;
import org.example.Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final EntityManager em;

    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
        System.out.println("User persisted: " + user.getUsername());
    }

    @Override
    public List<User> getAllUsers() {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            cq.select(root);
            return em.createQuery(cq).getResultList();
        }catch (Exception e){
            System.out.println("Error while fetching users");
            throw e;
        }
    }

    @Override
    public User getUserBy(int id) {
        System.out.println("getUserBy -> "+ id + " " + em.find(User.class, id));
        return em.find(User.class, id);
    }
}
