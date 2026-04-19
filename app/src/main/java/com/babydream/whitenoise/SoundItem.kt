package com.babydream.whitenoise

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class SoundItem(
    val id: Int,
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int,
    @RawRes val rawRes: Int
)

val sounds = listOf(
    SoundItem(
        id = 0,
        nameRes = R.string.sound_uterine,
        iconRes = R.drawable.ic_uterine,
        rawRes = R.raw.uterine
    ),
    SoundItem(
        id = 1,
        nameRes = R.string.sound_rain,
        iconRes = R.drawable.ic_rain,
        rawRes = R.raw.rain
    ),
    SoundItem(
        id = 2,
        nameRes = R.string.sound_ocean,
        iconRes = R.drawable.ic_ocean,
        rawRes = R.raw.ocean
    ),
    SoundItem(
        id = 3,
        nameRes = R.string.sound_waterfall,
        iconRes = R.drawable.ic_waterfall,
        rawRes = R.raw.waterfall
    ),
    SoundItem(
        id = 4,
        nameRes = R.string.sound_fan,
        iconRes = R.drawable.ic_fan,
        rawRes = R.raw.fan
    ),
    SoundItem(
        id = 5,
        nameRes = R.string.sound_humidifier,
        iconRes = R.drawable.ic_humidifier,
        rawRes = R.raw.humidifier
    ),
    SoundItem(
        id = 6,
        nameRes = R.string.sound_radio_static,
        iconRes = R.drawable.ic_radio_static,
        rawRes = R.raw.radio_static
    ),
    SoundItem(
        id = 7,
        nameRes = R.string.sound_white_noise,
        iconRes = R.drawable.ic_white_noise,
        rawRes = R.raw.white_noise
    )
)