

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

package work.bg.server.core.mq.condition

import work.bg.server.core.mq.FieldBase
import work.bg.server.core.mq.ModelExpression
import work.bg.server.core.mq.ModelExpressionVisitor

class IsNotExpression(val field:FieldBase,val value:Any?=null):ModelExpression(){
    override fun accept(visitor: ModelExpressionVisitor,parent:ModelExpression?): Boolean {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        visitor.visit(this,parent)
        return true
    }

    override fun render(parent:ModelExpression?): Pair<String, Map<String, Any?>>? {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }
}