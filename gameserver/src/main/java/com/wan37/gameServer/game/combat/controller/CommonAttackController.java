package com.wan37.gameServer.game.combat.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;

import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 14:29
 * @version 1.00
 * Description: 攻击服务
 */


@Controller
public class CommonAttackController implements IController {


    @Resource
    private CombatService combatService;

    @Resource
    private PlayerDataService playerDataService;


    @Resource
    private NotificationManager notificationManager;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Long gameObjectId = Long.valueOf(command[1]);

        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        Msg msg = combatService.playerCommonAttack(player,gameObjectId);

        message.setFlag((byte) 1);
        message.setContent(msg.getMsg().getBytes());
        ctx.writeAndFlush(message);
    }
}
