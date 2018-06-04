

import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL

count = 0
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
                                    label(text: 'Streitwert:')
                                }
                                td {
                                    //textField(id: 'nStreitwert', columns: 10)
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
                                    spinner(
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
    
            
        }
  
    }

}