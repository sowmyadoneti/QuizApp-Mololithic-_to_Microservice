package com.example.question_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.question_service.model.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer>{

	List<Question> findByCategory(String category);

	
	@Query(value="SELECT q.id FROM question q where q.category=:categoryName ORDER BY RANDOM() LIMIT :numQuestions", nativeQuery=true)
	List<Integer> findRandomQuestionsByCategory(String categoryName, int numQuestions);

}
