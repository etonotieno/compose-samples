/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetcaster.tv.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class JetcasterAppState(
    val navHostController: NavHostController
) {
    fun navigateToDiscover() {
        navHostController.navigate(Screen.Discover.route)
    }

    fun navigateToLibrary() {
        navHostController.navigate(Screen.Library.route)
    }

    fun navigateToProfile() {
        navHostController.navigate(Screen.Profile.route)
    }

    fun navigateToSearch() {
        navHostController.navigate(Screen.Search.route)
    }

    fun navigateToSettings() {
        navHostController.navigate(Screen.Settings.route)
    }

    fun showPodcastDetails(podcastUri: String) {
        val screen = Screen.Show(podcastUri)
        navHostController.navigate(screen.route)
    }

    fun playEpisode(episodeUri: String) {
        val screen = Screen.Player(episodeUri)
        navHostController.navigate(screen.route)
    }

    fun navigateBack() {
        navHostController.popBackStack()
    }
}

@Composable
fun rememberJetcasterAppState(
    navHostController: NavHostController = rememberNavController()
) =
    remember(navHostController) {
        JetcasterAppState(navHostController)
    }

sealed interface Screen {
    val route: String

    data object Discover : Screen {
        override val route = "/"
    }

    data object Library : Screen {
        override val route = "/library"
    }

    data object Search : Screen {
        override val route = "/search"
    }

    data object Profile : Screen {
        override val route = "/profile"
    }

    data object Settings : Screen {
        override val route: String = "/settings"
    }

    data class Show(private val podcastUri: String) : Screen {
        override val route = "$root/$podcastUri"

        companion object : Screen {
            private const val root = "/show"
            override val route = "$root/{showUri}"
        }
    }

    data class Player(private val episodeUri: String) : Screen {
        override val route = "$root/$episodeUri"

        companion object : Screen {
            private const val root = "/player"
            override val route = "$root/{episodeUri}"
        }
    }
}
