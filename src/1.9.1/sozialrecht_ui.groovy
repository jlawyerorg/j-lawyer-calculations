

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.swing.SwingConstants
import java.util.ArrayList
import java.util.Locale
import org.jlawyer.plugins.calculation.CalculationTable
//import rvgtables_ui
//import pkhtables_ui

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
faktorFormat = new DecimalFormat("0.00")
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
                                    label(text: 'pauschale Erhöhung/Reduzierung: ')
                                }
                                td {
                                        cbchange=comboBox(id:'cb', items:['Erhöhung um 20%', 'Erhöhung um 10%', 'Mittelgebühr', 'Veringerung um 10%', 'Veringerung um 20%', 'eigene'], selectedItem:'Mittelgebühr', itemStateChanged: {
                                                calculate()
                                            })
                                    }
                            }
                        }   
                    }     
                }
            }
            
            tr {
                td (colfill:true) { 
                    panel(border: titledBorder(title: 'RVG-Berechnung Vorverfahren')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkVV2302 = checkBox(id: 'bVV2302', text: 'Geschäftsgebühr VV2302:', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    lblVV2302uR = label(id: 'nVV2302uR', text: '0,00')
                                }
                                td {
                                    lblVV2302oR = label(id: 'nVV2302oR', text: '0,00')
                                }
                                td {
                                    txtVV2302=formattedTextField(id: 'nVV2302', format: betragFormat, text: '0,00', columns: 4)
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                                   
                
                            tr {
                                td {
                                    chkVV1005 = checkBox(id: 'bVV1005',text: 'Einigungsgebühr VV1005.:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV1005uR = label(id: 'nVV1005uR', text: '0,00')
                                }
                                td {
                                    lblVV1005oR = label(id: 'nVV1005oR', text: '0,00')
                                }
                                td (align: 'right') {
                                    txtVV1005=formattedTextField(id: 'nVV1005', format: betragFormat, text: '0,00', columns: 4)
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
                                    chkVV3102 = checkBox(id: 'nGeschaeftsGebuehr', text: 'Verfahrensgebühr VV3102:', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    lblVV3102uR = label(id: 'nVV3102uR', text: '0,00') 
                                }
                                td {
                                    lblVV3102oR = label(id: 'nVV3102oR', text: '0,00') 
                                }
                                td {
                                    txtVV3102=formattedTextField(id: 'nVV3102', format: betragFormat, text: '0,00', columns: 4)
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
                                    chkVV3106 = checkBox(id: 'bVV3106',text: 'Terminsgebühr VV3106:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV3106uR = label(id: 'nVV3106uR', text: '0,00') 
                                }
                                td {
                                    lblVV3106oR = label(id: 'nVV3106oR', text: '0,00') 
                                }
                                td {
                                    txtVV3106=formattedTextField(id: 'nVV3106', format: betragFormat, text: '0,00', columns: 4)
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV1006 = checkBox(id: 'bVV1006',text: 'Einigungsgebühr VV1006.:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV1006uR = label(id: 'nVV1006uR', text: '0,00')
                                }
                                td {
                                    lblVV1006oR = label(id: 'nVV1006oR', text: '0,00')
                                }
                                td (align: 'right') {
                                    txtVV1006=formattedTextField(id: 'nVV1006', format: betragFormat, text: '0,00', columns: 4)
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkVV7002 =  checkBox(text: 'Auslagen VV7002ff.:', selected: false,stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    label(text: ' ')
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
                                    chkVV3204 = checkBox(id: 'nVV3204', text: 'Verfahrensgebühr VV3204:', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    lblVV3204uR = label(id: 'nVV3204uR', text: '0,00') 
                                }
                                td {
                                    lblVV3204oR = label(id: 'nVV3204oR', text: '0,00') 
                                }
                                td {
                                    txtVV3204=formattedTextField(id: 'nVV3204', format: betragFormat, text: '0,00', columns: 4)
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                
                            tr {
                                td {
                                    chkVV3205 = checkBox(id: 'bVV3205',text: 'Terminsgebühr VV3205:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV3205uR = label(id: 'nVV3205uR', text: '0,00') 
                                }
                                td {
                                    lblVV3205oR = label(id: 'nVV3205oR', text: '0,00') 
                                }
                                td {
                                    txtVV3205=formattedTextField(id: 'nVV3205', format: betragFormat, text: '0,00', columns: 4)
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV1006Berufung = checkBox(id: 'bVV1006Berufung',text: 'Einigungsgebühr VV1006.:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV1006BerufunguR = label(id: 'nVV1006BerufunguR', text: '0,00')
                                }
                                td {
                                    lblVV1006BerufungoR = label(id: 'nVV1006BerufungoR', text: '0,00')
                                }
                                td (align: 'right') {
                                    txtVV1006Berufung=formattedTextField(id: 'nVV1006', format: betragFormat, text: '0,00', columns: 4)
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
                                    chkTagegeld = checkBox(id: 'bVVTagegeld',text: 'Tagegeld VV7005.:', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    cbTagegeld=comboBox(id:'cb', items:['bis 4h', '4 bis 8h', 'ab 8h'], selectedItem:'bis 4h', itemStateChanged: {
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
                                    label(text: 'zwischen Summe:')
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {0.00
                                   lblzwsum = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkmwst = checkBox(text: 'Mehrwertsteuer VV7008:', selected: true, stateChanged: {
                                        calculate()
                                    })
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
                                // do not close the window - have user do it.
                                // java.awt.Container container=com.jdimension.jlawyer.client.utils.FrameUtils.getDialogOfComponent(SCRIPTPANEL)
                                // container.setVisible(false)
                                // ((javax.swing.JDialog)container).dispose()
                                        
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

    float gebuehr=0f
    float factor=0.0f
    float change=1.0f
    float urahmen=0.0f
    float orahmen=0.0f
    
    switch (cbchange){
        case {cbchange.getItemAt(cbchange.getSelectedIndex())=='Erhöhung um 20%'}:change = 1.2f
        break
        case {cbchange.getItemAt(cbchange.getSelectedIndex())=='Erhöhung um 10%'}:change = 1.1f
        break
        case {cbchange.getItemAt(cbchange.getSelectedIndex())=='Veringerung um 10%'}:change = 0.9f
        break
        case {cbchange.getItemAt(cbchange.getSelectedIndex())=='Veringerung um 20%'}:change = 0.8f
        break
        default: change = 1.0f
    }
    switch (spnMandanten){
        case {spnMandanten.value.toFloat()==1f}: factor = 0f
        break
        case {spnMandanten.value.toFloat()==8f}: factor = 2f
        break
        default: factor = (spnMandanten.value.toFloat()-1f)*0.3f
        break
    }
  
  
    if(chkVV2302.isSelected()) {
        urahmen = 50f + (50f * factor);
        orahmen = 640f +(640f * factor);
        lblVV2302uR.text = df.format(urahmen);
        lblVV2302oR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV2302.text) == 0f) {
                txtVV2302.text = df.format(300f + (300f*factor))  
            } else {
                txtVV2302.text = txtVV2302.text
            }
        } else {
            txtVV2302.text = df.format(300f + (300f*factor))
        }
    } else {
        lblVV2302uR.text = df.format(0f);
        lblVV2302oR.text = df.format(0f)
        txtVV2302.text = df.format(0f)
    }
        
    if(chkVV1005.isSelected()) {
        urahmen = 50f;
        orahmen = 640f;
        lblVV1005uR.text = df.format(urahmen);
        lblVV1005oR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV1005.text) == 0f) {
                txtVV1005.text = df.format(300f)
            } else {
                txtVV1005.text = txtVV1005.text
            }
        } else {
            txtVV1005.text = df.format(300f)
        }
    } else {
        lblVV1005uR.text = df.format(0f);
        lblVV1005oR.text = df.format(0f)
        txtVV1005.text = df.format(0f)
    }

    if(chkvorVV7002.isSelected()) {
        gebuehr=(df.parse(txtVV2302.text)+df.parse(txtVV1005.text)) * 0.2f;
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

    if(chkVV3102.isSelected()) {
        urahmen = 50f + (50f * factor);
        orahmen = 550f +(550f * factor);
        lblVV3102uR.text = df.format(urahmen);
        lblVV3102oR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV3102.text) == 0f) {
                txtVV3102.text = df.format((urahmen+orahmen)/2*change)
            } else {
                txtVV3102.text = txtVV3102.text
            }
        } else {
            txtVV3102.text = df.format((urahmen+orahmen)/2*change)
        }
    } else {
        lblVV3102uR.text = df.format(0f);
        lblVV3102oR.text = df.format(0f)
        txtVV3102.text = df.format(0f)
        }
    
    if(chkAnrechenbarerAnteil.isSelected()) {
        gebuehr = df.parse(txtVV2302.text) / 2f * -1f
        if (gebuehr > -175f) {
            lblAnrechenbarerAnteil.text = df.format(gebuehr)
        } else {
         lblAnrechenbarerAnteil.text = df.format(-175f)   
        }
    } else {
        lblAnrechenbarerAnteil.text = df.format(0f)
    }

    if(chkVV3106.isSelected()) {
        urahmen = 50f;
        orahmen = 510f;
        lblVV3106uR.text = df.format(urahmen);
        lblVV3106oR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV3106.text) == 0f) {
                txtVV3106.text = df.format((urahmen+orahmen)/2*change)
            } else {
                txtVV3106.text = txtVV3106.text
            }
        } else {
            txtVV3106.text = df.format((urahmen+orahmen)/2*change)
        }
    } else {
        lblVV3106uR.text = df.format(0f);
        lblVV3106oR.text = df.format(0f)
        txtVV3106.text = df.format(0f)
        }

    if(chkVV1006.isSelected()) {
        urahmen = 50f;
        orahmen = 550f;
        lblVV1006uR.text = df.format(urahmen);
        lblVV1006oR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV1006.text) == 0f) {
                txtVV1006.text = df.format((urahmen+orahmen)/2*change)
            } else {
                txtVV1006.text = txtVV1006.text
            }
        } else {
            txtVV1006.text = df.format((urahmen+orahmen)/2*change)
        }
    } else {
        lblVV1006uR.text = df.format(0f);
        lblVV1006oR.text = df.format(0f)
        txtVV1006.text = df.format(0f)
    }
    
    if(chkVV7002.isSelected()) {
        gebuehr=(
        df.parse(txtVV3102.text)
        +df.parse(lblAnrechenbarerAnteil.text)
        +df.parse(txtVV3106.text)
        +df.parse(txtVV1006.text)
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
    
    if(chkVV3204.isSelected()) {
        urahmen = 60f + (60f * factor);
        orahmen = 680f +(680f * factor);
        lblVV3204uR.text = df.format(urahmen);
        lblVV3204oR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV3204.text) == 0f) {
                txtVV3204.text = df.format((urahmen+orahmen)/2*change)
            } else {
                txtVV3204.text = txtVV3204.text
            }
        } else {
            txtVV3204.text = df.format((urahmen+orahmen)/2*change)
        }
    } else {
        lblVV3204uR.text = df.format(0f);
        lblVV3204oR.text = df.format(0f)
        txtVV3204.text = df.format(0f)
    }
    
    if(chkVV3205.isSelected()) {
        urahmen = 50f;
        orahmen = 510f;
        lblVV3205uR.text = df.format(urahmen);
        lblVV3205oR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV3205.text) == 0f) {
                txtVV3205.text = df.format((urahmen+orahmen)/2*change)
            } else {
                txtVV3205.text = txtVV3205.text
            }
        } else {
            txtVV3205.text = df.format((urahmen+orahmen)/2*change)
        }     
    } else {
        lblVV3205uR.text = df.format(0f);
        lblVV3205oR.text = df.format(0f)
        txtVV3205.text = df.format(0f)
    }

    if(chkVV1006Berufung.isSelected()) {
        urahmen = 60f;
        orahmen = 680f;
        lblVV1006BerufunguR.text = df.format(urahmen);
        lblVV1006BerufungoR.text = df.format(orahmen);
        if (cbchange.getItemAt(cbchange.getSelectedIndex())=='eigene') {
            if (df.parse(txtVV1006Berufung.text) == 0f) {
                txtVV1006Berufung.text = df.format((urahmen+orahmen)/2*change)
            } else {
                txtVV1006Berufung.text = txtVV1006Berufung.text
            }
        } else {
            txtVV1006Berufung.text = df.format((urahmen+orahmen)/2*change)
        } 
    } else {
        lblVV1006BerufunguR.text = df.format(0f);
        lblVV1006BerufungoR.text = df.format(0f)
        txtVV1006Berufung.text = df.format(0f)
    }

    if(chkVV7002Berufung.isSelected()) {
        gebuehr=(
        df.parse(txtVV3204.text)
        +df.parse(txtVV3205.text)
        +df.parse(txtVV1006Berufung.text)
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

    if(chkTagegeld.isSelected()) {
        switch (cbTagegeld){
        case {cbTagegeld.getItemAt(cbTagegeld.getSelectedIndex())=='bis 4h'}:lblVV7005.text=df.format(25f)
        break
        case {cbTagegeld.getItemAt(cbTagegeld.getSelectedIndex())=='4 bis 8h'}:lblVV7005.text=df.format(40f)
        break
        case {cbTagegeld.getItemAt(cbTagegeld.getSelectedIndex())=='ab 8h'}:lblVV7005.text=df.format(70f)
        break
        default: lblVV7005.text=df.format(0f)
        }
    } else {
        lblVV7005.text = df.format(0f)
    }

   if(chkAuslagenmM.isSelected()) {
        lblAuslagenmM.text = txtAuslagenmM.text
    } else {
        lblAuslagenmM.text = df.format(0f)
    }

    gebuehr=(
        df.parse(txtVV2302.text)
        +df.parse(lblvorVV7002.text)
        +df.parse(txtVV1005.text)
        +df.parse(txtVV3102.text)
        +df.parse(lblAnrechenbarerAnteil.text)
        +df.parse(txtVV3106.text)
        +df.parse(txtVV1006.text)
        +df.parse(lblVV7002.text)
        +df.parse(txtVV3204.text)
        +df.parse(txtVV3205.text)
        +df.parse(txtVV1006Berufung.text)
        +df.parse(lblVV7002Berufung.text)
        +df.parse(lblVV7000.text)
        +df.parse(lblVV7003.text)
        +df.parse(lblAuslagenmM.text)
        +df.parse(lblVV7005.text)
        )
    lblzwsum.text=df.format(gebuehr)

    if(chkmwst.isSelected()) {
        gebuehr=df.parse(lblzwsum.text)*0.19f
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
        df.parse(lblzwsum.text)
        +df.parse(lblmwst.text)
        +df.parse(lblAuslagenoM.text)
    )
    lblsum1.text=df.format(gebuehr)
    
    if(chkquote.isSelected()){
    gebuehr=df.parse(txtquote.text)*df.parse(lblsum1.text)
    lblquote.text=df.format(gebuehr)
    } else {
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
        
    if(chkVV2302.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Geschäftsgebühr Nr. 2302, 1008 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV2302.text).append(" €</td>");
        sbf.append("</tr>");
    }

    if(chkVV1005.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Einigungsgebühr Nr.1005 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV1005.text).append(" €</td>");
        sbf.append("</tr>");
    } 
  
     if(chkvorVV7002.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen im Vorverfahren Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblvorVV7002.text).append(" €</td>");
        sbf.append("</tr>");
    } 

    if(chkVV3102.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Verfahrensgebühr Nr. 3102, 1008 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV3102.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkAnrechenbarerAnteil.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">abz&uuml;glich anrechenbarer Teil</td>");
        sbf.append("<td align=\"right\">").append(lblAnrechenbarerAnteil.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV3106.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Terminsgebühr Nr. 3106 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV3106.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV1006.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Einigungsgebühr Nr. 1006 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV1006.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV7002.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblVV7002.text).append(" €</td>");
        sbf.append("</tr>");
     }
    if(chkVV3204.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Verfahrensgebühr Nr. 3204, 1008 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV3204.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV3205.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Terminsgebühr Nr. 3205 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV3205.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV1006Berufung.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Einigungsgebühr Nr. 1006 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV1006Berufung.text).append(" €</td>");
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
    if(chkTagegeld.selected) {
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
        sbf.append("<tr><td colspan=\"2\"><hr noshade size=\"2\"/></td></tr>")
        sbf.append("<tr>");
        sbf.append("<td align=\"left\"><b>zwischen Summe</b></td>");
        sbf.append("<td align=\"right\"><b>").append(lblzwsum.text).append(" €</td></b>");
        sbf.append("</tr>");
        sbf.append("<tr>");
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
    if(chkVV2302.selected) {
        row=new ArrayList<String>();
        row.add("Geschäftsgebühr Nr. 2302, 1008 VV RVG");
        row.add(txtVV2302.text);
        ct.addRow(row);
    }
     if(chkVV1005.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr.1005 VV RVG");
        row.add(txtVV1005.text);
        ct.addRow(row);
    }
    if(chkvorVV7002.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen im Vorverfahren Nr. 7002 VV RVG");
        row.add(lblvorVV7002.text);
        ct.addRow(row);
    }
    if(chkVV3102.selected) {
        row=new ArrayList<String>();
        row.add("Verfahrensgebühr Nr. 3102, 1008 VV RVG");
        row.add(txtVV3102.text);
        ct.addRow(row);
    }
    if(chkAnrechenbarerAnteil.selected) {
        row=new ArrayList<String>();
        row.add("abzüglich anrechenbarer Teil");
        row.add(lblAnrechenbarerAnteil.text);
        ct.addRow(row);
    }
    if(chkVV3106.selected) {
        row=new ArrayList<String>();
        row.add("Terminsgebühr Nr. 3106 VV RVG");
        row.add(txtVV3106.text);
        ct.addRow(row);
    }
    if(chkVV1006.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr. 1006 VV RVG");
        row.add(txtVV1006.text);
        ct.addRow(row);
    } 
    if(chkVV7002.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen Nr. 7002 VV RVG");
        row.add(lblVV7002.text);
        ct.addRow(row);
    }
    if(chkVV3204.selected) {
        row=new ArrayList<String>();
        row.add("Verfahrensgebühr Nr. 3204, 1008 VV RVG");
        row.add(txtVV3204.text);
        ct.addRow(row);
    }
    if(chkVV3205.selected) {
        row=new ArrayList<String>();
        row.add("Terminsgebühr Nr. 3205 VV RVG");
        row.add(txtVV3205.text);
        ct.addRow(row);
    }
    if(chkVV1006Berufung.selected) {
        row=new ArrayList<String>();
        row.add("Einigungsgebühr Nr. 1006 VV RVG");
        row.add(txtVV1006Berufung.text);
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
    if(chkTagegeld.selected){
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
        row.add("zwischen Summe");
        row.add(lblzwsum.text);
        ct.addRow(row);  
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
    ct.setColumnLabels(colLabels);
    ct.setAlignment(1, CalculationTable.ALIGNMENT_RIGHT);
    
    return ct;
    
}