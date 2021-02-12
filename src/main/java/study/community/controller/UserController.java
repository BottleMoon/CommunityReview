package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.community.domain.User;
import study.community.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //Login Get
    @GetMapping(value = "/login")
    public String loginGet(User user) {
        return "user/login";
    }

    //Login Post
    @PostMapping(value = "/login")
    public String loginPost(User user, HttpServletRequest req)throws Exception {

        if (userService.login(user)) {
            user = userService.findById(user.getId());
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
        }
        else {
            return "redirect:/login";
        }
        return "redirect:/";
    }

    //signup Get
    @GetMapping(value = "/signup")
    public String signupGet(User user) {
        return "user/signup";
    }

    //signup Post
    @PostMapping(value = "/signup")
    public String signupPost(User user) {
        if(userService.createUser(user)) {
            return "redirect:/login";
        }
        else {
            return "redirect:/signup";
        }
    }

    //Mypage Get
    @GetMapping("/mypage")
    public String myPage(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null){
            return "redirect:/login";
        }
        return "user/mypage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
