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
        return "/home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password, Model model) throws Exception {
        User user = users.findFirstByName(userName);
        if(userName.equals("JoshnJoe") && password.equals("SecretPassage")) {
            return "admin";
        }
        if (user == null) {
            return "redirect:/register";
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            return "redirect:/";
        }
        session.setAttribute("username", userName);
        model.addAttribute("user", user);

        return "redirect:/";
    }

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
        Random random = new Random((System.currentTimeMillis()));
        ArrayList<Poll> pollList = (ArrayList)polls.findAll();

        if(pollList.size()==0){
            return("redirect:/");
        }

        Poll poll = pollList.get(random.nextInt(pollList.size()));
        model.addAttribute("poll", poll);
        return("/takepoll");
    }

    @RequestMapping(path = "/get-polls", method = RequestMethod.GET)
    public String getPolls(HttpSession session, Model model)throws Exception{
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        ArrayList<Poll> userPolls = polls.findByUser(user);

        if (userPolls.size() == 0){
            return("redirect:/");
        }

        model.addAttribute("polls", userPolls);
        return("/userpolls");
    }

    @RequestMapping(path = "/get-poll", method = RequestMethod.GET)
    public String getPolls(HttpSession session, Model model, Integer id)throws Exception{
        Poll poll = polls.findOne(id);

//        if (userPolls.size() == 0){
//            return("redirect:/");
//        }
        model.addAttribute("poll", poll);
        return("/poll");
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
    public String profile()throws Exception{
        return"/profile";
    }

    //error here with not returning to poll page
    @RequestMapping(path ="/submit-answer", method = RequestMethod.GET)
    public String results(String topic, Integer userid, Integer pollid, String answer) {
        Result result = new Result(topic,userid,pollid,answer);
        results.save(result);
        return"takepoll";
    }

    @RequestMapping(path = "/edit-profile", method = RequestMethod.POST)
    public String editUser(HttpSession session, String userName, String newpassword, String country, String city, String zip,Integer userId)throws Exception{
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        if(userName != "" || userName != null) {
            user.setName(userName);
        }
        if(newpassword != "" || newpassword != null) {
            user.setPassword(newpassword);
        }
        if(country != "" || country != null) {
            user.setPassword(country);
        }
        if(city != "" || city != null) {
            user.setPassword(city);
        }
        if(zip != "" || zip != null) {
            user.setPassword(zip);
        }
        users.save(user);
        return("/home");
    }

    @RequestMapping(path = "/edit-profile", method = RequestMethod.GET)
    public String getEditUser(HttpSession session, Model model)throws Exception{
        String username = (String)session.getAttribute("username");
        User user = users.findFirstByName(username);
        model.addAttribute("user", user);
        users.save(user);
        return("/editprofile");
    }

    //TODO: Figure out a better way to handle this
    @RequestMapping(path = "/find-user-username", method = RequestMethod.POST)
    public String FindUserAdmin(Model model, String username){
        User user = users.findFirstByName(username);
        if(user == null){
            user.setName("DOES NOT EXIST");
            user.setPassword("N/A");
            user.setCountry("N/A");
            user.setCity("N/A");
            user.setZip("N/A");
        }
        model.addAttribute("selectedUser", user);
        return("/admin");
    }

    @RequestMapping(path = "/update-user-info", method = RequestMethod.POST)
    public String updateUserAdmin(Model model, String userid, String country, String city, String zip){
        User user = users.findById(Integer.parseInt(userid));
        if(country != null) {
            user.setCountry(country);
        }
        if(city != null) {
            user.setCity(city);
        }
        if(zip != null) {
            user.setZip(zip);
        }
        users.save(user);
        model.addAttribute("message", "User information updated");
        return("/admin");
    }

    //TODO: make it so that the user is deleted but their polls and results are saved
    @RequestMapping(path = "/delete-user", method = RequestMethod.POST)
    public String deleteUserAdmin(Model model, String userid){
        int id = Integer.parseInt(userid);
        results.delete(id);
        polls.delete(id);
        users.delete(id);
        model.addAttribute("deleted", "user deleted");
        return("/admin");
    }

    //TODO: make this paginated. Could probably do the same for display all users for admin
    //TODO: and letting the user see the polls they've made
    @RequestMapping(path = "/view-all-polls", method = RequestMethod.GET)
    public String viewAllPollsAdmin(Model model){
        ArrayList<Poll> pollsAll =(ArrayList) polls.findAll();

        if(pollsAll.size() == 0){
            model.addAttribute("noPolls", "There are no polls found");
        }
        else {
            model.addAttribute("poll", pollsAll);
        }
        return("/admin");
    }

    @RequestMapping(path = "/find-poll-by-username", method = RequestMethod.POST)
    public String findPollByUserAdmin(Model model, String userid){
        Poll poll = polls.findByUserId(Integer.parseInt(userid));
        if(poll == null){
            model.addAttribute("notFound", "no polls were found");
        }
        else {
            model.addAttribute("pollUserID", poll);
        }
        return("/admin");
    }

    @RequestMapping(path = "/admin", method = RequestMethod.POST)
    public String admin() {
        return "/admin";
    }
}
