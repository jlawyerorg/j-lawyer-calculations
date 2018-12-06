

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
                                
                        /*cmdCopy = button(text: 'Kopieren', enabled: false, toolTipText: 'In Zwischenablage kopieren', actionPerformed: {
                                if(binding.callback != null)
                                binding.callback.processResultToClipboard(copyToClipboard())
                                java.awt.Container container=com.jdimension.jlawyer.client.utils.FrameUtils.getDialogOfComponent(SCRIPTPANEL)
                                container.setVisible(false)
                                ((javax.swing.JDialog)container).dispose()
                                        
                            })
                        
                        cmdDocument = button(text: 'Dokument erstellen', enabled: false, toolTipText: 'Ergebnis in Dokument uebernehmen', actionPerformed: {
                                if(binding.callback != null)
                                binding.callback.processResultToDocument(copyToDocument(), SCRIPTPANEL)
                                        
                            })*/
                    
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
                                    txtStreitWert=textField(id: 'nStreitwert', text: binding.claimvalue, columns: 10)
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
                                    spnMandanten = spinner(id: 'spin1', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 8, 
                                            value:1,
                                            stepSize:1),stateChanged: {
                                                calculate()
                                            })
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
                    panel(border: titledBorder(title: 'RVG-Berechnung Vorverfahren')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkVV2300 = checkBox(id: 'bVV2300', text: 'Geschäftsgebühr VV2300:', selected: false, stateChanged: {
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
                                    chkVV1001 = checkBox(id: 'bVV1001',text: 'Einigungsgebühr VV1001.:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    spnVV1001 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.5f,
                                            stepSize:0.1), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV1001 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkvorVV7002 =  checkBox(text: 'Vorverfahren Auslagen VV7002ff.:', selected: false,stateChanged: {
                                                calculate()
                                   })
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    lblvorVV7002 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                                   }

                        }  
                        
                       
                    }     
                }
            }
            
              tr {
                td {
                    panel(border: titledBorder(title: 'RVG-Berechnung 1. Instanz')) {
                        tableLayout (cellpadding: 5) {                           
                            tr {
                                td {
                                    chkVV3100 = checkBox(id: 'nGeschaeftsGebuehr', text: 'Verfahrensgebühr VV3100:', selected: true, stateChanged: {
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
                                    chkAnrechenbarerAnteil = checkBox(text: 'abzüglich anrechenbarer Teil:', selected: false, stateChanged: {
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
                                    chkVV3104 = checkBox(id: 'bVV3104',text: 'Terminsgebühr VV3104:', selected: true, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    spnVV3104 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.2f,
                                            stepSize:0.1), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV3104 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV1003 = checkBox(id: 'bVV1003',text: 'Einigungsgebühr VV1003f.:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    spnVV1003 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.0f,
                                            stepSize:0.1), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV1003 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkVV7002 =  checkBox(text: 'Auslagen VV7002ff.:', selected: true,stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    lblVV7002 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                        }
                    }
                }
              }
           
           tr {
                td {
                    panel(border: titledBorder(title: 'RVG-Berechnung 2. Instanz')) {
                        tableLayout (cellpadding: 5) {                           
                            tr {
                                td {
                                    chkVV3200 = checkBox(id: 'nGeschaeftsGebuehr', text: 'Verfahrensgebühr VV3200:', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    spnVV3200 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.6f,
                                            stepSize:0.1f), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV3200 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                
                            tr {
                                td {
                                    chkVV3202 = checkBox(id: 'bVV3104',text: 'Terminsgebühr VV3202:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    spnVV3202 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.2f,
                                            stepSize:0.1), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV3202 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV1003Berufung = checkBox(id: 'bVV1003',text: 'Einigungsgebühr VV1003f.:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    spnVV1003Berufung = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.0f,
                                            stepSize:0.1), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                    lblVV1003Berufung = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkVV7002Berufung =  checkBox(text: 'Auslagen VV7002ff.:', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    lblVV7002Berufung = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                        }
                    }
                }
            }
           
           
            tr {
                td {
                    panel(border: titledBorder(title: 'Sonstiges')) {
                        tableLayout (cellpadding: 5) { 
                           
                            tr {
                                td {
                                   chkVV7003 =  checkBox(id:'bVV7003', text: 'Fahrtkosten VV7003/7004 (netto):', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    txtFahrtkosten=textField(id: 'nFahrtkosten', text: '0.00', columns: 4)
                                }
                             td (align: 'right') {
                                    lblVV7003 = label(text: '0,00')
                                }
                                 td (align: 'right') {
                                    label(text: 'EUR' )
                                }
                            }
                             tr {
                                td {
                                    label(text: 'Tagegeld VV7005.:')
                                }
                                td {
                                    chkTagegeld4h = checkBox(id: 'bTagegeld4h',text: 'bis 4h', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                 td (align: 'right'){
                                    lblVV7005 = label(text: '0,00')
                                }
                                td  (align: 'right'){
                                    label(text: 'EUR')
                                }
                             }
                             tr{
                                 td {
                                    label(text: ' ')
                                }
                                td {
                                    chkTagegeld6h = checkBox(id: 'bVVTagegeld6h',text: '4 bis 8h', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                             }
                            tr{
                                td {
                                    label(text: ' ')
                                }
                                 
                                td {
                                    chkTagegeld8h = checkBox(id: 'bTagegeld8h',text: 'ab 8h', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                            }
                    
                            tr {
                                td {
                                   chkAuslagenmM =  checkBox(id:'bAuslagenmM', text: 'steuerpflichtige Auslagen (netto):', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    txtAuslagenmM=textField(id: 'nAuslagenmM', text: '0.00', columns: 4)
                                }
                             td (align: 'right') {
                                    lblAuslagenmM = label(text: '0,00')
                                }
                                 td (align: 'right') {
                                    label(text: 'EUR' )
                                }
                            }
                            
                            tr {
                                td {
                                    chkmwst = checkBox(text: 'Mehrwertsteuer VV7008:', selected: true, stateChanged: {
                                        calculate()
                                    }
                                    )
                                }
                                td {
                                    label(text: '19%')
                                }
                                td (align: 'right') {
                                   lblmwst = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkAuslagenoM =  checkBox(id:'bAuslagenoM', text: 'steuerfreie Auslagen:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    txtAuslagenoM=textField(id: 'nAuslagenoM', text: '0.00', columns: 4)
                                }
                             td (align: 'right') {
                                    lblAuslagenoM = label(text: '0,00')
                                }
                                 td (align: 'right') {
                                    label(text: 'EUR' )
                                }
                            }
                            tr {
                                td {
                                    label(text: 'Summe:')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                   lblsum1 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }

                            tr {
                                td {
                                    chkquote = checkBox(text: 'Quote (Bsp: 0,5):', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    txtquote=textField(id: 'nquote', text: 1, columns: 4)
                                }
                                td (align: 'right') {
                                   lblquote = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            /*tr {
                                td {
                                    label(text: 'Quote (Bsp: 1 / 2):')
                                }
                                td {
                                    spndivident = spinner(
                                        model:spinnerNumberModel(minimum:1.0f, 
                                            maximum: 20.0f, 
                                            value:1.0f,
                                            stepSize:1), stateChanged: {
                                                calculate()
                                            })
                                }
                                 td {
                                    spndivisor = spinner(
                                        model:spinnerNumberModel(minimum:1.0f, 
                                            maximum: 20f, 
                                            value:1.0f,
                                            stepSize:1), stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                   lblquote = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }*/
                            tr {
                                td {
                                   chkZahlungen =  checkBox(id:'bAuslagenoM', text: 'bisherige Zahlungen (Brutto)', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    txtZahlungen=textField(id: 'nZahlungen', text: '0.00', columns: 4)
                                }
                             td (align: 'right') {
                                    lblZahlungen = label(text: '0,00')
                                }
                                 td (align: 'right') {
                                    label(text: 'EUR' )
                                }
                            }
                            tr {
                                td {
                                    label(text: 'darin enthaltene Mehrwertsteuer:')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                   lblmwstZahlung = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    label(text: 'Summe:')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                   lblsum2 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
        
                        }  
                        
                    
                    }     
                }
            }
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
                                java.awt.Container container=com.jdimension.jlawyer.client.utils.FrameUtils.getDialogOfComponent(SCRIPTPANEL)
                                container.setVisible(false)
                                ((javax.swing.JDialog)container).dispose()
                                        
                            })
                        
                        cmdDocument = button(text: 'Dokument erstellen', enabled: false, toolTipText: 'Ergebnis in Dokument uebernehmen', actionPerformed: {
                                if(binding.callback != null)
                                binding.callback.processResultToDocument(copyToDocument(), SCRIPTPANEL)
                                        
                            })
                    
                    }
                }

                
            }
            
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
    float factor=0.0f
    
       if(chkVV2300.isSelected()) {
       switch (spnMandanten){
            case {spnMandanten.value.toFloat()==1f}: factor = 0f
            break
            case {spnMandanten.value.toFloat()==8f}: factor = 2f
            break
            default: factor = (spnMandanten.value.toFloat()-1f)*0.3f
            break
       }
       switch(spnVV2300) {
            case {factor < spnVV2300.value.toFloat()}: factor = spnVV2300.value.toFloat() + factor
            break
            case {factor >= spnVV2300.value.toFloat()}: factor = 2f * spnVV2300.value.toFloat()
            break
       }
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), factor);  
        lblVV2300.text = df.format(gebuehr)
    } else {
        lblVV2300.text = df.format(0f)
    }
    if(chkVV1001.isSelected()) {
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), spnVV1001.value.toFloat());
        lblVV1001.text = df.format(gebuehr)
    } else {
        lblVV1001.text = df.format(0f)
    }
    if(chkvorVV7002.isSelected()) {
        gebuehr=(df.parse(lblVV2300.text)+df.parse(lblVV1001.text)) * 0.2f;
        switch(gebuehr) {
            case {it < 20f}: gebuehr = gebuehr
            break
            case {it >= 20f}: gebuehr = 20f  
            break
        }
        lblvorVV7002.text = df.format(gebuehr)
    } else {
        lblvorVV7002.text = df.format(0f)
    }
    if(chkVV3100.isSelected()) {
            switch (spnMandanten){
            case {spnMandanten.value.toFloat()==1f}: factor = 0f
            break
            case {spnMandanten.value.toFloat()==8f}: factor = 2f
            break
            default: factor = (spnMandanten.value.toFloat()-1f)*0.3f
            break
       }
       switch(spnVV3100) {
            case {factor < spnVV3100.value.toFloat()}: factor = spnVV3100.value.toFloat() + factor
            break
            case {factor >= spnVV3100.value.toFloat()}: factor = 2f * spnVV3100.value.toFloat()
            break
       }
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), factor);
        lblVV3100.text = df.format(gebuehr)
    } else {
        lblVV3100.text = df.format(0f)
    }
    
    if(chkAnrechenbarerAnteil.isSelected()) {
        lblAnrechenbarerAnteil.text = df.format(df.parse(lblVV2300.text) / 2f * -1f)
    } else {
        lblAnrechenbarerAnteil.text = df.format(0f)
    }
    if(chkVV3104.isSelected()) {
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), spnVV3104.value.toFloat());
        lblVV3104.text = df.format(gebuehr)
    } else {
        lblVV3104.text = df.format(0f)
    }

    if(chkVV1003.isSelected()) {
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), spnVV1003.value.toFloat());
        lblVV1003.text = df.format(gebuehr)
    } else {
        lblVV1003.text = df.format(0f)
    }
    if(chkVV7002.isSelected()) {
        gebuehr=(
            df.parse(lblVV3100.text)
            +df.parse(lblAnrechenbarerAnteil.text)
            +df.parse(lblVV3104.text)
            +df.parse(lblVV1003.text)
            ) * 0.2f;
        switch(gebuehr) {
            case {it < 20f}: gebuehr = gebuehr
            break
            case {it >= 20f}: gebuehr = 20f  
            break
        }
        lblVV7002.text = df.format(gebuehr)
    } else {
        lblVV7002.text = df.format(0f)
    }
   
if(chkVV3200.isSelected()) {
            switch (spnMandanten){
            case {spnMandanten.value.toFloat()==1f}: factor = 0f
            break
            case {spnMandanten.value.toFloat()==8f}: factor = 2f
            break
            default: factor = (spnMandanten.value.toFloat()-1f)*0.3f
            break
       }
       switch(spnVV3200) {
            case {factor < spnVV3200.value.toFloat()}: factor = spnVV3200.value.toFloat() + factor
            break
            case {factor >= spnVV3200.value.toFloat()}: factor = 2f * spnVV3200.value.toFloat()
            break
       }
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), factor);
        lblVV3200.text = df.format(gebuehr)
    } else {
        lblVV3200.text = df.format(0f)
    }
    
    if(chkVV3202.isSelected()) {
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), spnVV3202.value.toFloat());
        lblVV3202.text = df.format(gebuehr)
    } else {
        lblVV3202.text = df.format(0f)
    }

    if(chkVV1003Berufung.isSelected()) {
        gebuehr=rvgtab.berechneWertGebuehr(streitWert.toFloat(), spnVV1003Berufung.value.toFloat());
        lblVV1003Berufung.text = df.format(gebuehr)
    } else {
        lblVV1003Berufung.text = df.format(0f)
    }

    if(chkVV7002Berufung.isSelected()) {
        gebuehr=(
            df.parse(lblVV3200.text)
            +df.parse(lblVV3202.text)
            +df.parse(lblVV1003Berufung.text)
         ) * 0.2f;
        switch(gebuehr) {
            case {it < 20f}: gebuehr = gebuehr
            break
            case {it >= 20f}: gebuehr = 20f  
            break
        }
        lblVV7002Berufung.text = df.format(gebuehr)
    } else {
        lblVV7002Berufung.text = df.format(0f)
    }


    if(chkVV7003.isSelected()) {
        lblVV7003.text = txtFahrtkosten.text
    } else {
        lblVV7003.text = df.format(0f)
    }

    if(chkTagegeld8h.isSelected()){
        lblVV7005.text=df.format(70f)
    }else if(chkTagegeld6h.isSelected()){
        lblVV7005.text=df.format(40f)
    }else if(chkTagegeld4h.isSelected()){
        lblVV7005.text=df.format(25f)
    }else{
        lblVV7005.text=df.format(0f)
    }

   if(chkAuslagenmM.isSelected()) {
        lblAuslagenmM.text = txtAuslagenmM.text
    } else {
        lblAuslagenmM.text = df.format(0f)
    }

    if(chkmwst.isSelected()) {
        gebuehr=((
            df.parse(lblVV2300.text)
            +df.parse(lblvorVV7002.text)
            +df.parse(lblVV1001.text)
            +df.parse(lblVV3100.text)
            +df.parse(lblAnrechenbarerAnteil.text)
            +df.parse(lblVV3104.text)
            +df.parse(lblVV1003.text)
            +df.parse(lblVV7002.text)
            +df.parse(lblVV3200.text)
            +df.parse(lblVV3202.text)
            +df.parse(lblVV1003Berufung.text)
            +df.parse(lblVV7002Berufung.text)
            +df.parse(lblVV7003.text)
            +df.parse(lblAuslagenmM.text)
            +df.parse(lblVV7005.text)
            )*0.19f)
        lblmwst.text = df.format(gebuehr)
    } else {
        lblmwst.text = df.format(0f)
    }

    if(chkAuslagenoM.isSelected()) {
        lblAuslagenoM.text = txtAuslagenoM.text
    } else {
        lblAuslagenoM.text = df.format(0f)
    }
    
    gebuehr=(
             df.parse(lblVV2300.text)
            +df.parse(lblvorVV7002.text)
            +df.parse(lblVV1001.text)
            +df.parse(lblVV3100.text)
            +df.parse(lblAnrechenbarerAnteil.text)
            +df.parse(lblVV3104.text)
            +df.parse(lblVV1003.text)
            +df.parse(lblVV7002.text)
            +df.parse(lblVV3200.text)
            +df.parse(lblVV3202.text)
            +df.parse(lblVV1003Berufung.text)
            +df.parse(lblVV7002Berufung.text)
            +df.parse(lblVV7003.text)
            +df.parse(lblVV7005.text)
            +df.parse(lblmwst.text)
            +df.parse(lblAuslagenoM.text)
    )
    lblsum1.text=df.format(gebuehr)

    /*lblquote.text=(df.parse(lblsum1.text)*spndivident.value.toFloat())/spndivisor.value.toFloat()
      
    lblquote.text=(spndivident.value.toFloat()/spndivisor.value.toFloat())*df.parse(lblsum1.text)*/
    
    if(chkquote.isSelected()){
    gebuehr=df.parse(txtquote.text)*df.parse(lblsum1.text)
    lblquote.text=df.format(gebuehr)
    } else{
        lblquote.text=lblsum1.text
    }

    if(chkZahlungen.isSelected()) {
        lblZahlungen.text = txtZahlungen.text
    } else {
        lblZahlungen.text = df.format(0f)
        }

    if(chkmwst.isSelected()) {
        gebuehr=(df.parse(lblZahlungen.text)*0.19f)
        lblmwstZahlung.text = df.format(gebuehr)
    } else {
        lblmwstZahlung.text = df.format(0f)
        }
    
    gebuehr=df.parse(lblquote.text)-df.parse(lblZahlungen.text)
    lblsum2.text=df.format(gebuehr)
    


    cmdCopy.enabled=true
    cmdDocument.enabled=true
    return gebuehr
}

def String copyToClipboard() {
    sbf=new StringBuffer()
    sbf.append("<html><table width=\"85%\">");
    sbf.append("<tr>")
        sbf.append("<td align=\"left\"><b>Position</b></td>");
        sbf.append("<td align=\"right\"><b>Betrag</b></td>");
        sbf.append("</tr>");
        sbf.append("<tr><td colspan=\"2\"><hr noshade size=\"2\"/></td></tr>");
        
    if(chkVV2300.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Geschäftsgebühr Nr. 2300, 1008 VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV2300.text).append(" €</td>");
        sbf.append("</tr>");
    }
  
     if(chkvorVV7002.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen im Vorverfahren Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblvorVV7002.text).append(" €</td>");
        sbf.append("</tr>");
     }
     if(chkVV1001.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Einigungsgebühr Nr.1001 VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV1001.text).append(" €</td>");
        sbf.append("</tr>");
     }  

    if(chkVV3100.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Verfahrensgebühr Nr. 3100, 1008 VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV3100.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkAnrechenbarerAnteil.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">abz&uuml;glich anrechenbarer Teil</td>");
        sbf.append("<td align=\"right\">").append(lblAnrechenbarerAnteil.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV3104.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Terminsgebühr Nr. 3104 VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV3104.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV1003.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Einigungsgebühr Nr. 1003 VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV1003.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV7002.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblVV7002.text).append(" €</td>");
        sbf.append("</tr>");
     }
    if(chkVV3200.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Verfahrensgebühr Nr. 3200, 1008 VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV3200.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV3202.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Terminsgebühr Nr. 3202 VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV3202.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV1003Berufung.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Einigungsgebühr Nr. 1003f VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV1003Berufung.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV7002Berufung.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblVV7002Berufung.text).append(" €</td>");
        sbf.append("</tr>");
     }
    if(chkVV7003.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Fahrtkosten Nr. 7003/7004 VV RVG:</td>");
        sbf.append("<td align=\"right\">").append(lblVV7003.text).append(" €</td>");
        sbf.append("</tr>");
     }
     if((chkTagegeld4h.selected) || (chkTagegeld6h.selected) || (chkTagegeld8h.selected)) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Tagegeld Nr. 7005 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblVV7005.text).append(" €</td>");
        sbf.append("</tr>");
     }
     if(chkAuslagenmM.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">sonstige steuerpflichtige Auslagen (netto):</td>");
        sbf.append("<td align=\"right\">").append(lblAuslagenmM.text).append(" €</td>");
        sbf.append("</tr>");
     }     
    if(chkmwst.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Mehrwertsteuer 19% Nr. 7008 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblmwst.text).append(" €</td>");
        sbf.append("</tr>");
     }
    if(chkAuslagenoM.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">sonstige steuerfreie Auslagen</td>");
        sbf.append("<td align=\"right\">").append(lblAuslagenoM.text).append(" €</td>");
        sbf.append("</tr>");
     } 
     
        sbf.append("<tr><td colspan=\"2\"><hr noshade size=\"2\"/></td></tr>");
        sbf.append("<tr>")
        sbf.append("<td align=\"left\"><b>Summe</b></td>");
        sbf.append("<td align=\"right\"><b>").append(lblsum1.text).append(" €</td></b>");
        sbf.append("</tr>");

    if(chkquote.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Quote ").append(txtquote.text).append(" (Streitwert ").append(txtStreitWert.text).append(" EUR)</td>").append(":</td>");
        sbf.append("<td align=\"right\">").append(lblquote.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkZahlungen.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">bisherige Zahlungen</td>");
        sbf.append("<td align=\"right\">").append(lblZahlungen.text).append(" €</td>");
        sbf.append("</tr>");
     } 
    if((chkmwst.selected)&&(chkZahlungen.selected)) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">darin enthaltenen MwSt. (19%)</td>");
        sbf.append("<td align=\"right\">").append(lblmwstZahlung.text).append(" €</td>");
        sbf.append("</tr>");
     }
    if(chkZahlungen.selected) {
        
        sbf.append("<tr>")
        sbf.append("<td align=\"left\"><b>Summe</b></td>");
        sbf.append("<td align=\"right\"><b>").append(lblsum2.text).append(" €</td></b>");
        sbf.append("</tr>");
     }

    sbf.append("</table></html>");
    
    return sbf.toString()
    
}

