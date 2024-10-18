package com.example.mvvm.ui.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DetailsScreen(productId: String) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val firestore = FirebaseFirestore.getInstance()

    // Fetch product details from Firestore using product ID
    LaunchedEffect(productId) {
        firestore.collection("products").document(productId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                product = documentSnapshot.toObject(Product::class.java)
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }


    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (product == null) {
            Text(text = "Product not found", fontSize = 20.sp, color = Color.Gray)
        } else {
            ProductDetails(product!!)
        }
    }
}

@Composable
fun ProductDetails(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        product.image?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = product.name, fontSize = 24.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Price: ${product.price}", fontSize = 20.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.description, fontSize = 16.sp, color = Color.DarkGray)
    }
}
