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
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.view.viewevent.DestinasiHomeEvent
import com.example.projectpam.ui.view.viewpeserta.DestinasiHomePeserta
import com.example.projectpam.ui.view.viewtiket.DestinasiHomeTiket
import com.example.projectpam.ui.view.viewtransaksi.DestinasiHomeTransaksi


val PrimaryColor = Color(0xFF87A9D0)
val AccentColor = Color(0xFFF8C5D4)
val BackgroundColor = Color(0xFFEDEDED)
val TextColor = Color.Black

object DestinasiHalamanUtama : DestinasiNavigasi {
    override val route = "halaman_utama"
    override val titleRes = "Halaman Utama"
}

@Composable
fun HalamanUtama(navController: NavController, modifier: Modifier = Modifier
) {
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
                        .padding(20.dp)
                        .fillMaxWidth()
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                        .background(PrimaryColor.copy(alpha = 0.2f)),
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
                            color = TextColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                // Kontainer konten utama
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFF3F4F6),
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
                        Button(
                            onClick = {
                                navController.navigate(DestinasiHomePeserta.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF87A9D0),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Peserta",
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Button(
                            onClick = {
                                navController.navigate(DestinasiHomeEvent.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF87A9D0),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Event",
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Button(
                            onClick = {
                                navController.navigate(DestinasiHomeTiket.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF87A9D0),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Tiket",
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Button(
                            onClick = {
                                navController.navigate(DestinasiHomeTransaksi.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF87A9D0),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Transaksi",
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

