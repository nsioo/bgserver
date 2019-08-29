/*
 *
 *  *
 *  *  * Copyright (c) Shanghai Xing Ye, Co. Ltd.
 *  *  * https://bg.work
 *  *  *
 *  *  *This program is free software: you can redistribute it and/or modify
 *  *  *it under the terms of the GNU Affero General Public License as published by
t *  *  *he Free Software Foundation, either version 3 of the License.

 *  *  *This program is distributed in the hope that it will be useful,
 *  *  *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  *GNU Affero General Public License for more details.

 *  *  *You should have received a copy of the GNU Affero General Public License
 *  *  *along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *  *
  *
  */

package work.bg.server.chat

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.redis.client.Redis
import io.vertx.redis.client.RedisAPI
import io.vertx.redis.client.RedisOptions

object ModelClientHub {
    lateinit var vertx: Vertx
    private var sessionIDToSessionMap =  HashMap<String,ModelClientChatSession>()
    private var chatUUIDToSessionMap = HashMap<String,ModelClientChatSession>()
    fun getModelClientChatSessionBySessionID(chatSessionID:String,refreshTimeout:Boolean=true):ModelClientChatSession?{
        val mccs= this.sessionIDToSessionMap[chatSessionID]
        if(refreshTimeout){
            mccs?.let {
                mccs.refreshTimeout(chatSessionID)
            }
        }
        return mccs
    }

    fun getModelClientChatSessionByChatUUID(chatUUID:String,
                                  refreshTimeout: Boolean=true):ModelClientChatSession?{
        val mccs = this.chatUUIDToSessionMap[chatUUID]
        if(refreshTimeout){
            mccs?.let {
                mccs.refreshTimeout()
            }
        }
        return mccs
    }

    fun removeModelClientChatSession(chatSessionID: String){
        val mccs = this.getModelClientChatSessionBySessionID(chatSessionID,
                false)
        mccs?.let {
            this.sessionIDToSessionMap.remove(chatSessionID)
            mccs.removeDeviceSessionID(chatSessionID)
        }
    }
    ////Handler<AsyncResult<Redis>> onConnect
    fun getRegModelClientChatSessionFromRedis(chatSessionID: String,
                                              handler:Handler<AsyncResult<ModelClientChatSession>>){
        val options = RedisOptions()
        Redis.createClient(this.vertx, options)
                .connect { onConnect ->
                    if (onConnect.succeeded()) {
                        val client = onConnect.result()
                        val redisApi = RedisAPI.api(client)

                        redisApi.hgetall(chatSessionID){
                            if(it.succeeded()){
                                val resp = it.result()
                                val len = resp.size()
                                var corpID=null as Long?
                                var model = null as String?
                                var modelID=null as Long?
                                var devType=null as Int?
                                var channelMeta =null as String?
                                var chatUUID = null as String?
                                for(i in 0..len/2){
                                    val key = resp[i*2].toString()
                                    val value = resp[i*2+1]
                                    when (key) {
                                        "corpID" -> corpID = value?.toLong()
                                        "model" -> model=value?.toString()
                                        "modelID" -> modelID = value?.toLong()
                                        "devType" -> devType = value?.toInteger()
                                        "channelMeta" -> channelMeta = value?.toString()
                                        "chatUUID" ->chatUUID = value?.toString()
                                    }
                                }

                                if(corpID!=null && model!=null && modelID!=null && devType!=null && chatUUID!=null){
                                    var mccs = this.getModelClientChatSessionByChatUUID(chatUUID)
                                    if(mccs!=null){
                                        if(!mccs.hasDeviceChatSessionID(chatSessionID,devType)){
                                            mccs.addChatDeviceSessionID(chatSessionID,devType)
                                        }
                                        handler.handle(Future.succeededFuture(mccs))
                                    }
                                    else{
                                       mccs = ModelClientChatSession(ModelClientRedis(client,channelMeta),
                                               model,
                                               corpID,
                                               modelID,
                                               chatUUID,
                                               arrayListOf(ModelClientChatDeviceSessionID(devType,chatSessionID)
                                       ))
                                        mccs.vertx = this.vertx
                                        this.chatUUIDToSessionMap[chatUUID]=mccs
                                        this.sessionIDToSessionMap[chatSessionID]=mccs
                                        mccs.subscribeToRedis(Handler<AsyncResult<UInt>> {
                                            if(it.succeeded()){
                                                handler.handle(Future.succeededFuture(mccs))
                                            }
                                            else{
                                                handler.handle(Future.failedFuture(it.cause()))
                                            }
                                        })
                                    }
                                }
                                else{
                                    handler.handle(Future.failedFuture(resp.toString()))
                                }
                            }
                            else{
                                handler.handle(Future.failedFuture(it.cause()))
                            }
                        }
                    }
                    else{
                        handler.handle(Future.failedFuture(onConnect.cause()))
                    }
                }
    }
}