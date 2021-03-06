package com.wan37.gameServer.game.bag.service;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/5 10:40.
 *  说明： 背包管理
 */

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.TypeReference;
import com.wan37.gameServer.game.bag.model.Bag;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.things.manager.ThingsCacheMgr;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.mysql.pojo.entity.TBag;
import com.wan37.mysql.pojo.entity.TBagExample;
import com.wan37.mysql.pojo.mapper.TBagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre> </pre>
 */

@Slf4j
@Service
public class BagsService {

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private ThingsCacheMgr thingsCacheMgr;

    @Resource
    private TBagMapper tBagMapper;


    /**
     *
     *  展示背包格子
     */
    public Map<Integer,Item> show(Player player) {

        return player.getBag().getItemMap();
    }


    /**
     *  获取物品的物品信息
     */
    public Things getThings(Player player, Integer locationIndex) {
        Bag bag = player.getBag();
        Item item = bag.getItemMap().get(locationIndex);
        if (item == null)
            return null;
        return thingsCacheMgr.get(item.getThings().getId());
    }


    /**
     *   从数据库加载背包
     */

    public void loadBag(Player player) {
        TBagExample tBagExample = new TBagExample();
        tBagExample.or().andPlayerIdEqualTo(player.getId());
        List<TBag> tBagList = tBagMapper.selectByExample(tBagExample);

        tBagList.forEach( tBag ->  {

            // 普通背包的加载
            if (tBag.getType() == 1) {
                log.debug("tBag  {}", tBag );

                Bag bag = new Bag(tBag.getPlayerId(),tBag.getBagSize());
                Map<Integer,Item> itemMap =  JSON.parseObject(tBag.getGoods(),
                        new TypeReference<Map<Integer,Item>>(){});


                if (null == itemMap)
                    bag.setItemMap(new LinkedHashMap<>());
                else
                    bag.setItemMap(itemMap);

                log.debug("itemMap {} ", itemMap );
                bag.setType(tBag.getType());
                bag.setBagName(tBag.getBagName());
                player.setBag(bag);

                log.debug("bag {} ", bag );
            }
        });
    }


    /**
     *  持久化背包数据
     */

    public void saveBag(Player player){
        TBag tBag = new TBag();
        Bag bag = player.getBag();
        tBag.setPlayerId(player.getId());
        tBag.setType(bag.getType());
        tBag.setBagName(bag.getBagName());
        tBag.setBagSize(bag.getBagSize());
        tBag.setGoods(JSON.toJSONString(bag.getItemMap()));

        if (tBagMapper.updateByPrimaryKeySelective(tBag) == 1) {
            log.debug("更新背包成功 {}",tBag);
        } else {
            tBagMapper.insertSelective(tBag);
            log.debug("保存背包成功 {}",tBag);
        }
    }





}
