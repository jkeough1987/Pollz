package com.theironyard.Controllers;

import com.theironyard.Entities.Poll;
import com.theironyard.Entities.Result;
import com.theironyard.Entities.User;
import com.theironyard.Services.PollRepo;
import com.theironyard.Services.ResultRepo;
import com.theironyard.Services.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by joshuakeough on 11/3/16.
 */
@Controller
public class PollzGetController {
    @Autowired
    UserRepo users;

    @Autowired
    PollRepo polls;

    @Autowired
    ResultRepo results;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        model.addAttribute("user", user);
        return "/home";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String goToRegister() throws Exception {
        return ("/register");
    }

    @RequestMapping(path = "/take-poll", method = RequestMethod.GET)
    public String takePoll(HttpSession session, Model model) throws Exception {
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        Random random = new Random((System.currentTimeMillis()));
        ArrayList<Poll> pollList = (ArrayList) polls.findAll();

        if (pollList.size() == 0) {
            return ("redirect:/");
        }

        Poll poll = pollList.get(random.nextInt(pollList.size()));
        model.addAttribute("user", user);
        model.addAttribute("poll", poll);
        return ("/takepoll");
    }

    @RequestMapping(path = "/get-polls", method = RequestMethod.GET)
    public String getPolls(HttpSession session, Model model) throws Exception {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        ArrayList<Poll> userPolls = polls.findByUser(user);

        if (userPolls.size() == 0) {
            return ("redirect:/");
        }

        model.addAttribute("polls", userPolls);
        return ("/userpolls");
    }

    @RequestMapping(path = "/get-poll", method = RequestMethod.GET)
    public String getPolls(HttpSession session, Model model, Integer id) throws Exception {
        Poll poll = polls.findOne(id);
        ArrayList<Result> results1 = results.findAllByPollId(id);
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int f = 0;
        ArrayList<Result> ra = new ArrayList<>();
        ArrayList<Result> rb = new ArrayList<>();
        ArrayList<Result> rc = new ArrayList<>();
        ArrayList<Result> rd = new ArrayList<>();
        ArrayList<Result> re = new ArrayList<>();
        ArrayList<Result> rf = new ArrayList<>();
        for (Result r : results1) {

            if (r.getAnswer().equals(poll.getResponseA())) {
                ra.add(r);
            }
            if (r.getAnswer().equals(poll.getResponseB())) {
                rb.add(r);
            }
            if (r.getAnswer().equals(poll.getResponseC())) {
                rc.add(r);
            }
            if (r.getAnswer().equals(poll.getResponseA())) {
                rd.add(r);
            }
            if (r.getAnswer().equals(poll.getResponseA())) {
                re.add(r);
            }
            if (r.getAnswer().equals(poll.getResponseA())) {
                rf.add(r);
            }
             a = ra.size();
             b = rb.size();
             c = rc.size();
             d = rd.size();
             e = rd.size();
             f = rd.size();
        }
        model.addAttribute("a",a);
        model.addAttribute("b",b);
        model.addAttribute("c",c);
        model.addAttribute("d",d);
        model.addAttribute("e",e);
        model.addAttribute("f",f);


        model.addAttribute("poll", poll);
        return ("/poll");
    }


    @RequestMapping(path = "/create-poll", method = RequestMethod.GET)
    public String getcreatePoll(HttpSession session) throws Exception {
        return ("/createpoll");
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String profile() throws Exception {
        return "/profile";
    }


    @RequestMapping(path = "/edit-profile", method = RequestMethod.GET)
    public String getEditUser(HttpSession session, Model model) throws Exception {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        model.addAttribute("user", user);
        users.save(user);
        return ("/editprofile");
    }

    //TODO: make this paginated. Could probably do the same for display all users for admin
    //TODO: and letting the user see the polls they've made
    //TODO: sample code is located in peoplewebdb
    @RequestMapping(path = "/view-all-polls", method = RequestMethod.GET)
    public String viewAllPollsAdmin(Model model) {
        ArrayList<Poll> pollsAll = (ArrayList) polls.findAll();

        if (pollsAll.size() == 0) {
            model.addAttribute("noPolls", "There are no polls found");
        } else {
            model.addAttribute("poll", pollsAll);
        }
        return ("/admin");
    }

}