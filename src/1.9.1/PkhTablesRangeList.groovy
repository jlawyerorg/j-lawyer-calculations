import java.util.ArrayList
import PkhTablesRange

class PkhTablesRangeList { 
    ArrayList<PkhTablesRange> ranges
    
    PkhTablesRangeList() {
        ranges=new ArrayList<PkhTablesRange>()
        ranges.add(new PkhTablesRange(0,500,45))
        ranges.add(new PkhTablesRange(500,1000,80))
        ranges.add(new PkhTablesRange(1000,1500,115))
        ranges.add(new PkhTablesRange(1500,2000,150))
        ranges.add(new PkhTablesRange(2000,3000,201))
        ranges.add(new PkhTablesRange(3000,4000,252))
        ranges.add(new PkhTablesRange(4000,5000,303))
        ranges.add(new PkhTablesRange(5000,6000,354))
        ranges.add(new PkhTablesRange(6000,7000,405))
        ranges.add(new PkhTablesRange(7000,8000,456))
        ranges.add(new PkhTablesRange(8000,9000,507))
        ranges.add(new PkhTablesRange(9000,10000,558))
        ranges.add(new PkhTablesRange(10000,13000,604))
        ranges.add(new PkhTablesRange(13000,16000,650))
        ranges.add(new PkhTablesRange(16000,19000,696))
        ranges.add(new PkhTablesRange(19000,22000,742))
        ranges.add(new PkhTablesRange(22000,25000,788))
        ranges.add(new PkhTablesRange(25000,30000,863))
        ranges.add(new PkhTablesRange(30000,35000,938))
        ranges.add(new PkhTablesRange(35000,40000,1013))
        ranges.add(new PkhTablesRange(40000,45000,1088))
        ranges.add(new PkhTablesRange(45000,50000,1163))
        ranges.add(new PkhTablesRange(50000,65000,1248))
        ranges.add(new PkhTablesRange(65000,80000,1333))
        ranges.add(new PkhTablesRange(80000,95000,1418))
        ranges.add(new PkhTablesRange(95000,110000,1503))
        ranges.add(new PkhTablesRange(110000,125000,1588))
        ranges.add(new PkhTablesRange(125000,140000,1673))
        ranges.add(new PkhTablesRange(140000,155000,1758))
        ranges.add(new PkhTablesRange(155000,170000,1843))
        ranges.add(new PkhTablesRange(170000,185000,1928))
        ranges.add(new PkhTablesRange(185000,200000,2013))
        ranges.add(new PkhTablesRange(200000,230000,2133))
        ranges.add(new PkhTablesRange(230000,260000,2253))
        ranges.add(new PkhTablesRange(260000,290000,2373))
        ranges.add(new PkhTablesRange(290000,320000,2493))
        ranges.add(new PkhTablesRange(320000,350000,2613))
        ranges.add(new PkhTablesRange(350000,380000,2733))
        ranges.add(new PkhTablesRange(380000,410000,2853))
        ranges.add(new PkhTablesRange(410000,440000,2973))
        ranges.add(new PkhTablesRange(440000,470000,3093))
        ranges.add(new PkhTablesRange(470000,500000,3213))
    }
    
    ArrayList<PkhTablesRange> getRanges() {
        return ranges
    }
    
    float getMappedValue(float streitWert) {
           for(PkhTablesRange r: ranges) {
       if(r.contains(streitWert))
            return r.mappedValue
   }
   return -1f
    }
    
}
