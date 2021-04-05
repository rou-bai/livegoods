package com.livegoods.comments.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Comments;

public interface CommentsService {
    /*
    根据房屋id查询评论
     */
    Result<Comments> getCommentsByHouseId(String houseId, int page);
}
