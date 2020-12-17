package com.tickoff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickoff")
public class IndexController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping(value="/test",method= RequestMethod.GET)
    public List<Map<String,Object>> getTest(){
        String sql="select * from user_mset";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }
}
