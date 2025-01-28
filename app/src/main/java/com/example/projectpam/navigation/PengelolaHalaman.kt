package com.example.projectpam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectpam.ui.view.DestinasiHalamanUtama
import com.example.projectpam.ui.view.HalamanUtama
import com.example.projectpam.ui.view.viewevent.DestinasiDetailEvent
import com.example.projectpam.ui.view.viewevent.DestinasiEntryEvent
import com.example.projectpam.ui.view.viewevent.DestinasiHomeEvent
import com.example.projectpam.ui.view.viewevent.DestinasiUpdateEvent
import com.example.projectpam.ui.view.viewevent.DetailEventView
import com.example.projectpam.ui.view.viewevent.EntryEventScreen
import com.example.projectpam.ui.view.viewevent.EventHomeScreen
import com.example.projectpam.ui.view.viewevent.UpdateEventView
import com.example.projectpam.ui.view.viewpeserta.DestinasiDetailPeserta
import com.example.projectpam.ui.view.viewpeserta.DestinasiEntryPeserta
import com.example.projectpam.ui.view.viewpeserta.DestinasiHomePeserta
import com.example.projectpam.ui.view.viewpeserta.DestinasiUpdatePeserta
import com.example.projectpam.ui.view.viewpeserta.DetailPesertaView
import com.example.projectpam.ui.view.viewpeserta.EntryPesertaScreen
import com.example.projectpam.ui.view.viewpeserta.PesertaHomeScreen
import com.example.projectpam.ui.view.viewpeserta.UpdatePesertaView
import com.example.projectpam.ui.view.viewtiket.DestinasiDetailTiket
import com.example.projectpam.ui.view.viewtiket.DestinasiEntryTiket
import com.example.projectpam.ui.view.viewtiket.DestinasiHomeTiket
import com.example.projectpam.ui.view.viewtiket.DestinasiUpdateTiket
import com.example.projectpam.ui.view.viewtiket.DetailTiketView
import com.example.projectpam.ui.view.viewtiket.EntryTiketScreen
import com.example.projectpam.ui.view.viewtiket.TiketHomeScreen
import com.example.projectpam.ui.view.viewtiket.UpdateTiketView
import com.example.projectpam.ui.view.viewtransaksi.DestinasiDetailTransaksi
import com.example.projectpam.ui.view.viewtransaksi.DestinasiEntryTransaksi
import com.example.projectpam.ui.view.viewtransaksi.DestinasiHomeTransaksi
import com.example.projectpam.ui.view.viewtransaksi.DetailTransaksiView
import com.example.projectpam.ui.view.viewtransaksi.EntryTransaksiScreen
import com.example.projectpam.ui.view.viewtransaksi.TransaksiHomeScreen

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHalamanUtama.route,
        modifier = modifier,
    ) {
        // Halaman utama
        composable(DestinasiHalamanUtama.route) {
            HalamanUtama(
                navController = navController,
                modifier = modifier
            )
        }
        // Peserta
        composable(DestinasiHomePeserta.route) {
            PesertaHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPeserta.route) },
                onDetailClick = { idPeserta ->
                    navController.navigate("${DestinasiDetailPeserta.route}/$idPeserta") {
                        popUpTo(DestinasiHomePeserta.route) { inclusive = false }
                    }
                },
                navigateBack = {navController.popBackStack()}
            )
        }
        composable(DestinasiEntryPeserta.route) {
            EntryPesertaScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailPeserta.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailPeserta.ID_PESERTA) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString(DestinasiDetailPeserta.ID_PESERTA).orEmpty()
            DetailPesertaView(
                id_peserta = idPeserta,
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdatePeserta.route}/$idPeserta")
                }
            )
        }
        composable(
            DestinasiUpdatePeserta.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdatePeserta.ID_PESERTA) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString(DestinasiUpdatePeserta.ID_PESERTA).orEmpty()
            UpdatePesertaView(navigateBack = { navController.popBackStack() }, modifier = modifier)
        }

        // Event
        composable(DestinasiHomeEvent.route) {
            EventHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryEvent.route) },
                onDetailClick = { idEvent ->
                    navController.navigate("${DestinasiDetailEvent.route}/$idEvent") {
                        popUpTo(DestinasiHomeEvent.route) { inclusive = false }
                    }
                },
                navigateBack = {navController.popBackStack()}
            )
        }
        composable(DestinasiEntryEvent.route) {
            EntryEventScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailEvent.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailEvent.ID_EVENT) { type = NavType.StringType })
        ) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getString(DestinasiDetailEvent.ID_EVENT).orEmpty()
            DetailEventView(
                id_event = idEvent,
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateEvent.route}/$idEvent")
                }
            )
        }
        composable(
            DestinasiUpdateEvent.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateEvent.ID_EVENT) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString(DestinasiUpdateEvent.ID_EVENT).orEmpty()
            UpdateEventView(navigateBack = { navController.popBackStack() }, modifier = modifier)
        }

        // Tiket
        composable(DestinasiHomeTiket.route) {
            TiketHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTiket.route) },
                onDetailClick = { idTiket ->
                    navController.navigate("${DestinasiDetailTiket.route}/$idTiket") {
                        popUpTo(DestinasiHomeTiket.route) { inclusive = false }
                    }
                },
                navigateBack = {navController.popBackStack()}
            )
        }
        composable(DestinasiEntryTiket.route) {
            EntryTiketScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailTiket.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailTiket.ID_TIKET) { type = NavType.StringType })
        ) { backStackEntry ->
            val idTiket = backStackEntry.arguments?.getString(DestinasiDetailTiket.ID_TIKET).orEmpty()
            DetailTiketView(
                idTiket = idTiket,
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateTiket.route}/$idTiket")
                }
            )
        }
        composable(
            DestinasiUpdateTiket.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateTiket.ID_TIKET) { type = NavType.StringType })
        ) { backStackEntry ->
            val idTiket = backStackEntry.arguments?.getString(DestinasiUpdateTiket.ID_TIKET).orEmpty()
            UpdateTiketView(navigateBack = { navController.popBackStack() }, modifier = modifier)
        }

        // Transaksi
        composable(DestinasiHomeTransaksi.route) {
            TransaksiHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTransaksi.route) },
                onDetailClick = { idTransaksi ->
                    navController.navigate("${DestinasiDetailTransaksi.route}/$idTransaksi") {
                        popUpTo(DestinasiHomeTransaksi.route) { inclusive = false }
                    }
                },
                navigateBack = {navController.popBackStack()}
            )
        }
        composable(DestinasiEntryTransaksi.route) {
            EntryTransaksiScreen(navigateBack = { navController.popBackStack() })
        }
       /* composable(
            DestinasiDetailTransaksi.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailTransaksi.ID_TRANSAKSI) { type = NavType.StringType })
        ) { backStackEntry ->
            val idTransaksi = backStackEntry.arguments?.getString(DestinasiDetailTransaksi.ID_TRANSAKSI).orEmpty()
            DetailTransaksiView(
                idTransaksi = idTransaksi,
                navigateBack = { navController.popBackStack() }
            )
        } */
    }
}
