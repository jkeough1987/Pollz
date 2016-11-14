package com.theironyard.Controllers;

import com.theironyard.Entities.Poll;
import com.theironyard.Entities.Result;
import com.theironyard.Entities.User;
import com.theironyard.Services.PollRepo;
import com.theironyard.Services.ResultRepo;
import com.theironyard.Services.UserRepo;
import com.theironyard.Utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

    Integer counter = 0;

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
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        Random random = new Random((System.currentTimeMillis()));
        ArrayList<Poll> pollList = (ArrayList) polls.findAll();

        if (pollList.size() == 0) {
            return ("redirect:/create-poll");
        }

        Poll poll = pollList.get(random.nextInt(pollList.size()));

         Date currentDate = Date.valueOf(LocalDate.now());

        if(poll.getTimeToLive().after(currentDate)){
            poll.setExpired(true);
        }

        while (poll.isExpired() == true){
            poll = pollList.get(random.nextInt(pollList.size()));
        }

        /*
        left this in due to possible concurrency issues with checking the date time
        if(poll.isExpired() == true){
            poll = pollList.get(random.nextInt(pollList.size()));
        }*/

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
            return ("redirect:/create-poll");
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
            if (r.getAnswer().equals(poll.getResponseD())) {
                rd.add(r);
            }
            if (r.getAnswer().equals(poll.getResponseE())) {
                re.add(r);
            }
            if (r.getAnswer().equals(poll.getResponseF())) {
                rf.add(r);
            }
            a = ra.size();
            b = rb.size();
            c = rc.size();
            d = rd.size();
            e = re.size();
            f = rf.size();
        }
        model.addAttribute("a", a);
        model.addAttribute("b", b);
        model.addAttribute("c", c);
        model.addAttribute("d", d);
        model.addAttribute("e", e);
        model.addAttribute("f", f);


        model.addAttribute("poll", poll);
        return ("/poll");
    }


    @RequestMapping(path = "/create-poll", method = RequestMethod.GET)
    public String getcreatePoll(HttpSession session) throws Exception {
        return ("/createpoll");
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String profile(HttpSession session, Model model, String show) throws Exception {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        if (show != null){
            model.addAttribute("show", show);
        }
        model.addAttribute("user", user);
        return ("/editprofile");
    }


//    @RequestMapping(path = "/edit-profile", method = RequestMethod.GET)
//    public String getEditUser(HttpSession session, Model model) throws Exception {
//        String username = (String) session.getAttribute("username");
//        User user = users.findFirstByName(username);
//        model.addAttribute("user", user);
//        users.save(user);
//        return ("/editprofile");
//    }

    //TODO: Make this paginated
    /*@RequestMapping(path = "/view-all-users", method = RequestMethod.GET)
    public String getAllUsers(Model model){
        ArrayList<User> allUsers = (ArrayList) users.findAll();

        if(allUsers.size()==0){
            model.addAttribute("areUsers", "No users are currently registered");
        }
        model.addAttribute("allusers", allUsers);
        return ("/admin");
    }*/

    @RequestMapping(path = "/view-all-users", method = RequestMethod.GET)
    public String getAllUsers(Model model, Integer offset, HttpSession session) {
        String previous = "Previous";
        String next = "Next";
        ;
        if (offset == null) {
            offset = 0;
        }


        counter = counter + offset;

        ArrayList<User> allUsers = (ArrayList) users.findAll();
        if (allUsers == null) {
            model.addAttribute("areUsers", "No users are currently registered");
        }
        ArrayList<User> pageList = new ArrayList();
        for (User u : allUsers) {
            if (pageList.size() <= 1) {
                if (allUsers.indexOf(u) >= counter && allUsers.indexOf(u) < counter +1) {
                    pageList.add(u);
                }
            }
        }


        if(counter < users.count() - 1){
            model.addAttribute("next", next);

        }

        if(counter> 0) {
            model.addAttribute("previous", previous);
        }
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        if(user == null) return "redirect:/";
        if(user.getAdmin()){
            model.addAttribute("admin", "true");
        }
        model.addAttribute("allusers", pageList);
        return ("/admin");
    }

    //TODO: make this paginated. Could probably do the same for display all users for admin
    @RequestMapping(path = "/view-all-polls", method = RequestMethod.GET)
    public String viewAllPollsAdmin(Model model, HttpSession session) {
        ArrayList<Poll> pollsAll = (ArrayList) polls.findAll();

        if (pollsAll.size() == 0) {
            model.addAttribute("noPolls", "There are no polls found");
        } else {

            model.addAttribute("poll", pollsAll);
        }
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        if(user == null) return "redirect:/";
        if(user.getAdmin()){
            model.addAttribute("admin", "true");
        }
        return ("/admin");
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String admin(HttpSession session, Model model, String targetUserName, Integer userid) {
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        if(user == null) return "redirect:/";

        if(user.getAdmin()){
            model.addAttribute("admin", "true");
            if(targetUserName != null){
                User u = users.findFirstByName(targetUserName);
                model.addAttribute("selectedUser", u);

            }

            if(userid != null){
                ArrayList<Poll> pollArrayList = (ArrayList) polls.findAllByUserId(userid);
                if (pollArrayList.size() == 0) {
                    model.addAttribute("notFound", "no polls were found");
                } else {
                    model.addAttribute("pollUserID", pollArrayList);
                }
            }
            return ("/admin");
        }
        return "redirect:/";
    }

}
