package com.example.Landing.services;

import com.example.Landing.LandingApplicationTest;
import com.example.Landing.domain.Page;
import com.example.Landing.domain.Type;
import com.example.Landing.repo.UserDetailsRepo;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = LandingApplicationTest.class)
public class PagesServiceTest {

    @Autowired
    PagesService pagesService;
    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Test
    public void uploadPageTest() throws IOException {
        InputStream is = PagesServiceTest.class.getResourceAsStream("/140522091859.zip");
        MultipartFile file = new MockMultipartFile("filename","140522091859.zip",
                "application/zip", IOUtils.toByteArray(is));
        Page page = pagesService.uploadPage(file, Type.SPORT, userDetailsRepo.findAll().get(0), "title", "description");
        Assert.assertNotNull(page);
        Assert.assertEquals("title",page.getTitle());
        Assert.assertEquals("description",page.getDescription());

    }

}
