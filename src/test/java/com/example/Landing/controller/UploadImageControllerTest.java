package com.example.Landing.controller;

import com.example.Landing.LandingApplicationTest;
import com.example.Landing.domain.BgImage;
import com.example.Landing.repo.BgImageRepo;
import com.example.Landing.repo.UserDetailsRepo;
import com.example.Landing.services.BgImageService;
import com.example.Landing.services.PagesServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LandingApplicationTest.class)
@WebAppConfiguration
public class UploadImageControllerTest {


    private MockMvc mockMvc;

    @Autowired
    BgImageRepo bgImageRepo;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    BgImageService bgImageService;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    private BgImage image;

    @Before
    public void init() throws IOException {
        bgImageRepo.deleteAll();
        InputStream is = PagesServiceTest.class.getResourceAsStream("/1.png");
        MultipartFile myImage = new MockMultipartFile("filename","1.png",
                "image/png", IOUtils.toByteArray(is));
        image = bgImageService.uploadImage(myImage,userDetailsRepo.findAll().get(0),255,255,true);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void uploadImageTest() throws Exception {
        final InputStream IS = Thread.currentThread().getContextClassLoader().getResourceAsStream("1.png");
        MockMultipartFile myImage = new MockMultipartFile("file", "1.png", "image/png", IS);

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/user/admin/image/upload")
                .file(myImage)
                .param("width", "255")
                .param("height", "230")
                .param("isPaid", "false")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/admin/image")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("1.png"));
    }

    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/admin/image/" + bgImageRepo.findAll().get(0).getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception{

        BgImage bgImage = new BgImage(null,"BGName",222, 233,555,"image/png",false, new Date(System.currentTimeMillis()));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String content = writer.writeValueAsString(bgImage);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/admin/image/"+bgImageRepo.findAll().get(0).getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
