package com.babydream.whitenoise

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes

// ============================================================
// SOUND FILE SOURCES — Download and place in res/raw/
// ============================================================
// uterine.ogg → https://music.youtube.com/watch?v=Cb5tJKN_zTA
//
// rain.ogg → https://freesound.org/people/itinerantmonk108/sounds/772074/
// License: CC BY 4.0
//
// ocean.ogg → https://freesound.org/people/Mick%20Gibbs/sounds/360333/
// License: CC0
//
// waterfall.ogg → https://pixabay.com/sound-effects/waterfall-ambience-loop-32917/
// License: Pixabay free license
//
// fan.ogg → https://pixabay.com/sound-effects/film-special-effects-fan-looped-102085/
// License: Pixabay free license
//
// humidifier.ogg → https://pixabay.com/sound-effects/film-special-effects-humidifier-73917/
// License: Pixabay free license
//
// radio_static.ogg → https://freesound.org/people/nakkivene66/sounds/583365/
// License: CC0
//
// white_noise.ogg → https://freesound.org/search/?q=Shhh+Sound
// License: CC0
//
// ============================================================
// CRITICAL EXPORT INSTRUCTIONS — READ BEFORE PROCESSING
// ============================================================
//
// The goal is for each sound to be PERCEPTUALLY DISTINCT from the others.
// Do NOT apply uniform processing to all files. Each sound has a unique
// spectral and dynamic character that must be preserved.
//
// GENERAL RULES (apply to all files):
// • Use Audacity (free) or Reaper for all editing
// • Export format: OGG Vorbis, STEREO (NOT mono), Quality 6 (~192 kbps)
// • Sample rate: 44100 Hz
// • Target duration: 30–90 seconds of seamlessly loopable audio
// • To create a seamless loop: use Effect → Crossfade Loops, or manually
// align the waveform so the end fades into the beginning with no click/pop
// • Normalize each file independently to -3 dB peak AFTER all processing
// • Do NOT apply the same EQ or compression to every file
//
// ============================================================
// PER-SOUND EXPORT PROFILES
// ============================================================
//
// uterine.ogg — WOMB / HEARTBEAT
// Character: Deep, low-frequency, muffled, rhythmic pulse
// • Keep full stereo width
// • Apply low-pass filter: cut everything above 800 Hz (simulates womb wall)
// • Boost low-mids: +4 dB shelf at 120 Hz
// • Add subtle reverb (room size 30%, decay 1.2s) for "enclosed" feel
// • The heartbeat rhythm must stay clearly audible — do not over-compress
// • Final level: slightly quieter than other sounds (-6 dB peak)
//
// rain.ogg — SOFT RAIN
// Character: Broadband, airy, gentle, high-frequency shimmer
// • Keep stereo width wide
// • Do NOT apply low-pass filter — rain needs its high-frequency "hiss"
// • Slight high-shelf boost: +2 dB above 6 kHz for airiness
// • Very light compression: ratio 2:1, threshold -24 dB, slow attack (50ms)
// • Ensure no sudden volume spikes (thunder, cars) — cut those sections out
// • Loop point: find a 45s segment with consistent density
//
// ocean.ogg — OCEAN WAVES / SEA
// Character: Slow, rhythmic surge and retreat, deep rumble + mid splash
// • Keep full stereo (the left-right movement of waves is important)
// • Gentle low-shelf boost: +3 dB at 80 Hz for the "weight" of the sea
// • Do NOT high-pass filter — the sub-bass rumble is part of the experience
// • Slow compression: ratio 3:1, attack 200ms, release 800ms
// • Loop tip: ocean loops sound best at 60–90s so wave rhythm feels natural
// • Avoid loops that cut mid-wave — always loop at the "quiet" trough point
//
// waterfall.ogg — WATERFALL
// Character: Continuous roar, dense broadband noise, no clear rhythm
// • This is the most "white noise"-like of the natural sounds — keep it dense
// • Slight mid-range cut: -2 dB around 2–4 kHz to reduce harshness
// • Light low-shelf boost: +2 dB at 100 Hz for body
// • No reverb needed — waterfalls are already spatially rich
// • Compression: ratio 4:1 for a very steady, constant level (no dynamics)
// • This sound should feel "powerful" — do not over-attenuate the low end
//
// fan.ogg — ELECTRIC FAN HUM
// Character: Mechanical, tonal, steady drone with subtle oscillation
// • This is a TONAL sound — preserve the fundamental hum frequency
// • Do NOT apply heavy EQ — the mechanical character must be recognizable
// • If the original has an oscillation (volume sweeping left-right), KEEP IT
// • Light low-pass: gentle rolloff above 4 kHz (fans are not bright sounds)
// • Very tight compression: ratio 6:1 — fan should sound perfectly steady
// • Loop must be PHASE-PERFECT — any click or bump at the loop point will
// be immediately noticeable due to the tonal nature of this sound
//
// humidifier.ogg — HUMIDIFIER MIST
// Character: Softer and higher-pitched than fan, breathy hiss, less tonal
// • Mid-forward sound — boost: +2 dB around 1–2 kHz
// • High-pass filter: roll off below 200 Hz (humidifiers are not bass-heavy)
// • This sound must be PERCEPTIBLY DIFFERENT from fan.ogg:
// fan.ogg = lower pitch, mechanical drone, tonal
// humidifier = higher pitch, breathy hiss, less tonal
// • If the two files sound similar after export, pitch-shift humidifier.ogg
// up by +2 semitones to increase contrast
// • Steady compression: ratio 5:1
//
// radio_static.ogg — RADIO OUT OF TUNE / AM STATIC
// Character: Crackling, grainy, mid-forward, irregular noise bursts
// • This is the ONLY sound that should feel slightly unpredictable/irregular
// • Do NOT heavy-compress — the crackle variations are part of the character
// • Band-pass filter: keep 300 Hz – 3 kHz (AM radio frequency range)
// • Cut everything below 200 Hz and above 5 kHz
// • Light saturation/distortion (+5% drive in Audacity Distortion effect)
// to enhance the "cheap radio" quality
// • If the file has music or voices beneath the static, cut that section
// • Loop at a moment of continuous static (not during a crackle burst)
//
// white_noise.ogg — PURE WHITE NOISE ("Shhhhh")
// Character: Flat, featureless, perfectly uniform broadband noise
// • This is the REFERENCE sound — perfectly flat frequency response
// • Do NOT apply any EQ, boost, or cut — keep it spectrally neutral
// • Apply hard limiting at -1 dB to prevent any clipping
// • Compression: ratio 10:1 — this must be the most CONSTANT, FLAT sound
// • It should sound noticeably "flatter" and more uniform than waterfall.ogg
// • Loop point does not matter — white noise is perfectly seamless anywhere
// • Tip: generate directly in Audacity via Generate → Noise → White
// if the downloaded file has any artifacts
//
// ============================================================
// FINAL CHECKLIST BEFORE ADDING TO res/raw/
// ============================================================
// [ ] Play all 8 files back-to-back — each one must sound DISTINCTLY different
// [ ] uterine = muffled deep pulse (unique: rhythmic + low-pass filtered)
// [ ] rain = airy high-frequency hiss (unique: brightest of all sounds)
// [ ] ocean = slow surging rumble (unique: only sound with slow rhythm)
// [ ] waterfall= dense continuous roar (unique: loudest, most powerful)
// [ ] fan = steady tonal mechanical (unique: has a clear pitch/hum)
// [ ] humidifier= breathy mid-range hiss (unique: softer and higher than fan)
// [ ] radio = grainy crackling static (unique: only irregular/imperfect one)
// [ ] white_noise = perfectly flat noise (unique: flattest spectral profile)
// [ ] No two files should be confusable at 50% phone volume in a quiet room
// [ ] All files loop without audible click, pop, or jump
// [ ] All files are OGG Vorbis, stereo, 44100 Hz, Quality 6
// ============================================================

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