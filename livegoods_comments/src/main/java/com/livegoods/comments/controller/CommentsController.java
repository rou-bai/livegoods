package com.livegoods.comments.controller;

import com.livegoods.comments.service.CommentsService;
import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @RequestMapping("/comment")
    public Result<Comments> getComment(String id, int page){
        return commentsService.getCommentsByHouseId(id, page);

    }
}
