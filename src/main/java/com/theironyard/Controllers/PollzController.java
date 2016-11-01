package com.theironyard.Controllers;
import com.theironyard.Entities.Poll;
import com.theironyard.Entities.Result;
import com.theironyard.Services.ResultRepo;
import com.theironyard.Services.PollRepo;
import com.theironyard.Entities.User;
import com.theironyard.Services.UserRepo;
import com.theironyard.Utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password, Model model) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            return "redirect:/register";
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            return "redirect:/";
        }
        session.setAttribute("username", userName);
        model.addAttribute("user", user);

        return "redirect:/";
    }

    //TODO: Maybe this could be a direct link from the HTML page? <a href = "register.html">, something like that
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String goToRegister()throws Exception{
        return("/register");
    }

    @RequestMapping(path = "/create-profile", method = RequestMethod.POST)
    public String registerUser(HttpSession session, String userName, String newpassword, String country, String city, String zip)throws Exception{
        User user = new User(userName,PasswordStorage.createHash(newpassword),country,city,zip);
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
        Poll poll = pollList.get(random.nextInt(pollList.size()));
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
    public String getcreatePoll(HttpSession session)throws Exception{
        return("/createpoll");
    }

    @RequestMapping(path = "/create-poll", method = RequestMethod.POST)
    public String createPoll(HttpSession session, String pollName, String topic, String responseA,String responseB,String responseC,String responseD,String responseE,String responseF )throws Exception{
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        if(responseC.equals("")) {
            responseC = null;
        }
        if(responseD.equals("")) {
            responseD = null;
        }
        if(responseE.equals("")) {
            responseE = null;
        }
        if(responseF.equals("")) {
            responseF = null;
        }
        Poll poll = new Poll(pollName,topic,responseA,responseB,responseC,responseD,responseE,responseF,user);
        polls.save(poll);
        return("/createpoll");
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String profile(HttpSession session)throws Exception{
        return"/profile";
    }

    @RequestMapping(path ="/submit-answer", method = RequestMethod.POST)
    public String results(String topic, Integer userid, Integer pollid, String answer) {
        Result result = new Result(topic,userid,pollid,answer);
        results.save(result);
        return"/take-poll";
    }

}
