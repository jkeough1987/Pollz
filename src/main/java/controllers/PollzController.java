package Controllers;

import Entities.*;
import Services.*;
import Utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;

@Controller
public class PollzController {

    @Autowired
    UserRepo users;

    @Autowired
    PollRepo polls;

    @Autowired
    ResultRepo results;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(HttpSession session, HttpServletResponse response, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }
        session.setAttribute("username", userName);
        response.sendRedirect("/Home");
        return user;
    }

    //TODO: Maybe this could be a direct link from the HTML page? <a href = "register.html">, something like that
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register( String username, String password)throws Exception{
        return("/register");
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        return("/home");
    }

    @RequestMapping(path = "/take-poll", method = RequestMethod.GET)
    public String takePoll(HttpServletResponse response, Model model)throws Exception{
        Random random = new Random(System.currentTimeMillis());
        ArrayList<Poll> pollList = (ArrayList)polls.findAll();
        Poll poll = pollList.get(random.nextInt(pollList.size()-1));
        model.addAttribute("poll", poll);
        return("/takepoll");
    }

    @RequestMapping(path = "/get-polls", method = RequestMethod.GET)
    public String getPolls(HttpServletResponse response)throws Exception{
        return("userpolls");
    }

    @RequestMapping(path = "/create-polls", method = RequestMethod.GET)
    public String createPoll()throws Exception{
        return("/createpoll");
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String profile(HttpServletResponse response)throws Exception{
        return("/createpoll");
    }

}
