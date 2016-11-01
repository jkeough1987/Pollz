package controllers;

import Entities.*;
import Services.*;
import Utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class PollzController {

    @Autowired
    UserRepo users;

    @Autowired
    PollRepo polls;

    @Autowired
    ResultsRepo results;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(HttpSession session, HttpServletResponse response, String username, String password) throws Exception {
        User user = users.findFirstByName(username);
        if (user == null) {
            user = new User(username, PasswordStorage.createHash(password));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }
        session.setAttribute("username", username);
        response.sendRedirect("/");
        return user;
    }
    
}
