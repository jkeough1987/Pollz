package com.theironyard.Controllers;

import com.theironyard.Entities.Poll;
import com.theironyard.Entities.Result;
import com.theironyard.Services.ResultRepo;
import com.theironyard.Services.PollRepo;
import com.theironyard.Entities.User;
import com.theironyard.Services.UserRepo;
import com.theironyard.Utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

@Controller
public class PollzPostController {

    @Autowired
    UserRepo users;

    @Autowired
    PollRepo polls;

    @Autowired
    ResultRepo results;


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password, Model model) throws Exception {
        User user = users.findFirstByName(userName);

        if (userName.equals("JoshnJoe") && password.equals("SecretPassage")) {
            return "/admin";
        }

        if (user == null) {
            return "redirect:/register";
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            //write to log file unsuccessful login attempts for users
            writeFile(user.getName());
            return "redirect:/";
        }

        if(user.getAdmin() && PasswordStorage.verifyPassword(password, user.getPassword())){
            model.addAttribute("admin", "true");
            return "/admin";
        }

        session.setAttribute("username", userName);
        model.addAttribute("user", user);

        return "redirect:/";
    }

    @RequestMapping(path = "/create-profile", method = RequestMethod.POST)
    public String registerUser(HttpSession session, String userName, String newpassword, String country, String city, String zip) throws Exception {
        if(country == null) {
            country = "";
        }
        if(city == null) {
            city = "";
        }
        if(zip == null) {
            zip = "";
        }

        Boolean isAdmin = false;

        User user = new User(userName, PasswordStorage.createHash(newpassword), country, city, zip, isAdmin);
        users.save(user);
        return ("/home");
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        return ("/home");
    }


    @RequestMapping(path = "/create-poll", method = RequestMethod.POST)
    public String createPoll(HttpSession session, String pollName, String topic, String responseA, String responseB, String responseC, String responseD, String responseE, String responseF) throws Exception {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        if (responseC.equals("")) {
            responseC = null;
        }
        if (responseD.equals("")) {
            responseD = null;
        }
        if (responseE.equals("")) {
            responseE = null;
        }
        if (responseF.equals("")) {
            responseF = null;
        }
        Poll poll = new Poll(pollName, topic, responseA, responseB, responseC, responseD, responseE, responseF, user);
        polls.save(poll);
        return ("/createpoll");
    }


    @RequestMapping(path = "/edit-profile", method = RequestMethod.POST)
    public String editUser(HttpSession session, String currentPassword,String newPassword1, String newPassword2, String country, String city, String zip) throws Exception {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        if(currentPassword != null && newPassword1 != null && newPassword2 != null) {
            if(PasswordStorage.verifyPassword(currentPassword, user.getPassword())&& newPassword1.equals(newPassword2)){
                user.setPassword(PasswordStorage.createHash(newPassword1));
            }
            return "redirect:/profile";
        }


        if (!country.equals("")) {
            user.setCountry(country);
        }
        if (!city.equals("")) {
            user.setCity(city);
        }
        if (!zip.equals("")) {
            user.setZip(zip);
        }
//        user.setZip(zip);
//        user.setCity(city);
//        user.setCountry(country);
        users.save(user);

        return ("redirect:/");
    }


    //TODO: Figure out a better way to handle this
    @RequestMapping(path = "/find-user-username", method = RequestMethod.POST)
    public String FindUserAdmin(Model model, String username) {
        User user = users.findFirstByName(username);
        if (user == null) {
            user.setName("DOES NOT EXIST");
            user.setPassword("N/A");
            user.setCountry("N/A");
            user.setCity("N/A");
            user.setZip("N/A");
        }
        model.addAttribute("selectedUser", user);
        return ("/admin");
    }

    @RequestMapping(path = "/update-user-info", method = RequestMethod.POST)
    public String updateUserAdmin(Model model, String userid, String country, String city, String zip) {
        User user = users.findById(Integer.parseInt(userid));
        if (country != null) user.setCountry(country);
        if (city != null) user.setCity(city);
        if (zip != null) user.setZip(zip);
        users.save(user);
        model.addAttribute("message", "User information updated");
        return ("/admin");
    }

    //TODO: make it so that the user is deleted but their polls and results are saved
    @RequestMapping(path = "/delete-user", method = RequestMethod.POST)
    public String deleteUserAdmin(Model model, String useridString) {
        int user_id = Integer.parseInt(useridString);
        int id = user_id;
        ArrayList<Poll> pollArrayList = (ArrayList) polls.findAllByUserId(user_id);

        for(Poll p : pollArrayList){
            Result result = results.findFirstByPollId(p.getId());
            if(result == null){
                break;
            }
            results.delete(result.getId());
        }

        for(Poll p : pollArrayList) {
            polls.delete(p.getId());
        }

        users.delete(id);
        model.addAttribute("deleted", "user deleted");
        return ("/admin");
    }


    @RequestMapping(path = "/find-poll-by-username", method = RequestMethod.POST)
    public String findPollByUserAdmin(Model model, String userid) {
        int user_id = Integer.parseInt(userid);
         ArrayList<Poll> pollArrayList = (ArrayList) polls.findAllByUserId(user_id);
        if (pollArrayList.size() == 0) {
            model.addAttribute("notFound", "no polls were found");
        } else {
            model.addAttribute("pollUserID", pollArrayList);
        }
        return ("/admin");
    }


    @RequestMapping(path = "/remove-users-polls", method = RequestMethod.POST)
    public String RemoveAllPollsByUser(Model model, String useridString) {
        int user_id = Integer.parseInt(useridString);
        int id = user_id;
        ArrayList<Poll> pollArrayList = (ArrayList) polls.findAllByUserId(user_id);

        for(Poll p : pollArrayList){
            Result result = results.findFirstByPollId(p.getId());
            if(result == null){
                break;
            }
            results.delete(result.getId());
        }

        for(Poll p : pollArrayList) {
            polls.delete(p.getId());
        }
        model.addAttribute("pollsRemoved", "All polls and results removed");
        return ("/admin");
    }


    @RequestMapping(path = "/admin", method = RequestMethod.POST)
    public String admin() {
        return "/admin";
    }

    //error here with not returning to poll page
    @RequestMapping(path = "/submit-answer", method = RequestMethod.POST)
    public String results(String topic, Integer userid, Integer pollid, String answer) {
        Result result = new Result(topic, userid, pollid, answer);
        results.save(result);
        return "redirect:/take-poll";
    }

    public static void writeFile(String username){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String time = df.format(cal.getTime());
        Files.write("loginAttempts.txt", username, time);
    }
}
