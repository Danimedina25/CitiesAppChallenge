package com.danifitdev.citiesappchallenge.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.danifitdev.citiesappchallenge.R

val ArrowRightIcon: Painter
    @Composable
    get() = painterResource(id = R.drawable.arrow_right)

val ArrowLeftIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.arrow_left)

