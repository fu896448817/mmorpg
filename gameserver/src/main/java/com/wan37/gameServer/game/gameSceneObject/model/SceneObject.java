package com.wan37.gameServer.game.sceneObject.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/30 23:39.
 *  说明：
 */

import com.wan37.gameServer.model.Creature;
import com.wan37.gameServer.model.IExcel;
import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

/**
 * <pre> </pre>
 */

@Data
public class SceneObject extends Creature implements IExcel<Long>  {

    @EntityName(column = "id")
    private Long id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "hp")
    private Long hp;

    @EntityName(column = "mp")
    private Long mp;

    @EntityName(column = "交谈文本")
    private String talk;

    @EntityName(column = "技能")
    private String skills;

    @EntityName(column = "状态")
    private Integer state;

    @EntityName(column = "角色类型")
    private Integer roleType;

    @EntityName(column = "刷新时间")
    private Long refreshTime;

    @EntityName(column = "掉落")
    private String drop;



    // 死亡时间
    private long deadTime;


    public  Long getDeadTime() {
        return deadTime;
    }
    public  void  setDeadTime(Long deadTime){
        this.deadTime = deadTime;
    }

    @Override
    public Long getKey() {
        return id;
    }
}
