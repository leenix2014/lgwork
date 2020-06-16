package com.lagou.edu.controller;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ResumeController {

    @Autowired
    private ResumeDao resumeDao;

    @RequestMapping("/list")
    public String list(Map<String, Object> model) {
        List<Resume> list = resumeDao.findAll();
        model.put("list", list);
        return "list";
    }

    @RequestMapping("/detail")
    public String detail(Map<String, Object> model, Long id) {
        if(id == null){
            return "detail";
        }
        Optional<Resume> detail = resumeDao.findById(id);
        if (detail.isEmpty()){
            return "redirect:/list";
        }
        Resume resume = detail.get();
        model.put("resume", resume);
        return "detail";
    }

    @RequestMapping("/save")
    public String save(Resume resume) {
        resumeDao.saveAndFlush(resume);
        return "redirect:/list";
    }

    @RequestMapping("/delete")
    public String delete(Long id) {
        resumeDao.deleteById(id);
        return "redirect:/list";
    }
}
