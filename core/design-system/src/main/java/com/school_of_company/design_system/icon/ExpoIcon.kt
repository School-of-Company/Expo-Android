package com.school_of_company.design_system.icon

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.school_of_company.design_system.R

@Composable
fun ExpoMainLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_eexxppoo),
        contentDescription = stringResource(id = R.string.main_expo_logo_description),
        modifier = modifier
    )
}

@Composable
fun QrGuideImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.qr_guide),
        contentDescription = stringResource(id = R.string.qr_guide),
        modifier = modifier
    )
}

@Composable
fun DownArrowIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_downarrowicon),
        contentDescription = stringResource(id = R.string.down_arrow_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun RightArrowIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_right_arrow),
        contentDescription = stringResource(id = R.string.right_arrow_description),
        modifier = modifier
    )
}

@Composable
fun LeftArrowIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_left_arrow),
        contentDescription = stringResource(id = R.string.left_arrow_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun UpArrowIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_up_arrow),
        contentDescription = stringResource(id = R.string.up_arrow_description),
        modifier = modifier
    )
}

@Composable
fun XIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_x),
        contentDescription = stringResource(id = R.string.x_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun ExpoIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_expo),
        contentDescription = stringResource(id = R.string.expo_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun FilterIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_filter),
        contentDescription = stringResource(id = R.string.filter_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun ImageIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_image),
        contentDescription = stringResource(id = R.string.image_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun CheckIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_check),
        contentDescription = stringResource(id = R.string.check_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun PlusIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_plus),
        contentDescription = stringResource(id = R.string.plus_description),
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun TrashIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_trash),
        contentDescription = stringResource(id = R.string.trash_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun UserIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_user),
        contentDescription = stringResource(id = R.string.user_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun WarnIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_warn),
        contentDescription = stringResource(id = R.string.warn_description),
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun WonderIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_wonder),
        contentDescription = stringResource(id = R.string.wonder_description),
        modifier = modifier
    )
}

@Composable
fun CopyIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_copy),
        contentDescription = stringResource(id = R.string.copy_description),
        modifier = modifier
    )
}

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_circle),
        contentDescription = stringResource(id = R.string.circle_description),
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun BellIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_bell),
        contentDescription = stringResource(id = R.string.bell_description),
        modifier = modifier
    )
}

@Composable
fun LogoutIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_logout),
        contentDescription = stringResource(id = R.string.logout_description),
        modifier = modifier
    )
}

@Composable
fun SettingIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_setting),
        contentDescription = stringResource(id = R.string.setting_description),
        modifier = modifier
    )
}

@Composable
fun CheckBoxIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_check_box),
        contentDescription = stringResource(id = R.string.checkbox_description),
        modifier = modifier
    )
}

@Composable
fun DropDownIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_drop_down),
        contentDescription = stringResource(id = R.string.drop_down_description),
        modifier = modifier
    )
}

@Composable
fun TwoCircleIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_two_circle),
        contentDescription = stringResource(id = R.string.two_circle_description),
        modifier = modifier
    )
}

@Composable
fun LongContentIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_long_content),
        contentDescription = stringResource(id = R.string.long_content_description),
        modifier = modifier
    )
}

@Composable
fun ShortContentIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_short_content),
        contentDescription = stringResource(id = R.string.short_content_description),
        modifier = modifier
    )
}

@Composable
fun HomeIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_house),
        contentDescription = stringResource(id = R.string.home_description),
        modifier = modifier
    )
}

@Composable
fun AppendIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_append),
        contentDescription = stringResource(id = R.string.append_description),
        modifier = modifier
    )
}

@Composable
fun ProgramIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_program),
        contentDescription = stringResource(id = R.string.program_description),
        modifier = modifier
    )
}

@Composable
fun LocationIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_location),
        contentDescription = stringResource(id = R.string.location_icon_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun EyeIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = if (isSelected) painterResource(id = R.drawable.ic_open_eye) else painterResource(
            id = R.drawable.ic_eye_close
        ),
        contentDescription = "눈 아이콘",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun LogoutIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_logout),
        contentDescription = "로그아웃 아이콘",
        modifier = modifier,
        tint = tint
    )
}