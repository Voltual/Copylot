//Copyright (C) 2025 Voltual
// 本程序是自由软件：你可以根据自由软件基金会发布的 GNU 通用公共许可证第3版
//（或任意更新的版本）的条款重新分发和/或修改它。
//本程序是基于希望它有用而分发的，但没有任何担保；甚至没有适销性或特定用途适用性的隐含担保。
// 有关更多细节，请参阅 GNU 通用公共许可证。
//
// 你应该已经收到了一份 GNU 通用公共许可证的副本
// 如果没有，请查阅 <http://www.gnu.org/licenses/>.
package dev.copylot.coding.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.roundScreenPadding(): Modifier {
    val context = LocalContext.current
    val roundScreenPaddings by ThemeColorStore.getRoundScreenPaddingFlow(context).collectAsState(
        initial = ThemeColorStore.RoundScreenPaddings(false, 0f, 0f, 0f, 0f)
    )

    return if (roundScreenPaddings.enabled) {
        val density = LocalDensity.current
        // 直接在 this 上调用 padding 函数
        this.padding(
            PaddingValues(
                start = with(density) { roundScreenPaddings.left.dp },
                top = with(density) { roundScreenPaddings.top.dp },
                end = with(density) { roundScreenPaddings.right.dp },
                bottom = with(density) { roundScreenPaddings.bottom.dp }
            )
        )
    } else {
        this // 不启用时返回原始 Modifier
    }
}