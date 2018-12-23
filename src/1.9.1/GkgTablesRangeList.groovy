import java.util.ArrayList
import GkgTablesRange

class GkgTablesRangeList { 
    ArrayList<GkgTablesRange> ranges
    
    GkgTablesRangeList() {
        ranges=new ArrayList<GkgTablesRange>()
        ranges.add(new GkgTablesRange(0,500,35))
        ranges.add(new GkgTablesRange(500,1000,53))
        ranges.add(new GkgTablesRange(1000,1500,71))
        ranges.add(new GkgTablesRange(1500,2000,89))
        ranges.add(new GkgTablesRange(2000,3000,108))
        ranges.add(new GkgTablesRange(3000,4000,127))
        ranges.add(new GkgTablesRange(4000,5000,146))
        ranges.add(new GkgTablesRange(5000,6000,165))
        ranges.add(new GkgTablesRange(6000,7000,184))
        ranges.add(new GkgTablesRange(7000,8000,203))
        ranges.add(new GkgTablesRange(8000,9000,222))
        ranges.add(new GkgTablesRange(9000,10000,241))
        ranges.add(new GkgTablesRange(10000,13000,267))
        ranges.add(new GkgTablesRange(13000,16000,293))
        ranges.add(new GkgTablesRange(16000,19000,319))
        ranges.add(new GkgTablesRange(19000,22000,345))
        ranges.add(new GkgTablesRange(22000,25000,371))
        ranges.add(new GkgTablesRange(25000,30000,406))
        ranges.add(new GkgTablesRange(30000,35000,441))
        
        ranges.add(new GkgTablesRange(35000,40000,476))
        ranges.add(new GkgTablesRange(40000,45000,511))
        ranges.add(new GkgTablesRange(45000,50000,546))
        ranges.add(new GkgTablesRange(50000,65000,666))
        ranges.add(new GkgTablesRange(65000,80000,786))
        ranges.add(new GkgTablesRange(80000,95000,906))
        ranges.add(new GkgTablesRange(95000,110000,1026))
        ranges.add(new GkgTablesRange(110000,125000,1146))
        ranges.add(new GkgTablesRange(125000,140000,1266))
        ranges.add(new GkgTablesRange(140000,155000,1386))
        ranges.add(new GkgTablesRange(155000,170000,1506))
        ranges.add(new GkgTablesRange(170000,185000,1626))
        ranges.add(new GkgTablesRange(185000,200000,1746))
        ranges.add(new GkgTablesRange(200000,230000,1925))
        ranges.add(new GkgTablesRange(230000,260000,2104))
        ranges.add(new GkgTablesRange(260000,290000,2283))
        ranges.add(new GkgTablesRange(290000,320000,2462))
        ranges.add(new GkgTablesRange(320000,350000,2641))
        ranges.add(new GkgTablesRange(350000,380000,2820))
        ranges.add(new GkgTablesRange(380000,410000,2999))
        ranges.add(new GkgTablesRange(410000,440000,3178))
        ranges.add(new GkgTablesRange(440000,470000,3357))
        ranges.add(new GkgTablesRange(470000,500000,3536))
        
    }
    
    ArrayList<GkgTablesRange> getRanges() {
        return ranges
    }
    
    float getMappedValue(float streitWert) {
           for(GkgTablesRange r: ranges) {
       if(r.contains(streitWert))
            return r.mappedValue
   }
   return -1f
    }
    
}



