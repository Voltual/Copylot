//Copyright (C) 2025 Voltual
// 本程序是自由软件：你可以根据自由软件基金会发布的 GNU 通用公共许可证第3版
//（或任意更新的版本）的条款重新分发和/或修改它。
//本程序是基于希望它有用而没有任何担保；甚至没有适销性或特定用途适用性的隐含担保。
// 有关更多细节，请参阅 GNU 通用公共许可证。
//
// 你应该已经收到了一份 GNU 通用公共许可证的副本
// 如果没有，请查阅 <http://www.gnu.org/licenses/>.
package cc.bbq.xq.ui.theme

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore 
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.themeSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")

data class CustomColorSet(
    val lightSet: ColorSet = ColorSet.defaultLight(),
    val darkSet: ColorSet = ColorSet.defaultDark()
)

data class ColorSet(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val error: Color,
    val onError: Color,
    val background: Color,
    val onBackground: Color,
    val messageLikeBg: Color,
    val messageCommentBg: Color,
    val messageDefaultBg: Color,
    val billingIncome: Color,
    val billingExpense: Color
) {
    fun toList(): List<Pair<String, Color>> = listOf(
        "primary" to primary, "onPrimary" to onPrimary, "primaryContainer" to primaryContainer,
        "onPrimaryContainer" to onPrimaryContainer, "secondary" to secondary, "onSecondary" to onSecondary,
        "secondaryContainer" to secondaryContainer, "onSecondaryContainer" to onSecondaryContainer,
        "surface" to surface, "onSurface" to onSurface, "surfaceVariant" to surfaceVariant,
        "onSurfaceVariant" to onSurfaceVariant, "outline" to outline, "error" to error,
        "onError" to onError, "background" to background, "onBackground" to onBackground,
        "messageLikeBg" to messageLikeBg, "messageCommentBg" to messageCommentBg,
        "messageDefaultBg" to messageDefaultBg, "billingIncome" to billingIncome, "billingExpense" to billingExpense
    )

    fun copyWith(name: String, newColor: Color): ColorSet {
        return when (name) {
            "primary" -> copy(primary = newColor)
            "onPrimary" -> copy(onPrimary = newColor)
            "primaryContainer" -> copy(primaryContainer = newColor)
            "onPrimaryContainer" -> copy(onPrimaryContainer = newColor)
            "secondary" -> copy(secondary = newColor)
            "onSecondary" -> copy(onSecondary = newColor)
            "secondaryContainer" -> copy(secondaryContainer = newColor)
            "onSecondaryContainer" -> copy(onSecondaryContainer = newColor)
            "surface" -> copy(surface = newColor)
            "onSurface" -> copy(onSurface = newColor)
            "surfaceVariant" -> copy(surfaceVariant = newColor)
            "onSurfaceVariant" -> copy(onSurfaceVariant = newColor)
            "outline" -> copy(outline = newColor)
            "error" -> copy(error = newColor)
            "onError" -> copy(onError = newColor)
            "background" -> copy(background = newColor)
            "onBackground" -> copy(onBackground = newColor)
            "messageLikeBg" -> copy(messageLikeBg = newColor)
            "messageCommentBg" -> copy(messageCommentBg = newColor)
            "messageDefaultBg" -> copy(messageDefaultBg = newColor)
            "billingIncome" -> copy(billingIncome = newColor)
            "billingExpense" -> copy(billingExpense = newColor)
            else -> this
        }
    }

    companion object {
        fun defaultLight() = ColorSet(
            primary = Color(0xFFFB7299), onPrimary = Color(0xFFFFFFFF), primaryContainer = Color(0xFFFFD9E2),
            onPrimaryContainer = Color(0xFF3F0019), secondary = Color(0xFFFF8A9F), onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFFFD9E2), onSecondaryContainer = Color(0xFF3F0019), surface = Color(0xFFFFFBFE),
            onSurface = Color(0xFF1C1B1F), surfaceVariant = Color(0xFFE7E0EC), onSurfaceVariant = Color(0xFF49454F),
            outline = Color(0xFF79747E), error = Color(0xFFB3261E), onError = Color(0xFFFFFFFF),
            background = Color(0xFFFFFBFE), onBackground = Color(0xFF1C1B1F), messageLikeBg = Color(0xFFE8F5E9),
            messageCommentBg = Color(0xFFE3F2FD), messageDefaultBg = Color(0xFFFFF9C4), billingIncome = Color(0xFF4CAF50),
            billingExpense = Color(0xFFF44336)
        )

        fun defaultDark() = ColorSet(
            primary = Color(0xFFFFB1C8), onPrimary = Color(0xFF5E1133), primaryContainer = Color(0xFF7D2949),
            onPrimaryContainer = Color(0xFFFFD9E2), secondary = Color(0xFFFFB2C7), onSecondary = Color(0xFF633747),
            secondaryContainer = Color(0xFF7A4F5F), onSecondaryContainer = Color(0xFFFFD9E2), surface = Color(0xFF1C1B1F),
            onSurface = Color(0xFFE6E1E5), surfaceVariant = Color(0xFF49454F), onSurfaceVariant = Color(0xFFCAC4D0),
            outline = Color(0xFF938F99), error = Color(0xFFF2B8B5), onError = Color(0xFF601410),
            background = Color(0xFF1C1B1F), onBackground = Color(0xFFE6E1E5), messageLikeBg = Color(0xFF1E3A2F),
            messageCommentBg = Color(0xFF1A2D40), messageDefaultBg = Color(0xFF3E2E23), billingIncome = Color(0xFF81C784),
            billingExpense = Color(0xFFE57373)
        )
    }
}

