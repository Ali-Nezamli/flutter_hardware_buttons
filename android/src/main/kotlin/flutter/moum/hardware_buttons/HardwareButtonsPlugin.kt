package flutter.moum.hardware_buttons

import android.app.Activity
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.PluginRegistry

class HardwareButtonsPlugin {
    companion object {
        private const val VOLUME_BUTTON_CHANNEL_NAME = "flutter.moum.hardware_buttons.volume"
        private const val HOME_BUTTON_CHANNEL_NAME = "flutter.moum.hardware_buttons.home"
        private const val LOCK_BUTTON_CHANNEL_NAME = "flutter.moum.hardware_buttons.lock"

        @JvmStatic
        fun registerWith(registrar: PluginRegistry.Registrar) {
            val activity = registrar.activity()
            activity?.let { safeActivity ->
                val application = safeActivity.application

                registrar.addActivityResultListener(HardwareButtonsWatcherManager.getInstance(application, safeActivity))

                val volumeButtonChannel = EventChannel(registrar.messenger(), VOLUME_BUTTON_CHANNEL_NAME)
                volumeButtonChannel.setStreamHandler(VolumeButtonStreamHandler(safeActivity))

                val homeButtonChannel = EventChannel(registrar.messenger(), HOME_BUTTON_CHANNEL_NAME)
                homeButtonChannel.setStreamHandler(HomeButtonStreamHandler(safeActivity))

                val lockButtonChannel = EventChannel(registrar.messenger(), LOCK_BUTTON_CHANNEL_NAME)
                lockButtonChannel.setStreamHandler(LockButtonStreamHandler(safeActivity))
            } ?: run {
                // Handle the case where activity is null, if necessary
                println("Activity is null")
            }
        }
    }
}
