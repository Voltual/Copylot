package dev.copylot.coding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.copylot.coding.ui.theme.BBQBackgroundCard
import dev.copylot.coding.ui.theme.BBQTheme
import dev.copylot.coding.ui.theme.roundScreenPadding

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BBQTheme {
                // 使用 BBQBackgroundCard 作为根容器，支持全局背景图片
                BBQBackgroundCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .roundScreenPadding(), // 应用圆屏适配 padding
                    onClick = null // 不需要点击事件
                ) {
                    // Surface 作为内容容器
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Greeting("Android")
                    }
                }
            }
        }
    }
}

// 重启主 Activity 的辅助函数
fun restartMainActivity(context: Context) {
    val packageManager = context.packageManager
    val intent = packageManager.getLaunchIntentForPackage(context.packageManager.getPackageInfo(context.packageName, 0).packageName)
    intent?.let {
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val options = androidx.core.app.ActivityOptionsCompat.makeCustomAnimation(
            context,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        context.startActivity(it, options.toBundle())
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}