package controllers;

import Entities.*;
import Services.*;
import Utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PollzController {

    @Autowired
    UserRepo users;

    @Autowired
    PollRepo polls;

    @Autowired
    ResultsRepo results;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login
}
