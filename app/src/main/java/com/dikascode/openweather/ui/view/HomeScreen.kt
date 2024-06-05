package com.dikascode.openweather.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var city by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (city.text.isEmpty()) {
                Toast.makeText(context, "Please enter a city name", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate("weatherDetail")
            }
        }) {
            Text("Get Weather")
        }
    }
}
