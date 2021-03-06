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

package work.bg.server.core.model

import com.google.gson.JsonObject
import dynamic.model.query.mq.*
import dynamic.model.query.mq.model.ModelBase
import dynamic.model.web.spring.boot.annotation.Model
import dynamic.model.web.spring.boot.model.ActionResult
import dynamic.model.web.spring.boot.model.ReadActionParam
import work.bg.server.core.cache.PartnerCache
import work.bg.server.core.ui.ModelView
import kotlin.reflect.KClass

@Model(name = "corp", title="公司")
class BaseCorp(table:String, schema:String):ContextModel(table,schema){
  companion object: RefSingleton<BaseCorp> {
    override lateinit var ref: BaseCorp
  }

  constructor():this("base_corp","public")

//  override fun <T : ModelBase> getModelFields(overrideBaseCls: KClass<T>?): FieldCollection {
//    return super.getModelFields(AccessControlModel::class)
//  }

  override fun corpIsolationFields(): Array<ModelField>? {
    return null
  }

  val id= ModelField(null, "id", FieldType.BIGINT, "标识", primaryKey = FieldPrimaryKey())
  val name= ModelField(null, "name", FieldType.STRING, "名称")
  val website= ModelField(null, "website", FieldType.STRING, "网站")
  val address= ModelField(null, "address", FieldType.STRING, "地址")
  val telephone= ModelField(null, "telephone", FieldType.STRING, "电话")
  val fax= ModelField(null, "fax", FieldType.STRING, "传真")
  val comment= ModelField(null, "comment", FieldType.TEXT, "注释")
  val partners= ModelMany2ManyField(null, "partner_id", FieldType.BIGINT, "用户", "public.base_corp_partner_rel", "partner_id",
          "base_partner", "id")
  val partnerRoles= ModelOne2ManyField(null,
          "m_corp_id",
          FieldType.BIGINT, "角色",
          "public.base_partner_role",
          "corp_id")

  override fun listAction(param: ReadActionParam, pc: PartnerCache): ActionResult? {
    return null
  }

  override fun createAction(modelData: ModelData?, pc: PartnerCache): ActionResult? {
    return null
  }

  override fun editAction(modelData: ModelData?, pc: PartnerCache): ActionResult? {
    return null
  }

  override fun deleteAction(data: JsonObject, pc: PartnerCache): ActionResult? {
    return null
  }
  override fun acCount(criteria: ModelExpression?, partnerCache: PartnerCache): Int {
    return 0
  }

  override fun acDelete(criteria: ModelExpression?, partnerCache: PartnerCache?): Pair<Long?, String?> {
    return Pair(0,null)
  }

  override fun acEdit(modelData: ModelData, criteria: ModelExpression?, partnerCache: PartnerCache): Pair<Long?, String?> {
    return Pair(0,null)
  }

  override fun acDelete(modelData: ModelData, criteria: ModelExpression?, partnerCache: PartnerCache?): Pair<Long?, String?> {
    return  return Pair(0,null)
  }

  override fun acMax(field: FieldBase, partnerCache: PartnerCache, criteria: ModelExpression?): Long? {
    return null
  }

  override fun loadModelViewType(data: JsonObject, pc: PartnerCache): ActionResult {
    return ActionResult()
  }
}
