package com.example.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.dao.QuestionRepo;
import com.example.quizapp.dao.QuizRepo;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;

@Service
public class QuizService {

	@Autowired
	QuestionRepo questionRepo;
	
	@Autowired
	QuizRepo quizRepo;
	
	public ResponseEntity<String> createquiz(String category, int numQ, String title) {
		
		List<Question> questions=questionRepo.findRandomQuestionsByCategory(category,numQ);
		
		Quiz quiz=new Quiz();
		quiz.setCategory(category);
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizRepo.save(quiz);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
		Optional<Quiz> quiz= quizRepo.findById(id);
		List<Question> questionsfromdb = quiz.get().getQuestions();
		List<QuestionWrapper> questionsforuser=new ArrayList<>();
		for(Question q:questionsfromdb) {
			QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
			questionsforuser.add(qw);
		}
		return new ResponseEntity<>(questionsforuser,HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		 Quiz quiz=quizRepo.findById(id).get();
		 List<Question> questions=quiz.getQuestions();
		 int right=0;
		 int i=0;
		 for(Response response:responses) {
			 if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
				 right++;
			 }
			 i++;
		 }
		 return new ResponseEntity<>(right, HttpStatus.OK);
	}

}
