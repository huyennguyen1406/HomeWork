package cg.controller;

import cg.model.Human;
import cg.service.IHumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HumanController {
    @Autowired
    IHumanService humanService;

    @GetMapping
    public ModelAndView showAll(@PageableDefault Pageable pageable,Optional<String> search){
        ModelAndView modelAndView = new ModelAndView("index");
        Page<Human> humans;
        if(search.isPresent()) {
            humans = humanService.findByName(search.get(), pageable);
            modelAndView.addObject("search",search.get());
        } else {
            humans = humanService.findAll(pageable);
        }
        modelAndView.addObject("humans",humans);
        return modelAndView;
    }

    @GetMapping("/asc")
    public ModelAndView sortByAsc(@SortDefault(sort = "name", direction = Sort.Direction.ASC)@PageableDefault Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        Page<Human> humans = humanService.findAll(pageable);
        modelAndView.addObject("humans", humans);
        return  modelAndView;
    }

    @GetMapping("/desc")
    public ModelAndView sortByDesc(@SortDefault(sort = "name", direction = Sort.Direction.DESC)@PageableDefault Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        Page<Human> humans = humanService.findAll(pageable);
        modelAndView.addObject("humans", humans);
        return  modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("human",new Human());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@Valid @ModelAttribute("human") Human human, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()) {
            return new ModelAndView("create").addObject("human", human);
        }
        humanService.save(human);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Human human = new Human();
        if (humanService.findOne(id).isPresent()){
            human = humanService.findOne(id).get();
        }
        modelAndView.addObject("human", human);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@Valid @ModelAttribute("human") Human human, BindingResult bindingResult, @PathVariable("id") Long id){
        if(bindingResult.hasFieldErrors()) {
            return new ModelAndView("edit").addObject("human",human);
        }
        human.setId(id);
        humanService.save(human);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        modelAndView.addObject("human", humanService.findOne(id));
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        humanService.delete(id);
        return new ModelAndView("redirect:/");
    }
}