object ThemeColorStore {
    val DEFAULT_COLORS = CustomColorSet()

    private val LIGHT_COLOR_KEYS = listOf(
        "light_primary", "light_onPrimary", "light_primaryContainer", "light_onPrimaryContainer", "light_secondary",
        "light_onSecondary", "light_secondaryContainer", "light_onSecondaryContainer", "light_surface", "light_onSurface",
        "light_surfaceVariant", "light_onSurfaceVariant", "light_outline", "light_error", "light_onError", "light_background",
        "light_onBackground", "light_messageLikeBg", "light_messageCommentBg", "light_messageDefaultBg", "light_billingIncome",
        "light_billingExpense"
    )

    private val DARK_COLOR_KEYS = listOf(
        "dark_primary", "dark_onPrimary", "dark_primaryContainer", "dark_onPrimaryContainer", "dark_secondary",
        "dark_onSecondary", "dark_secondaryContainer", "dark_onSecondaryContainer", "dark_surface", "dark_onSurface",
        "dark_surfaceVariant", "dark_onSurfaceVariant", "dark_outline", "dark_error", "dark_onError", "dark_background",
        "dark_onBackground", "dark_messageLikeBg", "dark_messageCommentBg", "dark_messageDefaultBg", "dark_billingIncome",
        "dark_billingExpense"
    )

    private val DPI_KEY = floatPreferencesKey("dpi")
    private val FONT_SIZE_KEY = floatPreferencesKey("font_size")
    private val DRAWER_HEADER_LIGHT_BG_URI_KEY = stringPreferencesKey("drawer_header_light_bg_uri")
    private val DRAWER_HEADER_DARK_BG_URI_KEY = stringPreferencesKey("drawer_header_dark_bg_uri")

    private val GLOBAL_BACKGROUND_URI_KEY = stringPreferencesKey("global_background_uri")


    suspend fun saveGlobalBackgroundUri(context: Context, uri: String?) {
        context.themeSettingsDataStore.edit { preferences ->
            if (uri != null) preferences[GLOBAL_BACKGROUND_URI_KEY] = uri
            else preferences.remove(GLOBAL_BACKGROUND_URI_KEY)
        }
    }

    fun getGlobalBackgroundUriFlow(context: Context): Flow<String?> {
        return context.themeSettingsDataStore.data.map { it[GLOBAL_BACKGROUND_URI_KEY] }
    }

