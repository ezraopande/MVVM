package com.example.mvvm.ui.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mvvm.navigation.ROUTE_SHOP

@Composable
fun About(navController: NavController){

    Column {
        Text(text = "About screen")


        Text(
            text = "shop",
            modifier= Modifier
                .clickable { navController.navigate(ROUTE_SHOP) }
        )











    }



}