def CalculationTable copyToDocument() {
    CalculationTable ct=new CalculationTable();
    ArrayList<String> colLabels=new ArrayList<String>();
    colLabels.add("Position");
    colLabels.add("Betrag");
    
    ArrayList<String> row=new ArrayList<String>();
    /*row.add(" ");
        row.add(" ");
        ct.addRow(row);
    */
    if(chkVV2300.selected) {
        row=new ArrayList<String>();
        row.add("Geschäftsgebühr Nr. 2300, 1008 VV RVG");
        row.add(lblVV2300.text);
        ct.addRow(row);
    }
    if(chkvorVV7002.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen im Vorverfahren Nr. 7002 VV RVG");
        row.add(lblvorVV7002.text);
        ct.addRow(row);
    }
    if(chkVV1001.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr.1001 VV RVG");
        row.add(lblVV1001.text);
        ct.addRow(row);
    }

    if(chkVV3100.selected) {
        row=new ArrayList<String>();
        row.add("Verfahrensgebühr Nr. 3100, 1008 VV RVG");
        row.add(lblVV3100.text);
        ct.addRow(row);
    }
    if(chkAnrechenbarerAnteil.selected) {
        row=new ArrayList<String>();
        row.add("abzüglich anrechenbarer Teil");
        row.add(lblAnrechenbarerAnteil.text);
        ct.addRow(row);
    }
    if(chkVV3104.selected) {
        row=new ArrayList<String>();
        row.add("Terminsgebühr Nr. 3104 VV RVG");
        row.add(lblVV3104.text);
        ct.addRow(row);
    }
    if(chkVV1003.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr. 1003 VV RVG");
        row.add(lblVV1003.text);
        ct.addRow(row);
    } 
    if(chkVV7002.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen Nr. 7002 VV RVG");
        row.add(lblVV7002.text);
        ct.addRow(row);
    }
    if(chkVV3200.selected) {
        row=new ArrayList<String>();
        row.add("Verfahrensgebühr Nr. 3w00, 1008 VV RVG");
        row.add(lblVV3w00.text);
        ct.addRow(row);
    }
    if(chkVV3202.selected) {
        row=new ArrayList<String>();
        row.add("Terminsgebühr Nr. 3202 VV RVG");
        row.add(lblVV3104.text);
        ct.addRow(row);
    }
    if(chkVV1003Berufung.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr. 1003f VV RVG");
        row.add(lblVV1003Berufung.text);
        ct.addRow(row);
    } 
    if(chkVV7002Berufung.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen Nr. 7002 VV RVG");
        row.add(lblVV7002Berufung.text);
        ct.addRow(row);
    }
    if(chkVV7003.selected) {
        row=new ArrayList<String>();
        row.add("Fahrtkosten Nr. 7003/7004 VV RVG");
        row.add(lblVV7003.text);
        ct.addRow(row);
    }
    if((chkTagegeld4h.selected) || (chkTagegeld6h.selected) || (chkTagegeld8h.selected)) {
        row=new ArrayList<String>();
        row.add("Tagegeld Nr. 7005 VV RVG");
        row.add(lblVV7005.text);
        ct.addRow(row);
    }
    if(chkAuslagenmM.selected) {
        row=new ArrayList<String>();
        row.add("sonstige steuerpflichtige Auslagen (netto)");
        row.add(lblAuslagenmM.text);
        ct.addRow(row);
    }
    if(chkmwst.selected) {
        row=new ArrayList<String>();
        row.add("Mehrwertsteuer 19% Nr. 7008 VV RVG");
        row.add(lblmwst.text);
        ct.addRow(row);
    }
    if(chkAuslagenoM.selected) {
        row=new ArrayList<String>();
        row.add("sonstige steuerfreie Auslagen");
        row.add(lblAuslagenoM.text);
        ct.addRow(row);
    }
    row=new ArrayList<String>();
        row.add("Summe");
        row.add(lblsum1.text);
        ct.addRow(row);
    
    if(chkquote.selected) {
        row=new ArrayList<String>();
        row.add("Quote (Streitwert " + txtStreitWert.text + " EUR)");
        row.add(lblquote.text);
        ct.addRow(row);
    }
    if(chkZahlungen.selected) {
        row=new ArrayList<String>();
        row.add("bisherige Zahlungen");
        row.add(lblZahlungen.text);
        ct.addRow(row);
    }
    if((chkmwst.selected)&&(chkZahlungen.selected)) {
        row=new ArrayList<String>();
        row.add("darin enthaltene MwSt. (19%)");
        row.add(lblmwstZahlung.text);
        ct.addRow(row);
    }
    if(chkZahlungen.selected) {
        row=new ArrayList<String>();
        row.add("Summe");
        row.add(lblsum2.text);
        ct.addRow(row);
    }
    
    ct.setColumnLabels(colLabels);
    ct.setAlignment(1, CalculationTable.ALIGNMENT_RIGHT);
    
    return ct;
    
}