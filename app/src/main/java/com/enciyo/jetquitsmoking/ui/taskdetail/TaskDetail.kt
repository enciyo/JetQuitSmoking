package com.enciyo.jetquitsmoking.ui.taskdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enciyo.data.entity.PeriodEntity
import com.enciyo.domain.dto.Period
import com.enciyo.jetquitsmoking.R

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    vm: TaskDetailViewModel = hiltViewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val scroll = rememberLazyListState()
    LaunchedEffect(state) {
        scroll.scrollToItem(state.activePeriodIndex)
    }

    Box(modifier = modifier) {
        if (vm.needSmokeCount.toInt() > 0)
            Periods(
                scrollState = scroll,
                needSmokeCount = vm.needSmokeCount,
                period = state.taskPeriodEntities,
                activePeriodIndex = state.activePeriodIndex
            )
        else
            NoNeedSmokeToday()
    }
}

@Composable
private fun Periods(
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    needSmokeCount: String,
    period: List<Period>,
    activePeriodIndex: Int,
) {
    LazyColumn(modifier = modifier, state = scrollState) {
        item { Header(needSmokeCount = needSmokeCount) }
        itemsIndexed(period) { index, item ->
            val isActive = remember { activePeriodIndex == index }
            val color = Color.Black

            Item(period = item, color = color)
        }
    }
}


@Composable
private fun NoNeedSmokeToday(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
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
                text = stringResource(R.string.today_you_wont_smoke),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier, needSmokeCount: String) {
    Text(
        text = "In this task\nyou will smoke $needSmokeCount cigarettes.",
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(bottomStartPercent = 0, bottomEndPercent = 100)
            )
            .padding(vertical = 24.dp, horizontal = 12.dp),
        fontSize = MaterialTheme.typography.h6.fontSize,
        color = MaterialTheme.colors.onPrimary,
    )

}


@Composable
private fun Item(
    modifier: Modifier = Modifier,
    period: Period,
    color: Color,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(width = 6.dp, height = 30.dp)
                .background(color = color)
                .align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .background(color = color, shape = CircleShape)
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
