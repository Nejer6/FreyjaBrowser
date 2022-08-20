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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.nejer.freyja.APP
import com.nejer.freyja.MainViewModel
import com.nejer.freyja.R
import com.nejer.freyja.TopBar
import com.nejer.freyja.models.Branch
import com.nejer.freyja.navigation.NavRoute
import com.nejer.freyja.ui.theme.DarkBlue
import com.nejer.freyja.ui.theme.Orange

//@Composable
//fun ArchiveScreen(navController: NavHostController) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        TopBar {
//            IconButton(onClick = { APP.onBackPressed() }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_vector),
//                    contentDescription = "back to web",
//                    tint = DarkBlue
//                )
//            }
//        }
//
//        Folder(navController)
//    }
//
//}

//@Composable
//fun Folder(navController: NavHostController) {
//    val listBranches = remember {
//        mutableStateListOf(
//            Branch(
//                "root", mutableListOf(
//                    Branch(
//                        "first child", mutableListOf(
//                            Branch(
//                                "Жёсткое порево", mutableListOf(
//                                    Branch("stackoverflow.com"),
//                                    Branch("developer.android.com")
//                                )
//                            ),
//                            Branch("google.com"),
//                            Branch("yandex.ru")
//                        )
//                    ),
//                    Branch("youtube.com")
//                )
//            )
//        )
//    }
//
//    LazyColumn {
//        items(
//            listBranches.last().children
//        ) { branch ->
//            if (branch.children.size == 0) {
//                UrlCard(branch = branch, navController)
//            } else {
//                FolderCard(branch = branch, listBranches = listBranches)
//            }
//        }
//    }
//
//    if (listBranches.last().children.size == 0) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_searching),
//                contentDescription = "empty folder",
//                tint = Color.Unspecified
//            )
//        }
//    }
//
//    BackHandler(enabled = true) {
//        if (listBranches.size > 1) {
//            listBranches.removeLast()
//        } else {
//            navController.navigate(NavRoute.Main.route)
//        }
//    }
//}

@Composable
fun Card(onClick: () -> Unit, content: @Composable () -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(70.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(Orange)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        content()
    }
}

@Composable
fun FolderCard(branch: Branch, listBranches: SnapshotStateList<Branch>) {
    Card(onClick = {
        listBranches.add(branch)
    }) {
        Text(
            text = branch.value,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )

        Divider(color = DarkBlue.copy(0.3f), thickness = 1.dp)

        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_document),
                contentDescription = "elements inside",
                tint = DarkBlue
            )

            Text(text = branch.children.size.toString())
        }
    }
}

@Composable
fun UrlCard(url: String, navController: NavHostController) {
    Card(onClick = {
        APP.webView.loadUrl(url)
        APP.url.value = url
        navController.navigate(NavRoute.Main.route)
    }) {
        Text(
            text = url,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 17.sp
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_link),
            contentDescription = "link",
            tint = DarkBlue
        )
    }
}

@Composable
fun NewArchive(navController: NavHostController, viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            IconButton(onClick = { APP.onBackPressed() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vector),
                    contentDescription = "back to web",
                    tint = DarkBlue
                )
            }
        }

        ColumnOfUrls(viewModel, navController)
    }
}

@Composable
private fun ColumnOfUrls(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val urls = viewModel.getAllUrls().observeAsState(listOf()).value

    if (urls.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = R.drawable.ic_searching),
                contentDescription = "empty folder",
                tint = Color.Unspecified
            )
        }
    } else {
        LazyColumn {
            items(urls) { url ->
                UrlCard(url = url.url, navController = navController)
            }
        }
    }
}