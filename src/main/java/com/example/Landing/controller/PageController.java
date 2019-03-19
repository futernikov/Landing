package com.example.Landing.controller;

import com.example.Landing.domain.Page;
import com.example.Landing.domain.Type;
import com.example.Landing.domain.User;
import com.example.Landing.repo.PagesRepo;
import com.example.Landing.services.PagesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user/admin/page")
public class PageController {
    @Value("${upload.page.path}")
    private String uploadPagePath;

    @Autowired
    private PagesRepo pagesRepo;

    @Autowired
    PagesService pagesService;

    @GetMapping
    public List<Page> list(){
        return pagesRepo.findAll();
    }

    @GetMapping("{id}")
    public Page getOne(@PathVariable("id") Page page){
        return page;
    }

    @PutMapping("{id}")
    public  Page update(@PathVariable("id") Page pageFromDb, @RequestBody Page page){
        BeanUtils.copyProperties(page, pageFromDb, "id");
        return pagesRepo.save(pageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Page page){
        pagesRepo.delete(page);
    }

    @PostMapping("upload")
    public Page uploadPage(@RequestBody MultipartFile file, @RequestParam Type type, @RequestParam String title, @RequestParam String description, @AuthenticationPrincipal User requester) throws IOException {
        return pagesService.uploadPage(file,type,requester,title,description);
    }

    @PostMapping
    public Page create(@RequestBody Page page){
        return pagesRepo.save(page);
    }
}