/*
 *
 *  *
 *  *  *
 *  *  *  * Copyright (c) Shanghai Xing Ye, Co. Ltd.
 *  *  *  * https://bg.work
 *  *  *  *
 *  *  *  *This program is free software: you can redistribute it and/or modify
 *  *  *  *it under the terms of the GNU Affero General Public License as published by
 * t *  *  *he Free Software Foundation, either version 3 of the License.
 *
 *  *  *  *This program is distributed in the hope that it will be useful,
 *  *  *  *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  *  *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  *  *GNU Affero General Public License for more details.
 *
 *  *  *  *You should have received a copy of the GNU Affero General Public License
 *  *  *  *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *   *  *
 *   *
 *
 */

package dynamic.model.query.mq

import dynamic.model.query.mq.model.ModelBase
import org.apache.commons.logging.LogFactory

class CreateStatement(vararg val fieldValues: FieldValue, val model: ModelBase): dynamic.model.query.mq.ModelExpression(){
    private  val logger = LogFactory.getLog(javaClass)
    override fun accept(visitor: ModelExpressionVisitor, parent: ModelExpression?): Boolean {
        visitor.visit(this,parent)
        return true
    }

    override fun render(parent: ModelExpression?): Pair<String, Map<String, FieldValue>>? {
        var render= ModelCriteriaRender()
        this.accept(render,parent)
        return Pair(render.namedSql.toString(),render.namedParameters)
    }
}

