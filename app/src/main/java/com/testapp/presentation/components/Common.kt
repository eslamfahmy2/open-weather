package com.testapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.testapp.R
import com.testapp.domain.state.SearchSuggestionState
import com.testapp.domain.state.SearchWidgetState
import com.testapp.presentation.components.theme.backgroundClose

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    searchWidgetState: SearchWidgetState,
    onSearchTriggered: () -> Unit,
    searchList: List<String> = listOf(),
    modifier: Modifier,
    title: String
) {

    Box(
        modifier = modifier
            .animateContentSize(),
    ) {

        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = dimensionResource(id = R.dimen._20sdp))
                .align(Alignment.TopCenter),
            text = title,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary
        )

        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .defaultMinSize(minHeight = dimensionResource(id = R.dimen._56sdp))
                .animateContentSize()
                .align(Alignment.TopEnd),
            color = Color.Transparent
        ) {
            when (searchWidgetState) {
                SearchWidgetState.CLOSED -> {

                    IconButton(
                        onClick = { onSearchTriggered() },
                        modifier = Modifier
                            .padding(end = dimensionResource(id = R.dimen._8sdp))
                            .wrapContentWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    }
                }
                SearchWidgetState.OPENED -> {

                    val focusManager = LocalFocusManager.current
                    val searchSuggestionOpenState =
                        remember { mutableStateOf(SearchSuggestionState.CLOSED) }

                    LaunchedEffect(key1 = Unit) {
                        searchSuggestionOpenState.value = SearchSuggestionState.OPENED
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(
                                    bottomStart = dimensionResource(id = R.dimen._20sdp),
                                    bottomEnd = dimensionResource(id = R.dimen._25sdp)
                                )
                            )
                            .padding(top = dimensionResource(id = R.dimen._16sdp)),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = dimensionResource(id = R.dimen._16sdp))
                        ) {

                            IconButton(
                                modifier = Modifier
                                    .alpha(ContentAlpha.medium),
                                onClick = onCloseClicked
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Search Icon",
                                    tint = Color.Black
                                )
                            }

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(30),
                                value = text,
                                onValueChange = {
                                    onTextChange(it)
                                },
                                placeholder = {
                                    Text(
                                        modifier = Modifier
                                            .alpha(ContentAlpha.medium),
                                        text = "Search city",
                                        color = MaterialTheme.colors.onSurface
                                    )
                                },
                                textStyle = TextStyle(
                                    fontSize = MaterialTheme.typography.body1.fontSize
                                ),
                                singleLine = true,
                                trailingIcon = {
                                    if (text.isNotEmpty()) {
                                        IconButton(
                                            onClick = {
                                                if (text.isNotEmpty()) {
                                                    onTextChange("")
                                                } else {
                                                    onCloseClicked()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Close Icon",
                                                tint = MaterialTheme.colors.primary
                                            )
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        onSearchClicked(text)
                                        focusManager.clearFocus()
                                        onCloseClicked()
                                    }
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                                    focusedIndicatorColor = MaterialTheme.colors.primary,
                                    unfocusedIndicatorColor = MaterialTheme.colors.primary
                                )
                            )
                        }

                        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

                        if (searchSuggestionOpenState.value == SearchSuggestionState.OPENED && text.isNotEmpty() && searchList.isNotEmpty()) {

                            searchList.forEach { searchText ->
                                SearchListItem(
                                    searchText = searchText,
                                    onClicked = {
                                        onSearchClicked(searchText)
                                        focusManager.clearFocus()
                                        onCloseClicked()
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._4sdp)))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = backgroundClose,
                                        shape = RoundedCornerShape(
                                            bottomStart = dimensionResource(id = R.dimen._20sdp),
                                            bottomEnd = dimensionResource(id = R.dimen._25sdp)
                                        )
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(dimensionResource(id = R.dimen._6sdp))
                                        .clickable {
                                            searchSuggestionOpenState.value =
                                                SearchSuggestionState.CLOSED
                                        },
                                    imageVector = Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "close search suggestions"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchAppBarTablet(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    searchWidgetState: SearchWidgetState,
    onSearchTriggered: () -> Unit,
    searchList: List<String> = listOf(),
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .animateContentSize(),
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .defaultMinSize(minHeight = dimensionResource(id = R.dimen._56sdp))
                .animateContentSize()
                .align(Alignment.TopEnd),
            color = Color.Transparent
        ) {
            when (searchWidgetState) {
                SearchWidgetState.CLOSED -> {
                    IconButton(
                        onClick = { onSearchTriggered() },
                        modifier = Modifier
                            .padding(end = dimensionResource(id = R.dimen._8sdp))
                            .wrapContentWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    }
                }
                SearchWidgetState.OPENED -> {

                    val focusManager = LocalFocusManager.current
                    val searchSuggestionOpenState =
                        remember { mutableStateOf(SearchSuggestionState.CLOSED) }

                    LaunchedEffect(key1 = Unit) {
                        searchSuggestionOpenState.value = SearchSuggestionState.OPENED
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .padding(dimensionResource(id = R.dimen._16sdp)),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.End
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(.9f)
                        ) {

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(30),
                                value = text,
                                onValueChange = {
                                    onTextChange(it)
                                },
                                placeholder = {
                                    Text(
                                        modifier = Modifier
                                            .alpha(ContentAlpha.medium),
                                        text = "Search city",
                                        color = MaterialTheme.colors.onSurface
                                    )
                                },
                                textStyle = TextStyle(
                                    fontSize = MaterialTheme.typography.body1.fontSize
                                ),
                                singleLine = true,
                                trailingIcon = {
                                    if (text.isNotEmpty()) {
                                        IconButton(
                                            onClick = {
                                                if (text.isNotEmpty()) {
                                                    onTextChange("")
                                                } else {
                                                    onCloseClicked()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Close Icon",
                                                tint = MaterialTheme.colors.primary
                                            )
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        onSearchClicked(text)
                                        focusManager.clearFocus()
                                        onCloseClicked()
                                    }
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                                    focusedIndicatorColor = MaterialTheme.colors.primary,
                                    unfocusedIndicatorColor = MaterialTheme.colors.primary
                                )
                            )

                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

                            if (searchSuggestionOpenState.value == SearchSuggestionState.OPENED && text.isNotEmpty() && searchList.isNotEmpty()) {
                                Column(
                                    modifier = Modifier.background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(dimensionResource(id = R.dimen._15sdp))
                                    )
                                ) {
                                    searchList.forEach { searchText ->
                                        SearchListItem(
                                            searchText = searchText,
                                            onClicked = {
                                                onSearchClicked(searchText)
                                                focusManager.clearFocus()
                                                onCloseClicked()
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        IconButton(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen._4sdp))
                                .weight(1f, true),
                            onClick = onCloseClicked
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_full_arrow_left),
                                contentDescription = "back Icon",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchListItem(searchText: String, onClicked: () -> Unit) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClicked()
            }
            .padding(start = dimensionResource(id = R.dimen._8sdp))
            .padding(dimensionResource(id = R.dimen._8sdp)),
        text = searchText.replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Start
    )
}
