package com.example.Landing.services;

import com.example.Landing.domain.BgImage;
import com.example.Landing.domain.User;
import com.example.Landing.exeption.ErrorCodes;
import com.example.Landing.exeption.RestException;
import com.example.Landing.repo.BgImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class BgImageService {
    @Autowired
    private BgImageRepo bgImageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public BgImage uploadImage(MultipartFile file, User requester, Integer width, Integer height, Boolean isPaid) throws IOException {
        if(file.isEmpty()){
            throw new RestException(ErrorCodes.EMPTY_FILE);
        }
        String fileName = file.getOriginalFilename();
        BgImage bgImage = bgImageRepo.save(new BgImage(null,fileName,(int)file.getSize(),width, height,file.getContentType(),isPaid, new Date(System.currentTimeMillis())));
        Path path = Paths.get(uploadPath, bgImage.getId() +".png");
        Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
        return bgImage;
    }
}
