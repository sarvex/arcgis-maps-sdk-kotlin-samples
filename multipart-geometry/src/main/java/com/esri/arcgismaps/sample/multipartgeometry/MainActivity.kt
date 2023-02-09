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

package com.esri.arcgismaps.sample.multipartgeometry

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.Color
import com.arcgismaps.geometry.MutablePart
import com.arcgismaps.geometry.Part
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.Polygon
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
import com.esri.arcgismaps.sample.multipartgeometry.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

private val Color.Companion.blue: Color
    get() {
        return fromRgba(0, 0, 255, 255)
    }

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    // set up data binding for the activity
    private val activityMainBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val mapView by lazy {
        activityMainBinding.mapView
    }

    // create the graphic overlays
    private val inputGeometryGraphicsOverlay: GraphicsOverlay by lazy { GraphicsOverlay() }

    // simple black line symbol for outlines
    private val lineSymbol = SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.black, 1f)


    // the two polygons for perform spatial operations
    private lateinit var inputPolygon1: Polygon
    private lateinit var inputPolygon2: Polygon
    private lateinit var inputPolygon3: Polygon

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
            graphicsOverlays.add(inputGeometryGraphicsOverlay)
        }

        // create input polygons and add graphics to display these polygons in an overlay
        createPolygons()

//        // get the envelop to set the viewpoint
//        val envelope = GeometryEngine.union(inputPolygon1, inputPolygon2)?.extent
//        if (envelope != null) {
//            lifecycleScope.launch {
//                mapView.setViewpointGeometry(envelope, 2000.0)
//            }
//        }
//
        val startPoint = Point(-16924906.559, 1765424.315, SpatialReference.webMercator())

        lifecycleScope.launch {
            // set viewpoint of map view to starting point and scale
            mapView.setViewpointCenter(startPoint, 5000000.0)
        }

    }

    private fun createPolygons() {
        // create input polygon 1
        val polygonBuilder1 = PolygonBuilder(SpatialReference.webMercator()) {
            // add points to the point collection
            addPoint(Point(-16893384.354, 1797362.785))
            addPoint(Point(-16906653.344, 1805983.448))
            addPoint(Point(-16923562.648, 1794139.292))
            addPoint(Point(-16931915.782, 1778365.794))
            addPoint(Point(-16920441.867, 1766835.746))
            addPoint(Point(-16902996.310, 1775924.587))
            addPoint(Point(-16884973.892, 1776723.593))
            addPoint(Point(-16885574.639, 1787067.672))
        }
        inputPolygon1 = polygonBuilder1.toGeometry()

        // create and add a blue graphic to show input polygon 1
        val blueFill = SimpleFillSymbol(SimpleFillSymbolStyle.Solid, Color.green, lineSymbol)
        inputGeometryGraphicsOverlay.graphics.add(Graphic(inputPolygon1, blueFill))


        // create input polygon 2
        val polygonBuilder2 = PolygonBuilder(SpatialReference.webMercator()) {
            // add points to the point collection
            addPoint(Point(-16858450.243, 1742851.240))
            addPoint(Point(-16855023.117, 1750367.747))
            addPoint(Point(-16851622.565, 1754465.787))
            addPoint(Point(-16847965.830, 1753538.988))
            addPoint(Point(-16844727.408, 1750799.497))
            addPoint(Point(-16842944.574, 1748335.896))
            addPoint(Point(-16843642.062, 1744124.096))
            addPoint(Point(-16847082.624, 1741811.876))
            addPoint(Point(-16847082.624, 1741811.876))

        }
        inputPolygon2 = polygonBuilder2.toGeometry()

        // create and add a blue graphic to show input polygon 1
        val blueFill1 = SimpleFillSymbol(SimpleFillSymbolStyle.Solid, Color.green, lineSymbol)
        inputGeometryGraphicsOverlay.graphics.add(Graphic(inputPolygon2, blueFill1))


// create input polygon 3
        // outer ring
        val outerRing = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-16880340.494, 1773326.325),
                Point(-16876640.464, 1776412.769),
                Point(-16872674.995, 1778972.215),
                Point(-16868358.393, 1776288.558),
                Point(-16866185.610, 1772204.552),
                Point(-16869440.156, 1770232.417),
                Point(-16869038.563, 1765496.008),
                Point(-16873369.795, 1762842.210),
                Point(-16877813.295, 1758771.939),
                Point(-16882697.501, 1767154.035),
            ),
            SpatialReference.webMercator()
        )

        // inner ring
        val innerRing = MutablePart.createWithPoints(
            listOf(
                // add points to the point collection
                Point(-16879134.222, 1775149.169),
                Point(-16876798.116, 1774258.498),
                Point(-16876798.116, 1774258.498),
                Point(-16879767.814, 1771782.058),
            ),
            SpatialReference.webMercator()
        )


        // add both parts (rings) to a polygon and create a geometry from it
        inputPolygon3 = Polygon(listOf(outerRing, innerRing))
        // create and add a green graphic to show input polygon 2
        val greenFill = SimpleFillSymbol(SimpleFillSymbolStyle.Solid, Color.green, lineSymbol)
        inputGeometryGraphicsOverlay.graphics.add(Graphic(inputPolygon3, greenFill))
    }

}
