import java.util.ArrayList
import GNotKgTablesRange

class GNotKgTablesRangeList {
    ArrayList<GNotKgTablesRange> ranges

    GNotKgTablesRangeList() {
        ranges=new ArrayList<GNotKgTablesRange>()
        // Gebuehrentabelle B (Anlage 2 zu § 34 Abs. 3 GNotKG)
        ranges.add(new GNotKgTablesRange(0,500,15))
        ranges.add(new GNotKgTablesRange(500,1000,19))
        ranges.add(new GNotKgTablesRange(1000,1500,23))
        ranges.add(new GNotKgTablesRange(1500,2000,27))
        ranges.add(new GNotKgTablesRange(2000,3000,33))
        ranges.add(new GNotKgTablesRange(3000,4000,39))
        ranges.add(new GNotKgTablesRange(4000,5000,45))
        ranges.add(new GNotKgTablesRange(5000,6000,51))
        ranges.add(new GNotKgTablesRange(6000,7000,57))
        ranges.add(new GNotKgTablesRange(7000,8000,63))
        ranges.add(new GNotKgTablesRange(8000,9000,69))
        ranges.add(new GNotKgTablesRange(9000,10000,75))
        ranges.add(new GNotKgTablesRange(10000,13000,83))
        ranges.add(new GNotKgTablesRange(13000,16000,91))
        ranges.add(new GNotKgTablesRange(16000,19000,99))
        ranges.add(new GNotKgTablesRange(19000,22000,107))
        ranges.add(new GNotKgTablesRange(22000,25000,115))
        ranges.add(new GNotKgTablesRange(25000,30000,125))
        ranges.add(new GNotKgTablesRange(30000,35000,135))
        ranges.add(new GNotKgTablesRange(35000,40000,145))
        ranges.add(new GNotKgTablesRange(40000,45000,155))
        ranges.add(new GNotKgTablesRange(45000,50000,165))
        ranges.add(new GNotKgTablesRange(50000,65000,192))
        ranges.add(new GNotKgTablesRange(65000,80000,219))
        ranges.add(new GNotKgTablesRange(80000,95000,246))
        ranges.add(new GNotKgTablesRange(95000,110000,273))
        ranges.add(new GNotKgTablesRange(110000,125000,300))
        ranges.add(new GNotKgTablesRange(125000,140000,327))
        ranges.add(new GNotKgTablesRange(140000,155000,354))
        ranges.add(new GNotKgTablesRange(155000,170000,381))
        ranges.add(new GNotKgTablesRange(170000,185000,408))
        ranges.add(new GNotKgTablesRange(185000,200000,435))
        ranges.add(new GNotKgTablesRange(200000,230000,485))
        ranges.add(new GNotKgTablesRange(230000,260000,535))
        ranges.add(new GNotKgTablesRange(260000,290000,585))
        ranges.add(new GNotKgTablesRange(290000,320000,635))
        ranges.add(new GNotKgTablesRange(320000,350000,685))
        ranges.add(new GNotKgTablesRange(350000,380000,735))
        ranges.add(new GNotKgTablesRange(380000,410000,785))
        ranges.add(new GNotKgTablesRange(410000,440000,835))
        ranges.add(new GNotKgTablesRange(440000,470000,885))
        ranges.add(new GNotKgTablesRange(470000,500000,935))
        ranges.add(new GNotKgTablesRange(500000,550000,1015))
        ranges.add(new GNotKgTablesRange(550000,600000,1095))
        ranges.add(new GNotKgTablesRange(600000,650000,1175))
        ranges.add(new GNotKgTablesRange(650000,700000,1255))
        ranges.add(new GNotKgTablesRange(700000,750000,1335))
        ranges.add(new GNotKgTablesRange(750000,800000,1415))
        ranges.add(new GNotKgTablesRange(800000,850000,1495))
        ranges.add(new GNotKgTablesRange(850000,900000,1575))
        ranges.add(new GNotKgTablesRange(900000,950000,1655))
        ranges.add(new GNotKgTablesRange(950000,1000000,1735))
    }

    ArrayList<GNotKgTablesRange> getRanges() {
        return ranges
    }

    float getMappedValue(float geschaeftsWert) {
        for(GNotKgTablesRange r: ranges) {
            if(r.contains(geschaeftsWert))
                return r.mappedValue
        }
        // Ueber 1.000.000 EUR: je angefangene 50.000 EUR zusaetzlich 75 EUR
        // (§ 34 Abs. 3 GNotKG, Tabelle B)
        if(geschaeftsWert > 1000000) {
            float ueber = geschaeftsWert - 1000000
            int stufen = (int) Math.ceil(ueber / 50000.0)
            return 1735 + stufen * 75
        }
        return -1f
    }

    float berechneGebuehr(float geschaeftsWert, float faktor) {
        float basisGebuehr = getMappedValue(geschaeftsWert)
        if(basisGebuehr < 0) return 0f
        return basisGebuehr * faktor
    }
}
