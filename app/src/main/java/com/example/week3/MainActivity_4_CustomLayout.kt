package com.example.week3

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.week3.ui.theme.Week3Theme
import kotlinx.coroutines.launch

const val TAG = "cmseo"
class MainActivity_4_CustomLayout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week3Theme {
                Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
            }
        }
    }
}

/*
fun Modifier.customLayoutModifier() = Modifier.layout { measurable, constraints ->

}*/

fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) =
    this.then(
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)

            // Check the composable has a first firstBaselineToTop
            check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
            val firstBaseline = placeable[FirstBaseline]

            // Height of the composable with padding - first baseline
            val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
            val height = placeable.height + placeableY
            Log.d(TAG, placeable.toString())
            Log.d(TAG, "firstBaselineToTop.roundToPx(): ${firstBaselineToTop.roundToPx()}," +
                    "firstBaseline: ${firstBaseline}," +
                    "placeable.height: ${placeable.height}")
            layout(placeable.width, height) {
                placeable.placeRelative(0, placeableY)
            }
        }
    )

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    Week3Theme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    Week3Theme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}