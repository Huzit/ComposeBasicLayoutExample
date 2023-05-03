package com.weather.composebasiclayoutexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.composebasiclayoutexample.ui.theme.ComposeBasicLayoutExampleTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicLayoutExampleTheme {
                TestApp(modifier = Modifier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestApp(modifier: Modifier) {
    Scaffold(
        bottomBar = { SootheBottomNavigation() }
    ) { padding ->
        HomeScreen(Modifier.padding(padding).background(color = androidx.compose.material.MaterialTheme.colors.background))
    }
}

@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        modifier,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(id = R.string.bottom_navigation_home))
            },
            selected = true,
            onClick = { }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_profile))
            },
            selected = true,
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
        HomeSection(title = R.string.favorite_collections) {
            FavoriteCollectionGrid()
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(id = title).uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

//아이콘 리사이클러
@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
    ) {
        items(10) { item ->
            AlignYourBodyElement(drawable = R.drawable.ic_launcher_background, text = R.string.name)
        }
    }
}

//몸 아이콘
@Composable
fun AlignYourBodyElement(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    @StringRes text: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(88.dp)
        )
        Text(
            text = stringResource(id = text),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
        )
    }
}

//검색창
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .heightIn(min = 56.dp)
            .fillMaxWidth()
    )
}

//컬렉션 그리드
@Composable
fun FavoriteCollectionGrid(
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(120.dp)
    ) {
        items(10) { item ->
            FavoriteCollectionCard(
                drawable = R.drawable.ic_launcher_background,
                text = R.string.fc2_nature_meditations,
                modifier = Modifier.height(56.dp)
            )
        }
    }
}

//즐겨찾는 컬렉션 카드
@Composable
fun FavoriteCollectionCard(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    @StringRes text: Int
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp)
            )
            Text(text = stringResource(text))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun HomeSectionPreview() {
    ComposeBasicLayoutExampleTheme() {
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun MainPreview() {
    ComposeBasicLayoutExampleTheme {
        TestApp(modifier = Modifier)
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    Column(modifier = Modifier.padding(10.dp)) {
        SearchBar()
        AlignYourBodyElement(
            modifier = Modifier.padding(8.dp),
            drawable = R.drawable.ic_launcher_foreground,
            text = R.string.name
        )
        FavoriteCollectionCard(
            drawable = R.drawable.ic_launcher_background,
            text = R.string.fc2_nature_meditations
        )
    }
}