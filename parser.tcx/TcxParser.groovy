import groovy.time.TimeCategory

/**
 <Trackpoint>
 <Time>2012-01-21T13:42:26.000Z</Time>
 <Position>
 <LatitudeDegrees>38.900338439270854</LatitudeDegrees>
 <LongitudeDegrees>-92.3433980718255</LongitudeDegrees>
 </Position>
 <AltitudeMeters>213.39999389648438</AltitudeMeters>
 <DistanceMeters>0.0</DistanceMeters>
 <HeartRateBpm>
 <Value>87</Value>
 </HeartRateBpm>
 <Cadence>0</Cadence>
 <Extensions>
 <TPX xmlns="http://www.garmin.com/xmlschemas/ActivityExtension/v2">
 <Speed>0.0</Speed>
 <Watts>0</Watts>
 </TPX>
 </Extensions>
 </Trackpoint>
 **/

//building the new...
//http://groovy.codehaus.org/Creating+XML+using+Groovy's+MarkupBuilder

//or update in place...
//http://groovy.codehaus.org/Updating+XML+with+XmlSlurper

File inputFile = new File('../files/testWithPower.tcx.xml')

XmlParser parser = new XmlParser()
parser.setNamespaceAware(false)
Node node = parser.parse(inputFile)
def summaryPoints = []
def dataPoints = []

////Never really worked...keeping to save some typing
//node.Activities.Activity[0].each {
//    summaryPoints.add([id: it.Id.text(),
//            startTime: it.Lap.@StartTime,
//            totalTime: it.Lap.TotalTimeSeconds.text(),
//            distance: it.Lap.DistanceMeters.text(),
//            maxSpeed: it.Lap.MaximumSpeed.text(),
//            calories: it.Lap.Calories.text(),
//            avgHr: it.Lap.AverageHeartRateBpm.Value.text(),
//            maxHr: it.Lap.MaximumHeartRateBpm.Value.text(),
//            intensity: it.Lap.Intensity.text(),
//            cadence: it.Lap.Cadence.text(),
//            triggerMethod: it.Lap.TriggerMethod.text()
//    ])
//}

/** Successfully grabbed all of the elements...
 node.Activities.Activity[0].Lap[0].Track[0].Trackpoint.each {dataPoints.add([latitude: it.Position.LatitudeDegrees.text(),
 longitude: it.Position.LongitudeDegrees.text(),
 time: it.Time.text(),
 heartRate: it.HeartRateBpm.Value.text(),
 cadence: it.Cadence.text(),
 speed: it.Extensions.TPX.Speed.text(),
 wattage: it.Extensions.TPX.Watts.text()
 ])}**/

//Trying to edit the elements in place...

def previous = node.Activities.Activity[0].Lap[0].Track[0].Trackpoint[0].Time.text()
def current

def times = node.Activities.Activity[0].Lap[0].Track[0].each {
    println "Doing some math..."
    println "Previous: ${previous}"

    current = it.Time.text()
    println "Current: ${current}"

    def currentDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss.SSS", it.Time.text())
    def previousDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss.SSS", previous)
    use(TimeCategory) {
        currentDate.time = ((currentDate.time - previousDate.time) * (0.9)) + previousDate.time
    }
    println "after ${currentDate.toTimestamp()}"

    previous = current
}

//times.each { currentTime ->
//    currentTime = Date.parse(currentTime).minus(250)
//}


println 'done'

//File outputFile = new File('D:/IProjects/tcx-parser/files/result.gpx.xml')
//new XmlNodePrinter(new PrintWriter(new FileWriter(outputFile))).print(xml)

def buildDopedFile() {

}
