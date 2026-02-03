import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import java.text.DecimalFormat
import java.text.NumberFormat
import java.math.RoundingMode
import java.util.ArrayList
import java.util.Locale
import javax.swing.JSpinner
import javax.swing.JRadioButton
import org.jlawyer.plugins.calculation.StyledCalculationTable
import org.jlawyer.plugins.calculation.CalculationTable
import org.jlawyer.plugins.calculation.Cell
import com.jdimension.jlawyer.client.settings.ServerSettings
import com.jdimension.jlawyer.persistence.InvoicePosition

@Bindable
class GNotKgTaxModel {
    @Bindable BigDecimal ustFactor=TaxPropertiesUtils.getUstFactor();
    @Bindable BigDecimal ustPercentage=TaxPropertiesUtils.getUstPercentage();
    @Bindable String ustPercentageString=TaxPropertiesUtils.getUstPercentageString();
}

df = NumberFormat.getInstance(Locale.GERMANY).getNumberInstance();
df.setMaximumFractionDigits(2);
df.setMinimumFractionDigits(2);
df.setParseBigDecimal(true);

betragFormat = NumberFormat.getInstance(Locale.GERMANY).getNumberInstance();
betragFormat.setMaximumFractionDigits(2);
betragFormat.setMinimumFractionDigits(2);
faktorFormat = new DecimalFormat("0.0");

taxModel=new GNotKgTaxModel();

def void updateTax() {
    if(radioUst19.isSelected()) {
        taxModel.ustFactor=0.19g
        taxModel.ustPercentage=19g
        taxModel.ustPercentageString='19'
    }
    if(radioUst16.isSelected()) {
        taxModel.ustFactor=0.16g
        taxModel.ustPercentage=16g
        taxModel.ustPercentageString='16'
    }
}

