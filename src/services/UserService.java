/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Profile;
import models.User;
import utils.EntityManagerUtil;

/**
 *
 * @author HP
 */
public class UserService implements IUser {

    EntityManager EM;

    public UserService() {
        EM = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public User addUser(User user) {
        EntityTransaction et = null;
        User userSaved;
        try {
            et = EM.getTransaction();
            et.begin();
            EM.persist(user);
            EM.flush();
            et.commit();
            userSaved = user;
        } catch (Exception ex) {
            if (et == null && et.isActive()) {
                et.rollback();
            }
            System.err.println("Erreur lors de l'insertion de la produit " + ex.getMessage());
            throw ex;
        }
        return userSaved;
    }

    @Override
    public List<User> allUser() {
        List<User> users = null;
        try {
            users = EM.
                    createNamedQuery("User.findAll", User.class)
                    .getResultList();
        } catch (Exception ex) {
            System.err.println("Erreur lors de la recuperation des utilisateurs : " + ex.getMessage());
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;

        try {
            user = EM.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception ex) {
            System.err.println("Erreur lors de la recuperation de l'utilisateur : " + ex.getMessage());
            return user;
        }
        return user;
    }

    @Override
    public User getUserByTel(String tel) {
        User user = null;

        try {
            user = EM.createNamedQuery("User.findByTel", User.class)
                    .setParameter("email", tel)
                    .getSingleResult();
        } catch (Exception ex) {
            System.err.println("Erreur lors de la recuperation de l'utilisateur : " + ex.getMessage());
            return user;
        }
        return user;
    }

    @Override
    public User getConnexion(String login) {
        User user = null;

        try {
            user = EM.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception ex) {
            System.err.println("Erreur lors de la recuperation de l'utilisateur : " + ex.getMessage());
            return user;
        }
        return user;
    }

}
