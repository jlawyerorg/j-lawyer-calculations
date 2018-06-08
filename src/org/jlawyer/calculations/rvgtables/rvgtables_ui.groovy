

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat

class Range { 
    float low
    float high
    float mappedValue
    
    Range(low, high, mappedValue) {
        this.low=low
        this.high=high
        this.mappedValue=mappedValue
    }
    
    boolean contains(float number){(number > low && number <= high)}
}

def ranges = []
ranges.add(new Range(0,500,45))
ranges.add(new Range(500,1000,80))
ranges.add(new Range(1000,1500,115))
ranges.add(new Range(1500,2000,150))
ranges.add(new Range(2000,3000,201))
ranges.add(new Range(3000,4000,252))
ranges.add(new Range(4000,5000,303))
ranges.add(new Range(5000,6000,354))
ranges.add(new Range(6000,7000,405))
ranges.add(new Range(7000,8000,456))
ranges.add(new Range(8000,9000,507))
ranges.add(new Range(9000,10000,558))
ranges.add(new Range(10000,13000,604))
ranges.add(new Range(13000,16000,650))
ranges.add(new Range(16000,19000,696))
ranges.add(new Range(19000,22000,742))
ranges.add(new Range(22000,25000,788))
ranges.add(new Range(25000,30000,863))
ranges.add(new Range(30000,35000,938))
ranges.add(new Range(35000,40000,1013))
ranges.add(new Range(40000,45000,1088))
ranges.add(new Range(45000,50000,1163))
ranges.add(new Range(50000,65000,1248))
ranges.add(new Range(65000,80000,1333))
ranges.add(new Range(80000,95000,1418))
ranges.add(new Range(95000,110000,1503))
ranges.add(new Range(110000,125000,1588))
ranges.add(new Range(125000,140000,1673))
ranges.add(new Range(140000,155000,1758))
ranges.add(new Range(155000,170000,1843))
ranges.add(new Range(170000,185000,1928))
ranges.add(new Range(185000,200000,2013))
ranges.add(new Range(200000,230000,2133))
ranges.add(new Range(230000,260000,2253))
ranges.add(new Range(260000,290000,2373))
ranges.add(new Range(290000,320000,2493))
ranges.add(new Range(320000,350000,2613))
ranges.add(new Range(350000,380000,2733))
ranges.add(new Range(380000,410000,2853))
ranges.add(new Range(410000,440000,2973))
ranges.add(new Range(440000,470000,3093))
ranges.add(new Range(470000,500000,3213))

def float berechneWertGebuehr(float streitWert) { 
   println( streitWert * 1.5d)
   //nGeschaeftsGebuehr.text = df.format(nStreitwert.text.toInteger() * 1.5d)
   return 14f
}

new SwingBuilder().edt {
    SCRIPTPANEL=panel(size: [300, 300]) {
        //borderLayout()
        label (text: getRvgTableAsHtml(ranges))
    }

}

def String getRvgTableAsHtml(List<Range> ranges) {
    StringBuffer sb=new StringBuffer()
    df = new DecimalFormat("0.00")
    sb.append('<html><body>')
    sb.append('<table border=1>')
    sb.append('<tr><td><b>Streitwert bis... EUR</b></td><td><b>Geb&uuml;hr in EUR</b></td></tr>')
    for(Range r: ranges) {
        
        sb.append('<tr><td align=right>' + df.format(r.high) + '</td><td align=right>' + df.format(r.mappedValue) + '</td></tr>')
    }
    sb.append('</table>')
    sb.append('</body></html>')
//    java.io.File f=new java.io.File('.')
//    println(f.getAbsolutePath())
    return sb.toString();
    
}