    suspend fun saveColors(context: Context, colors: CustomColorSet) {
        context.themeSettingsDataStore.edit { preferences ->
            colors.lightSet.toList().forEachIndexed { index, (_, color) ->
                preferences[intPreferencesKey(LIGHT_COLOR_KEYS[index])] = color.toArgb()
            }
            colors.darkSet.toList().forEachIndexed { index, (_, color) ->
                preferences[intPreferencesKey(DARK_COLOR_KEYS[index])] = color.toArgb()
            }
        }
    }

    // 注意：load 方法保持 runBlocking，因为它们需要在 Activity 初始化（非 suspend 上下文）时同步加载
    // ThemeColorStore.kt

    fun loadColors(context: Context): CustomColorSet {
        return runBlocking {
            val preferences = context.themeSettingsDataStore.data.first()

            val lightSet: ColorSet = try {
                // 优先尝试从图片主题加载
                preferences[IMAGE_THEME_LIGHT_URI_KEY]?.let { uriString ->
                    val uri = Uri.parse(uriString)
                    val bitmap = ColorUtils.getBitmapFromUri(context, uri)
                    ColorUtils.extractColorsFromBitmap(bitmap)
                } ?: loadColorSet(preferences, LIGHT_COLOR_KEYS, DEFAULT_COLORS.lightSet)
            } catch (e: Exception) {
                // 如果图片主题加载失败，回退到手动设置的颜色
                loadColorSet(preferences, LIGHT_COLOR_KEYS, DEFAULT_COLORS.lightSet)
            }

            val darkSet: ColorSet = try {
                preferences[IMAGE_THEME_DARK_URI_KEY]?.let { uriString ->
                    val uri = Uri.parse(uriString)
                    val bitmap = ColorUtils.getBitmapFromUri(context, uri)
                    ColorUtils.extractColorsFromBitmap(bitmap)
                } ?: loadColorSet(preferences, DARK_COLOR_KEYS, DEFAULT_COLORS.darkSet)
            } catch (e: Exception) {
                loadColorSet(preferences, DARK_COLOR_KEYS, DEFAULT_COLORS.darkSet)
            }

            CustomColorSet(lightSet, darkSet)
        }
    }

    private fun loadColorSet(preferences: Preferences, keys: List<String>, defaultSet: ColorSet): ColorSet {
        return try {
            if (keys.any { !preferences.contains(intPreferencesKey(it)) }) return defaultSet
            ColorSet(
                primary = Color(preferences[intPreferencesKey(keys[0])]!!),
                onPrimary = Color(preferences[intPreferencesKey(keys[1])]!!),
                primaryContainer = Color(preferences[intPreferencesKey(keys[2])]!!),
                onPrimaryContainer = Color(preferences[intPreferencesKey(keys[3])]!!),
                secondary = Color(preferences[intPreferencesKey(keys[4])]!!),
                onSecondary = Color(preferences[intPreferencesKey(keys[5])]!!),
                secondaryContainer = Color(preferences[intPreferencesKey(keys[6])]!!),
                onSecondaryContainer = Color(preferences[intPreferencesKey(keys[7])]!!),
                surface = Color(preferences[intPreferencesKey(keys[8])]!!),
                onSurface = Color(preferences[intPreferencesKey(keys[9])]!!),
                surfaceVariant = Color(preferences[intPreferencesKey(keys[10])]!!),
                onSurfaceVariant = Color(preferences[intPreferencesKey(keys[11])]!!),
                outline = Color(preferences[intPreferencesKey(keys[12])]!!),
                error = Color(preferences[intPreferencesKey(keys[13])]!!),
                onError = Color(preferences[intPreferencesKey(keys[14])]!!),
                background = Color(preferences[intPreferencesKey(keys[15])]!!),
                onBackground = Color(preferences[intPreferencesKey(keys[16])]!!),
                messageLikeBg = Color(preferences[intPreferencesKey(keys[17])]!!),
                messageCommentBg = Color(preferences[intPreferencesKey(keys[18])]!!),
                messageDefaultBg = Color(preferences[intPreferencesKey(keys[19])]!!),
                billingIncome = Color(preferences[intPreferencesKey(keys[20])]!!),
                billingExpense = Color(preferences[intPreferencesKey(keys[21])]!!)
            )
        } catch (e: Exception) {
            defaultSet
        }
    }

