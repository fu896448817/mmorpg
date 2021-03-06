package com.wan37.gameServer.game.things.model;

import com.wan37.gameServer.model.IExcel;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:07
 * @version 1.00
 * Description: 物品，包括在装备栏的装备，在背包的物品
 */

@Data
public class Things implements IExcel<Integer> {


    @EntityName(column="物品编号")
    private Integer  id;

    @EntityName(column="物品名称")
    private String  name;

    @EntityName(column="触发效果")
    private Integer  buffer;

    @EntityName(column="能否绑定")
    private Integer isBind;

    @EntityName(column = "种类")
    private Integer kind;

    @EntityName(column = "属性")
    private String roleProperties;

    @EntityName(column = "部位")
    private String part;

    @EntityName(column = "描述")
    private String describe;


    // 物品状态，1为已装备，2为未装备
    private Integer state;

    // 物品属性
    private Set<RoleProperty> thingRoleProperty = new HashSet<>();





    @Override
    public Integer getKey() {
        return id;
    }
}
