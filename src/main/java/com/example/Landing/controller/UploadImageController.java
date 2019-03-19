package com.example.Landing.controller;

import com.example.Landing.domain.BgImage;
import com.example.Landing.domain.User;
import com.example.Landing.exeption.ErrorCodes;
import com.example.Landing.exeption.RestException;
import com.example.Landing.repo.BgImageRepo;
import com.example.Landing.services.BgImageService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/user/admin/image")
public class UploadImageController {

    private static final String DEFAULT_FILE_NAME = "2.png";
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private BgImageRepo bgImageRepo;
    @Autowired
    private BgImageService bgImageService;

    @PostMapping("upload")
    public BgImage uploadImage( @RequestBody MultipartFile file,@RequestParam Integer width, @RequestParam Integer height,
                                @RequestParam Boolean isPaid,@AuthenticationPrincipal User requester) throws IOException {
        return bgImageService.uploadImage(file,requester,width,height,isPaid);
    }

    @GetMapping("download")
    public void downloadImage(@RequestParam Long id, HttpServletResponse response)
            throws IOException {
        String contentType = "image/png";
        BgImage byId = bgImageRepo.findById(id).orElseThrow(() -> new RestException(ErrorCodes.NOT_FOUND));
        Path path = Paths.get(uploadPath, byId.getId() + ".png");
        final File file = path.toFile();
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName());

        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

        IOUtils.copy(inStream, outStream);

        outStream.flush();
        inStream.close();
    }

    @GetMapping
    public List<BgImage> list(){
        return bgImageRepo.findAll();
    }

    @GetMapping("{id}")
    public BgImage getOne(@PathVariable("id") BgImage bgImage){
        return bgImage;
    }

    @PutMapping("{id}")
    public  BgImage update(@PathVariable("id") BgImage imageFromDb, @RequestBody BgImage bgImage){
        BeanUtils.copyProperties(bgImage, imageFromDb, "id");
        return bgImageRepo.save(imageFromDb);
    }
    @DeleteMapping("{id}")
    public  void delete(@PathVariable("id") BgImage bgImage){
        bgImageRepo.delete(bgImage);
    }
}
