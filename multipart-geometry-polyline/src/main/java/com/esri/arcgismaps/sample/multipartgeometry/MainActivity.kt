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

package com.esri.arcgismaps.sample.multipartgeometrypolyline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.Color
import com.arcgismaps.geometry.MutablePart
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.PolygonBuilder
import com.arcgismaps.geometry.PolylineBuilder
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.symbology.SimpleLineSymbol
import com.arcgismaps.mapping.symbology.SimpleLineSymbolStyle
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
import com.esri.arcgismaps.sample.multipartgeometrypolyline.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

private val Color.Companion.blue: Color
    get() {
        return fromRgba(164, 237, 245, 255)
    }

class MainActivity : AppCompatActivity() {

    // set up data binding for the activity
    private val activityMainBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val mapView by lazy {
        activityMainBinding.mapView
    }

    // create the graphic overlays
    private val graphicsOverlay: GraphicsOverlay by lazy { GraphicsOverlay() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // authentication with an API key or named user is
        // required to access basemaps and other location services
        ArcGISEnvironment.apiKey = ApiKey.create(BuildConfig.API_KEY)
        lifecycle.addObserver(mapView)

        // set up the MapView
        mapView.apply {
            // create an ArcGISMap with a light gray basemap
            map = ArcGISMap(BasemapStyle.ArcGISTerrain)
            // create graphics overlays to show the inputs and results of the spatial operation
            graphicsOverlays.add(graphicsOverlay)
        }

        // viewpoint for river
        val startPoint = Point(-13431214.44, 5131070.713071987, SpatialReference.webMercator())

        lifecycleScope.launch {
            // set viewpoint of map view to starting point and scale
            mapView.setViewpointCenter(startPoint, 250.0)
        }

        // create a buffer around the clicked location
        lifecycleScope.launch {
            mapView.onSingleTapConfirmed.collect { event ->
                // get map point tapped, return if null
                val mapPoint = event.mapPoint ?: return@collect
                mapPoint.x
                mapPoint.y
                val screenCoordinate = event.screenCoordinate
                screenCoordinate.x
                screenCoordinate.y
            }
        }
        constructRiverStream()
    }

    private fun constructRiverStream() {

        // create first river stream which is basically a Mutable Part and connects to the ocean
        val riverstream1 = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-13431206.32, 5131073.67),
                Point(-13431209.45, 5131072.27),
                Point(-13431212.87, 5131071.16),
            ),
            SpatialReference.webMercator()
        )

        // create second river stream which is basically a Mutable Part and connects to the first river stream
        val riverstream2 = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-13431212.87, 5131071.16),
                Point(-13431214.08, 5131068.29),
                Point(-13431216.15, 5131064.61),
                Point(-13431219.98, 5131060.63),
            ),
            SpatialReference.webMercator()
        )

        // create third river stream which is basically a Mutable Part and connects to the first river stream
        val riverstream3 = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-13431212.87, 5131071.16),
                Point(-13431216.00, 5131068.84),
                Point(-13431217.36, 5131065.42),
                Point(-13431224.11, 5131061.44),
            ),
            SpatialReference.webMercator()
        )

        // create fourth river stream which is basically a Mutable Part and connects to the first river stream
        val riverstream4 = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-13431211.06, 5131071.87),
                Point(-13431219.33, 5131070.76),
                Point(-13431225.88, 5131066.53),
                Point(-13431226.99, 5131063.80),
            ),
            SpatialReference.webMercator()
        )

        // // create fifth river stream which is basically a Mutable Part and connects to the first river stream
        val riverstream5 = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-13431209.30, 5131072.27),
                Point(-13431219.33, 5131075.44),
                Point(-13431225.88, 5131072.17),
                Point(-13431232.33, 5131070.20),
                Point(-13431238.78, 5131070.66),
            ),
            SpatialReference.webMercator()
        )

        // add all the parts created above to a List
        var parts = listOf(riverstream1, riverstream2, riverstream3, riverstream4, riverstream5)

        // Create a PolygonBuilder using the list of parts created above
        var polylineBuilder = PolylineBuilder(parts)

        // define a line symbol which will represent the boundary of the polygon
        val lineSymbol = SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.blue, 4f)

        // create and add a green graphic to fill color of the Polygon Builder.
        graphicsOverlay.graphics.add(Graphic(polylineBuilder.toGeometry(), lineSymbol))

    }
}


