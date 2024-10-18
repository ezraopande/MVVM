package com.example.mvvm.ui.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ViewProductsScreen(navController: NavController) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isEmpty by remember { mutableStateOf(false) }

    val firestore = FirebaseFirestore.getInstance()

    // Fetch products from Firestore
    LaunchedEffect(Unit) {
        firestore.collection("products")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val fetchedProducts = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Product::class.java)?.apply {
                        id = document.id  // Assign document ID to product
                    }
                }
                products = fetchedProducts
                isLoading = false
                isEmpty = fetchedProducts.isEmpty()
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    // UI
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
        } else if (isEmpty) {
            Text(text = "No products found", fontSize = 20.sp, color = Color.Gray)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                items(products) { product ->
                    ProductItem(product) {
                        navController.navigate("details/${product.id}")  // Navigate to details screen with product ID
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },  // Handle click to navigate to details screen
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            product.image?.let {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = product.name, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Price: ${product.price}", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = product.description, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}
// Product data class
data class Product(
    var id: String = "",
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val image: String? = null
)
