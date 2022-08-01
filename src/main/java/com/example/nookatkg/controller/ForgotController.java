package com.example.nookatkg.controller;

import com.example.nookatkg.helper.Message;
import com.example.nookatkg.model.User;
import com.example.nookatkg.repo.UserRepository;
import com.example.nookatkg.service.CategoryService;
import com.example.nookatkg.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class ForgotController {
    Random random = new Random(1000);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/forgot")
    public String openEmail(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("categories",categoryService.getCategories());
        model.addAttribute("title", "Сырсөз  өзгөртүү");
        return "forgot_email_form";
    }
    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email, HttpSession session,Model model){

        System.out.println("EMAIL " + email);
        int otp = random.nextInt(999999);
        System.out.println("OTP  " + otp);

        //write code for send otp to email...
        String subject = "OTP from SCM";
        String message = ""
                + "<div style='border:1px solid #e2e2e2;padding:20px'>"
                +"<h1>"+"Nookat Tany"+"</h1>"
                +"<h3>"
                +"OTP   : "
                +"<b>"+otp
                +"</n>"
                +"</h3>"
                +"</div>";

        String to = email;
        boolean flag = emailService.sendEmail(subject,message,to);
        if(flag){
            session.setAttribute("myotp",otp);
            session.setAttribute("email",email);
            model.addAttribute("user",new User());
            model.addAttribute("categories",categoryService.getCategories());
            model.addAttribute("title", "Сырсөз  өзгөртүү");
            return "verify_otp";
        }else {
            session.setAttribute("message","Электрондук почтаңыздын идентификаторун текшериңиз !!");
            model.addAttribute("user",new User());
            model.addAttribute("categories",categoryService.getCategories());
            model.addAttribute("title", "Сырсөз  өзгөртүү");
            return "forgot_email_form";
        }
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") int otp,HttpSession session,Model model){
        int myOtp = (int)session.getAttribute("myotp");
        String email = (String)session.getAttribute("email");

        if(myOtp==otp){
            //password change from
            User user = userRepository.getUserByUsername(email);
            if(user==null){
                //send error message

                session.setAttribute("message","Мындай электрондук почта менен катталынган эмес!!");
                model.addAttribute("user",new User());
                model.addAttribute("categories",categoryService.getCategories());
                model.addAttribute("title", "Сырсөз  өзгөртүү");

                return "forgot_email_form";
            }else
                //send change password form

                model.addAttribute("user",new User());
            model.addAttribute("categories",categoryService.getCategories());
           model.addAttribute("title", "Сырсөз  өзгөртүү");


            return "password_change_form";
        }else {
            session.setAttribute("message","Сиз туура эмес otp киргиздиңиз !!");
            model.addAttribute("user",new User());
            model.addAttribute("categories",categoryService.getCategories());
            model.addAttribute("title", "Сырсөз  өзгөртүү");

            return "verify_otp";
        }
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword") String newpassword,HttpSession session){
        String email = (String)session.getAttribute("email");
        User user = userRepository.getUserByUsername(email);
        user.setPassword(bCryptPasswordEncoder.encode(newpassword));
        userRepository.save(user);
        session.setAttribute("message",new Message("Сырсөз ийгиликтүү өзгөртүлдүү !!","alert"));

        return "redirect:/";
    }
}
