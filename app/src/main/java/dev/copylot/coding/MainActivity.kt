package dev.copylot.coding

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.OpenDocumentTree
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.border
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidBorder
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.*
import androidx.core.app.ActivityOptionsCompat

// JSON 配置的数据结构
@Serializable
data class Modification(
    val type: String, // "line_range" | "single_line" | "keyword_replace"
    val file_path: String,
    val start_line: Int? = null,
    val end_line: Int? = null,
    val line: Int? = null,
    val old_text: String? = null,
    val new_text: String? = null,
    val global: Boolean? = null
)

@Serializable
data class Config(
    val modifications: List<Modification>
)

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 1001
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val json = Json { ignoreUnknownKeys = true }
    private var rootUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 检查权限
        if (allPermissionsGranted()) {
            initContent()
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        val launcher = registerForActivityResult(RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                initContent()
            } else {
                Toast.makeText(this, "需要存储权限才能修改文件", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        launcher.launch(REQUIRED_PERMISSIONS)
    }

    private fun initContent() {
        setContent {
            MainScreen()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        
        var jsonInput by remember { mutableStateOf(sampleJson) }
        var isProcessing by remember { mutableStateOf(false) }
        var resultMessage by remember { mutableStateOf("") }

        // 文件选择器
        val directoryPickerLauncher = registerForActivityResult(OpenDocumentTree()) { uri: Uri? ->
            uri?.let {
                rootUri = uri
                documentFileTree = it
                Toast.makeText(context, "已选择目录", Toast.LENGTH_SHORT).show()
            }
        }

        // 保存结果的文件选择器
        val saveResultLauncher = registerForActivityResult(CreateDocument()) { uri: Uri? ->
            uri?.let {
                scope.launch {
                    try {
                        contentResolver.openOutputStream(it)?.use { output ->
                            output.write(resultMessage.toByteArray())
                        }
                        Toast.makeText(context, "结果已保存", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "保存失败: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("JSON 文件修改工具") }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // JSON 输入框
                BasicTextField(
                    value = jsonInput,
                    onValueChange = { jsonInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .draw.border(1.dp, MaterialTheme.colorScheme.outline)
                        .padding(8.dp),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace
                    )
                )

                Spacer(Modifier.height(16.dp))

                // 操作按钮
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { directoryPickerLauncher.launch(null) },
                        enabled = !isProcessing
                    ) {
                        Text("选择目录")
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                isProcessing = true
                                resultMessage = processJson(jsonInput, context)
                                isProcessing = false
                            }
                        },
                        enabled = !isProcessing
                    ) {
                        Text("执行修改")
                    }

                    Button(
                        onClick = {
                            saveResultLauncher.launch("result.txt")
                        },
                        enabled = resultMessage.isNotEmpty() && !isProcessing
                    ) {
                        Text("保存结果")
                    }
                }

                Spacer(Modifier.height(16.dp))

                // 结果显示
                if (resultMessage.isNotEmpty()) {
                    Text("执行结果：", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        resultMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .draw.border(1.dp, MaterialTheme.colorScheme.outline)
                            .padding(8.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }

                Spacer(Modifier.height(8.dp))
                Text("提示：请先点击'选择目录'按钮选择要修改的文件所在目录")
            }
        }
    }

    // 处理 JSON 配置的函数
    private suspend fun processJson(jsonString: String, context: Context): String = withContext(Dispatchers.IO) {
        return@withContext try {
            val config = json.decodeFromString<Config>(jsonString)
            
            if (rootUri == null) {
                return@withContext "错误：请先选择文件目录"
            }

            val results = mutableListOf<String>()
            
            for (mod in config.modifications) {
                try {
                    val result = when (mod.type) {
                        "line_range" -> modifyLineRange(mod, context)
                        "single_line" -> modifySingleLine(mod, context)
                        "keyword_replace" -> replaceKeyword(mod, context)
                        else -> "未知操作类型: ${mod.type}"
                    }
                    results.add("文件: ${mod.file_path} - $result")
                } catch (e: Exception) {
                    results.add("文件: ${mod.file_path} - 错误: ${e.message}")
                }
            }
            
            results.joinToString("\n")
        } catch (e: Exception) {
            "JSON 解析错误: ${e.message}"
        }
    }

    // 按行范围替换
    private suspend fun modifyLineRange(mod: Modification, context: Context): String {
        val lines = readFileLines(mod.file_path, context)
        if (lines == null) return "文件不存在"
        
        val start = (mod.start_line ?: 1) - 1
        val end = (mod.end_line ?: lines.size) - 1
        
        if (start < 0 || end >= lines.size || start > end) {
            return "行号范围无效"
        }
        
        val newLines = mutableListOf<String>()
        newLines.addAll(lines.subList(0, start))
        newLines.addAll(mod.new_text?.split("\n") ?: emptyList())
        if (end < lines.size - 1) {
            newLines.addAll(lines.subList(end + 1, lines.size))
        }
        
        writeFile(mod.file_path, newLines.joinToString("\n"), context)
        return "已替换行范围 ${mod.start_line}-${mod.end_line}"
    }

    // 单行替换
    private suspend fun modifySingleLine(mod: Modification, context: Context): String {
        val lines = readFileLines(mod.file_path, context)
        if (lines == null) return "文件不存在"
        
        val lineNum = (mod.line ?: 1) - 1
        if (lineNum < 0 || lineNum >= lines.size) {
            return "行号无效"
        }
        
        val newLines = lines.toMutableList()
        newLines[lineNum] = mod.new_text ?: ""
        
        writeFile(mod.file_path, newLines.joinToString("\n"), context)
        return "已替换第 ${mod.line} 行"
    }

    // 关键词替换
    private suspend fun replaceKeyword(mod: Modification, context: Context): String {
        val content = readFileContent(mod.file_path, context)
        if (content == null) return "文件不存在"
        
        val oldText = mod.old_text ?: ""
        val newText = mod.new_text ?: ""
        val newContent = if (mod.global == true) {
            content.replace(oldText, newText)
        } else {
            content.replaceFirst(oldText, newText)
        }
        
        writeFile(mod.file_path, newContent, context)
        return if (mod.global == true) "已全局替换关键词" else "已替换第一个关键词"
    }

    // 读取文件内容
    private suspend fun readFileContent(filePath: String, context: Context): String? {
        return try {
            val file = File(context.filesDir, filePath)
            if (!file.exists()) return null
            file.readText()
        } catch (e: Exception) {
            null
        }
    }

    // 读取文件行
    private suspend fun readFileLines(filePath: String, context: Context): List<String>? {
        return try {
            val file = File(context.filesDir, filePath)
            if (!file.exists()) return null
            file.readLines()
        } catch (e: Exception) {
            null
        }
    }

    // 写入文件（自动备份）
    private suspend fun writeFile(filePath: String, content: String, context: Context) {
        val file = File(context.filesDir, filePath)
        val bakFile = File(context.filesDir, "$filePath.bak")
        
        // 备份原文件
        if (file.exists()) {
            file.copyTo(bakFile, overwrite = true)
        }
        
        // 写入新内容
        file.writeText(content)
    }

    // 样例 JSON
    private val sampleJson = """{
  "modifications": [
    {
      "type": "line_range",
      "file_path": "src/main.py",
      "start_line": 10,
      "end_line": 15,
      "new_text": "def new_function():\n    print(\"Updated logic\")\n    return True"
    },
    {
      "type": "single_line",
      "file_path": "config.yaml",
      "line": 5,
      "new_text": "timeout: 30"
    },
    {
      "type": "keyword_replace",
      "file_path": "README.md",
      "old_text": "legacy API",
      "new_text": "new API",
      "global": true
    }
  ]
}"""
}

// 全局变量存储文档树 Uri
var documentFileTree: Uri? = null

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