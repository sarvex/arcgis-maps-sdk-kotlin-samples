/* Copyright 2022 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.esri.arcgismaps.sample.multipartgeometrypoint

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.Color
import com.arcgismaps.geometry.Multipoint
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.symbology.PictureMarkerSymbol
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
import com.esri.arcgismaps.sample.multipartgeometrypoint.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

private val Color.Companion.blue: Color
    get() {
        return fromRgba(0, 0, 255, 255)
    }

class MainActivity : AppCompatActivity() {

    // set up data binding for the activity
    private val activityMainBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val mapView by lazy {
        activityMainBinding.mapView
    }

    // create the graphic overlay
    private val graphicsOverlay: GraphicsOverlay by lazy { GraphicsOverlay() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // authentication with an API key or named user is
        // required to access basemaps and other location services
        ArcGISEnvironment.apiKey = ApiKey.create(BuildConfig.API_KEY)
        lifecycle.addObserver(mapView)

        // set up the MapView
        mapView.apply {
            // create an ArcGISMap with a streets basemap
            map = ArcGISMap(BasemapStyle.ArcGISStreets)
            // create graphics overlays to show the inputs and results of the spatial operation
            graphicsOverlays.add(graphicsOverlay)
        }

        // create a viewpoint
        val startPoint = Point(-13431350.44, 5131196.25, SpatialReference.webMercator())

        lifecycleScope.launch {
            // set viewpoint of map view to starting point and scale
            mapView.setViewpointCenter(startPoint, 3500.0)
        }

        // Create Multipoint geometry using collection of points representing each individual tree
        val multipoint = Multipoint(
            listOf(
                Point(-13431214.195681, 5131066.057930),
                Point(-13431220.44, 5131172.25),
                Point(-13431385.44, 5131040.25),
                Point(-13431435.44, 5131260.25),
                Point(-13431320.44, 5131145.25),
                Point(-13431420.44, 5131125.25),
                Point(-13431260.44, 5131095.25),
                Point(-13431320.44, 5131055.25),
                Point(-13431295.44, 5131330.25),
                Point(-13431465.44, 5131180.25),
                Point(-13431260.44, 5131265.25),
                Point(-13431380.44, 5131195.25),
            ),
            SpatialReference.webMercator()
        )
        lifecycleScope.launch {
            // create a tree symbol
            val treeSymbol = createPinSymbol()
            // creates a graphic with the tree point and symbol
            val treeGraphic =
                Graphic(multipoint, treeSymbol)
            graphicsOverlay.graphics.add(treeGraphic)
        }
    }

    // Create a tree symbol which is a PictureMarkerSymbol
    private suspend fun createPinSymbol(): PictureMarkerSymbol {
        val pinDrawable = ContextCompat.getDrawable(this, R.drawable.tree) as BitmapDrawable
        val pinSymbol = PictureMarkerSymbol(pinDrawable)
        pinSymbol.load().getOrThrow()
        pinSymbol.width = 80f
        pinSymbol.height = 70f
        return pinSymbol
    }
}