new SwingBuilder().edt {
    SCRIPTPANEL=panel(size: [300, 300]) {
        tableLayout {
            tr {
                td (align: 'right') {
                    panel {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    button(text: 'Berechnen', actionPerformed: {
                                            calculate()
                                        })
                                }
                                td {
                                    cmdCopy=button(text: 'Kopieren', enabled: false, toolTipText: 'In Zwischenablage kopieren', actionPerformed: {
                                            if(binding.callback != null)
                                                binding.callback.processResultToClipboardAsText(copyToClipboard())
                                        })
                                }
                                td {
                                    cmdDocument=button(text: 'Dokument', enabled: false, toolTipText: 'In Dokument uebernehmen', actionPerformed: {
                                            if(binding.callback != null)
                                                binding.callback.processResultToDocument(copyToDocument(), SCRIPTPANEL)
                                        })
                                }
                                td {
                                    cmdInvoice=button(text: 'als Rechnung', enabled: false, toolTipText: 'Ergebnis in Rechnung uebernehmen', actionPerformed: {
                                            calculate();
                                            if(binding.callback != null)
                                                binding.callback.processResultToInvoice(copyToInvoice());
                                        })
                                }
                            }
                        }
                    }
                }
            }

            // Basisdaten
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Basisdaten')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td (colfill:true) {
                                    label(text: 'Kaufpreis:')
                                }
                                td {
                                    txtKaufpreis=formattedTextField(id: 'nKaufpreis', format: betragFormat, text: betragFormat.format(binding.claimvalue), columns: 10, actionPerformed: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: 'Grundschuldbetrag:')
                                }
                                td {
                                    txtGrundschuld=formattedTextField(id: 'nGrundschuld', format: betragFormat, text: '0,00', columns: 10, actionPerformed: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    label(text: 'Umsatzsteuersatz')
                                }
                                td {
                                    panel {
                                        btnGrpUst = buttonGroup(id:'GrpUst')
                                        radioUst16 = radioButton(text: '16 %', buttonGroup: btnGrpUst, selected: taxModel.ustPercentage==16, stateChanged: {
                                            updateTax()
                                            calculate()
                                        })
                                        radioUst19 = radioButton(text: '19 %', buttonGroup: btnGrpUst, selected: taxModel.ustPercentage==19, stateChanged: {
                                            updateTax()
                                            calculate()
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Notargebuehren - Kaufvertrag
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Notargebühren - Kaufvertrag')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkKV21100 = checkBox(text: 'KV 21100 Beurkundung Kaufvertrag:', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    spnKV21100 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f,
                                            maximum: 10.0f,
                                            value:2.0f,
                                            stepSize:0.1f), stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'auf Kaufpreis')
                                }
                                td (align: 'right') {
                                    lblKV21100 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkKV22110 = checkBox(text: 'KV 22110 Vollzugsgebühr:', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    spnKV22110 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f,
                                            maximum: 10.0f,
                                            value:0.5f,
                                            stepSize:0.1f), stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'auf Kaufpreis')
                                }
                                td (align: 'right') {
                                    lblKV22110 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkKV22200 = checkBox(text: 'KV 22200 Betreuungsgebühr:', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    spnKV22200 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f,
                                            maximum: 10.0f,
                                            value:0.5f,
                                            stepSize:0.1f), stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'auf Kaufpreis')
                                }
                                td (align: 'right') {
                                    lblKV22200 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                        }
                    }
                }
            }

            // Notargebuehren - Grundschuld
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Notargebühren - Grundschuld')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkKV21200 = checkBox(text: 'KV 21200 Beurkundung Grundschuld:', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    spnKV21200 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f,
                                            maximum: 10.0f,
                                            value:1.0f,
                                            stepSize:0.1f), stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'auf Grundschuld')
                                }
                                td (align: 'right') {
                                    lblKV21200 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkKV22111 = checkBox(text: 'KV 22111 Vollzugsgebühr Grundschuld:', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    spnKV22111 = spinner(
                                        model:spinnerNumberModel(minimum:0.0f,
                                            maximum: 10.0f,
                                            value:0.3f,
                                            stepSize:0.1f), stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'auf Grundschuld')
                                }
                                td (align: 'right') {
                                    lblKV22111 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                        }
                    }
                }
            }

            // Auslagen
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Auslagen')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkDokPauschale = checkBox(text: 'Dokumentenpauschale:', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    txtDokPauschale=formattedTextField(format: betragFormat, text: '100,00', columns: 8, actionPerformed: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkPorto = checkBox(text: 'Porto/Telekommunikation:', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td {
                                    txtPorto=formattedTextField(format: betragFormat, text: '20,00', columns: 8, actionPerformed: {
                                            calculate()
                                        })
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                        }
                    }
                }
            }

            // Grundbuchkosten
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Grundbuchkosten')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    chkKV14110 = checkBox(text: 'KV 14110 Eigentumsumschreibung (1,0 auf Kaufpreis):', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td (align: 'right') {
                                    lblKV14110 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkKV14150 = checkBox(text: 'KV 14150 Auflassungsvormerkung Eintragung (0,5 auf Kaufpreis):', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td (align: 'right') {
                                    lblKV14150 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkKV14152 = checkBox(text: 'KV 14152 Auflassungsvormerkung Löschung (pauschal):', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td (align: 'right') {
                                    lblKV14152 = label(text: '25,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td {
                                    chkKV14121 = checkBox(text: 'KV 14121 Grundschuld Eintragung (1,0 auf Grundschuld):', selected: true, stateChanged: {
                                            calculate()
                                        })
                                }
                                td (align: 'right') {
                                    lblKV14121 = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                        }
                    }
                }
            }

            // Ergebnis
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Ergebnis')) {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td (colfill:true) {
                                    label(text: 'Zwischensumme Notargebühren (netto):')
                                }
                                td (align: 'right') {
                                    lblZwischensumme = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    lblMwstText = label(text: 'MwSt 19 %:')
                                }
                                td (align: 'right') {
                                    lblMwst = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: '<html><b>Notargebühren (brutto):</b></html>')
                                }
                                td (align: 'right') {
                                    lblNotarBrutto = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: ' ')
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: '<html><b>Grundbuchkosten:</b></html>')
                                }
                                td (align: 'right') {
                                    lblGrundbuchGesamt = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: ' ')
                                }
                            }
                            tr {
                                td (colfill:true) {
                                    label(text: '<html><b>Gesamtkosten:</b></html>')
                                }
                                td (align: 'right') {
                                    lblGesamt = label(text: '0,00')
                                }
                                td {
                                    label(text: 'EUR')
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

def BigDecimal calculate() {
    GNotKgTablesRangeList rl = new GNotKgTablesRangeList()

    float kaufpreis = betragFormat.parse(txtKaufpreis.text).floatValue()
    float grundschuld = betragFormat.parse(txtGrundschuld.text).floatValue()

    BigDecimal notarSumme = 0g

    // Notargebuehren Kaufvertrag
    if(chkKV21100.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(kaufpreis, spnKV21100.value.toFloat()).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV21100.text = df.format(g)
        notarSumme = notarSumme + g
    } else {
        lblKV21100.text = df.format(0f)
    }

    if(chkKV22110.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(kaufpreis, spnKV22110.value.toFloat()).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV22110.text = df.format(g)
        notarSumme = notarSumme + g
    } else {
        lblKV22110.text = df.format(0f)
    }

    if(chkKV22200.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(kaufpreis, spnKV22200.value.toFloat()).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV22200.text = df.format(g)
        notarSumme = notarSumme + g
    } else {
        lblKV22200.text = df.format(0f)
    }

    // Notargebuehren Grundschuld
    if(chkKV21200.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(grundschuld, spnKV21200.value.toFloat()).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV21200.text = df.format(g)
        notarSumme = notarSumme + g
    } else {
        lblKV21200.text = df.format(0f)
    }

    if(chkKV22111.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(grundschuld, spnKV22111.value.toFloat()).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV22111.text = df.format(g)
        notarSumme = notarSumme + g
    } else {
        lblKV22111.text = df.format(0f)
    }

    // Auslagen
    if(chkDokPauschale.isSelected()) {
        BigDecimal g = betragFormat.parse(txtDokPauschale.text)
        notarSumme = notarSumme + g
    }

    if(chkPorto.isSelected()) {
        BigDecimal g = betragFormat.parse(txtPorto.text)
        notarSumme = notarSumme + g
    }

    // Zwischensumme Notar netto
    notarSumme = notarSumme.setScale(2, RoundingMode.HALF_UP)
    lblZwischensumme.text = df.format(notarSumme)

    // MwSt
    BigDecimal mwst = notarSumme.multiply(taxModel.ustFactor)
    mwst = mwst.setScale(2, RoundingMode.HALF_UP)
    lblMwst.text = df.format(mwst)
    lblMwstText.text = 'MwSt ' + taxModel.ustPercentageString + ' %:'

    // Notar brutto
    BigDecimal notarBrutto = notarSumme + mwst
    notarBrutto = notarBrutto.setScale(2, RoundingMode.HALF_UP)
    lblNotarBrutto.text = df.format(notarBrutto)

    // Grundbuchkosten (keine MwSt)
    BigDecimal grundbuchSumme = 0g

    if(chkKV14110.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(kaufpreis, 1.0f).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV14110.text = df.format(g)
        grundbuchSumme = grundbuchSumme + g
    } else {
        lblKV14110.text = df.format(0f)
    }

    if(chkKV14150.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(kaufpreis, 0.5f).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV14150.text = df.format(g)
        grundbuchSumme = grundbuchSumme + g
    } else {
        lblKV14150.text = df.format(0f)
    }

    if(chkKV14152.isSelected()) {
        BigDecimal g = 25.00g
        lblKV14152.text = df.format(g)
        grundbuchSumme = grundbuchSumme + g
    } else {
        lblKV14152.text = df.format(0f)
    }

    if(chkKV14121.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(grundschuld, 1.0f).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV14121.text = df.format(g)
        grundbuchSumme = grundbuchSumme + g
    } else {
        lblKV14121.text = df.format(0f)
    }

    grundbuchSumme = grundbuchSumme.setScale(2, RoundingMode.HALF_UP)
    lblGrundbuchGesamt.text = df.format(grundbuchSumme)

    // Gesamtkosten
    BigDecimal gesamt = notarBrutto + grundbuchSumme
    gesamt = gesamt.setScale(2, RoundingMode.HALF_UP)
    lblGesamt.text = df.format(gesamt)

    cmdCopy.enabled=true
    cmdDocument.enabled=true
    cmdInvoice.enabled=true
    return gesamt
}

def String copyToClipboard() {
    StyledCalculationTable st=copyToDocument();
    return st.toHtml();
}

def StyledCalculationTable copyToDocument() {
    int rowcount=0

    StyledCalculationTable ct=new StyledCalculationTable();
    ct.addHeaders("", "Position", "Betrag");
    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.table.emptyRows", true)) {
        ct.addRow("", "", "");
    }

    // Notargebuehren Kaufvertrag
    if(chkKV21100.selected) {
        ct.addRow(faktorFormat.format(spnKV21100.value.toFloat()), "Beurkundung Kaufvertrag KV 21100 GNotKG - " + txtKaufpreis.text + " €", lblKV21100.text + " €");
        rowcount=rowcount+1
    }
    if(chkKV22110.selected) {
        ct.addRow(faktorFormat.format(spnKV22110.value.toFloat()), "Vollzugsgebühr KV 22110 GNotKG - " + txtKaufpreis.text + " €", lblKV22110.text + " €");
        rowcount=rowcount+1
    }
    if(chkKV22200.selected) {
        ct.addRow(faktorFormat.format(spnKV22200.value.toFloat()), "Betreuungsgebühr KV 22200 GNotKG - " + txtKaufpreis.text + " €", lblKV22200.text + " €");
        rowcount=rowcount+1
    }

    // Notargebuehren Grundschuld
    if(chkKV21200.selected) {
        ct.addRow(faktorFormat.format(spnKV21200.value.toFloat()), "Beurkundung Grundschuld KV 21200 GNotKG - " + txtGrundschuld.text + " €", lblKV21200.text + " €");
        rowcount=rowcount+1
    }
    if(chkKV22111.selected) {
        ct.addRow(faktorFormat.format(spnKV22111.value.toFloat()), "Vollzugsgebühr Grundschuld KV 22111 GNotKG - " + txtGrundschuld.text + " €", lblKV22111.text + " €");
        rowcount=rowcount+1
    }

    // Auslagen
    if(chkDokPauschale.selected) {
        ct.addRow("", "Dokumentenpauschale", txtDokPauschale.text + " €");
        rowcount=rowcount+1
    }
    if(chkPorto.selected) {
        ct.addRow("", "Porto/Telekommunikation", txtPorto.text + " €");
        rowcount=rowcount+1
    }

    // Zwischensumme
    if((rowcount>1)) {
        if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.table.emptyRows", true)) {
            ct.addRow("", "", "");
        }
        ct.addRow("", "Zwischensumme Notargebühren (netto)", lblZwischensumme.text + " €");
    }

    // MwSt
    ct.addRow(taxModel.ustPercentageString + " %", "Umsatzsteuer", lblMwst.text + " €");

    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.table.emptyRows", true)) {
        ct.addRow("", "", "");
    }
    int notarBruttoRow=ct.addRow("", "Notargebühren (brutto)", lblNotarBrutto.text + " €");

    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.table.emptyRows", true)) {
        ct.addRow("", "", "");
    }

    // Grundbuchkosten
    int gbStart=0
    if(chkKV14110.selected) {
        ct.addRow("1,0", "Eigentumsumschreibung KV 14110 GNotKG - " + txtKaufpreis.text + " €", lblKV14110.text + " €");
        gbStart=gbStart+1
    }
    if(chkKV14150.selected) {
        ct.addRow("0,5", "Auflassungsvormerkung Eintragung KV 14150 GNotKG - " + txtKaufpreis.text + " €", lblKV14150.text + " €");
        gbStart=gbStart+1
    }
    if(chkKV14152.selected) {
        ct.addRow("", "Auflassungsvormerkung Löschung KV 14152 GNotKG", lblKV14152.text + " €");
        gbStart=gbStart+1
    }
    if(chkKV14121.selected) {
        ct.addRow("1,0", "Grundschuld Eintragung KV 14121 GNotKG - " + txtGrundschuld.text + " €", lblKV14121.text + " €");
        gbStart=gbStart+1
    }

    if(gbStart>0) {
        if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.table.emptyRows", true)) {
            ct.addRow("", "", "");
        }
        ct.addRow("", "Grundbuchkosten", lblGrundbuchGesamt.text + " €");
    }

    // Gesamtkosten
    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.table.emptyRows", true)) {
        ct.addRow("", "", "");
    }
    int footerRow=ct.addRow("", "Gesamtkosten", lblGesamt.text + " €");

    // Styling - HeaderRow
    ct.setRowForeGround(0, new TablePropertiesUtils().getHeaderForeColor());
    ct.setRowBackGround(0, new TablePropertiesUtils().getHeaderBackColor());
    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.header.Bold", true)) {
        ct.setRowBold(0, true);
    } else {
        ct.setRowBold(0, false);
    }

    // Zwischensummen
    ctRows=ct.getRowCount()
    if(ctRows>0) {
        for(int i=0;i<ctRows;i++) {
            String cellVal = ct.getValueAt(i, 1)
            if (cellVal == 'Zwischensumme Notargebühren (netto)' || cellVal == 'Notargebühren (brutto)' || cellVal == 'Grundbuchkosten') {
                if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.zwischensumme.Bold", true)) {
                    ct.setRowBold(i, true);
                }
                if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.zwischensumme.Italic", true)) {
                    ct.getCellAt(i, 1).setItalic(true);
                    ct.getCellAt(i, 2).setItalic(true);
                }
                ct.setRowForeGround(i, new TablePropertiesUtils().getZwischensummeForeColor());
                ct.setRowBackGround(i, new TablePropertiesUtils().getZwischensummeBackColor());
            }
        }
    }

    // FooterRow
    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.footerRow.Bold", true)) {
        ct.setRowBold(footerRow, true);
    } else {
        ct.setRowBold(footerRow, false);
    }
    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.footerRow.Underline", true)) {
        ct.getCellAt(footerRow, 2).setUnderline(true);
    }
    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.footerRow.Italic", true)) {
        ct.getCellAt(footerRow, 1).setItalic(true);
        ct.getCellAt(footerRow, 2).setItalic(true);
    }
    ct.setRowForeGround(footerRow, new TablePropertiesUtils().getFooterRowForeColor());
    ct.setRowBackGround(footerRow, new TablePropertiesUtils().getFooterRowBackColor());

    // TableLayout
    ct.setColumnAlignment(2, Cell.ALIGNMENT_RIGHT);
    ct.getCellAt(0,1).setAlignment(Cell.ALIGNMENT_LEFT);
    ct.setRowFontSize(0, 12);
    ct.setColumnWidth(0, 25);
    ct.setColumnWidth(1, 120);
    ct.setColumnWidth(2, 35);
    ct.setFontFamily(ServerSettings.getInstance().getSetting("plugins.global.tableproperties.table.fontfamily", "Arial"));
    if (ServerSettings.getInstance().getSettingAsBoolean("plugins.global.tableproperties.table.lines", true)) {
        ct.setLineBorder(true);
    } else {
        ct.setLineBorder(false);
    }
    ct.setBorderColor(new TablePropertiesUtils().getTableLineColor());
    ct.setFontSize(ServerSettings.getInstance().getSettingAsInt("plugins.global.tableproperties.table.fontsize", 12));

    return ct;
}

