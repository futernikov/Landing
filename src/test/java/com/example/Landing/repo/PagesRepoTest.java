package com.example.Landing.repo;

import com.example.Landing.LandingApplicationTest;
import com.example.Landing.domain.Page;
import com.example.Landing.domain.Type;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest(classes = {LandingApplicationTest.class})
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LandingApplicationTest.class)
public class PagesRepoTest {
    @Autowired
    PagesRepo pagesRepo;
   @Test
    public void saveTest(){
    pagesRepo.save(new Page( Type.AUTO,"testData", new Date(),"test","test"));
    }
}
