

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat
import java.util.List

new SwingBuilder().edt {
    SCRIPTPANEL=panel(size: [300, 300]) {
        //borderLayout()
//        println "timestamp: " + binding.timestamp
//        binding.variables.each{ 
//            println it.key
//            println it.value 
//        }
        
//        if(binding.callback != null)
//            binding.callback.processResult("schnuffel")
        
        label (text: getGkgTableAsHtml())
    }

}

def String getGkgTableAsHtml() {
    StringBuffer sb=new StringBuffer()
    df = new DecimalFormat("0.00")
    sb.append('<html><body>')
    sb.append('<table border=1>')
    sb.append('<tr><td><b>Streitwert bis... EUR</b></td><td><b>Geb&uuml;hr in EUR</b></td></tr>')
    for(GkgTablesRange r: new GkgTablesRangeList().getRanges()) {
        
        sb.append('<tr><td align=right>' + df.format(r.high) + '</td><td align=right>' + df.format(r.mappedValue) + '</td></tr>')
    }
    sb.append('</table>')
    sb.append('</body></html>')

    return sb.toString();    
}