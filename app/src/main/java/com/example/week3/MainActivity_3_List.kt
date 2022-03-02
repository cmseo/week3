package com.example.week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.week3.ui.theme.Week3Theme
import kotlinx.coroutines.launch

class MainActivity_3_List : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week3Theme {
                SimpleList()
            }
        }
    }
}

/*
@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(
            state = scrollState
        )
    ) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}
*/

@Composable
fun SimpleList() {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState
    ) {
        items(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun ImageList() {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(it)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Item #$index",
            style =MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun ScrollingList() {
    val listSize = 1000
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column {
        Row {
            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }
            ) {
                Text("Scroll to the top")
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }
            ) {
                Text("Scroll to the end")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize)
            {
                ImageListItem(it)
            }
        }
    }
}

@Preview
@Composable
fun SimpleListPreview() {
    Week3Theme {
//        SimpleList()
//        ImageList()
        ScrollingList()
    }
}
