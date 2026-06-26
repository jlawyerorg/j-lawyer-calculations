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
import javax.swing.JTable
import javax.swing.JScrollPane
import javax.swing.table.AbstractTableModel
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.DefaultCellEditor
import javax.swing.JCheckBox
import javax.swing.JTextField
import javax.swing.ListSelectionModel
import java.awt.Component
import java.awt.Dimension
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

// --- CSV Tatbestände (KV 21100 – 32016) ---
final String TAT_CSV = '''\
IN_RECHNUNG;KV_NR;TATBESTAND_KURZ;GEBUEHR_TYP;SATZ_VON;SATZ_BIS;MIN_EUR;MAX_EUR
1;21100;Beurkundung Vertrag;FAKTOR;2,0;2,0;120;0
1;21101;Annahme/Verfügung zu Urkunde;FAKTOR;0,5;0,5;30;0
1;21102;Verfügung/Aufhebung Vertrag;FAKTOR;1,0;1,0;60;0
1;21200;Einseitige Erklärungen/Testament/Vollmacht;FAKTOR;1,0;1,0;60;0
1;21201;Grundbuch-/Registeranträge Widerruf;FAKTOR;0,5;0,5;30;0
0;21300;Vorzeitige Beendigung vor Entwurfsversand;FEST;0;0;20;0
0;21301;Vorzeitige Beendigung – nur Beratung;MANUELL;0;0;0;0
0;21302;Vorzeitige Beendigung – 21100 Spannenfall;FAKTOR;0,5;2,0;120;0
0;21303;Vorzeitige Beendigung – 21102/21200 Spannenfall;FAKTOR;0,3;1,0;60;0
0;21304;Vorzeitige Beendigung – 21101/21201 Spannenfall;FAKTOR;0,3;0,5;30;0
0;22110;Vollzugsgebühr allgemein;FAKTOR;0,5;0,5;0;0
0;22111;Vollzugsgebühr – bei Beurkundung < 2,0;FAKTOR;0,3;0,3;0;0
0;22112;Vollzugsgebühr – Tätigkeiten 1–3;FEST;0;0;0;50
0;22113;Vollzugsgebühr – Gesellschafterliste;FEST;0;0;0;250
0;22114;Erzeugung strukturierter Daten XML;FAKTOR;0,2;0,2;0;125
0;22115;XML-Neben-Zusatzgebühr;FAKTOR;0,1;0,1;0;125
0;22120;Vollzug ohne Beurkundung – wäre 2,0;FAKTOR;1,0;1,0;0;0
0;22121;Vollzug ohne Beurkundung – wäre < 2,0;FAKTOR;0,5;0,5;0;0
0;22122;Überprüfung Eintragungsfähigkeit;FAKTOR;0,5;0,5;0;0
0;22123;Erledigung Beanstandungen;FAKTOR;0,5;0,5;0;0
0;22124;Nur Transmission/Prüfung;FEST;0;0;20;0
0;22125;XML-Erzeugung in besonderen Fällen;FAKTOR;0,5;0,5;0;250
0;22200;Betreuungsgebühr;FAKTOR;0,5;0,5;0;0
0;22201;Treuhandgebühr;FAKTOR;0,5;0,5;0;0
0;23100;Verfahren Rückgabe Erbvertrag;FAKTOR;0,3;0,3;0;0
0;23200;Verfahren Verlosung;FAKTOR;2,0;2,0;0;0
0;23201;Vorzeitige Beendigung Verlosung;FAKTOR;0,5;0,5;0;0
0;23300;Verfahren eidesstattliche Versicherung;FAKTOR;1,0;1,0;0;0
0;23301;Vorzeitige Beendigung eidesstattl. Versicherung;FAKTOR;0,3;0,3;0;0
0;23302;Vernehmung Zeugen/Sachverständige;FAKTOR;1,0;1,0;0;0
0;23400;Wechsel-/Scheckprotest;FAKTOR;0,5;0,5;0;0
0;23401;Weiterer Protest bei Notadressen;FAKTOR;0,3;0,3;0;0
0;23500;Aufnahme Vermögensverzeichnis/Siegelung;FAKTOR;2,0;2,0;0;0
0;23501;Vorzeitige Beendigung Vermögensverz.;FAKTOR;0,5;0,5;0;0
0;23502;Urkundsperson Vermögensverzeichnis;FAKTOR;1,0;1,0;0;0
0;23503;Siegelung/Entsiegelung isoliert;FAKTOR;0,5;0,5;0;0
0;23600;Freiwillige Versteigerung – Vorbereitung;FAKTOR;0,5;0,5;0;0
0;23601;Aufnahme Schätzung;FAKTOR;0,5;0,5;0;0
0;23602;Abhaltung Versteigerungstermin;FAKTOR;1,0;1,0;0;0
0;23603;Beurkundung Zuschlag;FAKTOR;1,0;1,0;0;0
0;23700;Versteigerung bewegliche Sachen;FAKTOR;3,0;3,0;0;0
0;23701;Beendigung vor Gebotsforderung;FAKTOR;0,5;0,5;0;0
0;23800;Vollstreckbarerklärung Anwaltsvergleich;FEST;0;0;72;0
0;23801;Vollstreckbarerklärung Schiedsspruch;FAKTOR;2,0;2,0;0;0
0;23802;Beendigung 23801 vor Fertigstellung;FAKTOR;1,0;1,0;0;0
0;23803;Vollstreckbare Ausfertigung – Tatsachenprüfung;FAKTOR;0,5;0,5;0;0
0;23804;Weitere vollstreckbare Ausfertigungen;FEST;0;0;24;0
0;23805;Bestätigung 1079 ZPO / 1110 ZPO;FEST;0;0;24;0
0;23806;Vollstreckbarerklärung notarielle Urkunde international;FEST;0;0;288;0
0;23807;Beendigung 23806 vor Fertigstellung;FEST;0;0;108;0
0;23808;Bescheinigung internationale Vollstreckung;FEST;0;0;19;0
0;23900;Verfahren Teilungssache;FAKTOR;6,0;6,0;0;0
0;23901;Vorzeitige Beendigung Teilungssache;FAKTOR;1,5;1,5;0;100
0;23902;Verweisung an anderen Notar;FAKTOR;1,5;1,5;0;100
0;23903;Abschluss nach Verhandlung;FAKTOR;3,0;3,0;0;0
0;24100;Entwurf – würde 2,0 Beurkundung;FAKTOR;0,5;2,0;120;0
0;24101;Entwurf – würde 1,0 Beurkundung;FAKTOR;0,3;1,0;60;0
0;24102;Entwurf – würde 0,5 Beurkundung;FAKTOR;0,3;0,5;30;0
0;24103;Serienentwurf – Gebührenermäßigung;MANUELL;0;0;0;0
0;24200;Beratungsgebühr allgemein;FAKTOR;0,3;1,0;0;0
0;24201;Beratung – könnte 1,0 Beurkundung;FAKTOR;0,3;0,5;0;0
0;24202;Beratung – könnte < 1,0 Beurkundung;FAKTOR;0,3;0,3;0;0
0;24203;Beratung Hauptversammlung/Gesellschaftervers.;FAKTOR;0,5;2,0;0;0
0;25100;Beglaubigung Unterschrift/Handzeichen;FAKTOR;0,2;0,2;20;70
0;25101;Beglaubigung – spezielle Fälle (GBO/WEG);FEST;0;0;20;0
0;25102;Beglaubigung Dokumente;FEST;0;0;10;0
0;25103;Sicherstellung Zeitpunkt Privaturkunde;FEST;0;0;20;0
0;25104;Bescheinigung Tatsachen/Verhältnisse;FAKTOR;1,0;1,0;0;0
0;25200;Bescheinigung Registerblatt;FEST;0;0;15;0
0;25201;Rangbescheinigung;FAKTOR;0,3;0,3;0;0
0;25202;Teilhypotheken-/Grundschuld-/Rentenschuldbrief;FAKTOR;0,3;0,3;0;0
0;25203;Bescheinigung Recht Inland/Ausland;FAKTOR;0,3;1,0;0;0
0;25204;Abgabe Erklärung aufgrund Vollmacht;MANUELL;0;0;0;0
0;25205;Zuziehung zweiter Notar;MANUELL;0;0;0;0
0;25206;Gründungsprüfung AG;FAKTOR;1,0;1,0;1000;0
0;25207;Apostille/Legalisation;FEST;0;0;25;0
0;25208;Mehrere Legalisationen;FEST;0;0;50;0
0;25209;Einsicht Grundbuch/Register;FEST;0;0;15;0
0;25210;Abdruck Register/Grundbuch;FEST;0;0;10;0
0;25211;Beglaubigter Abdruck;FEST;0;0;15;0
0;25212;Elektronische Datei unbeglaubigt;FEST;0;0;5;0
0;25213;Elektronische Datei beglaubigt;FEST;0;0;10;0
0;25214;Bescheinigung 21 Abs. 3 BNotO;FEST;0;0;15;0
0;25300;Verwahrung Geldbeträge je Auszahlung;FAKTOR;1,0;1,0;0;0
0;25301;Verwahrung Wertpapiere/Kostbarkeiten;FAKTOR;1,0;1,0;0;0
0;26000;Zusatzgebühr Sonntag/Feiertag/Außenzeiten;FAKTOR;0,3;0,3;0;30
0;26001;Fremdsprachengebühr;FAKTOR;0,3;0,3;0;5000
0;26002;Tätigkeit außerhalb Geschäftsstelle;FEST;0;0;0;0
0;26003;Tätigkeit außerhalb Geschäftsstelle, besondere;FEST;0;0;50;0
0;31000;Dokumentenpauschale bis DIN A3;MANUELL;0;0;0;0
0;31001;Dokumentenpauschale besondere Fälle;MANUELL;0;0;0;0
0;31002;Überlassung elektronischer Dateien;MANUELL;0;0;0;0
0;31003;Kopien größer als DIN A3;MANUELL;0;0;0;0
0;31004;Post- und Telekommunikationsdienstleistungen;MANUELL;0;0;0;0
0;31005;Pauschale Post & Telekom;FEST;0;0;20;0
0;31006;Fahrtkosten eigenem Fahrzeug;MANUELL;0;0;0;0
0;31007;Fahrtkosten anderes Verkehrsmittel;MANUELL;0;0;0;0
0;31008;Tage- und Abwesenheitsgeld;MANUELL;0;0;0;0
0;31009;Sonstige Auslagen Geschäftsreise;MANUELL;0;0;0;0
0;32000;Dokumentenpauschale Notar bis DIN A3;MANUELL;0;0;0;0
0;32001;Dokumentenpauschale Notar besondere Fälle;MANUELL;0;0;0;0
0;32002;Elektronische Datei Notar;MANUELL;0;0;0;0
0;32003;Kopien größer DIN A3 Notar;MANUELL;0;0;0;0
0;32004;Post- und Telekommunikationsdienste Notar;MANUELL;0;0;0;0
0;32005;Pauschale Post & Telekom Notar;FEST;0;0;20;0
0;32006;Fahrtkosten eigenes KFZ Notar;MANUELL;0;0;0;0
0;32007;Fahrtkosten anderes Verkehrsmittel Notar;MANUELL;0;0;0;0
0;32008;Tage- und Abwesenheitsgeld Notar;MANUELL;0;0;0;0
0;32009;Sonstige Auslagen Notar;MANUELL;0;0;0;0
0;32010;Dolmetscher, Übersetzer, zweiter Notar;MANUELL;0;0;0;0
0;32011;Datenabruf JVKostG;MANUELL;0;0;0;0
0;32012;Haftpflichtversicherung auf Antrag;MANUELL;0;0;0;0
0;32013;Haftpflichtversicherung über 60 Mio;MANUELL;0;0;0;0
0;32014;Umsatzsteuer auf Kosten;MANUELL;0;0;0;0
0;32015;Sonstige Aufwendungen Notar;MANUELL;0;0;0;0
0;32016;Videokommunikationssystem BNotK;FEST;0;0;25;0'''

