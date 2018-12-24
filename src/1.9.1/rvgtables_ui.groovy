

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat
import java.util.List

def float berechneWertGebuehr(float streitWert, float factor) { 

    RvgTablesRangeList rl = new RvgTablesRangeList()
    return rl.getMappedValue(streitWert) * factor
    
}

new SwingBuilder().edt {
    SCRIPTPANEL=panel(size: [300, 300]) {
        
        label (text: getRvgTableAsHtml())
    }

}

def String getRvgTableAsHtml() {
    StringBuffer sb=new StringBuffer()
    df = new DecimalFormat("0.00")
    sb.append('<html><body>')
    sb.append('<table border=1>')
    sb.append('<tr><td><b>Streitwert bis... EUR</b></td><td><b>Geb&uuml;hr in EUR</b></td></tr>')
    for(RvgTablesRange r: new RvgTablesRangeList().getRanges()) {
        
        sb.append('<tr><td align=right>' + df.format(r.high) + '</td><td align=right>' + df.format(r.mappedValue) + '</td></tr>')
    }
    sb.append('</table>')
    sb.append('</body></html>')
//    java.io.File f=new java.io.File('.')
//    println(f.getAbsolutePath())
    return sb.toString();    
}