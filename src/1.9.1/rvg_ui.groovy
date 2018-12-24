

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.swing.SwingConstants
import java.util.ArrayList
import java.util.Locale
import javax.swing.JTable
import org.jlawyer.plugins.calculation.CalculationTable
import rvgtables_ui
import pkhtables_ui

@Bindable
class Address { 
    String street, number, city
    String toString() { "address[street=$street,number=$number,city=$city]" }
}

count = 0
//df = new DecimalFormat("0.00")
df = NumberFormat.getInstance(Locale.GERMANY).getNumberInstance();
df.setMaximumFractionDigits(2);
df.setMinimumFractionDigits(2);

// betragFormat = NumberFormat.getInstance(Locale.GERMANY).getCurrency();
betragFormat = NumberFormat.getInstance(Locale.GERMANY).getNumberInstance();
betragFormat.setMaximumFractionDigits(2);
betragFormat.setMinimumFractionDigits(2);
faktorFormat = new DecimalFormat("0.00");

customEntries = ['Beispieleintrag 1',
                    'Beispieleintrag 2',
                    'Beispieleintrag 3',
                    'Beispieleintrag 4']

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
                td (colfill:true, align: 'left') {
                    panel(border: titledBorder(title: 'Basisdaten')) {
                        tableLayout {
                            tr {
                                td (colfill:true) {
                                    label(text: 'Streitwert:')
                                }
                                td {
                                    txtStreitWert=formattedTextField(id: 'nStreitwert', format: betragFormat, text: binding.claimvalue, columns: 10)
                                }
                                td {
                                    label(text: 'EUR')
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
                            tr {
                                td (colfill:true) {
                                    label(text: 'Prozesskostenhilfe')
                                }
                                td {
                                    chkPKH = checkBox(id: 'bpkh', selected: false, stateChanged: {
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
                td (colfill:true) {
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
                                    chkVV1000 = checkBox(id: 'bVV1000',text: 'Einigungsgebühr VV1000ff.:', selected: false, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    spnVV1000 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f, 
                                            maximum: 10.0f, 
                                            value:1.5f,
                                            stepSize:0.1), stateChanged: {
                                            calculate()
                                        })
                                }
                                td (align: 'right') {
                                    lblVV1000 = label(text: '0,00')
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
                td (colfill:true) {
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
                td (colfill:true) {
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
                                    chkVV3202 = checkBox(id: 'bVV3102',text: 'Terminsgebühr VV3202:', selected: false, stateChanged: {
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
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Sonstiges')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkVV7000 =  checkBox(id:'bVV7000', text: 'Kopien VV7000:', selected: false, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    txtVV7000=formattedTextField(id: 'nVV7000', format: betragFormat, text: '0,00', columns: 4)
                                }
                                td (align: 'right') {
                                    lblVV7000 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR' )
                                }
                            }
                            tr {
                                td {
                                    chkVV7003 =  checkBox(id:'bVV7003', text: 'Fahrtkosten VV7003/7004 (netto):', selected: false, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    txtFahrtkosten=formattedTextField(id: 'nFahrtkosten', format: betragFormat, text: '0,00', columns: 4)
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
                                    txtAuslagenmM=formattedTextField(id: 'nAuslagenmM', format: betragFormat, text: '0,00', columns: 4)
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
                                    txtAuslagenoM=formattedTextField(id: 'nAuslagenoM', format: betragFormat, text: '0,00', columns: 4)
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
                                    txtquote=formattedTextField(id: 'nquote', format: faktorFormat, text: 1, columns: 4)
                                }
                                td (align: 'right') {
                                    lblquote = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkZahlungen =  checkBox(id:'bAuslagenoM', text: 'bisherige Zahlungen (Brutto)', selected: false, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    txtZahlungen=formattedTextField(id: 'nZahlungen', format: betragFormat, text: '0,00', columns: 4)
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
                                td (align: 'right') {0.00
                                    lblsum2 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkdiffPKH = checkBox(id: 'bdiffPKH',text: 'Differenz PKH', selected: false, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    lbldiffPKH = label(text: '0,00')
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
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Benutzerdefiniert')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td (align: 'right') {
                                    panel {
                                        
                                        button(text:'Zurücksetzen', actionPerformed: { reset() })
                                    }
                                }
                            }
                            tr {
                                td {
                                    panel {
                                        cmbCustomEntryName = comboBox(items: customEntries, editable: true)
                                        txtCustomEntryValue = formattedTextField(id: 'nCustomEntryValue', format: betragFormat, columns:4, text: '0,00')
                                        label (text: 'EUR')
                                        button(text:'Hinzufügen', actionPerformed: { add() })
                                    }
                                }
                            }
                            tr {
                                td {
                                    panel{
                                        scrollPane(preferredSize:[600, 250]){
                                            customTable = table(){
                                                tableModel(){
                                                    closureColumn(header:'Position', read:{it.name})
                                                    closureColumn(header:'Betrag', read:{it.number})
                                                }
                                            }
                                        }
                                    }
                                }
                                
                            }
                            tr {
                                td (align: 'right') {
                                    panel {
                                        label(text: 'Summe:')
                                        lblsum3 = label(text: '0,00')
                                        label(text: 'EUR')
                                    }
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


def void reset() {
    customTable.model.getRows().clear() 
    //customTable.model.rowsModel.value = model
    customTable.model.fireTableDataChanged()
    calculate()
}
 
def void add() {
    def newEntry = ['name': cmbCustomEntryName.selectedItem, 'number': txtCustomEntryValue.text]
    customTable.model.rowsModel.value.add(newEntry)
    customTable.model.fireTableDataChanged()
    calculate()
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
    
    //println( streitWert.toFloat() * 1.5d)
    println( betragFormat.parse(streitWert).floatValue() * 1.5d)
    //nGeschaeftsGebuehr.text = df.format(nStreitwert.text.toInteger() * 1.5d)
   
    
    
    
    rvgtab= new rvgtables_ui()
    pkhtab= new pkhtables_ui()
    float gebuehr=0f
    float factor=0.0f
    float diffPKH=0.0f
    
    if(chkVV2300.isSelected()) {
        switch (spnMandanten){
        case {spnMandanten.value.toFloat()==1f}: factor = spnVV2300.value.toFloat()
            break
        case {spnMandanten.value.toFloat()==8f}: factor = 2f + spnVV2300.value.toFloat()
            break
        default: factor = (spnMandanten.value.toFloat()-1f)*0.3f + spnVV2300.value.toFloat()
            break
        }
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor);
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor)-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor);  
        }
        lblVV2300.text = df.format(gebuehr)
    } else {
        lblVV2300.text = df.format(0f)
    }
    if(chkVV1000.isSelected()) {
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1000.value.toFloat());
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1000.value.toFloat())-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1000.value.toFloat()))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1000.value.toFloat());
        }
        lblVV1000.text = df.format(gebuehr)
    } else {
        lblVV1000.text = df.format(0f)
    }
    if(chkvorVV7002.isSelected()) {
        gebuehr=(df.parse(lblVV2300.text)+df.parse(lblVV1000.text)) * 0.2f;
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
        case {spnMandanten.value.toFloat()==1f}: factor = spnVV3100.value.toFloat()
            break
        case {spnMandanten.value.toFloat()==8f}: factor = 2f + spnVV3100.value.toFloat()
            break
        default: factor = (spnMandanten.value.toFloat()-1f)*0.3f + spnVV3100.value.toFloat()
            break
        }
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor);
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor)-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor);  
        }     
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
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3104.value.toFloat());
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3104.value.toFloat())-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3104.value.toFloat()))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3104.value.toFloat());
        } 
        lblVV3104.text = df.format(gebuehr)
    } else {
        lblVV3104.text = df.format(0f)
    }

    if(chkVV1003.isSelected()) {
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003.value.toFloat());
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003.value.toFloat())-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003.value.toFloat()))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003.value.toFloat());
        }
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
        case {spnMandanten.value.toFloat()==1f}: factor = spnVV3200.value.toFloat()
            break
        case {spnMandanten.value.toFloat()==8f}: factor = 2f +  + spnVV3200.value.toFloat()
            break
        default: factor = (spnMandanten.value.toFloat()-1f)*0.3f + spnVV3200.value.toFloat()
            break
        }
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor);
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor)-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), factor);  
        }
        lblVV3200.text = df.format(gebuehr)
    } else {
        lblVV3200.text = df.format(0f)
    }
    
    if(chkVV3202.isSelected()) {
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3202.value.toFloat());
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3202.value.toFloat())-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3202.value.toFloat()))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV3202.value.toFloat());
        }
        lblVV3202.text = df.format(gebuehr)
    } else {
        lblVV3202.text = df.format(0f)
    }

    if(chkVV1003Berufung.isSelected()) {
        if(chkPKH.isSelected()) {
            gebuehr=pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003Berufung.value.toFloat());
            diffPKH=diffPKH+(rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003Berufung.value.toFloat())-pkhtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003Berufung.value.toFloat()))
        } else {
            gebuehr=rvgtab.berechneWertGebuehr(betragFormat.parse(streitWert).floatValue(), spnVV1003Berufung.value.toFloat());
        }
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
    
    if(chkVV7000.isSelected()) {
        lblVV7000.text = txtVV7000.text
    } else {
        lblVV7000.text = df.format(0f)
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

    if(chkdiffPKH.isSelected()) {
        lbldiffPKH.text = df.format(diffPKH)
    } else {
        lbldiffPKH.text = df.format(0f)
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
                +df.parse(lblVV1000.text)
                +df.parse(lblVV3100.text)
                +df.parse(lblAnrechenbarerAnteil.text)
                +df.parse(lblVV3104.text)
                +df.parse(lblVV1003.text)
                +df.parse(lblVV7002.text)
                +df.parse(lblVV3200.text)
                +df.parse(lblVV3202.text)
                +df.parse(lblVV1003Berufung.text)
                +df.parse(lblVV7002Berufung.text)
                +df.parse(lblVV7000.text)
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
        +df.parse(lblVV1000.text)
        +df.parse(lblVV3100.text)
        +df.parse(lblAnrechenbarerAnteil.text)
        +df.parse(lblVV3104.text)
        +df.parse(lblVV1003.text)
        +df.parse(lblVV7002.text)
        +df.parse(lblVV3200.text)
        +df.parse(lblVV3202.text)
        +df.parse(lblVV1003Berufung.text)
        +df.parse(lblVV7002Berufung.text)
        +df.parse(lblVV7000.text)
        +df.parse(lblVV7003.text)
        +df.parse(lblVV7005.text)
        +df.parse(lblmwst.text)
        +df.parse(lblAuslagenoM.text)
    )
    lblsum1.text=df.format(gebuehr)
    
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
    

    // custom entries
    customRows=customTable.getRowCount()
    System.out.println(customRows + " custom entries")
    // there is actually no calculation for custom entries, they will just be added to the output in copyToClipboard or copyToDocument
    float customSum=0f;
    for(int i=0;i<customRows;i++) {
        rowCustomEntryName=customTable.getValueAt(i, 0);
        rowCustomEntryValue=customTable.getValueAt(i, 1);
        customSum=customSum+df.parse(rowCustomEntryValue);
    }
    lblsum3.text=df.format(df.parse(lblsum2.text) + customSum);
    

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
    if(chkVV1000.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Einigungsgebühr Nr.1000ff VV RVG - </td>").append(txtStreitWert.text).append(" €</td>");
        sbf.append("<td align=\"right\">").append(lblVV1000.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkvorVV7002.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen im Vorverfahren Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblvorVV7002.text).append(" €</td>");
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
    if(chkVV7000.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Kopien Nr. 7000 VV RVG:</td>");
        sbf.append("<td align=\"right\">").append(lblVV7000.text).append(" €</td>");
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
        sbf.append("<td align=\"left\">Quote ").append(txtquote.text).append(":</td>");
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
    
    
    customRows=customTable.getRowCount()
    System.out.println(customRows + " custom entries")
    if(customRows>0) {
        for(int i=0;i<customRows;i++) {
            rowCustomEntryName=customTable.getValueAt(i, 0);
            rowCustomEntryValue=customTable.getValueAt(i, 1);
            sbf.append("<tr>")
            sbf.append("<td align=\"left\">" + rowCustomEntryName + "</td>");
            sbf.append("<td align=\"right\">").append(rowCustomEntryValue).append(" €</td>");
            sbf.append("</tr>");
        }
        sbf.append("<tr><td colspan=\"2\"><hr noshade size=\"2\"/></td></tr>");
        sbf.append("<tr>")
        sbf.append("<td align=\"left\"><b>Summe</b></td>");
        sbf.append("<td align=\"right\"><b>").append(lblsum3.text).append(" €</td></b>");
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
        row.add("Geschäftsgebühr Nr. 2300, 1008 VV RVG - " + txtStreitWert.text + " €");
        row.add(lblVV2300.text);
        ct.addRow(row);
    }
    if(chkVV1000.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr.1000ff VV RVG - " + txtStreitWert.text + " €");
        row.add(lblVV1000.text);
        ct.addRow(row);
    }
    if(chkvorVV7002.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen im Vorverfahren Nr. 7002 VV RVG");
        row.add(lblvorVV7002.text);
        ct.addRow(row);
    }
    if(chkVV3100.selected) {
        row=new ArrayList<String>();
        row.add("Verfahrensgebühr Nr. 3100, 1008 VV RVG - " + txtStreitWert.text + " €");
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
        row.add("Terminsgebühr Nr. 3104 VV RVG - " + txtStreitWert.text + " €");
        row.add(lblVV3104.text);
        ct.addRow(row);
    }
    if(chkVV1003.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr. 1003 VV RVG - " + txtStreitWert.text + " €");
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
        row.add("Verfahrensgebühr Nr. 3200, 1008 VV RVG - " + txtStreitWert.text + " €");
        row.add(lblVV3200.text);
        ct.addRow(row);
    }
    if(chkVV3202.selected) {
        row=new ArrayList<String>();
        row.add("Terminsgebühr Nr. 3202 VV RVG - " + txtStreitWert.text + " €");
        row.add(lblVV3104.text);
        ct.addRow(row);
    }
    if(chkVV1003Berufung.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr. 1003f VV RVG - " + txtStreitWert.text + " €");
        row.add(lblVV1003Berufung.text);
        ct.addRow(row);
    } 
    if(chkVV7002Berufung.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen Nr. 7002 VV RVG");
        row.add(lblVV7002Berufung.text);
        ct.addRow(row);
    }
    if(chkVV7000.selected) {
        row=new ArrayList<String>();
        row.add("Kopien Nr. 7000 VV RVG");
        row.add(lblVV7000.text);
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
        row.add("Quote " + txtquote.text + "");
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
    
    customRows=customTable.getRowCount()
    System.out.println(customRows + " custom entries")
    if(customRows>0) {
        for(int i=0;i<customRows;i++) {
            rowCustomEntryName=customTable.getValueAt(i, 0);
            rowCustomEntryValue=customTable.getValueAt(i, 1);
            row=new ArrayList<String>();
            row.add(rowCustomEntryName);
            row.add(rowCustomEntryValue);
            ct.addRow(row);
        }
        row=new ArrayList<String>();
        row.add("Summe");
        row.add(lblsum3.text);
        ct.addRow(row);
        
    }
    
    ct.setColumnLabels(colLabels);
    ct.setAlignment(1, CalculationTable.ALIGNMENT_RIGHT);
    
    return ct;
    
}


