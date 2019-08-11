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

package work.bg.server.util

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.junit.Test
import java.util.*

class MD5Test : StringSpec({
    var mdHash=MD5.hash("123456")
    mdHash shouldBe "e10adc3949ba59abbe56e057f20f883e"
})

class MD5Test2{
    init {
        print("start test ${Date()}")
    }
    @Test
    fun doTest(){
        var mdHash=MD5.hash("123456")
        mdHash shouldBe "e10adc3949ba59abbe56e057f20f883e"
    }
}



