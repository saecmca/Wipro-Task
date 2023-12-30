package com.example.task.ui.theme.view

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task.model.EntryItem
import com.example.task.ui.theme.viewmodel.EntryViewModel

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                contontent()
            }
        }
    }
}


@Composable
fun CurrencyView(curList: List<EntryItem>) {
    var selectedIndex by remember { mutableStateOf(-1) }
    LazyColumn {
        itemsIndexed(items = curList) { index, item ->
            CurrencyItem(item, index, selectedIndex) { i ->
                selectedIndex = i
            }
        }
    }
}


@Composable
fun CurrencyItem(curr: EntryItem, index: Int, selectedIndex: Int, onClick: (Int) -> Unit) {
    isLoading.value = false
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp, 8.dp, 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxSize()
        ) {

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = "Category: ${curr.Category}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

            }
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = "Type: ${curr.API}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

            }
        }
        Column(
            Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Desc: ${curr.Description}",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }
        Column(
            Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            OpenWebView(curr.Link)
        }
    }
}

@Composable
fun OpenWebView(url: String) {
    val openWebView =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle result if needed
        }

    val onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            openWebView.launch(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle exception if no app can handle the intent
        }
    }

    Text(
        text = url,
        style = TextStyle(fontSize = 18.sp),
        color = Color.Blue,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@ExperimentalMaterial3Api
@Composable
fun contontent() {
    // Creating a Top bar
    var mDisplayMenu by remember { mutableStateOf(false) }
    Column {
        TopAppBar(
            title = { Text("Wipro Task", color = Color.White) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue),

            actions = {
                // Creating Icon button for dropdown menu
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(Icons.Default.MoreVert, "",
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Menu 1") },
                        onClick = {
                            mDisplayMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Menu2") },
                        {
                            mDisplayMenu = false
                        }

                    )

                }
            }
        )

        if (isLoading.value)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        Surface(
            color = Color.Black, modifier = Modifier.fillMaxWidth()
        ) {
            Text("List of API Entries", fontSize = 30.sp, color = Color.White)
        }
        CurrencyView(curList = mainViewModel.entryList)
        mainViewModel.getEntryList()

    }


}

private var isLoading = mutableStateOf(true)
val mainViewModel = EntryViewModel()


