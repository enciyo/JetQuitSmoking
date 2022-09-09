package com.enciyo.jetquitsmoking.ui.taskdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enciyo.data.entity.Period
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    vm: TaskDetailViewModel = hiltViewModel(),
    taskId: Int,
    needSmokeCount: Int,
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(state) {
        lazyListState.scrollToItem(state.activePeriodIndex)
    }

    LaunchedEffect(Unit) { vm.taskId(taskId) }

    if (needSmokeCount > 0) {
        LazyColumn(modifier = modifier, state = lazyListState) {
            item {
                Header(needSmokeCount = needSmokeCount)
            }

            itemsIndexed(state.taskPeriods) { index, item ->
                Item(period = item, isActive = state.activePeriodIndex == index)
            }

        }
    } else NoNeedSmokeToday()


}

@Composable
private fun NoNeedSmokeToday(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(size = 240.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
        ) {
            Text(
                text = "Today you won't smoke.",
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier, needSmokeCount: Int) {
    Text(
        text = "In this task\nyou will smoke $needSmokeCount cigarettes.",
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.primary,
                shape = RoundedCornerShape(bottomStartPercent = 0, bottomEndPercent = 100)
            )
            .padding(vertical = 24.dp, horizontal = 12.dp),
        fontSize = MaterialTheme.typography.h6.fontSize,
        color = MaterialTheme.colors.onPrimary,
    )

}


@Composable
private fun Item(modifier: Modifier = Modifier, period: Period, isActive: Boolean = false) {
    val color = if (isActive) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(6.dp)
                .background(color = color)
                .align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .background(color, CircleShape)
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = period.time.time.toString(),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