// --- CSV einlesen ---
tatbestaende = []
TAT_CSV.readLines().eachWithIndex { line, i ->
    if(i==0) return
    def parts = line.split(';')
    def parseDe = { s ->
        s = s.trim()
        return new BigDecimal(s.replace(',', '.'))
    }
    tatbestaende << [
        selected: parts[0].trim() == '1',
        kvNr: parts[1].trim(),
        kurz: parts[2].trim(),
        typ: parts[3].trim(),
        satzVon: parseDe(parts[4]),
        satzBis: parseDe(parts[5]),
        faktorWert: parseDe(parts[4]),
        min: parseDe(parts[6]),
        max: parseDe(parts[7]),
        gesamt: 0g,
        manuellBetrag: 0g
    ]
}

// --- Vorlagen ---
def vorlagen = [
    'Kaufvertrag + Grundschuld': ['21100','22110','22200','21200','22111','32005'],
    'Testament': ['21100','21200','26003'],
    'Vollmacht': ['21200','26003'],
    'Standard': ['21100','21201','22110']
]

// Basis-Zuordnung je Vorlage: KV-Nr → Geschäftswert-Typ, _default als Fallback
def vorlagenBasis = [
    'Kaufvertrag + Grundschuld': [
        '_default': 'KAUFPREIS',
        '21200': 'GRUNDSCHULD', '21201': 'GRUNDSCHULD',
        '22111': 'GRUNDSCHULD', '22120': 'GRUNDSCHULD', '22121': 'GRUNDSCHULD'
    ],
    'Testament': ['_default': 'TESTAMENT'],
    'Vollmacht': ['_default': 'VOLLMACHT'],
    'Standard': ['_default': 'KAUFPREIS']
]

