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

package work.bg.server.product.model

import work.bg.server.core.RefSingleton
import work.bg.server.core.model.ContextModel
import work.bg.server.core.mq.FieldPrimaryKey
import work.bg.server.core.mq.FieldType
import work.bg.server.core.mq.ModelField
import work.bg.server.core.mq.ModelOne2OneField
import work.bg.server.core.spring.boot.annotation.Model

@Model(name="productSKUPattern")
class ProductSKUPattern:ContextModel("product_sku_pattern","public") {
    companion object : RefSingleton<ProductSKUPattern> {
        override lateinit var ref: ProductSKUPattern
    }
    val id= ModelField(null,
            "id",
            FieldType.BIGINT,
            "标示",
            primaryKey = FieldPrimaryKey())

    val name=ModelField(null,
            "name",
            FieldType.STRING,
            "名称")

    val skuPattern = ModelField(null,
            "sku_pattern",
            FieldType.STRING,
            "Sku模式")

    val skuCount= ModelField(null,
            "sku_count",
            FieldType.INT,
            "Sku数量")
    val product = ModelOne2OneField(null,
            "product_id",
            FieldType.BIGINT,
            "产品",
            targetModelTable = "public.product_product",
            targetModelFieldName = "id")
}