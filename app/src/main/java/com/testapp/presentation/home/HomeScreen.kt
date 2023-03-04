package com.testapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.testapp.R
import com.testapp.domain.getFormattedTime
import com.testapp.domain.getPrettyFormattedTime
import com.testapp.domain.models.Forecast
import com.testapp.domain.models.Weather
import com.testapp.domain.state.HomeScreenState
import com.testapp.domain.state.SearchWidgetState
import com.testapp.presentation.components.SearchAppBar
import com.testapp.presentation.components.SearchAppBarTablet
import com.testapp.presentation.components.theme.gradientEnd
import com.testapp.presentation.components.theme.gradientStart
import com.testapp.utils.*


@Composable
fun HomeScreen(
    uiState: HomeScreenState,
    searchBarState: SearchWidgetState,
    searchText: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    previousSearchList: List<String>,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.unsplash),
            contentDescription = "background",
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.horizontalGradient(
                        colors = listOf(gradientStart, gradientEnd, gradientStart),
                        startX = size.width / 5,
                        endX = size.width / 1
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            contentScale = ContentScale.Crop
        )

        when (uiState) {
            HomeScreenState.Loading -> {
                CircularProgressIndicator()
            }
            is HomeScreenState.Error -> {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = dimensionResource(id = R.dimen._18sdp))
                        .align(Alignment.TopCenter),
                    text = uiState.msg ?: "-",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            is HomeScreenState.Success -> {

                val configuration = LocalConfiguration.current
                val expanded = configuration.screenWidthDp > 840
                if (expanded) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        WeatherHome(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            weather = uiState.weather,
                            showTitle = true
                        )

                        SearchAppBarTablet(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            searchWidgetState = searchBarState,
                            text = searchText,
                            onTextChange = onTextChange,
                            onCloseClicked = onCloseClicked,
                            onSearchClicked = onSearchClicked,
                            onSearchTriggered = onSearchTriggered,
                            searchList = previousSearchList
                        )
                    }

                } else {

                    WeatherHome(
                        modifier = Modifier
                            .fillMaxWidth(),
                        weather = uiState.weather,
                        showTitle = false
                    )

                    SearchAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopEnd),
                        searchWidgetState = searchBarState,
                        text = searchText,
                        onTextChange = onTextChange,
                        onCloseClicked = onCloseClicked,
                        onSearchClicked = onSearchClicked,
                        onSearchTriggered = onSearchTriggered,
                        searchList = previousSearchList,
                        title = uiState.weather.localtime?.getFormattedTime(
                            BE_CURRENT_DATE_FORMAT,
                            FE_HOURS_DATE_FORMAT
                        ) ?: "",
                    )
                }
            }
        }
    }
}


@Composable
fun WeatherHome(weather: Weather, modifier: Modifier, showTitle: Boolean = false) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen._16sdp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showTitle) {
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = weather.localtime?.getFormattedTime(
                    BE_CURRENT_DATE_FORMAT,
                    FE_HOURS_DATE_FORMAT
                ) ?: "",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))
        }
        Text(
            modifier = Modifier
                .wrapContentWidth(),
            text = weather.name ?: "",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))

        Text(
            modifier = Modifier
                .wrapContentWidth(),
            text = weather.localtime?.getFormattedTime(
                BE_CURRENT_DATE_FORMAT,
                FE_FULL_DATE_FORMAT
            ) ?: "",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .placeholder(R.drawable.ic_sunny)
                .data("https:${weather.icon}")
                .build()
        )
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen._40sdp)),
            painter = painter,
            contentDescription = "sun icon"
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

        val degrees = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(weather.temp_f.toString())
            }
            withStyle(
                style = SpanStyle(fontWeight = FontWeight.Thin)
            ) {
                append(stringResource(id = R.string.f_degree))
            }
        }

        Text(
            modifier = Modifier
                .wrapContentWidth(),
            text = degrees,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))

        Text(
            modifier = Modifier
                .wrapContentWidth(),
            text = weather.text ?: "",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))

        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_wind),
                contentDescription = "wind icon"
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(dimensionResource(id = R.dimen._4sdp)),
                text = "${weather.wind_mph} ${stringResource(id = R.string.mph)} ",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onPrimary
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

            Image(
                painter = painterResource(id = R.drawable.ic_droplet),
                contentDescription = "droplet icon"
            )
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(dimensionResource(id = R.dimen._4sdp)),
                text = "${weather.humidity}${stringResource(id = R.string.percent)} ",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))
        Row {
            weather.forecast?.forEach { forecast ->
                ForecastDayItem(forecast)
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))

            }
        }
    }
}

@Composable
fun ForecastDayItem(forecast: Forecast) {
    Column(
        modifier = Modifier.wrapContentWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .placeholder(R.drawable.ic_sunny)
                .data("https:${forecast.icon}")
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "icon",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen._20sdp))
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))

        Text(
            modifier = Modifier
                .wrapContentWidth(),
            text = "${forecast.mintemp_f}°/${forecast.maxtemp_f}${stringResource(id = R.string.f_degree)}",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))

        Text(
            modifier = Modifier
                .wrapContentWidth(),
            text = forecast.date?.getPrettyFormattedTime(
                BE_FORECAST_DATE_FORMAT,
                FE_DAY_NAME_DATE_FORMAT
            ) ?: "",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}


