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

package com.esri.arcgismaps.sample.multipartgeometrypolygon

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
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.symbology.SimpleFillSymbol
import com.arcgismaps.mapping.symbology.SimpleFillSymbolStyle
import com.arcgismaps.mapping.symbology.SimpleLineSymbol
import com.arcgismaps.mapping.symbology.SimpleLineSymbolStyle
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
import com.esri.arcgismaps.sample.multipartgeometrypolygon.databinding.ActivityMainBinding
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

        // viewpoint for polygon
        val startPoint = Point(-16924906.559, 1765424.315, SpatialReference.webMercator())

        lifecycleScope.launch {
            // set viewpoint of map view to starting point and scale
            mapView.setViewpointCenter(startPoint, 5000000.0)
        }

        //create input polygons and add graphics to display these polygons in an overlay
        createPolygons()
    }

    private fun createPolygons() {

        // create input polygon 1
        val island1 = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-16983394.627, 1725046.488),
                Point(-16967695.178, 1741475.672),
                Point(-16939145.948, 1736705.524),
                Point(-16922037.192, 1720927.249),
                Point(-16910716.151, 1706864.030),
                Point(-16918722.930, 1684182.537),
                Point(-16937975.506, 1670250.694),
                Point(-16965194.254, 1691350.897),
                Point(-16981445.483, 1709668.314),
            ),
            SpatialReference.webMercator()
        )

        // create input polygon 2
        val island2 = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-16858450.243, 1742851.240),
                Point(-16855023.117, 1750367.747),
                Point(-16851622.565, 1754465.787),
                Point(-16847965.830, 1753538.988),
                Point(-16844727.408, 1750799.497),
                Point(-16842944.574, 1748335.896),
                Point(-16843642.062, 1744124.096),
                Point(-16847082.624, 1741811.876),
                Point(-16847082.624, 1741811.876),
            ),
            SpatialReference.webMercator()
        )

        // outer ring
        val outerIsland = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-16894265.768, 1780130.116),
                Point(-16888215.299, 1785870.657),
                Point(-16877750.891, 1783166.995),
                Point(-16873651.358, 1774526.028),
                Point(-16874800.004, 1764649.229),
                Point(-16875699.035, 1756346.556),
                Point(-16882975.780, 1749978.694),
                Point(-16892861.835, 1756082.012),
                Point(-16898920.962, 1763341.440),
                Point(-16902224.774, 1776136.580),
            ),
            SpatialReference.webMercator()
        )

        // inner ring
        val innerLake = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-16895378.882, 1773374.994),
                Point(-16887986.287, 1772724.682),
                Point(-16883906.162, 1768680.088),
                Point(-16887550.058, 1762729.048),
                Point(-16895020.285, 1765863.862),
            ),
            SpatialReference.webMercator()
        )

        var parts = listOf(island1, island2, outerIsland, innerLake)

        var polygonBuilder = PolygonBuilder(parts)

        //define a line symbol
        val lineSymbol = SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.blue, 2f)

        // create and add a green graphic to show input polygon 2
        val greenFill = SimpleFillSymbol(SimpleFillSymbolStyle.Solid, Color.green, lineSymbol)
        graphicsOverlay.graphics.add(Graphic(polygonBuilder.toGeometry(), greenFill))

    }
}


