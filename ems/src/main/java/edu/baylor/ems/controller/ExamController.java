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
        logger.info(Thread.currentThread().getId() + ":" + "listAllExams" + "()");
        List<Exam> exams = examService.findAllExams();
        if (exams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
    public ResponseEntity<List<Exam>> getExamsByStatus(@PathVariable("status") String status) {
        logger.info(Thread.currentThread().getId() + ":" + "getExamsByStatus" + "(" + status + ")");
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
        logger.info(Thread.currentThread().getId() + ":" + "createExam" + "(" + examDto + ")");
        Exam exam = examService.saveExam(examDto);
        emailService.sendExamAssignmentNotification(exam);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ResponseEntity<Integer> deleteExam(@PathVariable("id") Integer id) {
        logger.info(Thread.currentThread().getId() + ":" + "deleteExam" + "(" + id + ")");
        examService.deleteExam(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/take/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionEmsDto>> takeExam(@PathVariable("id") Integer id) {
        logger.info(Thread.currentThread().getId() + ":" + "takeExam" + "(" + id + ")");
        // check ID
        return examService.takeExam(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/submit/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exam> submitExam(@PathVariable("id") Integer id) {
        logger.info(Thread.currentThread().getId() + ":" + "submitExam" + "(" + id + ")");
        // check ID
        return examService.submitExam(id);
    }

    // change status to done + calculate wrong / correct / sum of answers

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/finish/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> finishExam(@PathVariable("id") Integer id) {
        logger.info(Thread.currentThread().getId() + ":" + "finishExam" + "(" + id + ")");
        // check ID
        return examService.finishExam(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exam> getExam(@PathVariable("id") Integer id) {
        logger.info(Thread.currentThread().getId() + ":" + "getExam" + "(" + id + ")");
        // check ID
        return new ResponseEntity<>(examService.findById(id).get(), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/review/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExamReviewDto> reviewExam(@PathVariable("id") Integer id) {
        logger.info(Thread.currentThread().getId() + ":" + "reviewExam" + "(" + id + ")");
        ExamReviewDto review = examService.reviewExam(id);
        return new ResponseEntity<ExamReviewDto>(review, HttpStatus.OK);
    }

}
