package com.yueguang.daoImpl;

import java.util.List;

import com.yueguang.dao.BaseDao;
import com.yueguang.dao.QuestionDao;
import com.yueguang.model.Question;

public class QuestionDaoImpl implements QuestionDao {
    BaseDao baseDao;
    
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void insertQuestion(Question question) {
        baseDao.save(question);
	}

	public List<Question> getAllQuestions() {
		return baseDao.getAllList(Question.class);
	}

	public Question getQuestionById(int questionid) {
		return (Question) baseDao.load(Question.class, questionid);
	}

	public void deleteByQuestionid(int questionid) {
	    baseDao.delete(Question.class, questionid);
	}

}
