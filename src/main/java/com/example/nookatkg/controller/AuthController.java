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

        try {
            if (result.hasErrors()){
                model.addAttribute("user",user);
                session.setAttribute("message",new Message("Мындай колдонуучу катталган !! ","alert-danger"));
                return "redirect:/";
            }

            serviceimpl.createUser(user.getUsername(),user.getEmail(),user.getPassword());
            session.setAttribute("message",new Message("Ийгиликтуу катталдыныз !!","alert"));
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("message",new Message("Myndai koldonuuchu bar !! ","alert-danger"));
            model.addAttribute("user",user);
            return "redirect:/";
        }

        return "redirect:/";
    }
}
