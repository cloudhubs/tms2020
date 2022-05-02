 package edu.baylor.ems.controller;

import edu.baylor.ems.dto.ExamDto;
import edu.baylor.ems.dto.ExamReviewDto;
import edu.baylor.ems.dto.QuestionEmsDto;
import edu.baylor.ems.model.Exam;
import edu.baylor.ems.service.EmailService;
import edu.baylor.ems.service.ExamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("exam")
public class ExamController {
    private static final Logger logger = LogManager.getLogger(ExamController.class.getName());
    @Autowired
    private ExamService examService;

    @Autowired
    private EmailService emailService;

    //    @PreAuthorize("hasAnyAuthority('ROLE_ems-frontend')")
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Exam>> listAllExams() {
        logger.info("HTTP requst come for find all exams");
        List<Exam> exams = examService.findAllExams();
        if (exams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
    public ResponseEntity<List<Exam>> getExamsByStatus(@PathVariable("status") String status) {
        logger.info("HTTP request come for find exams based on status #status = "+status);
        List<Exam> exams = examService.findAllExamsByStatus(status);
        if (exams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    //From CMS
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ResponseEntity<Exam> createExam(@RequestBody ExamDto examDto) {
        logger.info("HTTP request come for create new exam");
        logger.info("Exam service called for create new exam");
        Exam exam = examService.saveExam(examDto);
        logger.info("Email service called for sending exam notification #examid = "+exam.getId());
        emailService.sendExamAssignmentNotification(exam);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ResponseEntity<Integer> deleteExam(@PathVariable("id") Integer id) {
        logger.info("HTTP request come for delete an exam #examid = "+id);
        logger.info("Service called for delete exam #examid = "+id);
        examService.deleteExam(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/take/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionEmsDto>> takeExam(@PathVariable("id") Integer id) {
        logger.info("HTTP request come for taking an exam #examid = "+id);
        // check ID
        logger.info("Service called for take exam #examid = "+id);
        return examService.takeExam(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/submit/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exam> submitExam(@PathVariable("id") Integer id) {
        logger.info("HTTP request come for submitting exam #examid = "+id);
        // check ID
        logger.info("service called for submit exam #examid = "+id);
        return examService.submitExam(id);
    }

    // change status to done + calculate wrong / correct / sum of answers

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/finish/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> finishExam(@PathVariable("id") Integer id) {
        logger.info("HTTP request come for finish exam #examid = "+id);
        // check ID
        logger.info("service called for finish exam #examid = "+id);
        return examService.finishExam(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exam> getExam(@PathVariable("id") Integer id) {
        logger.info("HTTP request come for get exam by id #examid = "+id);
        // check ID
        logger.info("service called for get exam #examid = "+id);
        return new ResponseEntity<>(examService.findById(id).get(), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/review/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExamReviewDto> reviewExam(@PathVariable("id") Integer id) {
        logger.info("HTTP request come for review exam #examid = "+id);
        // check ID
        logger.info("service called for review exam #examid = "+id);

        ExamReviewDto review = examService.reviewExam(id);
        logger.info("Sending HTTP response");
        return new ResponseEntity<ExamReviewDto>(review, HttpStatus.OK);
    }

}
