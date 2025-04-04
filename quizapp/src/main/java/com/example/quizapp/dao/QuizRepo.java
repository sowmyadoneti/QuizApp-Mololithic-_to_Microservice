package com.example.quizapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapp.model.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz,Integer>{

}
