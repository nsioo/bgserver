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

package work.bg.server.core
import org.apache.commons.logging.LogFactory
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.Charset
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponseWrapper


class ResetRequestFilter:Filter {
    private val skipUrls:ArrayList<String> = arrayListOf(
            "/storage/file",
            "/storage/upload"
    )

    private  val logger = LogFactory.getLog(javaClass)
    private fun isSkipUrl(uri:String):Boolean{
        skipUrls.forEach {
            if(uri.contains(it,true)){
                return true
            }
        }
        return false
    }
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        if(!this.isSkipUrl((request as HttpServletRequest).requestURI)){
            val req = ResetRequestWrapper(request as HttpServletRequest)
            val res = ResetResponseWrapper(response as HttpServletResponse)
            chain?.doFilter(req, res)
//            val reqBody = req.bodyText
//            val resBody = res.bodyText
        }
        else{
            chain?.doFilter(request, response)
        }
    }
}