aktiveBasisMap = vorlagenBasis['Kaufvertrag + Grundschuld']

def String basisFuer(String kvNr) {
    if(aktiveBasisMap.containsKey(kvNr)) return aktiveBasisMap[kvNr]
    return aktiveBasisMap['_default'] ?: 'KAUFPREIS'
}

// --- Geschäftswert-Hilfsfunktionen ---
def BigDecimal geschaeftswertTestament(BigDecimal aktiv, BigDecimal passiv) {
    if(aktiv < 0g) aktiv = 0g
    if(passiv < 0g) passiv = 0g
    BigDecimal maxAbzug = aktiv.divide(2g, 2, RoundingMode.HALF_UP)
    BigDecimal abzugsfaehig = (passiv <= maxAbzug) ? passiv : maxAbzug
    BigDecimal gw = aktiv - abzugsfaehig
    return (gw < 0g) ? 0g : gw
}

def BigDecimal geschaeftswertVollmacht(BigDecimal vermoegen) {
    if(vermoegen < 0g) vermoegen = 0g
    return vermoegen.divide(2g, 2, RoundingMode.HALF_UP)
}

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

// --- TableModel für Tatbestände ---
colNames = ['', 'KV-Nr', 'Tatbestand', 'Typ', 'Faktor', 'Min', 'Max', 'Betrag'] as String[]

