package com.nejer.freyja.ui.screens.archive

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nejer.freyja.*
import com.nejer.freyja.R
import com.nejer.freyja.models.Folder
import com.nejer.freyja.navigation.NavRoute
import com.nejer.freyja.ui.screens.utils.TopBar

@Composable
fun NewArchive(navController: NavHostController, viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        val currentPath = remember {
            mutableStateOf("")
        }

        TopBar {
            IconButton(onClick = { APP.onBackPressed() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vector),
                    contentDescription = Constants.Text.BACK_TO_WEB,
                    tint = Constants.Colors.DarkBlue
                )
            }

            Text(text = currentPath.value)
        }

        FoldersColumn(navController, viewModel, currentPath)
    }
}

@Composable
private fun FoldersColumn(
    navController: NavHostController,
    viewModel: MainViewModel,
    currentPath: MutableState<String>,
) {

    val currentFolder = remember {
        mutableStateOf(viewModel.mainFolder)
    }

    if (currentFolder.value.children.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = R.drawable.ic_searching),
                contentDescription = Constants.Text.EMPTY_FOLDER,
                tint = Color.Unspecified
            )
        }
    } else {
        LazyColumn {
            items(currentFolder.value.children) { folder ->

                if (folder.children.isEmpty()) {
                    UrlCard(
                        url = currentPath.value + folder.value,
                        navController = navController,
                        title = folder.value
                    )
                } else {
                    FolderCard(currentFolder, folder, currentPath, navController)
                }
            }
        }
    }

    val parent = currentFolder.value.parent
    BackHandler(enabled = parent != null) {
        currentPath.value = currentPath.value.removeSuffix(currentFolder.value.value + "/")
        currentFolder.value = parent!!
    }
}

@Composable
private fun FolderCard(
    currentFolder: MutableState<Folder>,
    folder: Folder,
    currentPath: MutableState<String>,
    navController: NavHostController
) {
    Card(onClick = {
        currentFolder.value = folder
        currentPath.value += "${folder.value}/"
    }) {
        Row {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = folder.value,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Divider(color = Constants.Colors.DarkBlue.copy(0.3f), thickness = 1.dp)

                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_document),
                        contentDescription = Constants.Text.ELEMENTS_INSIDE,
                        tint = Constants.Colors.DarkBlue
                    )

                    Text(text = folder.children.size.toString())
                }
            }

            IconButton(onClick = {
                clickOnUrl(
                    url = currentPath.value + folder.value,
                    navController = navController
                )
            }, modifier = Modifier.offset(10.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = Constants.Text.LINK,
                    tint = Constants.Colors.DarkBlue
                )
            }
        }
    }
}

@Composable
fun Card(onClick: () -> Unit, content: @Composable () -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(70.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(Constants.Colors.Orange)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        content()
    }
}

@Composable
fun UrlCard(title: String, url: String, navController: NavHostController) {
    Card(onClick = {
        clickOnUrl(url, navController)
    }) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 17.sp
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_link),
            contentDescription = Constants.Text.LINK,
            tint = Constants.Colors.DarkBlue
        )
    }
}


private fun clickOnUrl(url: String, navController: NavHostController) {
    APP.webView.loadUrl(url)
    APP.url.value = url
    navController.navigate(NavRoute.Main.route)
}