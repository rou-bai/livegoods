package com.livegoods.comments.service.impl;

import com.livegoods.comments.dao.CommentsDao;
import com.livegoods.comments.service.CommentsService;
import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据脱敏，可以在多个位置实现。
 * 1、 数据库端。 可选。如果敏感数据，永远不会作为任何查询条件出现，可以使用。其他情况不推荐使用。
 * 2、 后端代码逻辑。 推荐，通过代码实现脱敏，可以保证原数据可靠，脱敏数据自定义。
 * 3、 前端显示逻辑。 不推荐。因为名为已经通过网络传递，容易丢失敏感数据。
 */

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsDao commentsDao;
    @Value("${livegoods.comments.defaultRows}")
    private int defaultRows;

    public Result<Comments> getCommentsByHouseId(String houseId, int page){
        List<Comments> comments = commentsDao.findCommentsByHouseId(houseId, page, defaultRows);
        //数据脱敏
        for(Comments comment:comments){
            String username = comment.getUsername();
            username = username.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            comment.setUsername(username);
        }

        //总数据行数
        long totalRows = commentsDao.findCommentsRowsByHouseId(houseId);
        long pages = (totalRows % defaultRows == 0) ? (totalRows / defaultRows) : ((totalRows / defaultRows) + 1);
        Result<Comments> result = new Result<>();
        result.setResults(comments);
        result.setStatus(200);
        result.setHasMore((pages - page) > 1);
        return result;
    }


}
