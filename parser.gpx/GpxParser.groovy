File inputFile = new File('../files/testWithPower.gpx.xml')

XmlParser parser = new XmlParser()
parser.setNamespaceAware(false)
Node node = parser.parse(inputFile)
def dataPoints = []

node.trk[0].trkseg[0].trkpt.each {
    dataPoints.add([latitude: it.@lat, longitude: it.@lon, time: it.time.text(), heartRate: it.extensions.TrackPointExtension.hr.text(), cadence: it.extensions.TrackPointExtension.cad.text()])
}

def xml = new XmlParser().parse(inputFile)
xml.trk[0].trkseg[0].trkpt.each {
//    Date formattedDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss",it.time.text())

}
//new XmlNodePrinter(new PrintWriter(new FileWriter(xmlFile))).print(xml)


println 'done'

//File outputFile = new File('D:/IProjects/tcx-parser/files/result.gpx.xml')
//new XmlNodePrinter(new PrintWriter(new FileWriter(outputFile))).print(xml)
