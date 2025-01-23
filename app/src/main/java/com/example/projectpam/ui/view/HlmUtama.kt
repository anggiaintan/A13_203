package com.example.projectpam.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectpam.R


val PrimaryColor = Color(0xFF87A9D0)
val AccentColor = Color(0xFFF8C5D4)
val BackgroundColor = Color(0xFFEDEDED)
val TextColor = Color.Black

@Composable
fun HalamanUtama(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = BackgroundColor)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = R.drawable.tiketku),
                        contentDescription = "Logo TiketKu"
                    )
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Column {
                        Text(
                            text = "TiketKu",
                            color = PrimaryColor,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify
                        )
                        Text(
                            text = "Aplikasi TiketKu by Anggia",
                            color = AccentColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Kontainer konten utama
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White,
                                    AccentColor.copy(alpha = 0.1f)
                                )
                            ),
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Judul
                        Text(
                            text = "Halo! Selamat Datang di Tiketku",
                            color = TextColor,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Subtitle
                        Text(
                            text = "Pilih menu berikut untuk mulai",
                            color = TextColor.copy(alpha = 0.7f),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        // Tombol Menu
                        MenuButton(
                            text = "Peserta",
                            icon = Icons.Default.Person,
                            navController = navController,
                            route = "Peserta"
                        )

                        MenuButton(
                            text = "Event",
                            icon = Icons.Default.Star,
                            navController = navController,
                            route = "Event"
                        )

                        MenuButton(
                            text = "Tiket",
                            icon = Icons.Default.Email,
                            navController = navController,
                            route = "Tiket"
                        )

                        MenuButton(
                            text = "Transaksi",
                            icon = Icons.Default.ShoppingCart,
                            navController = navController,
                            route = "Transaksi"
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun MenuButton(
    text: String,
    icon: ImageVector,
    navController: NavController,
    route: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navController.navigate(route) },
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = Color.White
        ),
        modifier = modifier
            .padding(vertical = 8.dp)
            .height(70.dp)
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Ikon $text",
                tint = Color.White,
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
