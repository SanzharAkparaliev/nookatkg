package com.example.nookatkg.controller;
import com.example.nookatkg.helper.Message;
import com.example.nookatkg.model.User;
import com.example.nookatkg.service.impl.UserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@Controller
public class AuthController {
    @Autowired
    private UserServiceimpl serviceimpl;
    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session){
//        try {
//            user.setRole("ROLE_USER");
//            user.setEnabled(true);
//            user.setImageUrl("car.jpg");
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            model.addAttribute("user",new User());
//            userRepository.save(user);
//            session.setAttribute("message",new Message("Successfully Register !! " ,"alert-success"));
//            return "signup";
//        }catch (Exception e){
//            e.printStackTrace();
//            model.addAttribute("user",user);
//            session.setAttribute("message",new Message("Something Went wrong !! " + e.getMessage(),"alert-danger"));
//            return "signup";
//        }
        System.out.println(user.getUsername());
        try {
            if (result.hasErrors()){
                model.addAttribute("user",user);
                session.setAttribute("message",new Message("Myndai koldonuuchu kattalgan !! ","alert-danger"));
                return "redirect:/";
            }

            serviceimpl.createUser(user.getUsername(),user.getEmail(),user.getPassword());
            session.setAttribute("message",new Message("Successfully Register !!","alert"));
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("message",new Message("Myndai koldonuuchu bar !! ","alert-danger"));
            model.addAttribute("user",user);
            return "redirect:/";
        }

        return "redirect:/";
    }
}
