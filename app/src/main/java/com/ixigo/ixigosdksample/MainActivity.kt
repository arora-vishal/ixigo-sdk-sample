package com.ixigo.ixigosdksample

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.ixigo.ixigosdksample.databinding.ActivityMainBinding
import com.ixigo.sdk.AppInfo
import com.ixigo.sdk.IxigoSDK
import com.ixigo.sdk.analytics.AnalyticsProvider
import com.ixigo.sdk.analytics.Event
import com.ixigo.sdk.auth.PartnerToken
import com.ixigo.sdk.auth.PartnerTokenCallback
import com.ixigo.sdk.auth.PartnerTokenErrorUserNotLoggedIn
import com.ixigo.sdk.auth.PartnerTokenProvider
import com.ixigo.sdk.bus.BusConfig
import com.ixigo.sdk.bus.BusSDK
import com.ixigo.sdk.common.Err
import com.ixigo.sdk.common.Ok
import com.ixigo.sdk.hotels.HotelsSDK
import com.ixigo.sdk.payment.PaymentSDK
import com.ixigo.sdk.trains.TrainsSDK
import com.ixigo.sdk.ui.Theme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WebView.setWebContentsDebuggingEnabled(true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val appInfo = AppInfo(
            clientId = "iximatr", // this is a unique identifier for host application - this will be provided for integration
            apiKey = "iximatr!2$", // this is a unique identifier for host application - this will be provided for integration
            appVersion = 123,
            appName = "sampleApp",
            deviceId = "sampleDeviceId",
            uuid = "sampleUUID"
        )

        val partnerTokenProvider = MyPartnerTokenProvider()
        val analyticsProvider = MyAnalyticsProvider()
        val theme = Theme(primaryColor = Color.parseColor("#FFFFAD"))


        // initialize ixigo sdk
        val ixigoSDK = IxigoSDK.init(
            context = application,
            appInfo = appInfo,
            partnerTokenProvider = partnerTokenProvider,
            theme = theme,
            analyticsProvider = analyticsProvider
        )

        // initialize payment sdk
        PaymentSDK.init()


        binding.flightsHome.setOnClickListener {
            ixigoSDK.flightsStartHome(this)
        }

        // user trips for flights, trains, hotels, bus require partner token to work
        binding.flightsTrips.setOnClickListener {
            ixigoSDK.flightsStartTrips(this)
        }

        // initialize bus sdk to use bus pages
        val busSDK = BusSDK.init(config = BusConfig.PROD)

        binding.busHome.setOnClickListener {
            busSDK.launchHome(this)
        }

        binding.busTrips.setOnClickListener {
            busSDK.launchTrips(this)
        }

        // initialize hotels sdk to use hotels pages
        val hotelsSDK = HotelsSDK.init()

        binding.hotelsHome.setOnClickListener {
            hotelsSDK.launchHome(this)
        }

        binding.hotelsTrips.setOnClickListener {
            hotelsSDK.launchTrips(this)
        }
    }

    class MyAnalyticsProvider : AnalyticsProvider {
        override fun logEvent(event: Event) {
            Log.d("SDK Event", "Received event from ixigo-sdk $event")
        }
    }

    class MyPartnerTokenProvider : PartnerTokenProvider {
        override fun fetchPartnerToken(
            activity: FragmentActivity,
            requester: PartnerTokenProvider.Requester,
            callback: PartnerTokenCallback
        ) {
            // if user is not logged in
            callback.invoke(Err(PartnerTokenErrorUserNotLoggedIn()))
            // if user is logged in
            // callback.invoke(Err(PartnerToken("token")))
        }

    }
}