    suspend fun saveDpi(context: Context, dpi: Float) {
        context.themeSettingsDataStore.edit { it[DPI_KEY] = dpi }
    }

    fun loadDpi(context: Context): Float {
        return runBlocking { context.themeSettingsDataStore.data.first()[DPI_KEY] ?: 1.0f }
    }

    suspend fun saveFontSize(context: Context, fontSize: Float) {
        context.themeSettingsDataStore.edit { it[FONT_SIZE_KEY] = fontSize }
    }

    fun loadFontSize(context: Context): Float {
        return runBlocking { context.themeSettingsDataStore.data.first()[FONT_SIZE_KEY] ?: 1.0f }
    }

    suspend fun saveDrawerHeaderLightBackgroundUri(context: Context, uri: String?) {
        context.themeSettingsDataStore.edit { preferences ->
            if (uri != null) preferences[DRAWER_HEADER_LIGHT_BG_URI_KEY] = uri
            else preferences.remove(DRAWER_HEADER_LIGHT_BG_URI_KEY)
        }
    }

    fun getDrawerHeaderLightBackgroundUriFlow(context: Context): Flow<String?> {
        return context.themeSettingsDataStore.data.map { it[DRAWER_HEADER_LIGHT_BG_URI_KEY] }
    }

    suspend fun saveDrawerHeaderDarkBackgroundUri(context: Context, uri: String?) {
        context.themeSettingsDataStore.edit { preferences ->
            if (uri != null) preferences[DRAWER_HEADER_DARK_BG_URI_KEY] = uri
            else preferences.remove(DRAWER_HEADER_DARK_BG_URI_KEY)
        }
    }

    fun getDrawerHeaderDarkBackgroundUriFlow(context: Context): Flow<String?> {
        return context.themeSettingsDataStore.data.map { it[DRAWER_HEADER_DARK_BG_URI_KEY] }
    }

    private val IMAGE_THEME_LIGHT_URI_KEY = stringPreferencesKey("image_theme_light_uri")
    private val IMAGE_THEME_DARK_URI_KEY = stringPreferencesKey("image_theme_dark_uri")

    suspend fun saveImageThemeLightUri(context: Context, uri: String?) {
        context.themeSettingsDataStore.edit { preferences ->
            if (uri != null) preferences[IMAGE_THEME_LIGHT_URI_KEY] = uri
            else preferences.remove(IMAGE_THEME_LIGHT_URI_KEY)
        }
    }

    fun getImageThemeLightUriFlow(context: Context): Flow<String?> {
        return context.themeSettingsDataStore.data.map { it[IMAGE_THEME_LIGHT_URI_KEY] }
    }

    suspend fun saveImageThemeDarkUri(context: Context, uri: String?) {
        context.themeSettingsDataStore.edit { preferences ->
            if (uri != null) preferences[IMAGE_THEME_DARK_URI_KEY] = uri
            else preferences.remove(IMAGE_THEME_DARK_URI_KEY)
        }
    }

    fun getImageThemeDarkUriFlow(context: Context): Flow<String?> {
        return context.themeSettingsDataStore.data.map { it[IMAGE_THEME_DARK_URI_KEY] }
    }

    // 新增：是否启用自定义 DPI 的 DataStore 键
    private val CUSTOM_DPI_ENABLED_KEY = booleanPreferencesKey("custom_dpi_enabled")

    // 新增：圆屏相关 DataStore 键
    private val ROUND_SCREEN_ENABLED_KEY = booleanPreferencesKey("round_screen_enabled")
    private val ROUND_SCREEN_LEFT_PADDING_KEY = floatPreferencesKey("round_screen_left_padding")
    private val ROUND_SCREEN_TOP_PADDING_KEY = floatPreferencesKey("round_screen_top_padding")
    private val ROUND_SCREEN_RIGHT_PADDING_KEY = floatPreferencesKey("round_screen_right_padding")
    private val ROUND_SCREEN_BOTTOM_PADDING_KEY = floatPreferencesKey("round_screen_bottom_padding")

