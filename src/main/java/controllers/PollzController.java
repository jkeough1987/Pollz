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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        model.addAttribute("user", user);
        return "";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password, Model model) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            return "redirect:/register";
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            return "redirect:/";
        }
//        session.setAttribute("username", userName);
        model.addAttribute("user", user);

        return "redirect:/";
    }

    //TODO: Maybe this could be a direct link from the HTML page? <a href = "register.html">, something like that
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String goToRegister()throws Exception{
        return("/register");
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String registerUser(HttpSession session, String name, String password, String country, String city, String zip)throws Exception{
        User user = new User(name,password,country,city,zip);
        session.setAttribute("username", name);
        users.save(user);
        return("/home");
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        return("/home");
    }

    @RequestMapping(path = "/take-poll", method = RequestMethod.GET)
    public String takePoll(HttpSession session, Model model)throws Exception{
        Random random = new Random(System.currentTimeMillis());
        ArrayList<Poll> pollList = (ArrayList)polls.findAll();
        Poll poll = pollList.get(random.nextInt(pollList.size()-1));
        model.addAttribute("poll", poll);
        return("/takepoll");
    }

    @RequestMapping(path = "/get-polls", method = RequestMethod.GET)
    public String getPolls(HttpSession session, User user, Model model)throws Exception{
        ArrayList<Poll> userPolls = polls.findByUser(user);
        model.addAttribute("polls", userPolls);
        return("userpolls");
    }

    @RequestMapping(path = "/create-poll", method = RequestMethod.GET)
    public String createPoll(HttpSession session)throws Exception{
        return("/createpoll");
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String profile(HttpSession session)throws Exception{
        return("/profile");
    }

}
