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

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    //Login Get
    @GetMapping(value = "/login")
    public String loginGet(User user) {
        return "user/login";
    }

    //Login Post
    @PostMapping(value = "/login")
    public String loginPost(User user, HttpServletRequest req)throws Exception {
        user = userService.findById(user.getId());
        if (userService.login(user)) {
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
        userService.createUser(user);

        return "index";
    }

    //Mypage Get
    @GetMapping("/mypage")
    public String myPage(){
        return "user/mypage";
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }


    /*
    @GetMapping("/list")
    public String userList( Model model){
        model.addAttribute("list",userService.list());
        return "user/list";
    }*/
}
