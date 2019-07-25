package com.example.demo.service;


import ch.qos.logback.core.db.dialect.DBUtil;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionPageDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//组装user和question时的中间层
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public QuestionPageDTO List(Integer currentPage, Integer size) {
        QuestionPageDTO questionPageDTO = new QuestionPageDTO();
        //先获取记录总数
        Integer totalCount = questionMapper.count();
        //计算并设置总页数
        questionPageDTO.setTotalPage(totalCount,size);
        //计算并设置当前页的页码
        questionPageDTO.setCurrentPage(currentPage,questionPageDTO.getTotalPage());
        Integer offset=size*(questionPageDTO.getCurrentPage()-1);
        List<Question> questionList=questionMapper.List(offset,size);
        List<QuestionDTO> questionDTOS=new ArrayList<QuestionDTO>();
        for (Question question:questionList
             ) {
            User user=userMapper.finById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        questionPageDTO.setQuestionDTOS(questionDTOS);
        questionPageDTO.setShowPrevious(questionPageDTO.getCurrentPage());
        questionPageDTO.setShowFirstPage(questionPageDTO.getCurrentPage());
        questionPageDTO.setShowNext(questionPageDTO.getCurrentPage(),questionPageDTO.getTotalPage());
        questionPageDTO.setShowEndPage(questionPageDTO.getCurrentPage(),questionPageDTO.getTotalPage());
        questionPageDTO.setPage(questionPageDTO.getShowListSize(),questionPageDTO.getCurrentPage(),questionPageDTO.getTotalPage());
        return questionPageDTO;

    }

    public QuestionPageDTO list(Integer userId, Integer currentPage, Integer size) {
        QuestionPageDTO questionPageDTO = new QuestionPageDTO();
        //先获取记录总数
        Integer totalCount = questionMapper.countById(userId);
        //计算并设置总页数
        questionPageDTO.setTotalPage(totalCount,size);
        //计算并设置当前页的页码
        questionPageDTO.setCurrentPage(currentPage,questionPageDTO.getTotalPage());
        Integer offset=size*(questionPageDTO.getCurrentPage()-1);
        List<Question> questionList=questionMapper.ListById(userId,offset,size);
        List<QuestionDTO> questionDTOS=new ArrayList<QuestionDTO>();
        for (Question question:questionList) {
            User user=userMapper.finById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        questionPageDTO.setQuestionDTOS(questionDTOS);
        questionPageDTO.setShowPrevious(questionPageDTO.getCurrentPage());
        questionPageDTO.setShowFirstPage(questionPageDTO.getCurrentPage());
        questionPageDTO.setShowNext(questionPageDTO.getCurrentPage(),questionPageDTO.getTotalPage());
        questionPageDTO.setShowEndPage(questionPageDTO.getCurrentPage(),questionPageDTO.getTotalPage());
        questionPageDTO.setPage(questionPageDTO.getShowListSize(),questionPageDTO.getCurrentPage(),questionPageDTO.getTotalPage());
        return questionPageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.finById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
