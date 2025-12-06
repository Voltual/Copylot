//Copyright (C) 2025 Voltual
// 本程序是自由软件：你可以根据自由软件基金会发布的 GNU 通用公共许可证第3版
//（或任意更新的版本）的条款重新分发和/或修改它。
//本程序是基于希望它有用而分发的，但没有任何担保；甚至没有适销性或特定用途适用性的隐含担保。
// 有关更多细节，请参阅 GNU 通用公共许可证。
//
// 你应该已经收到了一份 GNU 通用公共许可证的副本
// 如果没有，请查阅 <http://www.gnu.org/licenses/>。
package cc.bbq.xq.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.palette.graphics.Palette
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ColorUtils {

    /**
     * 从 Uri 中提取 Bitmap
     */
    suspend fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 从 Bitmap 中提取主要颜色
     */
    suspend fun extractColorsFromBitmap(bitmap: Bitmap?): ColorSet? = withContext(Dispatchers.Default) {
        if (bitmap == null) return@withContext null

        val palette = Palette.Builder(bitmap).generate()

        // 提取颜色，如果提取失败则使用默认颜色
        val primaryColor = palette.getVibrantColor(ThemeColorStore.DEFAULT_COLORS.lightSet.primary.toArgb())
        val secondaryColor = palette.getLightVibrantColor(ThemeColorStore.DEFAULT_COLORS.lightSet.secondary.toArgb())
        val backgroundColor = palette.getMutedColor(ThemeColorStore.DEFAULT_COLORS.lightSet.background.toArgb())

        // 创建 ColorSet 对象
        ColorSet(
            primary = Color(primaryColor),
            onPrimary = calculateForegroundColor(primaryColor),
            primaryContainer = lightenColor(Color(primaryColor), 0.2f),
            onPrimaryContainer = calculateForegroundColor(lightenColor(Color(primaryColor), 0.2f).toArgb()),
            secondary = Color(secondaryColor),
            onSecondary = calculateForegroundColor(secondaryColor),
            secondaryContainer = lightenColor(Color(secondaryColor), 0.2f),
            onSecondaryContainer = calculateForegroundColor(lightenColor(Color(secondaryColor), 0.2f).toArgb()),
            surface = Color(backgroundColor),
            onSurface = calculateForegroundColor(backgroundColor),
            surfaceVariant = lightenColor(Color(backgroundColor), 0.1f),
            onSurfaceVariant = calculateForegroundColor(lightenColor(Color(backgroundColor), 0.1f).toArgb()),
            outline = calculateOutlineColor(backgroundColor),
            error = Color(palette.getDarkVibrantColor(ThemeColorStore.DEFAULT_COLORS.lightSet.error.toArgb())),
            onError = calculateForegroundColor(palette.getDarkVibrantColor(ThemeColorStore.DEFAULT_COLORS.lightSet.error.toArgb())),
            background = Color(backgroundColor),
            onBackground = calculateForegroundColor(backgroundColor),
            messageLikeBg = lightenColor(Color(backgroundColor), 0.05f),
            messageCommentBg = lightenColor(Color(backgroundColor), 0.1f),
            messageDefaultBg = lightenColor(Color(backgroundColor), 0.15f),
            billingIncome = Color(palette.getVibrantColor(ThemeColorStore.DEFAULT_COLORS.lightSet.billingIncome.toArgb())),
            billingExpense = Color(palette.getDarkVibrantColor(ThemeColorStore.DEFAULT_COLORS.lightSet.billingExpense.toArgb()))
        )
    }

    /**
     * 计算前景色（根据背景色自动选择黑色或白色）
     */
    private fun calculateForegroundColor(backgroundColor: Int): Color {
        val red = android.graphics.Color.red(backgroundColor)
        val green = android.graphics.Color.green(backgroundColor)
        val blue = android.graphics.Color.blue(backgroundColor)
        // 计算亮度
        val luminance = 0.299 * red + 0.587 * green + 0.114 * blue
        // 根据亮度选择前景色
        return if (luminance > 128) Color.Black else Color.White
    }

    /**
     * 调整颜色亮度
     */
    private fun lightenColor(color: Color, factor: Float): Color {
        val red = (color.red + (1 - color.red) * factor).coerceIn(0f, 1f)
        val green = (color.green + (1 - color.green) * factor).coerceIn(0f, 1f)
        val blue = (color.blue + (1 - color.blue) * factor).coerceIn(0f, 1f)
        return Color(red, green, blue)
    }

    /**
     * 计算轮廓颜色
     */
    private fun calculateOutlineColor(backgroundColor: Int): Color {
        val red = android.graphics.Color.red(backgroundColor)
        val green = android.graphics.Color.green(backgroundColor)
        val blue = android.graphics.Color.blue(backgroundColor)
        // 计算亮度
        val luminance = 0.299 * red + 0.587 * green + 0.114 * blue
        // 根据亮度选择轮廓颜色
        return if (luminance > 128) Color.Gray else Color.LightGray
    }
}