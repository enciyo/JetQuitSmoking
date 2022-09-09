package com.enciyo.jetquitsmoking.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enciyo.data.entity.Task
import com.enciyo.jetquitsmoking.R
import com.enciyo.jetquitsmoking.ui.theme.TASK_ICONS
import com.enciyo.shared.isSameDay
import com.enciyo.shared.today


private val IMAGE_SIZE get() = 60.dp
private val TASK_SIZE get() = 120.dp


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    vm: MainViewModel = hiltViewModel(),
    onTaskDetail: (Task) -> Unit,
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val span: LazyGridItemSpanScope.(index: Int, item: Any) -> GridItemSpan = { index, item ->
        GridItemSpan(if (index == 0) maxCurrentLineSpan else 1)
    }
    val headerSpan: LazyGridItemSpanScope.() -> GridItemSpan = { GridItemSpan(maxCurrentLineSpan) }
    val columns = GridCells.Fixed(2)
    val now by remember {
        mutableStateOf(today().date)
    }

    LazyVerticalGrid(columns = columns, modifier = modifier) {
        item(span = headerSpan) {
            Header(userName = state.account?.name.orEmpty())
        }
        itemsIndexed(state.tasks, span = span) { index, item ->
            Task(item = item, index = index, isActive = now.isSameDay(item.time.date)) { onTaskDetail(item) }
        }
    }

}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    userName: String,
) {
    Text(
        text = stringResource(id = R.string.welcome_back, userName),
        modifier = modifier
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
private fun Task(
    modifier: Modifier = Modifier,
    item: Task,
    index: Int,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val color = if (isActive) MaterialTheme.colors.primary
    else MaterialTheme.colors.secondary

    LocalTextInputService.current?.hideSoftwareKeyboard()

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy((-12).dp)
    ) {
        Box(
            modifier = Modifier
                .size(TASK_SIZE)
                .align(Alignment.CenterHorizontally)
                .background(color, shape = CircleShape)
                .clickable(
                    enabled = isActive,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onClick
                )
        ) {
            Image(
                painter = painterResource(id = TASK_ICONS[index]),
                contentDescription = null,
                modifier = Modifier
                    .size(IMAGE_SIZE)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = stringResource(id = R.string.day, item.taskId),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(
                    color,
                    shape = RoundedCornerShape(12.dp)
                )
                .width(TASK_SIZE)
                .padding(vertical = 3.dp),
            fontSize = MaterialTheme.typography.body2.fontSize,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
        )
    }

}


