package com.yueguang.dao;

import java.util.List;

import com.yueguang.model.QuestiontoPlan;

public interface QuestiontoPlanDao {
    public void inserQtP(QuestiontoPlan questiontoPlan);
    public void updateQtP(QuestiontoPlan questiontoPlan);
    
    public void deleteQtP(int qtpid);
    
    public List<QuestiontoPlan> getQtPbyQuestionid(int questionid);
    
    public List<QuestiontoPlan> getQtPbyPlanid(int planid);
}
