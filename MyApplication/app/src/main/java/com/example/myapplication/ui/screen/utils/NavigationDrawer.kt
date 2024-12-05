package com.example.myapplication.ui.screen.utils

import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.ui.screen.Screen
import com.example.myapplication.ui.viewModels.UserScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(scope: CoroutineScope, drawerState: DrawerState,
                     userScreenViewModel: UserScreenViewModel = viewModel(), navHostController: NavHostController){

    val userStatsGetDTO by userScreenViewModel.userStatsGetDTO.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {ModalDrawerSheet {
            Text("${userStatsGetDTO.appUser?.firstName ?: "Unknown"} " +
                    (userStatsGetDTO.appUser?.lastName ?: "Unknown"), modifier = Modifier.padding(16.dp))
            Divider()
            NavigationDrawerItem(
                label = { Text(text = "My profile") },
                selected = false,
                onClick = {
                    navHostController.navigate(Screen.UserScreen.route)
                    scope.launch { drawerState.close() }
                          },
                icon = {
                    Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "My profile"
                    )
                }
            )

            NavigationDrawerItem(
                label = { Text(text = "Ranking") },
                selected = false,
                onClick = { navHostController.navigate(Screen.UsersRankingScreen.route)
                    scope.launch { drawerState.close() }},
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Assessment,
                        contentDescription = "Ranking"
                    )
                }
            )

        }}
    ) {
        Scaffold(
            topBar = {
                AppBar(scope, drawerState)
            }
        ) {
            paddingValues ->
            MainNavigation(modifier = Modifier.padding(paddingValues), navHostController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(scope: CoroutineScope, drawerState: DrawerState){
    TopAppBar(
        title = {
            Text(text = "FitAlly", fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    )
}