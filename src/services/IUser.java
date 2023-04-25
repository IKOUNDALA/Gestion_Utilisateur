/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;
import models.User;

/**
 *
 * @author HP
 */
public interface IUser {

    User addUser(User user);

    List<User> allUser();

    User getUserByEmail(String email);

    User getUserByTel(String tel);

    User getConnexion(String login);
}
