package com.wan37.gameServer.game.user.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.model.User;
import com.wan37.gameServer.game.user.service.UserService;
import com.wan37.gameServer.manager.cache.UserCacheMgr;

import com.wan37.mysql.pojo.entity.TPlayer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 14:25
 * @version 1.00
 * Description: mmorpg
 */
@Component
@Slf4j
public class ListGameRoleController implements IController {

    @Resource
    private UserService userService;

    @Resource
    private UserCacheMgr userCacheMgr;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split("\\s+");
        User user = userCacheMgr.get(ctx.channel().id().asLongText());
        List<TPlayer> tPlayerList = userService.findPlayers(user.getId());
        StringBuilder sb = new StringBuilder();

        tPlayerList.forEach( tPlayer -> {
            sb.append(tPlayer.toString());
            sb.append("\n");
        });
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