class TatbestandTableModel extends AbstractTableModel {
    def data
    def colNames
    def df

    TatbestandTableModel(data, colNames, df) {
        this.data = data
        this.colNames = colNames
        this.df = df
    }

    int getRowCount() { data.size() }
    int getColumnCount() { colNames.length }
    String getColumnName(int col) { colNames[col] }

    Class getColumnClass(int col) {
        if(col == 0) return Boolean.class
        return String.class
    }

    Object getValueAt(int row, int col) {
        def t = data[row]
        switch(col) {
            case 0: return t.selected
            case 1: return t.kvNr
            case 2: return t.kurz
            case 3:
                if(t.typ == 'FAKTOR') return 'Faktor'
                if(t.typ == 'FEST') return 'fest'
                if(t.typ == 'MANUELL') return 'manuell'
                return t.typ
            case 4:
                if(t.typ == 'FAKTOR') return df.format(t.faktorWert)
                return ''
            case 5: return t.min > 0g ? df.format(t.min) : ''
            case 6: return t.max > 0g ? df.format(t.max) : ''
            case 7:
                if(t.typ == 'MANUELL') return df.format(t.manuellBetrag)
                return t.selected ? df.format(t.gesamt) : ''
            default: return ''
        }
    }

    boolean isCellEditable(int row, int col) {
        if(col == 0) return true
        if(col == 4) return data[row].typ == 'FAKTOR'
        if(col == 7) return data[row].typ == 'MANUELL'
        return false
    }

    void setValueAt(Object val, int row, int col) {
        def t = data[row]
        if(col == 0) {
            t.selected = val as boolean
            fireTableRowsUpdated(row, row)
        } else if(col == 4 && t.typ == 'FAKTOR') {
            try {
                def parsed = new BigDecimal(val.toString().replace(',', '.'))
                if(parsed < t.satzVon) parsed = t.satzVon
                if(parsed > t.satzBis) parsed = t.satzBis
                t.faktorWert = parsed
                fireTableRowsUpdated(row, row)
            } catch(Exception e) {}
        } else if(col == 7 && t.typ == 'MANUELL') {
            try {
                t.manuellBetrag = new BigDecimal(val.toString().replace(',', '.'))
                fireTableRowsUpdated(row, row)
            } catch(Exception e) {}
        }
    }
}

tatModel = new TatbestandTableModel(tatbestaende, colNames, df)

