package com.example.Landing.controller;

import com.example.Landing.LandingApplicationTest;
import com.example.Landing.domain.Page;
import com.example.Landing.domain.Type;
import com.example.Landing.repo.PagesRepo;
import com.example.Landing.repo.UserDetailsRepo;
import com.example.Landing.services.PagesService;
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
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LandingApplicationTest.class)
@WebAppConfiguration
public class PageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private PagesService pagesService;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    PagesRepo pagesRepo;

    private Page page;

    @Before
    public void init() throws IOException {
        pagesRepo.deleteAll();
        InputStream is = PagesServiceTest.class.getResourceAsStream("/140522091859.zip");
        MultipartFile file = new MockMultipartFile("filename","140522091859.zip",
                "application/zip", IOUtils.toByteArray(is));
        page = pagesService.uploadPage(file, Type.SPORT, userDetailsRepo.findAll().get(0), "title", "description");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void getListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .get("/user/admin/page")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").isNotEmpty());
    }

    @Test
    public void uploadPageTest() throws Exception {

        final InputStream IS = Thread.currentThread().getContextClassLoader().getResourceAsStream("140522091859.zip");
        MockMultipartFile file1 = new MockMultipartFile("file", "140522091859.zip", "application/zip", IS);

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/user/admin/page/upload")
                .file(file1)
                .param("type", Type.AUTO.toString())
                .param("title", "title")
                .param("description", "description")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOneTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/admin/page/"+pagesRepo.findAll().get(0).getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").isNotEmpty());
    }

    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/admin/page/" + pagesRepo.findAll().get(0).getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception{

        Page page = new Page(Type.SPORT,"testName", new Date(),"testTitle","description");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String content = writer.writeValueAsString(page);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/admin/page/"+pagesRepo.findAll().get(0).getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
