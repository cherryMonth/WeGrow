package com.wegrow.wegrow.demo.controller;

import com.wegrow.wegrow.demo.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(tags = "CommentController")
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
}