def ArrayList copyToInvoice() {

    BigDecimal taxRate=0g;
    if(radioUst19.selected) {
        taxRate=19g;
    }
    if(radioUst16.selected) {
        taxRate=16g;
    }

    ArrayList positions=new ArrayList();

    // Notargebuehren Kaufvertrag
    if(chkKV21100.selected) {
        positions.add(InvoiceUtils.invoicePosition("Beurkundung Kaufvertrag KV 21100 GNotKG", "Kaufpreis " + txtKaufpreis.text + " €, Faktor " + faktorFormat.format(spnKV21100.value.toFloat()), taxRate, df.parse(lblKV21100.text).floatValue()));
    }
    if(chkKV22110.selected) {
        positions.add(InvoiceUtils.invoicePosition("Vollzugsgebühr KV 22110 GNotKG", "Kaufpreis " + txtKaufpreis.text + " €, Faktor " + faktorFormat.format(spnKV22110.value.toFloat()), taxRate, df.parse(lblKV22110.text).floatValue()));
    }
    if(chkKV22200.selected) {
        positions.add(InvoiceUtils.invoicePosition("Betreuungsgebühr KV 22200 GNotKG", "Kaufpreis " + txtKaufpreis.text + " €, Faktor " + faktorFormat.format(spnKV22200.value.toFloat()), taxRate, df.parse(lblKV22200.text).floatValue()));
    }

    // Notargebuehren Grundschuld
    if(chkKV21200.selected) {
        positions.add(InvoiceUtils.invoicePosition("Beurkundung Grundschuld KV 21200 GNotKG", "Grundschuld " + txtGrundschuld.text + " €, Faktor " + faktorFormat.format(spnKV21200.value.toFloat()), taxRate, df.parse(lblKV21200.text).floatValue()));
    }
    if(chkKV22111.selected) {
        positions.add(InvoiceUtils.invoicePosition("Vollzugsgebühr Grundschuld KV 22111 GNotKG", "Grundschuld " + txtGrundschuld.text + " €, Faktor " + faktorFormat.format(spnKV22111.value.toFloat()), taxRate, df.parse(lblKV22111.text).floatValue()));
    }

    // Auslagen
    if(chkDokPauschale.selected) {
        positions.add(InvoiceUtils.invoicePosition("Dokumentenpauschale", taxRate, betragFormat.parse(txtDokPauschale.text).floatValue()));
    }
    if(chkPorto.selected) {
        positions.add(InvoiceUtils.invoicePosition("Porto/Telekommunikation", taxRate, betragFormat.parse(txtPorto.text).floatValue()));
    }

    // Grundbuchkosten (keine MwSt)
    if(chkKV14110.selected) {
        positions.add(InvoiceUtils.invoicePosition("Eigentumsumschreibung KV 14110 GNotKG", "Kaufpreis " + txtKaufpreis.text + " €", 0f, df.parse(lblKV14110.text).floatValue()));
    }
    if(chkKV14150.selected) {
        positions.add(InvoiceUtils.invoicePosition("Auflassungsvormerkung Eintragung KV 14150 GNotKG", "Kaufpreis " + txtKaufpreis.text + " €", 0f, df.parse(lblKV14150.text).floatValue()));
    }
    if(chkKV14152.selected) {
        positions.add(InvoiceUtils.invoicePosition("Auflassungsvormerkung Löschung KV 14152 GNotKG", 0f, df.parse(lblKV14152.text).floatValue()));
    }
    if(chkKV14121.selected) {
        positions.add(InvoiceUtils.invoicePosition("Grundschuld Eintragung KV 14121 GNotKG", "Grundschuld " + txtGrundschuld.text + " €", 0f, df.parse(lblKV14121.text).floatValue()));
    }

    return positions;
}
