package com.wan37.gameServer.game.gameRole.service;

import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.manager.BufferCacheMgr;
import com.wan37.gameServer.manager.task.TaskManager;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/11 10:44
 * @version 1.00
 * Description: buffer管理服务
 */

@Service
public class BufferService {


    @Resource
    private TaskManager taskManager;

    @Resource
    private BufferCacheMgr bufferCacheMgr;


    public boolean startBuffer(Player player, Buffer buffer) {

        if (player != null ) {
            // 记录开始时间
            buffer.setStartTime(System.currentTimeMillis());
            player.getBufferList().add(buffer);

            if (buffer.getDuration() != -1) {
                taskManager.schedule(buffer.getDuration(),
                        () -> {
                            // 过期移除buffer
                            player.getBufferList().remove(buffer);
                            return null;
                        });
            }
            return true;
        } else {
            return false;
        }
    }



    public Buffer getTBuffer(int tBufferId) {
        return bufferCacheMgr.get(tBufferId);
    }


    public boolean removeBuffer(Player player, Buffer tBuffer) {
        if (player != null && tBuffer != null) {
            player.getBufferList().remove(tBuffer);
            return true;
        } else {
            return false;
        }
    }

}
