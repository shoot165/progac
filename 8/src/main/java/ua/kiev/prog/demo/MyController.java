package ua.kiev.prog.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.List;


@Controller
public class MyController {

    @Autowired
    private EntityManager em;

    @GetMapping("/")
    public String onIndex() {
        return "index";
    }


    @PostMapping("/response")
    public String doPost(Model model,
                          @RequestParam String name,
                          @RequestParam String lastName,
                          @RequestParam String age,
                          @RequestParam String season,
                          @RequestParam String gadget
    ) {


        model.addAttribute("name", name);
        model.addAttribute("lastName", lastName);
        model.addAttribute("age", age);
        model.addAttribute("season", season);
        model.addAttribute("gadget", gadget);

        User user = new User(name, lastName, age, season, gadget);
        model.addAttribute("users", List.of(user));

       addUser(user);

        return "result";
    }

    @Transactional
    public  void addUser(User user) {
   em.persist(user);
    }
}
