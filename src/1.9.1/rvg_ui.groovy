

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat
import javax.swing.SwingConstants
import java.util.ArrayList
import org.jlawyer.plugins.calculation.CalculationTable
import rvgtables_ui

@Bindable
class Address { 
    String street, number, city
    String toString() { "address[street=$street,number=$number,city=$city]" }
}

count = 0
df = new DecimalFormat("0.00")
new SwingBuilder().edt {
    SCRIPTPANEL=panel(size: [300, 300]) {
        //borderLayout()
        tableLayout {
            tr  {
                td (align: 'right') {
                    panel {
                        button(text: 'Berechnen', actionPerformed: {
                                //nGeschaeftsGebuehr.text = df.format(calculate(nStreitwert.text))
                                calculate()
                            })
                                
                        cmdCopy = button(text: 'Kopieren', enabled: false, toolTipText: 'In Zwischenablage kopieren', actionPerformed: {
                                if(binding.callback != null)
                                binding.callback.processResultToClipboard(copyToClipboard())
                                        
                            })
                        
                        cmdDocument = button(text: 'Dokument erstellen', enabled: false, toolTipText: 'Ergebnis in Dokument uebernehmen', actionPerformed: {
                                if(binding.callback != null)
                                binding.callback.processResultToDocument(copyToDocument())
                                        
                            })
                    
                    }
                }

                
            }
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Basisdaten')) {
                        tableLayout {
                            tr {
                                td (colfill:true) {
                                    label(text: 'Streitwert:')
                                }
                                td {
                                    txtStreitWert=textField(id: 'nStreitwert', columns: 10)
                                }
                            }
                
                            tr {
                                td (colfill:true) {
                        
                                }
                                td {
                        
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: 'Anzahl der Mandanten: ')
                        
                                }
                                td {
                                    spinner(id: 'spin1', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 200, 
                                            value:1,
                                            stepSize:1))
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: 'Anzahl der Gegner: ')
                        
                                }
                                td {
                                    spinner(
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 200, 
                                            value:1,
                                            stepSize:1))
                                }
                            }
        
        
        
        
        
        
                        }   
        
                        /*textlabel = label(text: 'Click the button!', constraints: BL.NORTH)
                        button(text:'Click Me',
                        actionPerformed: {count++; textlabel.text = "Clicked ${count} time(s)."; println "clicked"}, constraints:BL.SOUTH)*/
                    }     
                }
            }
            
            tr {
                td {
                    panel(border: titledBorder(title: 'RVG-Berechnung')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkVV2300 = checkBox(id: 'bVV2300', text: 'Geschäftsgebühr VV2300:', selected: true, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    spnVV2300 = spinner(id: 'nVV2300faktor', 
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.3f,
                                            stepSize:0.1f), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV2300 = label(id: 'nGeschaeftsGebuehr', text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV3100 = checkBox(id: 'nGeschaeftsGebuehr', text: 'Verfahrenssgebühr VV3100:', selected: true, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    spnVV3100 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.3f,
                                            stepSize:0.1f), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV3100 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                
                            tr {
                                td {
                                    chkAnrechenbarerAnteil = checkBox(text: 'abzüglich anrechenbarer Teil:', selected: true, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    lblAnrechenbarerAnteil = label(text: '0,00', foreground: java.awt.Color.RED)
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Erhöhungsgebühr VV1008:', selected: true)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: ' ')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Terminsgebühr VV3104:', selected: true)
                                }
                                td {
                                    spinner(
                                        model:spinnerNumberModel(minimum:0.0, 
                                            maximum: 10.0, 
                                            value:1.2,
                                            stepSize:0.1))
                                }
                                td (align: 'right') {
                                    label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Einigungsgebühr VV1000ff.:', selected: false)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: ' ')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Auslagen VV7002ff.:', selected: false)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Weitere Kosten VV7000ff.:', selected: false)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: ' ')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Gegnerische Kosten:', selected: false)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: ' ')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Mehrwertsteuer VV7008:', selected: true)
                                }
                                td {
                                    label(text: '19%')
                                }
                                td (align: 'right') {
                                    label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Gerichtsgebühr GKG Ziff. 1210ff.:', selected: false)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: ' ')
                                }
                            }
                            tr {
                                td {
                                    label(text: ' ')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td {
                                    label(text: ' ')
                                }
                            }
        
        
        
        
        
        
                        }  
                        
                        
        
                        /*textlabel = label(text: 'Click the button!', constraints: BL.NORTH)
                        button(text:'Click Me',
                        actionPerformed: {count++; textlabel.text = "Clicked ${count} time(s)."; println "clicked"}, constraints:BL.SOUTH)*/
                    }     
                }
            }
            
            
            /*
            bean address,
            street: bind { streetField.text },
            number: bind { numberField.text },
            city: bind { cityField.text }
             */
            
        }
        
  
    }

}

def float calculate() {
    streitWert=txtStreitWert.text
    if(streitWert.trim().length()==0) {
        txtStreitWert.foreground=java.awt.Color.RED
        txtStreitWert.text='???'
        return 0f;
    } else {
        txtStreitWert.foreground=java.awt.Color.BLACK
    }
    
    println( streitWert.toFloat() * 1.5d)
    //nGeschaeftsGebuehr.text = df.format(nStreitwert.text.toInteger() * 1.5d)
   
    
    
    
    rvgtab= new rvgtables_ui()
    float gebuehr=0f
    if(chkVV2300.isSelected()) {
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), spnVV2300.value.toFloat());
        lblVV2300.text = df.format(gebuehr)
    } else {
        lblVV2300.text = df.format(0f)
    }
    
    if(chkVV3100.isSelected()) {
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), spnVV3100.value.toFloat());
        lblVV3100.text = df.format(gebuehr)
    } else {
        lblVV3100.text = df.format(0f)
    }
    
    if(chkAnrechenbarerAnteil.isSelected()) {
        lblAnrechenbarerAnteil.text = df.format(df.parse(lblVV2300.text) / 2f)
    } else {
        lblAnrechenbarerAnteil.text = df.format(0f)
    }
    
    cmdCopy.enabled=true
    cmdDocument.enabled=true
    return gebuehr
}

def String copyToClipboard() {
    sbf=new StringBuffer()
    for (int i=0;i<10;i++) {
        for (int j=0;j<3;j++) {
            sbf.append(i + " " + j);
            if (j<3-1) sbf.append("\t");
        }
        sbf.append("\n");
    }
         
    //return sbf.toString()
    return "<html><table><tr><td>11</td><td>22</td></tr></table></html>"
}

def CalculationTable copyToDocument() {
    CalculationTable ct=new CalculationTable();
    ArrayList<String> colLabels=new ArrayList<String>();
    colLabels.add("Position");
    colLabels.add("Betrag");
    
    ArrayList<String> row=new ArrayList<String>();
    row.add("Irgendetwas Teures");
    row.add("578,00 EUR");
    ct.addRow(row);
    
    row=new ArrayList<String>();
    row.add("Nochwas Teures");
    row.add("308,00 EUR");
    ct.addRow(row);
    
    ct.setColumnLabels(colLabels);
    ct.setAlignment(1, CalculationTable.ALIGNMENT_RIGHT);
    
    return ct;
    
    
}