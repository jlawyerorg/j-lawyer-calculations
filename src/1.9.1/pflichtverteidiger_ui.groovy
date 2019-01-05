

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
                                    label(text: 'Gericht der 1. Instanz:')
                                }
                                td {
                                        cbGericht=comboBox(id:'cbGericht', items:['Amtsgericht', 'Strafkammer', 'Schwurgericht / OLG'], selectedItem:'Amtsgericht', itemStateChanged: {
                                                calculate()
                                            })
                                    }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: 'Mandant in Haft:')
                                }
                                td {
                                    chkHaft = checkBox(id: 'bHaft', text: '', selected: false, stateChanged: {
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
                    panel(border: titledBorder(title: 'allgemeine Gebühren')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkVV4100 = checkBox(id: 'bVV4100', text: '', selected: true, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    lblVV4100 = label(id: 'nVV4100', text: 'Grundgebühr Nr. 4100')
                                }
                                td (align: 'right') {
                                   txtVV4100 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV4102 = checkBox(id: 'bVV4102',text: '', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV4102 = label(id: 'nVV4102', text: 'Terminsgebühr Nr. 4102')
                                }
                                td (align: 'right') {
                                   txtVV4102 = label(text: '0,00')
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
                    panel(border: titledBorder(title: 'vorbereitendes Verfahren')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkVV4104 = checkBox(id: 'bVV4104', text: '', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    lblVV4104 = label(id: 'nVV4104', text: 'Verfahrensgebühr Nr. 4104')
                                }
                                td (align: 'right') {
                                   txtVV4104 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkvorVV7002 =  checkBox(text: '', selected: false,stateChanged: {
                                                calculate()
                                   })
                                }
                                td {
                                    label(text: 'Auslagen Nr. 7002')
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
                    panel(border: titledBorder(title: '1. Instanz')) {
                        tableLayout (cellpadding: 5) {                           
                            tr {
                                td {
                                    chkVV4106 = checkBox(id: 'nGeschaeftsGebuehr', text: '', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    lblVV4106 = label(id: 'nVV4106', text: 'Verfahrensgebühr Nr. 4106') 
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                   txtVV4106 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV4108 = checkBox(id: 'bVV4108',text: '', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV4108 = label(id: 'nVV4108', text: 'Terminsgebühr Nr. 4108') 
                                }
                                td {
                                    spnVV4108 = spinner(id: 'nspnVV4108', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 99, 
                                            value:1,
                                            stepSize:1),stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                   txtVV4108 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV4110 = checkBox(id: 'bVV4110',text: '', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV4110=label(id: 'nVV4110', text: 'Zuschlag (5-8h) Nr. 4110') 
                                }
                                td {
                                    spnVV4110 = spinner(id: 'nspnVV4110', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 99, 
                                            value:1,
                                            stepSize:1),stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                   txtVV4110 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV4111 = checkBox(id: 'bVV4111',text: '', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV4111=label(id: 'nVV4111', text: 'Zuschlag (ab 8h) Nr. 4111') 
                                }
                                td {
                                    spnVV4111 = spinner(id: 'nspnVV4111', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 99, 
                                            value:1,
                                            stepSize:1),stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                   txtVV4111 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkVV7002 =  checkBox(text: '', selected: false,stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    label(text: 'Auslagen Nr. 7002')
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
                    panel(border: titledBorder(title: 'Berufung')) {
                        tableLayout (cellpadding: 5) {                           
                            tr {
                                td {
                                    chkVV4124 = checkBox(id: 'nVV4124', text: '', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    lblVV4124 = label(id: 'nVV4124', text: 'Verfahrensgebühr Nr. 4124') 
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                   txtVV4124 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV4126 = checkBox(id: 'bVV4126',text: '', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    lblVV4126 = label(id: 'nVV4126', text: 'Terminsgebühr Nr. 4126') 
                                }
                                td {
                                    spnVV4126 = spinner(id: 'nspnVV4126', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 99, 
                                            value:1,
                                            stepSize:1),stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                   txtVV4126 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV4128 = checkBox(id: 'bVV4128',text: '', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    label(id: 'nVV4128', text: 'Zuschlag (5-8h) Nr. 4128') 
                                }
                                td {
                                    spnVV4128 = spinner(id: 'nspnVV4128', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 99, 
                                            value:1,
                                            stepSize:1),stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                   txtVV4128 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkVV4129 = checkBox(id: 'bVV4129',text: '', selected: false, stateChanged: {
                                        calculate()
                                    })
                                }
                                td {
                                    label(id: 'nVV4129', text: 'Zuschlag (ab 8h) Nr. 4129') 
                                }
                                td {
                                    spnVV4129 = spinner(id: 'nspnVV4129', 
                                        model:spinnerNumberModel(minimum:1, 
                                            maximum: 99, 
                                            value:1,
                                            stepSize:1),stateChanged: {
                                                calculate()
                                            })
                                }
                                td (align: 'right') {
                                   txtVV4129 = label(text: '0,00')
                                }
                                td (align: 'right') {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                   chkVV7002Berufung =  checkBox(text: '', selected: false, stateChanged: {
                                                calculate()
                                            })
                                }
                                td {
                                    label(text: 'Auslagen Nr. 7002')
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
                                    chkTagegeld = checkBox(id: 'bVVTagegeld',text: 'Tagegeld Nr. 7005', selected: false, stateChanged: {
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
                                   chkAuslagenmM =  checkBox(id:'bAuslagenmM', text: 'steuerpflichtige Auslagen (netto)', selected: false, stateChanged: {
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
                                    label(text: 'zwischen Summe')
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
                                    chkmwst = checkBox(text: 'Mehrwertsteuer VV7008', selected: true, stateChanged: {
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
                                   chkAuslagenoM =  checkBox(id:'bAuslagenoM', text: 'steuerfreie Auslagen', selected: false, stateChanged: {
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
                                    label(text: 'Summe')
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
                                    chkquote = checkBox(text: 'Quote (Bsp: 0,5)', selected: false, stateChanged: {
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
                                    label(text: 'darin enthaltene Mehrwertsteuer')
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
                                    label(text: 'Summe')
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
    float change=1.0f
    float urahmen=0.0f
    float orahmen=0.0f
    String VVVerhandG='4106, 4107'

    
    if (chkHaft.isSelected()) {
        lblVV4100.text = 'Grundgebühr Nr. 4101'
        lblVV4102.text = 'Terminsgebühr Nr. 4103'
        lblVV4104.text = 'Verfahrensgebühr Nr. 4105'
        switch (cbGericht){
            case {cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Strafkammer'}:
                lblVV4106.text='Verfahrensgebühr Nr. 4113';
                lblVV4108.text='Terminsgebühr Nr. 4115';
                lblVV4110.text='Zuschlag (5-8h) Nr. 4116:';
                lblVV4111.text='Zuschlag (ab 8h) Nr. 4117';
            break
            case {cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Schwurgericht / OLG'}:
                lblVV4106.text='Verfahrensgebühr Nr. 4119';
                lblVV4108.text='Terminsgebühr Nr. 4121';
                lblVV4110.text='Zuschlag (5-8h) Nr. 4122:';
                lblVV4111.text='Zuschlag (ab 8h) Nr. 4123'; 
            break
            default:
                lblVV4106.text='Verfahrensgebühr Nr. 4107';
                lblVV4108.text='Terminsgebühr Nr. 4109';
                lblVV4110.text='Zuschlag (5-8h) Nr. 4110:';
                lblVV4111.text='Zuschlag (ab 8h) Nr. 4111'; 
        }
        lblVV4124.text = 'Verfahrensgebühr Nr. 4125' 
        lblVV4126.text = 'Terminsgebühr Nr. 4127';

    } else {
        lblVV4100.text = 'Grundgebühr Nr. 4100'
        lblVV4102.text = 'Terminsgebühr Nr. 4102'
        lblVV4104.text = 'Verfahrensgebühr Nr. 4104'
        switch (cbGericht){
            case {cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Strafkammer'}:
                lblVV4106.text='Verfahrensgebühr Nr. 4112';
                lblVV4108.text='Terminsgebühr Nr. 4114';
                lblVV4110.text='Zuschlag (5-8h) Nr. 4116:';
                lblVV4111.text='Zuschlag (ab 8h) Nr. 4117';
            break
            case {cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Schwurgericht / OLG'}:
                lblVV4106.text='Verfahrensgebühr Nr. 4118';
                lblVV4108.text='Terminsgebühr Nr. 4120';
                lblVV4110.text='Zuschlag (5-8h) Nr. 4122:';
                lblVV4111.text='Zuschlag (ab 8h) Nr. 4123';
            break
            default:
                lblVV4106.text='Verfahrensgebühr Nr. 4106';
                lblVV4108.text='Terminsgebühr Nr. 4108';
                lblVV4110.text='Zuschlag (5-8h) Nr. 4110';
                lblVV4111.text='Zuschlag (ab 8h) Nr. 4111'; 
        }
        lblVV4124.text = 'Verfahrensgebühr Nr. 4124';
        lblVV4126.text = 'Terminsgebühr Nr. 4126';
    }
  
    if(chkVV4100.isSelected()) {
        if(chkHaft.isSelected()) {
            txtVV4100.text = df.format(192f)
        } else {
            txtVV4100.text = df.format(160f)
        }
    } else {
        txtVV4100.text = df.format(0f)
    }
        
    if(chkVV4102.isSelected()) {
        if(chkHaft.isSelected()) {
            txtVV4102.text = df.format(166f)
        } else {
            txtVV4102.text = df.format(136f)
        }
    } else {
        txtVV4102.text = df.format(0f)
    }

    if(chkVV4104.isSelected()) {
        if(chkHaft.isSelected()) {
            txtVV4104.text = df.format(161f)
        } else {
            txtVV4104.text = df.format(132f)
        }
    } else {
        txtVV4104.text = df.format(0f)
    }

    if(chkvorVV7002.isSelected()) {
        gebuehr=(df.parse(txtVV4104.text)) * 0.2f;
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
    if(chkVV4106.isSelected()) {
        if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Strafkammer') {
            if(chkHaft.isSelected()) {
            txtVV4106.text = df.format(180f)
            } else {
            txtVV4106.text = df.format(148f)
            }
        } else if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Schwurgericht / OLG') {
            if(chkHaft.isSelected()) {
            txtVV4106.text = df.format(385f)
            } else {
            txtVV4106.text = df.format(316f)
            }
        } else {
            if(chkHaft.isSelected()) {
            txtVV4106.text = df.format(161f)
            } else {
            txtVV4106.text = df.format(132f)
            }
        }
    } else {
        txtVV4106.text = df.format(0f)
        }

    if(chkVV4108.isSelected()) {
        if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Strafkammer') {
            if(chkHaft.isSelected()) {
            gebuehr = 312f;
            } else {
            gebuehr = 256f;
            }
        } else if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Schwurgericht / OLG') {
            if(chkHaft.isSelected()) {
            gebuehr = 517f;
            } else {
            gebuehr = 424f;
            }
        } else {
            if(chkHaft.isSelected()) {
            gebuehr = 268f;
            } else {
            gebuehr = 220f;
            }
        }
        gebuehr = gebuehr * spnVV4108.value.toFloat();
        txtVV4108.text = df.format(gebuehr)
    } else {
        txtVV4108.text = df.format(0f)
        }

    if(chkVV4110.isSelected()) {
        if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Strafkammer') {
            gebuehr = 128f;
        } else if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Schwurgericht / OLG') {
            gebuehr = 212f;
        } else {
            gebuehr = 110f;
            }
        gebuehr = gebuehr * spnVV4110.value.toFloat();
        txtVV4110.text = df.format(gebuehr) 
    } else {
        txtVV4110.text = df.format(0f)
    }

    if(chkVV4111.isSelected()) {
        if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Strafkammer') {
            gebuehr = 256f;
        } else if (cbGericht.getItemAt(cbGericht.getSelectedIndex())=='Schwurgericht / OLG') {
            gebuehr = 424f;
        } else {
            gebuehr = 220f;
            }
        gebuehr = gebuehr * spnVV4111.value.toFloat();
        txtVV4111.text = df.format(gebuehr) 
    } else {
        txtVV4111.text = df.format(0f)
    }

    if(chkVV7002.isSelected()) {
        gebuehr=(
        df.parse(txtVV4106.text)
        +df.parse(txtVV4108.text)
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
    
    if(chkVV4124.isSelected()) {
        if(chkHaft.isSelected()) {
            txtVV4124.text = df.format(161f)
        } else {
            txtVV4124.text = df.format(132f)
        }
    } else {
        txtVV4124.text = df.format(0f)
    }

    if(chkVV4126.isSelected()) {
        if(chkHaft.isSelected()) {
            gebuehr = 161f
        } else {
            gebuehr = 132f
        }
        gebuehr = gebuehr * spnVV4126.value.toFloat();
        txtVV4126.text = df.format(gebuehr) 
    } else {
        txtVV4126.text = df.format(0f)
    }

    if(chkVV4128.isSelected()) {
        gebuehr = 128f * spnVV4128.value.toFloat();
        txtVV4128.text = df.format(gebuehr) 
    } else {
        txtVV4128.text = df.format(0f)
    }

    if(chkVV4129.isSelected()) {
        gebuehr = 256f * spnVV4129.value.toFloat();
        txtVV4129.text = df.format(gebuehr) 
    } else {
        txtVV4129.text = df.format(0f)
    }

    if(chkVV7002Berufung.isSelected()) {
        gebuehr=(
        df.parse(txtVV4124.text)
        +df.parse(txtVV4126.text)
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
        df.parse(txtVV4100.text)
        +df.parse(txtVV4102.text)
        +df.parse(txtVV4104.text)
        +df.parse(lblvorVV7002.text)        
        +df.parse(txtVV4106.text)
        +df.parse(txtVV4108.text)
        +df.parse(txtVV4110.text)
        +df.parse(txtVV4111.text)
        +df.parse(lblVV7002.text)
        +df.parse(txtVV4124.text)
        +df.parse(txtVV4126.text)
        +df.parse(txtVV4128.text)
        +df.parse(txtVV4129.text)
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
        
    if(chkVV4100.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4100.text).append(" VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV4100.text).append(" €</td>");
        sbf.append("</tr>");
    }

    if(chkVV4102.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4102.text).append(" VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV4102.text).append(" €</td>");
        sbf.append("</tr>");
    } 

    if(chkVV4104.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4104.text).append(" VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV4104.text).append(" €</td>");
        sbf.append("</tr>");
    } 

     if(chkvorVV7002.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen im Vorverfahren Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblvorVV7002.text).append(" €</td>");
        sbf.append("</tr>");
    } 

    if(chkVV4106.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4106.text).append(" VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV4106.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV4108.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4108.text).append(" VV RVG für ").append(spnVV4108.value.toString()).append(" Verhandlungstage</td>");
        sbf.append("<td align=\"right\">").append(txtVV4108.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV4110.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4110.text).append(" VV RVG für ").append(spnVV4110.value.toString()).append(" Verhandlungstage</td>");
        sbf.append("<td align=\"right\">").append(txtVV4110.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV4111.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4111.text).append(" VV RVG für ").append(spnVV4111.value.toString()).append(" Verhandlungstage</td>");
        sbf.append("<td align=\"right\">").append(txtVV4111.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV7002.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Auslagen Nr. 7002 VV RVG</td>");
        sbf.append("<td align=\"right\">").append(lblVV7002.text).append(" €</td>");
        sbf.append("</tr>");
     }
    if(chkVV4124.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4124.text).append(" VV RVG</td>");
        sbf.append("<td align=\"right\">").append(txtVV4124.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV4126.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">").append(lblVV4126.text).append(" VV RVG für ").append(spnVV4126.value.toString()).append(" Verhandlungstage</td>");
        sbf.append("<td align=\"right\">").append(txtVV4126.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV4128.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Zuschlag (5-8h) Nr. 4128 VV RVG für ").append(spnVV4128.value.toString()).append(" Verhandlungstage</td>");
        sbf.append("<td align=\"right\">").append(txtVV4128.text).append(" €</td>");
        sbf.append("</tr>");
    }
    if(chkVV4129.selected) {
        sbf.append("<tr>")
        sbf.append("<td align=\"left\">Zuschlag (ab 8h) Nr. 4128 VV RVG für ").append(spnVV4129.value.toString()).append(" Verhandlungstage</td>");
        sbf.append("<td align=\"right\">").append(txtVV4129.text).append(" €</td>");
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
    if(chkVV4100.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4100.text + " VV RVG");
        row.add(txtVV4100.text);
        ct.addRow(row);
    }
    if(chkVV4102.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4102.text + " VV RVG");
        row.add(txtVV4102.text);
        ct.addRow(row);
    }
    if(chkVV4104.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4104.text + " VV RVG");
        row.add(txtVV4104.text);
        ct.addRow(row);
    }
    if(chkvorVV7002.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen im Vorverfahren Nr. 7002 VV RVG");
        row.add(lblvorVV7002.text);
        ct.addRow(row);
    }
    if(chkVV4106.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4106.text + " VV RVG");
        row.add(txtVV4106.text);
        ct.addRow(row);
    }
    if(chkVV4108.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4108.text + " VV RVG");
        row.add(txtVV4108.text);
        ct.addRow(row);
    }
    if(chkVV4110.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4110.text + " VV RVG");
        row.add(txtVV4110.text);
        ct.addRow(row);
    }
    if(chkVV4111.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4111.text + " VV RVG");
        row.add(txtVV4111.text);
        ct.addRow(row);
    }
    if(chkVV7002.selected) {
        row=new ArrayList<String>();
        row.add("Auslagen Nr. 7002 VV RVG");
        row.add(lblVV7002.text);
        ct.addRow(row);
    }
    if(chkVV4124.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4124.text + " VV RVG");
        row.add(txtVV4124.text);
        ct.addRow(row);
    }
    if(chkVV4126.selected) {
        row=new ArrayList<String>();
        row.add(lblVV4126.text + " VV RVG");
        row.add(txtVV4126.text);
        ct.addRow(row);
    }
    if(chkVV4128.selected) {
        row=new ArrayList<String>();
        row.add("Zuschlag (5-8h) Nr. 4128 VV RVG");
        row.add(txtVV4128.text);
        ct.addRow(row);
    }
    if(chkVV4129.selected) {
        row=new ArrayList<String>();
        row.add("Zuschlag (ab 8h) Nr. 4128 VV RVG");
        row.add(txtVV4129.text);
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