    // 保存圆屏 padding
    suspend fun saveRoundScreenPaddings(
        context: Context,
        enabled: Boolean,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        context.themeSettingsDataStore.edit { prefs ->
            prefs[ROUND_SCREEN_ENABLED_KEY] = enabled
            prefs[ROUND_SCREEN_LEFT_PADDING_KEY] = left
            prefs[ROUND_SCREEN_TOP_PADDING_KEY] = top
            prefs[ROUND_SCREEN_RIGHT_PADDING_KEY] = right
            prefs[ROUND_SCREEN_BOTTOM_PADDING_KEY] = bottom
        }
    }

    // 加载圆屏 padding（同步，用于初始化）
    data class RoundScreenPaddings(
        val enabled: Boolean,
        val left: Float,
        val top: Float,
        val right: Float,
        val bottom: Float
    )

    fun loadRoundScreenPaddings(context: Context): RoundScreenPaddings {
        return runBlocking {
            val prefs = context.themeSettingsDataStore.data.first()
            RoundScreenPaddings(
                enabled = prefs[ROUND_SCREEN_ENABLED_KEY] ?: false,
                left = prefs[ROUND_SCREEN_LEFT_PADDING_KEY] ?: 0f,
                top = prefs[ROUND_SCREEN_TOP_PADDING_KEY] ?: 0f,
                right = prefs[ROUND_SCREEN_RIGHT_PADDING_KEY] ?: 0f,
                bottom = prefs[ROUND_SCREEN_BOTTOM_PADDING_KEY] ?: 0f
            )
        }
    }

    // Flow：实时获取 RoundScreenPaddings
    fun getRoundScreenPaddingFlow(context: Context): Flow<RoundScreenPaddings> {
        return context.themeSettingsDataStore.data.map { prefs ->
            RoundScreenPaddings(
                enabled = prefs[ROUND_SCREEN_ENABLED_KEY] ?: false,
                left = prefs[ROUND_SCREEN_LEFT_PADDING_KEY] ?: 0f,
                top = prefs[ROUND_SCREEN_TOP_PADDING_KEY] ?: 0f,
                right = prefs[ROUND_SCREEN_RIGHT_PADDING_KEY] ?: 0f,
                bottom = prefs[ROUND_SCREEN_BOTTOM_PADDING_KEY] ?: 0f
            )
        }
    }

    // 重置函数（新增到 reset 逻辑中）
    suspend fun resetRoundScreenPaddings(context: Context) {
        context.themeSettingsDataStore.edit { prefs ->
            prefs.remove(ROUND_SCREEN_ENABLED_KEY)
            prefs.remove(ROUND_SCREEN_LEFT_PADDING_KEY)
            prefs.remove(ROUND_SCREEN_TOP_PADDING_KEY)
            prefs.remove(ROUND_SCREEN_RIGHT_PADDING_KEY)
            prefs.remove(ROUND_SCREEN_BOTTOM_PADDING_KEY)
        }
    }


    // 新增：保存是否启用自定义 DPI 的偏好
    suspend fun saveCustomDpiEnabled(context: Context, enabled: Boolean) {
        context.themeSettingsDataStore.edit { it[CUSTOM_DPI_ENABLED_KEY] = enabled }
    }

    // 新增：获取是否启用自定义 DPI 的 Flow
    fun getCustomDpiEnabledFlow(context: Context): Flow<Boolean> {
        return context.themeSettingsDataStore.data.map { it[CUSTOM_DPI_ENABLED_KEY] ?: false }
    }

    // 新增：同步加载是否启用自定义 DPI 的偏好
    fun loadCustomDpiEnabled(context: Context): Boolean {
        return runBlocking { context.themeSettingsDataStore.data.first()[CUSTOM_DPI_ENABLED_KEY] ?: false }
    }
}