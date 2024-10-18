package com.example.mvvm.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.mvvm.navigation.ROUTE_ABOUT
import com.example.mvvm.navigation.ROUTE_INSERT
import com.example.mvvm.navigation.ROUTE_SHOP
import com.example.mvvm.navigation.ROUTE_VIEW_PRODUCTS


@Composable
fun Home(navController: NavController){
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {


        Text(text = "Home")


        Text(
            "Go to about",
            modifier = Modifier
                .clickable { navController.navigate(ROUTE_ABOUT) }
        )

        Button(onClick = { navController.navigate(ROUTE_ABOUT) }) {

            Text(text = "about")

        }


        Text(
            text = "view shop",
            modifier = Modifier
                .clickable { navController.navigate(ROUTE_SHOP) }

        )

        AsyncImage(
            model = "https://ezra.goldtech.co.ke/storage/logo-1.jpg",
            contentDescription = null,

            modifier = Modifier.size(200.dp
            )

        )



        Text(
            text = "Insert Product",
            modifier = Modifier
                .clickable { navController.navigate(ROUTE_INSERT) }

        )

        Text(
            text = "View Products",
            modifier = Modifier
                .clickable { navController.navigate(ROUTE_VIEW_PRODUCTS) }
        )
















                


    }





}