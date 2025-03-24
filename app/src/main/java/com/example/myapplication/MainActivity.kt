package com.example.myapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "root") {
        composable("root") { RootScreen(navController) }
        composable("list") { ListScreen(navController) }
        composable("detail/{item}") { backStackEntry ->
            val item = backStackEntry.arguments?.getString("item") ?: "No Data"
            DetailScreen(navController, item)
        }
    }
}

@Composable
fun RootScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ảnh trên cùng
        Image(
            painter = painterResource(id = R.drawable.your_image), // Đổi "your_image" thành tên file ảnh trong drawable
            contentDescription = "App Icon",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Tiêu đề "Navigation" in đậm
        Text(
            text = "Navigation",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, // In đậm
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Đoạn văn bản mô tả
        Text(
            text = " Is a framework that simplifies the implementation of navigation between different UI components (activities, fragments, or composables) in an app",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Nút PUSH
        Button(onClick = { navController.navigate("list") }) {
            Text(text = "PUSH")
        }
    }
}


@Composable
fun ListScreen(navController: NavHostController) {
    val items = List(100) { "${it + 1} | The only way to do great work is to love what you do." }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Lazy Column",
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f), // Căn giữa
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp)) // Dùng để cân bằng với IconButton
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { item ->
                ListItem(text = item) { navController.navigate("detail/$item") }
            }
        }
    }
}


@Composable
fun ListItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text, fontSize = 16.sp)
        Button(onClick = onClick) { Text("→") }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, item: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = item, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
        Button(onClick = { navController.navigate("root") }, modifier = Modifier.padding(16.dp)) {
            Text(text = "BACK TO ROOT")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRootScreen() {
    MaterialTheme {
        RootScreen(navController = rememberNavController())
    }
}
