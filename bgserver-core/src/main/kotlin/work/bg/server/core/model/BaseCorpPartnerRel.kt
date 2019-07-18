

/*
 *
 *  *
 *  *  * Copyright (c) Shanghai Xing Ye, Co. Ltd.
 *  *  * https://bg.work
 *  *  *
 *  *  * GNU Lesser General Public License Usage
 *  *  * Alternatively, this file may be used under the terms of the GNU Lesser
 *  *  * General Public License version 3 as published by the Free Software
 *  *  * Foundation and appearing in the file LICENSE.txt included in the
 *  *  * project of this file. Please review the following information to
 *  *  * ensure the GNU Lesser General Public License version 3 requirements
 *  *  * will be met: https://www.gnu.org/licenses/lgpl-3.0.html.
 *  *
 *
 */

package work.bg.server.core.model

import work.bg.server.core.RefSingleton
import work.bg.server.core.acrule.inspector.ModelFieldInspector
import work.bg.server.core.acrule.inspector.ModelFieldMustCoexist
import work.bg.server.core.acrule.inspector.ModelFieldRequired
import work.bg.server.core.acrule.inspector.ModelFieldUnique
import work.bg.server.core.mq.*
import work.bg.server.core.model.billboard.CurrCorpBillboard
import work.bg.server.core.model.billboard.CurrPartnerBillboard
import work.bg.server.core.spring.boot.annotation.Model

@Model("corpPartnerRel")
class BaseCorpPartnerRel(table:String,schema:String):ContextModel(table,schema) {
    companion object :RefSingleton<BaseCorpPartnerRel>{
        override lateinit var ref: BaseCorpPartnerRel
    }
    val id=ModelField(null,
            "id",
            FieldType.BIGINT,
            "标识",
            primaryKey = FieldPrimaryKey())
    val corp=ModelMany2OneField(null,
            "corp_id",
            FieldType.BIGINT,
            "公司",
            "public.base_corp",
            "id",
            defaultValue = CurrCorpBillboard(),
            foreignKey= FieldForeignKey(action =ForeignKeyAction.CASCADE))
    val partner=ModelMany2OneField(null,
            "partner_id",
            FieldType.BIGINT,
            "用户",
            "public.base_partner",
            "id",
            defaultValue = CurrPartnerBillboard(),
            foreignKey = FieldForeignKey(action = ForeignKeyAction.CASCADE))
    val partnerRole=ModelMany2OneField(null,
            "partner_role_id",
            FieldType.BIGINT,"角色",
            "public.base_partner_role",
            "id",
            foreignKey = FieldForeignKey(action=ForeignKeyAction.CASCADE))
    val isDefaultCorp=ModelField(null,
            "is_default_corp",
            FieldType.INT,
            " 默认公司",
            null,
            0)
    constructor():this("base_corp_partner_rel","public")

    override fun skipCorpIsolationFields(): Boolean {
        return true
    }

    override fun isAssociative(): Boolean {
        return true
    }


    override fun maybeCheckACRule(): Boolean {
        return false
    }

    override fun getModelCreateFieldsInStoreInspectors(): Array<ModelFieldInspector>? {
        return arrayOf(ModelFieldUnique(corp,partner,advice = "用戶角色必須唯一",
                isolationType = ModelFieldUnique.IsolationType.IN_GLOBAL))
    }

    override fun getModelEditFieldsInStoreInspectors(): Array<ModelFieldInspector>? {
        return arrayOf(ModelFieldUnique(corp,partner,advice = "用戶角色必須唯一",
                isolationType = ModelFieldUnique.IsolationType.IN_GLOBAL))
    }

    override fun getModelEditFieldsInspectors(): Array<ModelFieldInspector>? {
        return arrayOf(ModelFieldRequired(this.corp,this.partner,this.partnerRole,advice = "缺少必要的字段"),
                ModelFieldMustCoexist(partnerRole,isDefaultCorp,advice = "必須选择用户角色"))
    }

    override fun getModelCreateFieldsInspectors(): Array<ModelFieldInspector>? {
        return arrayOf(ModelFieldRequired(this.corp,this.partner,this.partnerRole,advice = "缺少必要的字段"),
                ModelFieldMustCoexist(partnerRole,isDefaultCorp,advice = "必須选择用户角色"))
    }
}