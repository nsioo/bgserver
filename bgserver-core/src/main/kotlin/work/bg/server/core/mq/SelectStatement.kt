

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

package work.bg.server.core.mq

import work.bg.server.core.mq.aggregation.*
import work.bg.server.core.mq.join.JoinModel


class SelectStatement constructor(vararg val selectFields:FieldBase,val fromModel:ModelBase):ModelExpression(){
        var expression:ModelExpression?=null
        var joinModels:MutableList<JoinModel>?=null
        var groupBy:GroupBy?=null
        var orderBy:OrderBy?=null
        var offset:Int?=null
        var limit:Int?=null
        var countExpression:CountExpression?=null
        var avgExpressions:Array<out AvgExpression>?=null
        var maxExpressions:Array<out MaxExpression>?=null
        var minExpressions:Array<out MinExpression>?=null
        var sumExpressions:Array<out SumExpression>?=null
        fun count(countExpression:CountExpression?=null):SelectStatement{
            if(countExpression!=null){
                this.countExpression=countExpression
            }
            else{
                this.countExpression=CountExpression(null)
            }
            return this
        }
        fun avg(vararg avgExpression:AvgExpression):SelectStatement{
            this.avgExpressions=avgExpression
            return this
        }
        fun max(vararg maxExpression:MaxExpression):SelectStatement{
            this.maxExpressions=maxExpression
            return this
        }
        fun min(vararg minExpression: MinExpression):SelectStatement{
            this.minExpressions=minExpression
            return this
        }
        fun sum(vararg sumExpression:SumExpression):SelectStatement{
            this.sumExpressions=sumExpression
            return this
        }
        fun where(expression: ModelExpression?):SelectStatement{
            this.expression=expression
            return this
        }

        fun join(joinModel: JoinModel?):SelectStatement{
            if (this.joinModels==null){
              this.joinModels= mutableListOf<JoinModel>()
            }
            if(joinModel!=null){
                this.joinModels!!.add(joinModel)
            }
            return this
        }

        fun orderBy(orderBy:OrderBy?):SelectStatement{
            this.orderBy= orderBy
            return this
        }

        fun groupBy(groupBy:GroupBy?):SelectStatement{
            this.groupBy=groupBy;
            return this
        }
        fun offset(offset:Int?):SelectStatement{
            this.offset=offset
            return this
        }
        fun limit(limit:Int?):SelectStatement{
            this.limit=limit
            return this
        }
        override fun render(parent:ModelExpression?):Pair<String,Map<String,FieldValue>>?{
            var render=ModelCriteriaRender()
            this.accept(render,parent)
            return Pair(render.namedSql.toString(),render.namedParameters)
        }

        override fun accept(visitor: ModelExpressionVisitor,parent:ModelExpression?): Boolean {
            // TODO("not implemented")
            // To change body of created functions use File | Settings | File Templates.
            visitor.visit(this,parent)
            return true
        }
}