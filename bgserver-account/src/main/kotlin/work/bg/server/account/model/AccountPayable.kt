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

package work.bg.server.account.model

import com.google.gson.JsonObject
import dynamic.model.query.mq.*
import dynamic.model.web.errorcode.ErrorCode
import dynamic.model.web.spring.boot.annotation.Action
import work.bg.server.core.model.ContextModel
import dynamic.model.web.spring.boot.annotation.Model
import dynamic.model.web.spring.boot.model.ActionResult
import org.springframework.web.bind.annotation.RequestBody
import work.bg.server.core.cache.PartnerCache
import work.bg.server.core.model.field.EventLogField
import work.bg.server.crm.model.CustomerOrderInvoice

//应付款
@Model("accountPayable","应付")
class AccountPayable:ContextModel("account_payable","public") {
    companion object: RefSingleton<AccountPayable> {
        override lateinit var ref: AccountPayable
    }

    val id= ModelField(null,
            "id",
            FieldType.BIGINT,
            "标识",
            primaryKey = FieldPrimaryKey())

    val comment = ModelField(null,
            "comment",
            FieldType.STRING,
            "注释")

    val orderInvoice= ModelOne2OneField(null,
            "order_invoice_id",
            FieldType.BIGINT,
            "发票",
            targetModelTable = "public.crm_customer_order_invoice",
            targetModelFieldName = "id",
            foreignKey = FieldForeignKey(action = ForeignKeyAction.SET_NULL))
    val eventLogs = EventLogField(null,"event_logs","跟踪日志")

    @Action("confirmPayable")
    fun confirmPayable(@RequestBody data: JsonObject?,
                          partnerCache: PartnerCache):Any?{
        var ar= ActionResult()
        val modelID = data?.get("modelID")?.asLong
        modelID?.let {
            var v = this.rawRead(criteria = eq(this.id,modelID))?.firstOrNull()
            v?.let {
                val id = ModelDataObject.getModelDataObjectID(it.getFieldValue(this.orderInvoice))
                id?.let {
                    var o = ModelDataObject(model= CustomerOrderInvoice.ref)
                    o.setFieldValue(CustomerOrderInvoice.ref.id,id)
                    o.setFieldValue(AccountCustomerOrderInvoice.ref.status,1)
                    CustomerOrderInvoice.ref.rawEdit(o,
                            partnerCache = partnerCache,
                            useAccessControl = true)
                    ar.errorCode = ErrorCode.SUCCESS
                    return ar
                }
            }
        }
        ar.errorCode= ErrorCode.UNKNOW
        return ar
    }

    @Action("dropPayable")
    fun dropPayable(@RequestBody data: JsonObject?,
                       partnerCache: PartnerCache):Any?{
        var ar= ActionResult()
        val modelID = data?.get("modelID")?.asLong
        modelID?.let {
            var v = this.rawRead(criteria = eq(this.id,modelID))?.firstOrNull()
            v?.let {
                val id = ModelDataObject.getModelDataObjectID(it.getFieldValue(this.orderInvoice))
                id?.let {
                    var o = ModelDataObject(model= CustomerOrderInvoice.ref)
                    o.setFieldValue(CustomerOrderInvoice.ref.id,id)
                    o.setFieldValue(AccountCustomerOrderInvoice.ref.status,0)
                    CustomerOrderInvoice.ref.rawEdit(o,
                            partnerCache = partnerCache,
                            useAccessControl = true)
                    ar.errorCode = ErrorCode.SUCCESS
                    return ar
                }
            }
        }
        ar.errorCode= ErrorCode.UNKNOW
        return ar
    }
}