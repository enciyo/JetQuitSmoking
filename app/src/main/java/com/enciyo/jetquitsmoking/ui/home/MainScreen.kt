package com.enciyo.jetquitsmoking.ui.home

import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enciyo.domain.dto.Task
import com.enciyo.jetquitsmoking.R
import com.enciyo.jetquitsmoking.ui.theme.TASK_ICONS
import com.enciyo.shared.isSameDay
import kotlinx.datetime.LocalDate


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
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2)
    ) {
        header(state.userName)
        taskItems(taskEntities = state.task, today = state.today, onTaskDetail = onTaskDetail)
    }
    LocalTextInputService.current?.hideSoftwareKeyboard()
}


private fun LazyGridScope.header(name: String) {
    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
        Header(userName = name)
    }
}

private fun LazyGridScope.taskItems(
    taskEntities: List<Task>,
    today: LocalDate,
    onTaskDetail: (Task) -> Unit,
) = itemsIndexed(
    items = taskEntities,
    span = { index, _ -> GridItemSpan(if (index == 0) maxCurrentLineSpan else 1) },
    key = { _, item -> item.taskId }) { index, item ->
    val isActive = today.isSameDay(item.taskTime.date)

    val compareResult = today.compareTo(item.taskTime.date)
    val color =
        if (compareResult == 0) MaterialTheme.colors.primary
        else if (compareResult < 0) MaterialTheme.colors.secondary
        else Color.Black
    Task(
        item = item,
        drawableRes = TASK_ICONS[index],
        isActive = isActive,
        color = color,
    ) { onTaskDetail(item) }
}


@Composable
private fun Header(modifier: Modifier = Modifier, userName: String) {
    Text(
        text = stringResource(id = R.string.welcome_back, userName),
        modifier = modifier
            .background(color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(bottomStartPercent = 0, bottomEndPercent = 100))
            .padding(vertical = 24.dp, horizontal = 12.dp),
        fontSize = MaterialTheme.typography.h6.fontSize,
        color = MaterialTheme.colors.onPrimary,
    )
}


@Composable
private fun Task(
    modifier: Modifier = Modifier,
    item: Task,
    @DrawableRes drawableRes: Int,
    isActive: Boolean,
    color: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy((-12).dp)
    ) {
        TaskImage(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            isActive = isActive,
            color = color,
            onClick = onClick,
            drawableRes = drawableRes
        )
        TaskText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            day = item.taskId,
            color = color
        )
    }
}

@Composable
private fun TaskText(
    modifier: Modifier = Modifier,
    day: Int,
    color: Color,
) {
    Text(
        text = stringResource(id = R.string.day, day),
        modifier = modifier
            .background(color = color, shape = RoundedCornerShape(12.dp))
            .width(TASK_SIZE)
            .padding(vertical = 3.dp),
        fontSize = MaterialTheme.typography.body2.fontSize,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onPrimary,
    )
}

@Composable
private fun TaskImage(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    color: Color,
    @DrawableRes drawableRes: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(TASK_SIZE)
            .background(color, shape = CircleShape)
            .clickable(
                enabled = isActive,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClick
            )
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier
                .size(IMAGE_SIZE)
                .align(Alignment.Center)
        )
    }
}
