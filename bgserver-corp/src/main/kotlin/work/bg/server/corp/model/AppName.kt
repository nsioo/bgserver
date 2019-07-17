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

package work.bg.server.corp.model

import work.bg.server.core.config.AppNamePackage
import work.bg.server.core.config.AppPackageManifest

class AppName : AppNamePackage {
    override  fun get():AppPackageManifest{
        return AppPackageManifest("corp",
                "公司部门","/svg/department-app.svg",
                "在公司和员工直接添加部门的支持",
                1)
    }
}