import java.util.ArrayList
import RvgTablesRange

class RvgTablesRangeList { 
    ArrayList<RvgTablesRange> ranges
    
    RvgTablesRangeList() {
        ranges=new ArrayList<RvgTablesRange>()
        ranges.add(new RvgTablesRange(0,500,45))
        ranges.add(new RvgTablesRange(500,1000,80))
        ranges.add(new RvgTablesRange(1000,1500,115))
        ranges.add(new RvgTablesRange(1500,2000,150))
        ranges.add(new RvgTablesRange(2000,3000,201))
        ranges.add(new RvgTablesRange(3000,4000,252))
        ranges.add(new RvgTablesRange(4000,5000,303))
        ranges.add(new RvgTablesRange(5000,6000,354))
        ranges.add(new RvgTablesRange(6000,7000,405))
        ranges.add(new RvgTablesRange(7000,8000,456))
        ranges.add(new RvgTablesRange(8000,9000,507))
        ranges.add(new RvgTablesRange(9000,10000,558))
        ranges.add(new RvgTablesRange(10000,13000,604))
        ranges.add(new RvgTablesRange(13000,16000,650))
        ranges.add(new RvgTablesRange(16000,19000,696))
        ranges.add(new RvgTablesRange(19000,22000,742))
        ranges.add(new RvgTablesRange(22000,25000,788))
        ranges.add(new RvgTablesRange(25000,30000,863))
        ranges.add(new RvgTablesRange(30000,35000,938))
        ranges.add(new RvgTablesRange(35000,40000,1013))
        ranges.add(new RvgTablesRange(40000,45000,1088))
        ranges.add(new RvgTablesRange(45000,50000,1163))
        ranges.add(new RvgTablesRange(50000,65000,1248))
        ranges.add(new RvgTablesRange(65000,80000,1333))
        ranges.add(new RvgTablesRange(80000,95000,1418))
        ranges.add(new RvgTablesRange(95000,110000,1503))
        ranges.add(new RvgTablesRange(110000,125000,1588))
        ranges.add(new RvgTablesRange(125000,140000,1673))
        ranges.add(new RvgTablesRange(140000,155000,1758))
        ranges.add(new RvgTablesRange(155000,170000,1843))
        ranges.add(new RvgTablesRange(170000,185000,1928))
        ranges.add(new RvgTablesRange(185000,200000,2013))
        ranges.add(new RvgTablesRange(200000,230000,2133))
        ranges.add(new RvgTablesRange(230000,260000,2253))
        ranges.add(new RvgTablesRange(260000,290000,2373))
        ranges.add(new RvgTablesRange(290000,320000,2493))
        ranges.add(new RvgTablesRange(320000,350000,2613))
        ranges.add(new RvgTablesRange(350000,380000,2733))
        ranges.add(new RvgTablesRange(380000,410000,2853))
        ranges.add(new RvgTablesRange(410000,440000,2973))
        ranges.add(new RvgTablesRange(440000,470000,3093))
        ranges.add(new RvgTablesRange(470000,500000,3213))
    }
    
    ArrayList<RvgTablesRange> getRanges() {
        return ranges
    }
    
    float getMappedValue(float streitWert) {
           for(RvgTablesRange r: ranges) {
       if(r.contains(streitWert))
            return r.mappedValue
   }
   return -1f
    }
    
}
