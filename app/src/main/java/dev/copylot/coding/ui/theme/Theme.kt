//Copyright (C) 2025 Voltual
// 本程序是自由软件：你可以根据自由软件基金会发布的 GNU 通用公共许可证第3版
//（或任意更新的版本）的条款重新分发和/或修改它。
//本程序是基于希望它有用而分发的，但没有任何担保；甚至没有适销性或特定用途适用性的隐含担保。
//
// 你应该已经收到了一份 GNU 通用公共许可证的副本
// 如果没有，请查阅 <http://www.gnu.org/licenses/>。
package cc.bbq.xq.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.graphics.Color // 新增导入
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalContext

// 使用与XML完全相同的语义名称
private val LightColorScheme = lightColorScheme(
    primary = md_primary,
    onPrimary = md_onPrimary,
    primaryContainer = md_primaryContainer,
    onPrimaryContainer = md_onPrimaryContainer,
    secondary = md_secondary,
    onSecondary = md_onSecondary,
    secondaryContainer = md_secondaryContainer,
    onSecondaryContainer = md_onSecondaryContainer,
    surface = md_surface,
    onSurface = md_onSurface,
    surfaceVariant = md_surfaceVariant,
    onSurfaceVariant = md_onSurfaceVariant,
    outline = md_outline,
    error = md_error,
    onError = md_onError,
    background = md_background, // 新增
    onBackground = md_onBackground // 新增
)

private val DarkColorScheme = darkColorScheme(
    primary = md_primary_dark,
    onPrimary = md_onPrimary_dark,
    primaryContainer = md_primaryContainer_dark,
    onPrimaryContainer = md_onPrimaryContainer_dark,
    secondary = md_secondary_dark,
    onSecondary = md_onSecondary_dark,
    secondaryContainer = md_secondaryContainer_dark,
    onSecondaryContainer = md_onSecondaryContainer_dark,
    surface = md_surface_dark,
    onSurface = md_onSurface_dark,
    surfaceVariant = md_surfaceVariant_dark,
    onSurfaceVariant = md_onSurfaceVariant_dark,
    outline = md_outline_dark,
    error = md_error_dark,
    onError = md_onError_dark,
    background = md_background_dark, // 新增
    onBackground = md_onBackground_dark // 新增
)

// 修改：使用应用主题设置而不是系统主题
val MaterialTheme.messageLikeBg: Color
    @Composable get() {
        val customColors = ThemeManager.customColorSet
        return if (ThemeManager.isAppDarkTheme) {
            customColors?.darkSet?.messageLikeBg ?: message_like_bg_dark
        } else {
            customColors?.lightSet?.messageLikeBg ?: message_like_bg
        }
    }

// 同样方式更新其他扩展属性
val MaterialTheme.messageCommentBg: Color
    @Composable get() {
        val customColors = ThemeManager.customColorSet
        return if (ThemeManager.isAppDarkTheme) {
            customColors?.darkSet?.messageCommentBg ?: message_comment_bg_dark
        } else {
            customColors?.lightSet?.messageCommentBg ?: message_comment_bg
        }
    }

val MaterialTheme.messageDefaultBg: Color
    @Composable get() {
        val customColors = ThemeManager.customColorSet
        return if (ThemeManager.isAppDarkTheme) {
            customColors?.darkSet?.messageDefaultBg ?: message_default_bg_dark
        } else {
            customColors?.lightSet?.messageDefaultBg ?: message_default_bg
        }
    }

val MaterialTheme.billingIncome: Color
    @Composable get() {
        val customColors = ThemeManager.customColorSet
        return if (ThemeManager.isAppDarkTheme) {
            customColors?.darkSet?.billingIncome ?: billing_income_dark
        } else {
            customColors?.lightSet?.billingIncome ?: billing_income
        }
    }

val MaterialTheme.billingExpense: Color
    @Composable get() {
        val customColors = ThemeManager.customColorSet
        return if (ThemeManager.isAppDarkTheme) {
            customColors?.darkSet?.billingExpense ?: billing_expense_dark
        } else {
            customColors?.lightSet?.billingExpense ?: billing_expense
        }
    }
// 完整字体排版定义
private val AppTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
)

@Composable
fun BBQTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appDarkTheme: Boolean? = null,
    content: @Composable () -> Unit
) {
    // 优先级: appDarkTheme > 系统设置
    val useDarkTheme = when {
        appDarkTheme != null -> appDarkTheme
        else -> darkTheme
    }
    
    // 加载自定义颜色 - 修复：直接访问 customColorSet 属性
    val customColors = ThemeManager.customColorSet
    
    // 创建颜色方案 (优先使用自定义颜色)
    val colorScheme = if (useDarkTheme) {
        // 修复：使用 darkColorScheme 而不是 lightColorScheme
        customColors?.darkSet?.toDarkColorScheme() ?: DarkColorScheme
    } else {
        customColors?.lightSet?.toLightColorScheme() ?: LightColorScheme
    }
    
    // 应用 DPI 和字体大小 - 注意：这部分代码需要在 Activity 中执行
    // 这里只是传递 Context，实际应用在 Activity 中
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

// 修复：添加两个独立的转换函数
private fun ColorSet.toLightColorScheme() = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    outline = outline,
    error = error,
    onError = onError,
    background = background,
    onBackground = onBackground
)

// 新增：暗色主题转换函数
private fun ColorSet.toDarkColorScheme() = darkColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = primaryContainer,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    outline = outline,
    error = error,
    onError = onError,
    background = background,
    onBackground = onBackground
)
