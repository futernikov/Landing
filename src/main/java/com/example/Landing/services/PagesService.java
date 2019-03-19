package com.example.Landing.services;

import com.example.Landing.domain.Page;
import com.example.Landing.domain.Type;
import com.example.Landing.domain.User;
import com.example.Landing.exeption.ErrorCodes;
import com.example.Landing.exeption.RestException;
import com.example.Landing.repo.PagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class PagesService {

    @Autowired
    private PagesRepo pagesRepo;

    @Value("${upload.page.path}")
    private String uploadPagePath;

    public Page uploadPage(MultipartFile file, Type type, User requester,String title, String description)
            throws IOException {
        if(file.isEmpty()){
            throw new RestException(ErrorCodes.EMPTY_FILE);
        }
        String fileName = file.getOriginalFilename();
        Page page = pagesRepo.save(new Page(null,type,fileName,LocalDateTime.now(),requester,file.getContentType()
                ,title,description));
        Path path = Paths.get(uploadPagePath, page.getId() +".zip");
        Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
        return page;
    }
}
