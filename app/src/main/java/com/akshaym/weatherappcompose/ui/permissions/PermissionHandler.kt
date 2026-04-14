package com.akshaym.weatherappcompose.ui.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import timber.log.Timber

@Composable
fun PermissionHandler(
    context: Context, permissions: List<String>, onAllGranted: () -> Unit,
    rationalMessage: String,
    content: @Composable (hasPermission: Boolean, triggerRequest: () -> Unit) -> Unit
) {
    var permissionStatus by remember { mutableStateOf<PermissionStatus>(PermissionStatus.NotRequested) }
    var showRationaleDialog by remember { mutableStateOf(false) }
    val activity = context as? Activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result.values.all { it }
        if (granted) {
            permissionStatus = PermissionStatus.Granted
            Timber.i("OnAllGranted L:41")
            onAllGranted()
        } else {
            permissionStatus = PermissionStatus.Denied
            showRationaleDialog = true
        }
    }
    LaunchedEffect(Unit) {
        val isGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (isGranted) {
            permissionStatus = PermissionStatus.Granted
            onAllGranted()
        }
    }
    val isPermanentlyDenied = permissions.any { p ->
        activity?.let { !ActivityCompat.shouldShowRequestPermissionRationale(it, p) } ?: false
    }
    Box {
        if (permissionStatus == PermissionStatus.Granted) {
            content(true) { /* Already granted */ }
        } else {
            content(false) { launcher.launch(permissions.toTypedArray()) }
        }

        if (showRationaleDialog) {
            RationaleDialog(
                isPermanentlyDenied = isPermanentlyDenied,
                message = rationalMessage,
                onConfirm = {
                    showRationaleDialog = false
                    if (isPermanentlyDenied) {
                        openAppSettings(context)
                    } else {
                        launcher.launch(permissions.toTypedArray())
                    }
                },
                onDismiss = { showRationaleDialog = false }
            )
        }
    }
}

sealed class PermissionStatus {
    object Granted : PermissionStatus()
    object NotRequested : PermissionStatus()
    object Denied : PermissionStatus()
    object PermanentlyDenied : PermissionStatus()
}

@Composable
fun RationaleDialog(
    isPermanentlyDenied: Boolean,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isPermanentlyDenied) "Settings Required" else "Permission Needed") },
        text = {
            Text(if (isPermanentlyDenied) "$message\n\nPlease enable it in settings." else message)
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(if (isPermanentlyDenied) "Open Settings" else "Try Again")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}