// --- UI ---
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
                                td {
                                    label(text: '   ')
                                }
                                td (colfill:true) {
                                    label(text: 'Aktivvermögen (Testament):')
                                }
                                td {
                                    txtAktiv=formattedTextField(format: betragFormat, text: '0,00', columns: 10, actionPerformed: {
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
                                td {
                                    label(text: '   ')
                                }
                                td (colfill:true) {
                                    label(text: 'Passivvermögen (Testament):')
                                }
                                td {
                                    txtPassiv=formattedTextField(format: betragFormat, text: '0,00', columns: 10, actionPerformed: {
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
                                td {
                                    label(text: '')
                                }
                                td {
                                    label(text: '   ')
                                }
                                td (colfill:true) {
                                    label(text: 'Vermögenswert (Vollmacht):')
                                }
                                td {
                                    txtVollmacht=formattedTextField(format: betragFormat, text: '0,00', columns: 10, actionPerformed: {
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

            // Vorlage
            tr {
                td (colfill:true) {
                    panel {
                        tableLayout (cellpadding: 5) {
                            tr {
                                td {
                                    label(text: 'Vorlage:')
                                }
                                td {
                                    cmbVorlage = comboBox(items: vorlagen.keySet() as List, actionPerformed: {
                                        def sel = cmbVorlage.selectedItem
                                        if(sel != null && vorlagen.containsKey(sel)) {
                                            def kvList = vorlagen[sel]
                                            tatbestaende.each { t -> t.selected = false }
                                            kvList.each { nr ->
                                                tatbestaende.each { t ->
                                                    if(t.kvNr == nr) {
                                                        t.selected = true
                                                        if(t.typ == 'FAKTOR') t.faktorWert = t.satzVon
                                                    }
                                                }
                                            }
                                            // Basis-Zuordnung je Vorlage
                                            aktiveBasisMap = vorlagenBasis[sel] ?: ['_default': 'KAUFPREIS']
                                            // Grundbuchkosten je nach Vorlage
                                            boolean mitGrundbuch = (sel == 'Kaufvertrag + Grundschuld')
                                            chkKV14110.selected = mitGrundbuch
                                            chkKV14150.selected = mitGrundbuch
                                            chkKV14152.selected = mitGrundbuch
                                            chkKV14121.selected = mitGrundbuch
                                            tatModel.fireTableDataChanged()
                                            calculate()
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }

            // Tatbestände-Tabelle
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Tatbestände / Auslagen (Notargebühren)')) {
                        borderLayout()
                        scrollPane(constraints: BL.CENTER, preferredSize: new Dimension(800, 300)) {
                            tatTable = table() {
                                current.model = tatModel
                                current.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
                                current.setRowHeight(22)
                                current.getColumnModel().getColumn(0).setPreferredWidth(30)
                                current.getColumnModel().getColumn(0).setMaxWidth(30)
                                current.getColumnModel().getColumn(1).setPreferredWidth(55)
                                current.getColumnModel().getColumn(1).setMaxWidth(70)
                                current.getColumnModel().getColumn(2).setPreferredWidth(320)
                                current.getColumnModel().getColumn(3).setPreferredWidth(60)
                                current.getColumnModel().getColumn(3).setMaxWidth(70)
                                current.getColumnModel().getColumn(4).setPreferredWidth(60)
                                current.getColumnModel().getColumn(5).setPreferredWidth(60)
                                current.getColumnModel().getColumn(6).setPreferredWidth(60)
                                current.getColumnModel().getColumn(7).setPreferredWidth(80)
                                // Spalten 4-7 rechtsbündig
                                def rightRenderer = new DefaultTableCellRenderer()
                                rightRenderer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT)
                                current.getColumnModel().getColumn(4).setCellRenderer(rightRenderer)
                                current.getColumnModel().getColumn(5).setCellRenderer(rightRenderer)
                                current.getColumnModel().getColumn(6).setCellRenderer(rightRenderer)
                                current.getColumnModel().getColumn(7).setCellRenderer(rightRenderer)
                            }
                        }
                    }
                }
            }

            // Grundbuchkosten
            tr {
                td (colfill:true) {
                    panel(border: titledBorder(title: 'Grundbuchkosten (ohne USt)')) {
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

// --- Trigger initial template selection ---
cmbVorlage.selectedItem = 'Kaufvertrag + Grundschuld'

def BigDecimal calculate() {
    GNotKgTablesRangeList rl = new GNotKgTablesRangeList()

    BigDecimal kaufpreis = betragFormat.parse(txtKaufpreis.text)
    BigDecimal grundschuld = betragFormat.parse(txtGrundschuld.text)
    BigDecimal aktiv = betragFormat.parse(txtAktiv.text)
    BigDecimal passiv = betragFormat.parse(txtPassiv.text)
    BigDecimal vollmachtVerm = betragFormat.parse(txtVollmacht.text)

    BigDecimal gwTestament = geschaeftswertTestament(aktiv, passiv)
    BigDecimal gwVollmacht = geschaeftswertVollmacht(vollmachtVerm)

    BigDecimal notarSumme = 0g

    tatbestaende.each { t ->
        if(!t.selected) {
            t.gesamt = 0g
            return
        }

        // Geschäftswert dynamisch anhand Vorlage bestimmen
        String basisTyp = basisFuer(t.kvNr)
        BigDecimal gw
        switch(basisTyp) {
            case 'GRUNDSCHULD': gw = grundschuld; break
            case 'TESTAMENT': gw = gwTestament; break
            case 'VOLLMACHT': gw = gwVollmacht; break
            default: gw = kaufpreis; break
        }

        if(t.typ == 'FAKTOR') {
            BigDecimal g = new BigDecimal(rl.berechneGebuehr(gw.floatValue(), t.faktorWert.floatValue()).toString())
            g = g.setScale(2, RoundingMode.HALF_UP)
            if(t.min > 0g && g < t.min) g = t.min
            if(t.max > 0g && g > t.max) g = t.max
            t.gesamt = g
        } else if(t.typ == 'FEST') {
            t.gesamt = t.min > 0g ? t.min : 0g
        } else {
            // MANUELL
            t.gesamt = t.manuellBetrag ?: 0g
        }

        notarSumme = notarSumme + t.gesamt
    }

    tatModel.fireTableDataChanged()

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
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(kaufpreis.floatValue(), 1.0f).toString())
        g = g.setScale(2, RoundingMode.HALF_UP)
        lblKV14110.text = df.format(g)
        grundbuchSumme = grundbuchSumme + g
    } else {
        lblKV14110.text = df.format(0f)
    }

    if(chkKV14150.isSelected()) {
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(kaufpreis.floatValue(), 0.5f).toString())
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
        BigDecimal g = new BigDecimal(rl.berechneGebuehr(grundschuld.floatValue(), 1.0f).toString())
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

    // Tatbestände dynamisch
    tatbestaende.each { t ->
        if(!t.selected || t.gesamt == 0g) return

        String faktorStr = ''
        String basisText = ''
        String basisTyp = basisFuer(t.kvNr)
        if(t.typ == 'FAKTOR') {
            faktorStr = faktorFormat.format(t.faktorWert.floatValue())
            if(basisTyp == 'GRUNDSCHULD') {
                basisText = ' - ' + txtGrundschuld.text + ' €'
            } else if(basisTyp == 'TESTAMENT') {
                basisText = ' - Testament-GW'
            } else if(basisTyp == 'VOLLMACHT') {
                basisText = ' - Vollmacht-GW'
            } else {
                basisText = ' - ' + txtKaufpreis.text + ' €'
            }
        }

        ct.addRow(faktorStr, t.kurz + " KV " + t.kvNr + " GNotKG" + basisText, df.format(t.gesamt) + " €");
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

    // Tatbestände dynamisch
    tatbestaende.each { t ->
        if(!t.selected || t.gesamt == 0g) return

        String desc = ''
        String basisTyp = basisFuer(t.kvNr)
        if(t.typ == 'FAKTOR') {
            String basisLabel
            String basisWert
            if(basisTyp == 'GRUNDSCHULD') {
                basisLabel = 'Grundschuld'
                basisWert = txtGrundschuld.text
            } else if(basisTyp == 'TESTAMENT') {
                basisLabel = 'Testament-GW'
                basisWert = ''
            } else if(basisTyp == 'VOLLMACHT') {
                basisLabel = 'Vollmacht-GW'
                basisWert = ''
            } else {
                basisLabel = 'Kaufpreis'
                basisWert = txtKaufpreis.text
            }
            if(basisWert) {
                desc = basisLabel + " " + basisWert + " €, Faktor " + faktorFormat.format(t.faktorWert.floatValue())
            } else {
                desc = basisLabel + ", Faktor " + faktorFormat.format(t.faktorWert.floatValue())
            }
        }

        if(desc) {
            positions.add(InvoiceUtils.invoicePosition(t.kurz + " KV " + t.kvNr + " GNotKG", desc, taxRate.floatValue(), t.gesamt.floatValue()));
        } else {
            positions.add(InvoiceUtils.invoicePosition(t.kurz + " KV " + t.kvNr + " GNotKG", taxRate.floatValue(), t.gesamt.floatValue()));
        }
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
