package com.olixie.animalshelper;

import com.olixie.animalshelper.entity.*;
import com.olixie.animalshelper.mapper.NewMapper;
import com.olixie.animalshelper.mapper.PetAdoptMapper;
import com.olixie.animalshelper.mapper.PetMapper;
import com.olixie.animalshelper.service.ChatUserLinkService;
import com.olixie.animalshelper.service.UserService;
import com.olixie.animalshelper.util.ProjectConstant;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalsHelperApplicationTests {
    @Resource
    private PetAdoptMapper petAdoptMapper;

    @Resource
    private ChatUserLinkService chatUserLinkService;
    @Resource
    private UserService userService;


    @Resource
    private PetMapper petMapper;

    @Resource
    private NewMapper newsMapper;
    @Value("${props.upload-folder}")
    private String UPLOAD_FOLDER;

    @Test
    void contextLoads() {
        File file = new File(UPLOAD_FOLDER + "2023-12-25.jpg2023-12-25.jpg");
        System.out.println(file.exists());
        System.out.println(file.delete());
    }
    @Test
    public void testUserGetById(){
        User byId = userService.getById(-598138879);
        System.out.println(byId);
    }

    @Test
    public void insertPetAdopt() {
        for (int i = 0; i < 1000; i++) {
            PetAdopt petAdopt = new PetAdopt();
            petAdopt.setPid(1);
            petAdopt.setUid(1);
            //模拟生成的随机电话号码
            Random random = new Random();
            Long telephone = 1L;
            for (int j = 0; j < 10; j++) {
                telephone = telephone * 10 + random.nextInt(10);
            }
            //将插入时间进行一定程度的随机
            petAdopt.setCreateTime(LocalDateTime.now().plusSeconds(random.nextInt(180)));

            petAdoptMapper.insert(petAdopt);
        }
    }
    @Test
    public void testDateTimeFormatter(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
        String format = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println(format);
        dateTimeFormatter = DateTimeFormatter.ISO_TIME;
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
        dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));

        /*最终的使用效果：2024-03-05 10:38:43,使用该时间整理器*/
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Test
    public void insertNews() {
        for (int i = 0; i < 100; i++) {
            New newObject = new New();
            newObject.setTitle("文章标题" + UUID.randomUUID().toString());
            newObject.setContent("content暂未填写");
            newObject.setPublishDate(LocalDateTime.now());
            newsMapper.insert(newObject);
        }
    }

    @Test
    public void insertPets() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Pet pet = new Pet();
            pet.setName("宠物名称 " + random.nextInt(10));
            pet.setAge(random.nextInt(10));
            pet.setDescription("宠物描述" + random.nextInt(100));
            pet.setPhoto(random.nextInt(4) + 3 + ".jpg");
            pet.setSex(random.nextInt(2) == 1 ? "雄" : "雌");
            pet.setStatus(ProjectConstant.PET_STATUS_NOT_ADOPT);
            pet.setType(random.nextInt(5) + 1);
            petMapper.insert(pet);
        }
    }
    @Test
    public void testChatInitGetId(){
        chatUserLinkService.init(1,2);
    }

}
