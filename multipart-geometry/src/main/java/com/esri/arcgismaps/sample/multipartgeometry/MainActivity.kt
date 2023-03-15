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
import com.arcgismaps.geometry.*
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

//    // simple black line symbol for outlines
//    private val lineSymbol = SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.black, 1f)

    //define a line symbol
    val lineSymbol = SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.blue, 2f)


    // the two polygons for perform spatial operations
    private lateinit var inputPolygon1: Polygon
    private lateinit var inputPolygon2: Polygon
    private lateinit var inputPolygon3: Polygon

    // the two polygons for perform spatial operations
    private lateinit var inputPolyline1: Polyline
    private lateinit var inputPolyline2: Polyline
    private lateinit var inputPolyline3: Polyline

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

        val startPoint = Point(-13431214.44, 5131066.25, SpatialReference.webMercator())

        lifecycleScope.launch {
            // set viewpoint of map view to starting point and scale
            mapView.setViewpointCenter(startPoint, 4500.0)
        }

        //create the graphic
        river()

//        val riverGraphic = Graphic(river(), lineSymbol)
//        //add to the graphic overlay
//        inputGeometryGraphicsOverlay.graphics.add(riverGraphic)

        // create input polygons and add graphics to display these polygons in an overlay
//        createPolygons()

//        val startPoint = Point(-16924906.559, 1765424.315, SpatialReference.webMercator())
//
//        lifecycleScope.launch {
//            // set viewpoint of map view to starting point and scale
//            mapView.setViewpointCenter(startPoint, 5000000.0)
//        }

    }

    private fun river() {
        // create river polyline 1
        val polylineBuilder1 = PolylineBuilder(SpatialReference.webMercator()) {
            // create and add points to the point collection
            addPoint(Point(-13431205.44, 5131075.25))
            addPoint(Point(-13431210.44, 5131072.25))
            addPoint(Point(-13431215.44, 5131070.25))
            addPoint(Point(-13431220.44, 5131065.25))
            addPoint(Point(-13431225.44, 5131060.25))
            addPoint(Point(-13431230.44, 5131055.25))
            addPoint(Point(-13431235.44, 5131050.25))
            addPoint(Point(-13431240.44, 5131045.25))
            addPoint(Point(-13431245.44, 5131040.25))
        }

        inputPolyline1 = polylineBuilder1.toGeometry()


        inputGeometryGraphicsOverlay.graphics.add(Graphic(inputPolyline1, lineSymbol))

        val polylineBuilder2 = PolylineBuilder(SpatialReference.webMercator()) {
            // create and add points to the point collection
            addPoint(Point(-13431250.44, 5131030.25))
            addPoint(Point(-13431260.44, 5131020.25))
            addPoint(Point(-13431270.44, 5131010.25))
            addPoint(Point(-13431280.44, 5131000.25))
            addPoint(Point(-13431290.44, 5130999.25))
            addPoint(Point(-13431300.44, 5130980.25))
            addPoint(Point(-13431330.44, 5130970.25))
            addPoint(Point(-13431350.44, 5130960.25))
            addPoint(Point(-13431370.44, 5130950.25))
        }

        inputPolyline2 = polylineBuilder2.toGeometry()
        inputGeometryGraphicsOverlay.graphics.add(Graphic(inputPolyline2, lineSymbol))

        val polylineBuilder3 = PolylineBuilder(SpatialReference.webMercator()) {
            // create and add points to the point collection
            addPoint(Point(-13431400.44, 5130940.25))
            addPoint(Point(-13431420.44, 5130930.25))
            addPoint(Point(-13431440.44, 5130920.25))
            addPoint(Point(-13431480.44, 5130910.25))
        }

        inputPolyline3 = polylineBuilder3.toGeometry()
        inputGeometryGraphicsOverlay.graphics.add(Graphic(inputPolyline3, lineSymbol))

    }

    private fun createPolygons() {
        // create input polygon 1
        val polygonBuilder1 = PolygonBuilder(SpatialReference.webMercator()) {
            // add points to the point collection
            addPoint(Point(-16983394.627, 1725046.488))
            addPoint(Point(-16967695.178, 1741475.672))
            addPoint(Point(-16939145.948, 1736705.524))
            addPoint(Point(-16922037.192, 1720927.249))
            addPoint(Point(-16910716.151, 1706864.030))
            addPoint(Point(-16918722.930, 1684182.537))
            addPoint(Point(-16937975.506, 1670250.694))
            addPoint(Point(-16965194.254, 1691350.897))
//            addPoint(Point(-16981445.483, 1709668.314))
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

        // outer ring
        val outerRing = MutablePart.createWithPoints(
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
        val innerRing = MutablePart.createWithPoints(
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

        // add both parts (rings) to a polygon and create a geometry from it
        inputPolygon3 = Polygon(listOf(outerRing, innerRing))
        // create and add a green graphic to show input polygon 2
        val greenFill = SimpleFillSymbol(SimpleFillSymbolStyle.Solid, Color.green, lineSymbol)
        inputGeometryGraphicsOverlay.graphics.add(Graphic(inputPolygon3, greenFill))
    }

}
