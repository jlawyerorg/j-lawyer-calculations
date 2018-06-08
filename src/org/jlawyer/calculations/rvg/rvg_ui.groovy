

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat

@Bindable
class Address { 
    String street, number, city
    String toString() { "address[street=$street,number=$number,city=$city]" }
}

count = 0
df = new DecimalFormat("0.00 EUR")
new SwingBuilder().edt {
    SCRIPTPANEL=panel(size: [300, 300]) {
        //borderLayout()
        tableLayout {
            tr {
                td {
                    panel(border: titledBorder(title: 'Basisdaten')) {
                        tableLayout {
                            tr {
                                td {
                                    label(text: 'Streitwert:')
                                }
                                td {
                                    textField(id: 'nStreitwert', columns: 10)
                                }
                            }
                
                            tr {
                                td {
                        
                                }
                                td {
                        
                                }
                            }
                            tr {
                                td {
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
                                td {
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
                        tableLayout {
                            tr {
                                td {
                                    checkBox(text: 'Geschäftsgebühr VV2300:', selected: true)
                                }
                                td {
                                    spinner(
                                        model:spinnerNumberModel(minimum:0.0, 
                                            maximum: 10.0, 
                                            value:1.3,
                                            stepSize:0.1))
                                }
                                td (align: 'right') {
                                    label(id: 'nGeschaeftsGebuehr', text: '149,50 EUR')
                                }
                            }
                            tr {
                                td {
                                    checkBox(text: 'Verfahrenssgebühr VV3100:', selected: true)
                                }
                                td {
                                    spinner(
                                        model:spinnerNumberModel(minimum:0.0, 
                                            maximum: 10.0, 
                                            value:1.3,
                                            stepSize:0.1))
                                }
                                td (align: 'right') {
                                    label(text: '149,50 EUR')
                                }
                            }
                
                           tr {
                                td {
                                    checkBox(text: 'abzüglich anrechenbarer Teil:', selected: true)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: '74,75 EUR', foreground: java.awt.Color.RED)
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
                                    label(text: '138,00 EUR')
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
                            }
                            tr {
                                td {
                                    checkBox(text: 'Auslagen VV7002ff.:', selected: false)
                                }
                                td {
                                    label(text: ' ')
                                }
                                td (align: 'right') {
                                    label(text: '20,00 EUR')
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
                            }
                            tr {
                                td {
                                    checkBox(text: 'Mehrwertsteuer VV7008:', selected: true)
                                }
                                td {
                                    label(text: '19%')
                                }
                                td (align: 'right') {
                                    label(text: '72,63 EUR')
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
            tr {
                td (align: 'right') {
                    button(text: 'Berechnen', actionPerformed: {
                                        nGeschaeftsGebuehr.text = df.format(calculate(nStreitwert.text.toFloat()))
                                        
                                    })
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

def float calculate(float streitWert) { 
   println( streitWert * 1.5d)
   //nGeschaeftsGebuehr.text = df.format(nStreitwert.text.toInteger() * 1.5d)
   return 14f
}