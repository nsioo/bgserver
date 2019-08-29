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

package work.bg.server.core.spring.boot.annotation

import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.method.HandlerMethod
import work.bg.server.core.constant.ServletRequestAttributeTag
import work.bg.server.core.constant.SessionTag
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import work.bg.server.errorcode.ErrorCode
import work.bg.server.errorcode.jsonFormat
import work.bg.server.errorcode.reLogin

class ShouldLoginInterceptor constructor(private val unauthRedirectUrl:String?): HandlerInterceptorAdapter() {

    override  fun preHandle(request: HttpServletRequest,
                  response: HttpServletResponse,
                  handler: Any): Boolean {
        if(handler is HandlerMethod){
            val rm = (handler as HandlerMethod).getMethodAnnotation(
                    ShouldLogin::class.java)
            val partnerID=request.session
                    .getAttribute(SessionTag.SESSION_PARTNER_CACHE_KEY)
           // request.setAttribute(ServletRequestAttributeTag.PARTNER_CONTEXT_TAG,partner_ctx)
            if(rm!=null) if(partnerID== null){
                //  ErrorCode::
                response.contentType="application/json"
                response.characterEncoding="utf-8"
                response.status=200
                response.writer.print(ErrorCode.RELOGIN.reLogin(this.unauthRedirectUrl))
                response.writer.close()
                return false
            }
        }
        return true
    }
}