package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.Question;

public interface QuestionDao {
     public  void  insertQuestion(Question question);
     
     public  List<Question> getAllQuestions();  
     
     public Question getQuestionById(int questionid);
     
     public void deleteByQuestionid(int questionid);
     
}
