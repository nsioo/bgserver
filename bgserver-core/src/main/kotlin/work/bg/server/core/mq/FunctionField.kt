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

import work.bg.server.core.cache.PartnerCache

open class FunctionField<T>(model:ModelBase?,
                            name:String,
                            fieldType:FieldType,
                            title:String?,
                            val comp:((FieldValueArray,PartnerCache?,Any?)->T?)?=null,
                            val inv:((FieldValueArray, PartnerCache?, T?, Any?)->Unit)?=null,
                            open val depFields: Array<FieldBase?>?=null):FieldBase(name,title,fieldType,model) {
    open  fun  compute(fieldValueArray:FieldValueArray,partnerCache: PartnerCache?,data:Any?):T?{
            return this.comp?.invoke(fieldValueArray,partnerCache,data)
    }

    open fun  inverse(fieldValueArray:FieldValueArray, partnerCache: PartnerCache?, value:T?, data:Any?){
       this.inv?.invoke(fieldValueArray,partnerCache,value,data)
    }
}