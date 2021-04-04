package com.livegoods.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    private String id;
    private String title;
    private String img;
    private Long sales; //销售数量
    private Boolean recommendation; //是否推荐
    private int recommendationSort; //推荐排序
    private String city;

    public String getLink(){
        return "/items/" + id;
    }

    // 空处理，占位使用，避免Jackson做反向序列化抛出异常。
    public void setLink(String link){}

}
