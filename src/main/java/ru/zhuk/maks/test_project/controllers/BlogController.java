package ru.zhuk.maks.test_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zhuk.maks.test_project.models.Post;
import ru.zhuk.maks.test_project.repo.PostRepository;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogSubmit(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title,anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id_num}")
    public String blogDetails(@PathVariable(value="id_num") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
//        ArrayList<Post> res = new ArrayList<>();
//        post.ifPresent(res::add);
        model.addAttribute("post", post.get());
        return "blog-details";
    }
    @GetMapping("/blog/{id_num}/edit")
    public String blogEdit(@PathVariable(value="id_num") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
//        ArrayList<Post> res = new ArrayList<>();
//        post.ifPresent(res::add);
        model.addAttribute("post", post.get());
        return "blog-edit";
    }

    @PostMapping("/blog/{id_num}/edit")
    public String blogUpdate(@PathVariable(value="id_num") Long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> opt_post = postRepository.findById(id);
        Post post = opt_post.get();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id_num}/remove")
    public String blogRemove(@PathVariable(value="id_num") Long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
