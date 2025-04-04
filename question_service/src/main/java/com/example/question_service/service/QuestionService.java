package com.example.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.question_service.dao.QuestionRepo;
import com.example.question_service.model.Question;
import com.example.question_service.model.QuestionWrapper;
import com.example.question_service.model.Response;

@Service
public class QuestionService {

	@Autowired
	QuestionRepo questionRepo;
	
	
	
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionRepo.findAll(),HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
		try {
			return new ResponseEntity<>(questionRepo.findByCategory(category),HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		questionRepo.save(question);
		return new ResponseEntity<>("Success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<Integer>> generateQuestions(String categoryName, int numQuestions) {
		List<Integer> questions=questionRepo.findRandomQuestionsByCategory(categoryName,numQuestions);
		return new ResponseEntity<>(questions,HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> questionIds) {
		List<QuestionWrapper> qw=new ArrayList<>();
		for(Integer i:questionIds) {
			Question q=questionRepo.findById(i).get();
			QuestionWrapper wrapper=new QuestionWrapper();
			wrapper.setId(q.getId());
			wrapper.setQuestionTitle(q.getQuestionTitle());
			wrapper.setOption1(q.getOption1());
			wrapper.setOption2(q.getOption2());
			wrapper.setOption3(q.getOption3());
			wrapper.setOption4(q.getOption4());
			qw.add(wrapper);
		}
		return new ResponseEntity<>(qw,HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		
		int right=0;
		
		for(Response response:responses) {
			Question question=questionRepo.findById(response.getId()).get();
			if(response.getResponse().equals(question.getRightAnswer())) {
				right++;
			}
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
	

}
