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
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.Polyline
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

        // viewpoint for river
        val startPoint = Point(-13431214.44, 5131066.25, SpatialReference.webMercator())

        lifecycleScope.launch {
            // set viewpoint of map view to starting point and scale
            mapView.setViewpointCenter(startPoint, 4500.0)
        }

        //create the graphic
        river()
    }

    private fun river() {
        // the two polygons for perform spatial operations
        var inputPolyline1: Polyline
        var inputPolyline2: Polyline
        var inputPolyline3: Polyline

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

        //define a line symbol
        val lineSymbol = SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.blue, 2f)

        graphicsOverlay.graphics.add(Graphic(inputPolyline1, lineSymbol))

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
        graphicsOverlay.graphics.add(Graphic(inputPolyline2, lineSymbol))

        val polylineBuilder3 = PolylineBuilder(SpatialReference.webMercator()) {
            // create and add points to the point collection
            addPoint(Point(-13431400.44, 5130940.25))
            addPoint(Point(-13431420.44, 5130930.25))
            addPoint(Point(-13431440.44, 5130920.25))
            addPoint(Point(-13431480.44, 5130910.25))
        }

        inputPolyline3 = polylineBuilder3.toGeometry()
        graphicsOverlay.graphics.add(Graphic(inputPolyline3, lineSymbol))

    }
}


