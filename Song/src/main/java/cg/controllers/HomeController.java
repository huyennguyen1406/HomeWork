package cg.controllers;

import cg.model.Category;
import cg.model.Song;
import cg.service.ICategoryService;
import cg.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private ISongService songService;

    @Autowired
    private ICategoryService categoryService;

    @Value("${file-upload}")
    private String fileUpload;

    @Value("${view}")
    private String view;

    @GetMapping
    public ModelAndView showAll(@RequestParam(name = "name", required = false) String name,
                                @SortDefault(sort = {"name"}, direction = Sort.Direction.ASC) @PageableDefault(value = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        if (name == null) {
            name = "";
        }
        Page<Song> songs = songService.getAllSongByNameContaining(name, pageable);
        if (songs.isEmpty()) {
            modelAndView.addObject("message", "No Song !!!");
        }
        modelAndView.addObject("searchByName", name);
        modelAndView.addObject("songs", songs);
        modelAndView.addObject("view", view);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }

    @GetMapping("/create-song")
    public ModelAndView createSong() {
        ModelAndView modelAndView = new ModelAndView("create");
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("song", new Song());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Song song) {
        ModelAndView modelAndView = new ModelAndView("create");
        MultipartFile multipartFile = song.getSongFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(song.getSongFile().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        song.setSongLink(fileName);
        Song song1 = songService.saveSong(song);
        if (song1 != null) {
            Iterable<Category> categories = categoryService.findAll();
            modelAndView.addObject("categories", categories);
            modelAndView.addObject("message", "Create Product Successfully !!!");
        }
        return modelAndView;
    }

    @GetMapping("/view")
    public ModelAndView viewDetail(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Song song = songService.findById(id);
        modelAndView.addObject("view", view);
        modelAndView.addObject("song", song);
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView deleteSong(@RequestParam("id") Long id, @PageableDefault(value = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        songService.deleteSongById(id);
        String searchByName = "";
        Page<Song> songs = songService.getAllSongByNameContaining(searchByName, pageable);
        if (songs.isEmpty()) {
            modelAndView.addObject("message", "No Song !!!");
        }
        modelAndView.addObject("searchByName", searchByName);
        modelAndView.addObject("songs", songs);
        modelAndView.addObject("view", view);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }

//    @PostMapping("/search-by-name")
//    public ModelAndView searchByName(@RequestParam("searchByName") String name, @PageableDefault(value = 5) Pageable pageable){
//        return showAll(name,pageable);
//    }

    @GetMapping("/edit")
    public ModelAndView editSong(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Song song = songService.findById(id);
        modelAndView.addObject("view", view);
        modelAndView.addObject("song", song);
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateSong(@RequestParam("id") Long id, @ModelAttribute Song song) {
        ModelAndView modelAndView = new ModelAndView("edit");
        if (song.getSongFile().getOriginalFilename().equals("")) {
            song.setSongFile(songService.findById(id).getSongFile());
            song.setSongLink(songService.findById(id).getSongLink());
        } else {
            MultipartFile multipartFile = song.getSongFile();
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(song.getSongFile().getBytes(), new File(fileUpload + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            song.setSongLink(fileName);
        }
        songService.saveSong(song);
        modelAndView.addObject("view", view);
        modelAndView.addObject("song", song);
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.addObject("message", "Update Product Successfully !!!");
        return modelAndView;
    }

    @GetMapping("/search-by-category")
    public ModelAndView searchByCategory(@RequestParam("category") String id, @PageableDefault(value = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        Category category = categoryService.findById(Long.parseLong(id));
        Page<Song> songs = songService.getAllSongByCategory(category, pageable);
        modelAndView.addObject("searchByName", "");
        modelAndView.addObject("songs", songs);
        modelAndView.addObject("view", view);
        modelAndView.addObject("category", category);
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }
}
