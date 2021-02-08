package edu.baylor.ecs.cms.service;

import edu.baylor.ecs.cms.dto.ExamDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmsService {
    private static final Logger logger = LogManager.getLogger(EmsService.class.getName());
    private final RestTemplate restTemplate;
    @Value("${ems.ip}")
    private String ip;
    @Value("${ems.exam}")
    private String examContext;
    @Autowired
    private UmsService umsService;

    public EmsService(RestTemplateBuilder restTemplateBuilder) {

        logger.info(Thread.currentThread().getId() + ":" + "EmsService" + "(" + restTemplateBuilder + ")");
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<Object> createExam(ExamDto examDto) {
        logger.info(Thread.currentThread().getId() + ":" + "createExam" + "(" + examDto + ")");

//        if (!umsService.isExamineeIdValid(examDto.getExaminee())){
//            return ResponseEntity.badRequest().body(null);
//        }
//        examDto.setIssuer(umsService.getCurrentLoggedInUser());
        String uri = ip + examContext;
        Object obj = this.restTemplate.postForObject(uri, examDto, ExamDto.class);
        return ResponseEntity.ok(obj);
    }


    //ToDo: CRUD

//    public ResponseEntity<Object[]> updateExam(){
//        String categoryInfoPath = qmsIp + categoryInfoContext;
//        return restTemplate.getForEntity(categoryInfoPath, Object[].class);
//    }

}