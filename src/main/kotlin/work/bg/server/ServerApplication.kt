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

package work.bg.server

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import work.bg.server.core.runServer
import work.bg.server.core.spring.boot.annotation.ModelScan
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer
import org.springframework.context.annotation.Bean
import java.util.*


@SpringBootApplication
@ModelScan(packageNames = ["work.bg.server.core.model",
    "work.bg.server.corp.model",
    "work.bg.server.product.model",
    "work.bg.server.crm.model",
    "work.bg.server.account.model",
    "work.bg.server.worktable.model",
    "work.bg.server.setting.model",
    "work.bg.server.sms.model",
    "work.bg.server.chat.model"])
class ServerApplication{
    private val logger = LogFactory.getLog(javaClass)
    init {
        logger.info("bg.work server start ${Date()}")
    }
}

fun main(args: Array<String>){
    runServer<ServerApplication>(*args)
}