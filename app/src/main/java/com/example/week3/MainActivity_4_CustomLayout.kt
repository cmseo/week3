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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.week3.ui.theme.Week3Theme
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts

/**
 * https://developer.android.com/codelabs/jetpack-compose-layouts?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-layouts#6
 * https://velog.io/@juan-rybczinski/Jetpack-Compose-Create-your-custom-layout
* */

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

/**
 * Placeable
 * A Placeable corresponds to a child layout that can be positioned by its parent layout. Most Placeables are the result of a Measurable.measure call.
 *
 *
 * Check
 * Throws an IllegalStateException if the value is false.
 *
 *
 */
//@ExperimentalContracts
fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp): Modifier =
    this.then(
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)

/*            val a : String? = "abc"
            check(a != null)
            a.subSequence()*/

            // Check the composable has a first firstBaselineToTop
            check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
            val firstBaseline = placeable[FirstBaseline]

            // Height of the composable with padding - first baseline
            val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
            val height = placeable.height + placeableY
            layout(placeable.width, height) {
                placeable.placeRelative(0, placeableY)
            }
        }
    )

/*@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    // custom layout attributes
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // measure and position children given constraints logic here
    }
}*/

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    // custom layout attributes
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // measure and position children given constraints logic here
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }
        var yPosition: Int = 0
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            placeables.forEach {
                it.placeRelative(
                    x = 0,
                    y = yPosition
                )
                yPosition += it.height
            }
        }
    }
}

@Preview
@Composable
fun MyOwnColumnPreview() {
    Week3Theme {
        MyOwnColumn {
            Text("MyOwnColumn1")
            Text("MyOwnColumn2")
            Text("MyOwnColumn3")
            Text("MyOwnColumn4")
        }
    }
}

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