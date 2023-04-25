/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;
import javax.persistence.EntityManager;
import models.Profile;
import utils.EntityManagerUtil;

/**
 *
 * @author HP
 */
public class ProfileService implements IProfile {

    EntityManager EM;

    public ProfileService() {
        EM = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Profile> allProfile() {
        List<Profile> profiles = null;
        try {
            profiles = EM.
                    createNamedQuery("Profile.findAll", Profile.class)
                    .getResultList();
        } catch (Exception ex) {
            System.err.println("Erreur lors de la recuperation des profils : " + ex.getMessage());
        }
        return profiles;
    }

}
