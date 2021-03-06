package com.wan37.gameServer.game.scene.servcie;


import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameSceneObject.manager.GameObjectCacheMgr;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;

import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.gameSceneObject.model.NPC;
import com.wan37.gameServer.game.gameSceneObject.service.GameObjectService;
import com.wan37.gameServer.game.scene.model.GameScene;

import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 19:36
 * @version 1.00
 * Description: AOI(Area Of Interest)，中文就是感兴趣区域。通
 * 俗一点说，感兴趣区域就是玩家在场景实时看到的区域；也就是AOI会随着英雄的移动改变而改变。
 */
@Service
public class AOIService {

    @Resource
    private SceneCacheMgr sceneCacheMgr;

    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private GameObjectService gameObjectService;

    @Resource
    private PlayerCacheMgr playerCacheMgr;


    @Resource
    private PlayerDataService playerDataService;

    // 获取场景内的NPC
    public Map<Long,NPC> getNPCs(int sceneId) {
        GameScene gameScene = gameSceneService.findSceneById(sceneId);
        if (gameScene != null) {
            return gameScene.getNpcs();
        } else {
            return null;
        }
    }

    //  获取场景内怪物
    public Map<Long,Monster> getMonsters(Player player, int sceneId) {
        GameScene gameScene = gameSceneService.findSceneById(sceneId);
        // 如果玩家正处于副本中
        if (gameScene.getType() == 3 && player.getCurrentGameInstance() != null) {

            return player.getCurrentGameInstance().getMonsters();
        } else {
            return gameScene.getMonsters();
        }
    }



    public List<Player> getPlayerInScene(int sceneId) {
        List<Player> playerList = new ArrayList<>();
        GameScene gameScene = sceneCacheMgr.get(sceneId);
        Map<Long,Player> playerMap = gameScene.getPlayers();


        for (Long playerId: playerMap.keySet())  {
            ChannelHandlerContext ctx =   playerCacheMgr.getCxtByPlayerId(playerId);
            Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
            playerList.add(player);
        }

        return playerList;
    }

}
