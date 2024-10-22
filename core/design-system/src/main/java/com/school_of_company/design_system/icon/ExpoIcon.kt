package com.school_of_company.design_system.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.school_of_company.design_system.R

@Composable
fun ExpoLogoIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_expo_icon),
        contentDescription = stringResource(id = R.string.expo_main_icon_description),
        modifier = modifier
    )
}

@Composable
fun AeIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_ae_icon),
        contentDescription = stringResource(id = R.string.ae_icon_description),
        modifier = modifier
    )
}

@Composable
fun TimeIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_time_icon),
        contentDescription = stringResource(id = R.string.time_icon_description),
        modifier = modifier
    )
}

@Composable
fun FilterIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_filter_icon),
        contentDescription = stringResource(id = R.string.filter_icon_description),
        modifier = modifier
    )
}

@Composable
fun PeopleIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_people_icon),
        contentDescription = stringResource(id = R.string.people_icon_description),
        modifier = modifier
    )
}

@Composable
fun SearchIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_search_icon),
        contentDescription = stringResource(id = R.string.search_icon_description),
        modifier = modifier
    )
}

@Composable
fun ImageIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_image_icon),
        contentDescription = stringResource(id = R.string.image_icon_description),
        modifier = modifier
    )
}

@Composable
fun ExpoIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_expo1_icon),
        contentDescription = stringResource(id = R.string.expo_icon_description),
        modifier = modifier
    )
}

@Composable
fun PeopleListIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_people_list_icon),
        contentDescription = stringResource(id = R.string.people_list_icon_description),
        modifier = modifier
    )
}

@Composable
fun SettingIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_setting_icon),
        contentDescription = stringResource(id = R.string.setting_icon_description),
        modifier = modifier
    )
}

@Composable
fun DownArrowIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_down_arrow_icon),
        contentDescription = stringResource(id = R.string.down_arrow_icon),
        modifier = modifier
    )
}

@Composable
fun RightArrowIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_right_arrow_icon),
        contentDescription = stringResource(id = R.string.right_arrow_icon),
        modifier = modifier
    )
}

@Composable
fun LeftArrowIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_left_arrow_icon),
        contentDescription = stringResource(id = R.string.left_arrow_icon),
        modifier = modifier
    )
}

@Composable
fun UpArrowIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_up_arrow_icon),
        contentDescription = stringResource(id = R.string.up_arrow_icon),
        modifier = modifier
    )
}

@Composable
fun CancelIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_cancel_icon),
        contentDescription = stringResource(id = R.string.cancel_icon),
        modifier = modifier
    )
}