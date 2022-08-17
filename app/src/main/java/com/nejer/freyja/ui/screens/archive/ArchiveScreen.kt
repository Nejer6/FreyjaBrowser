package com.nejer.freyja.ui.screens.archive

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nejer.freyja.APP
import com.nejer.freyja.R
import com.nejer.freyja.TopBar
import com.nejer.freyja.models.Branch

@Composable
fun Folder(screen: MutableState<Int>) {
    val listBranches = remember {
        mutableStateListOf(
            Branch(
                "root", mutableListOf(
                    Branch(
                        "first child", mutableListOf(
                            Branch("thee branch")
                        )
                    ),
                    Branch("second child")
                )
            )
        )

    }

    LazyColumn {
        items(
            listBranches.last().children
        ) { branch ->
            Text(text = branch.value, modifier = Modifier.clickable {
                listBranches.add(branch)
            })
        }
    }

    BackHandler(enabled = true) {
        if (listBranches.size > 1) {
            listBranches.removeLast()
        } else {
            screen.value = 0
        }
    }
}

@Composable
fun Archive(screen: MutableState<Int>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            IconButton(onClick = { APP.onBackPressed() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "back to web"
                )
            }
        }

        Folder(screen)
    }

}